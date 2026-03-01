# Bitespeed Identity Reconciliation

A web service that identifies and consolidates customer contacts across multiple purchases using different email/phone combinations.

## 🚀 Live Endpoint

```
POST https://<your-render-url>/identify
```

## 📋 API

### `POST /identify`

**Request body** (JSON):
```json
{
  "email": "lorraine@hillvalley.edu",
  "phoneNumber": "123456"
}
```
At least one of `email` or `phoneNumber` must be provided.

**Response** (`200 OK`):
```json
{
  "contact": {
    "primaryContatctId": 1,
    "emails": ["lorraine@hillvalley.edu", "mcfly@hillvalley.edu"],
    "phoneNumbers": ["123456"],
    "secondaryContactIds": [23]
  }
}
```

## 🛠️ Local Setup

```bash
# Install dependencies
npm install

# Run in development mode
npm run dev

# Build and run in production
npm run build
npm start
```

The server starts on port `3000` by default (override with `PORT` env var).

## 🗄️ Tech Stack

- **Runtime**: Node.js + TypeScript
- **Framework**: Express
- **Database**: SQLite via `better-sqlite3` (zero-config, file-based)

## 🌐 Deploy to Render.com

1. Push this repo to GitHub.
2. Create a new **Web Service** on [render.com](https://render.com).
3. Connect your GitHub repo.
4. Set the following:
   - **Build Command**: `npm install && npm run build`
   - **Start Command**: `npm start`
5. Optionally set `DB_PATH=/data/data.db` and add a **persistent disk** at `/data` so your SQLite DB survives redeploys.

## 🔑 Key Logic

| Scenario | Behaviour |
|---|---|
| No existing contacts | Create new primary contact |
| Match found, no new info | Return existing consolidated contact |
| Match found, new info | Create new secondary contact linked to primary |
| Two separate primaries linked by request | Demote the newer primary → secondary; re-link its secondaries |
