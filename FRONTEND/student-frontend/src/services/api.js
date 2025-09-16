const BASE = (import.meta.env.VITE_API_BASE || '/api').replace(/\/$/, '')

async function request(method, path, body) {
  const res = await fetch(`${BASE}${path}`, {
    method,
    headers: { 'Content-Type': 'application/json' },
    body: body ? JSON.stringify(body) : undefined
  })
  if (!res.ok) {
    const text = await res.text().catch(() => '')
    throw new Error(text || `${res.status} ${res.statusText}`)
  }
  if (res.status !== 204) return res.json()
  return null
}

export function listStudents() { return request('GET', '/v1/students') }
export function createStudent(payload) { return request('POST', '/v1/students', payload) }
export function updateStudent(id, payload) { return request('PUT', `/v1/students/${id}`, payload) }
export function deleteStudent(id) { return request('DELETE', `/v1/students/${id}`) }
export function getStudent(id) { return request('GET', `/v1/students/${id}`) }
