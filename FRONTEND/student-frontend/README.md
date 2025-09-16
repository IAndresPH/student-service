# Student Frontend (Vue 3 + Vite)

## Dev
```bash
npm install
npm run dev
```

The dev server proxies `/api` to `http://localhost:8080` (see `vite.config.js`). Your backend endpoints:
- `GET    /api/v1/students`
- `POST   /api/v1/students`
- `PUT    /api/v1/students/{id}`
- `DELETE /api/v1/students/{id}`

Change the API base via `.env.*` files using `VITE_API_BASE`.
