import Database from "better-sqlite3";
import path from "path";

const DB_PATH = process.env.DB_PATH || path.join(__dirname, "..", "data.db");

let db: Database.Database;

export function getDb(): Database.Database {
  if (!db) {
    db = new Database(DB_PATH);
    db.pragma("journal_mode = WAL");
    initSchema(db);
  }
  return db;
}

function initSchema(db: Database.Database): void {
  db.exec(`
    CREATE TABLE IF NOT EXISTS Contact (
      id              INTEGER PRIMARY KEY AUTOINCREMENT,
      phoneNumber     TEXT,
      email           TEXT,
      linkedId        INTEGER,
      linkPrecedence  TEXT NOT NULL CHECK(linkPrecedence IN ('primary', 'secondary')),
      createdAt       DATETIME NOT NULL DEFAULT (datetime('now')),
      updatedAt       DATETIME NOT NULL DEFAULT (datetime('now')),
      deletedAt       DATETIME,
      FOREIGN KEY (linkedId) REFERENCES Contact(id)
    );

    CREATE INDEX IF NOT EXISTS idx_contact_email       ON Contact(email);
    CREATE INDEX IF NOT EXISTS idx_contact_phone       ON Contact(phoneNumber);
    CREATE INDEX IF NOT EXISTS idx_contact_linkedId    ON Contact(linkedId);
  `);
}
