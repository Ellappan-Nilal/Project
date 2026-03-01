import express, { Request, Response, NextFunction } from "express";
import { getDb } from "./db";
import { identify } from "./identify";
import { IdentifyRequest } from "./types";

const app = express();
app.use(express.json());

// ── Health check ──────────────────────────────────────────────────────────────
app.get("/", (_req: Request, res: Response) => {
  res.json({ status: "ok", message: "Bitespeed Identity Reconciliation Service" });
});

// ── /identify endpoint ────────────────────────────────────────────────────────
app.post("/identify", (req: Request, res: Response, next: NextFunction) => {
  try {
    const body = req.body as IdentifyRequest;

    const email = body.email ?? null;
    const phoneNumber = body.phoneNumber ?? null;

    if (!email && !phoneNumber) {
      res.status(400).json({
        error: "At least one of 'email' or 'phoneNumber' must be provided.",
      });
      return;
    }

    const db = getDb();
    const result = identify(db, { email, phoneNumber });

    res.status(200).json(result);
  } catch (err) {
    next(err);
  }
});

// ── Global error handler ──────────────────────────────────────────────────────
app.use((err: Error, _req: Request, res: Response, _next: NextFunction) => {
  console.error(err);
  res.status(500).json({ error: err.message ?? "Internal server error" });
});

// ── Start server ──────────────────────────────────────────────────────────────
const PORT = parseInt(process.env.PORT ?? "3000", 10);
app.listen(PORT, () => {
  console.log(`🚀 Server running on port ${PORT}`);
});

export default app;
