import Database from "better-sqlite3";
import { Contact, IdentifyRequest, IdentifyResponse } from "./types";

/**
 * Fetch all contacts that match the given email or phoneNumber (excluding deleted).
 */
function findMatchingContacts(
  db: Database.Database,
  email: string | null | undefined,
  phoneNumber: string | null | undefined
): Contact[] {
  const conditions: string[] = [];
  const params: (string | null)[] = [];

  if (email) {
    conditions.push("email = ?");
    params.push(email);
  }
  if (phoneNumber) {
    conditions.push("phoneNumber = ?");
    params.push(phoneNumber);
  }

  if (conditions.length === 0) return [];

  const query = `
    SELECT * FROM Contact
    WHERE deletedAt IS NULL AND (${conditions.join(" OR ")})
    ORDER BY createdAt ASC
  `;
  return db.prepare(query).all(...params) as Contact[];
}

/**
 * Given any contact (primary or secondary), resolve its primary contact.
 */
function resolvePrimary(db: Database.Database, contact: Contact): Contact {
  if (contact.linkPrecedence === "primary") return contact;
  const primary = db
    .prepare("SELECT * FROM Contact WHERE id = ? AND deletedAt IS NULL")
    .get(contact.linkedId) as Contact | undefined;
  if (!primary) {
    // Orphaned secondary — treat self as primary
    return contact;
  }
  return primary;
}

/**
 * Collect all contacts belonging to a primary (itself + all secondaries).
 */
function getCluster(db: Database.Database, primaryId: number): Contact[] {
  return db
    .prepare(
      `SELECT * FROM Contact
       WHERE deletedAt IS NULL AND (id = ? OR linkedId = ?)
       ORDER BY createdAt ASC`
    )
    .all(primaryId, primaryId) as Contact[];
}

/**
 * Build the response payload from a cluster of contacts.
 */
function buildResponse(
  primary: Contact,
  cluster: Contact[]
): IdentifyResponse {
  const emails: string[] = [];
  const phoneNumbers: string[] = [];
  const secondaryIds: number[] = [];

  // Primary first
  if (primary.email) emails.push(primary.email);
  if (primary.phoneNumber) phoneNumbers.push(primary.phoneNumber);

  for (const c of cluster) {
    if (c.id === primary.id) continue;
    secondaryIds.push(c.id);
    if (c.email && !emails.includes(c.email)) emails.push(c.email);
    if (c.phoneNumber && !phoneNumbers.includes(c.phoneNumber))
      phoneNumbers.push(c.phoneNumber);
  }

  return {
    contact: {
      primaryContatctId: primary.id,
      emails,
      phoneNumbers,
      secondaryContactIds: secondaryIds,
    },
  };
}

/**
 * Main identify function — all logic runs inside a transaction.
 */
export function identify(
  db: Database.Database,
  req: IdentifyRequest
): IdentifyResponse {
  const email = req.email?.trim() || null;
  const phoneNumber = req.phoneNumber?.toString().trim() || null;

  if (!email && !phoneNumber) {
    throw new Error("At least one of email or phoneNumber must be provided.");
  }

  return db.transaction(() => {
    const matches = findMatchingContacts(db, email, phoneNumber);

    // ── Case 1: No matches → create new primary contact ──────────────────
    if (matches.length === 0) {
      const stmt = db.prepare(`
        INSERT INTO Contact (phoneNumber, email, linkedId, linkPrecedence, createdAt, updatedAt)
        VALUES (?, ?, NULL, 'primary', datetime('now'), datetime('now'))
      `);
      const result = stmt.run(phoneNumber, email);
      const newContact = db
        .prepare("SELECT * FROM Contact WHERE id = ?")
        .get(result.lastInsertRowid) as Contact;

      return buildResponse(newContact, [newContact]);
    }

    // ── Resolve all primaries from matched contacts ───────────────────────
    const primarySet = new Map<number, Contact>();
    for (const m of matches) {
      const p = resolvePrimary(db, m);
      primarySet.set(p.id, p);
    }

    // The oldest primary wins
    const sortedPrimaries = [...primarySet.values()].sort(
      (a, b) => new Date(a.createdAt).getTime() - new Date(b.createdAt).getTime()
    );
    const truePrimary = sortedPrimaries[0];

    // ── Case 2: Multiple primaries found → merge newer ones into oldest ───
    if (sortedPrimaries.length > 1) {
      for (let i = 1; i < sortedPrimaries.length; i++) {
        const demoted = sortedPrimaries[i];
        // Demote this primary → secondary
        db.prepare(`
          UPDATE Contact
          SET linkedId = ?, linkPrecedence = 'secondary', updatedAt = datetime('now')
          WHERE id = ?
        `).run(truePrimary.id, demoted.id);

        // Re-link all of demoted's secondaries to the true primary
        db.prepare(`
          UPDATE Contact
          SET linkedId = ?, updatedAt = datetime('now')
          WHERE linkedId = ? AND deletedAt IS NULL
        `).run(truePrimary.id, demoted.id);
      }
    }

    // ── Case 3: Check if incoming info is truly new → create secondary ────
    const cluster = getCluster(db, truePrimary.id);

    const emailExists = !email || cluster.some((c) => c.email === email);
    const phoneExists =
      !phoneNumber || cluster.some((c) => c.phoneNumber === phoneNumber);

    if (!emailExists || !phoneExists) {
      // New information — create a secondary contact
      db.prepare(`
        INSERT INTO Contact (phoneNumber, email, linkedId, linkPrecedence, createdAt, updatedAt)
        VALUES (?, ?, ?, 'secondary', datetime('now'), datetime('now'))
      `).run(phoneNumber, email, truePrimary.id);
    }

    // Re-fetch updated cluster
    const finalCluster = getCluster(db, truePrimary.id);
    const finalPrimary = db
      .prepare("SELECT * FROM Contact WHERE id = ?")
      .get(truePrimary.id) as Contact;

    return buildResponse(finalPrimary, finalCluster);
  })();
}
