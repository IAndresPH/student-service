<template>
  <div class="student-manager">
    <h1>Student Manager</h1>

    <section class="config">
      <label>
        API Base URL:
        <input v-model="baseUrl" placeholder="http://localhost:8080" />
      </label>
      <button @click="loadAll">Load Students</button>
    </section>

    <section class="form">
      <h2>{{ editId ? 'Update Student' : 'Create Student' }}</h2>
      <form @submit.prevent="submit">
        <div class="grid">
          <label>
            Code
            <input v-model.trim="form.code" maxlength="30" required />
          </label>
          <label>
            Semester
            <input v-model.number="form.semester" type="number" min="1" required />
          </label>
          <label>
            Career
            <input v-model.trim="form.career" maxlength="80" required />
          </label>
          <label>
            GPA
            <input v-model.number="form.gpa" type="number" step="0.1" min="0" max="5" required />
          </label>
        </div>

        <div class="actions">
          <button type="submit">{{ editId ? 'Save' : 'Create' }}</button>
          <button type="button" @click="reset">Clear</button>
        </div>
      </form>

      <p v-if="error" class="error">{{ error }}</p>
      <p v-if="success" class="success">{{ success }}</p>
    </section>

    <section class="list">
      <h2>Students</h2>
      <table>
        <thead>
        <tr>
          <th>ID</th>
          <th>Code</th>
          <th>Semester</th>
          <th>Career</th>
          <th>GPA</th>
          <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="s in students" :key="s.id">
          <td>{{ s.id }}</td>
          <td>{{ s.code }}</td>
          <td>{{ s.semester }}</td>
          <td>{{ s.career }}</td>
          <td>{{ s.gpa }}</td>
          <td class="row-actions">
            <button @click="startEdit(s)">Edit</button>
            <button class="danger" @click="remove(s.id)">Delete</button>
          </td>
        </tr>
        <tr v-if="!students.length">
          <td colspan="6" class="empty">No data</td>
        </tr>
        </tbody>
      </table>
    </section>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const baseUrl = ref(localStorage.getItem('student_api_base') || 'http://localhost:8080')
const students = ref([])
const error = ref('')
const success = ref('')
const editId = ref(null)

const form = ref({
  code: '',
  semester: 1,
  career: '',
  gpa: 0.0
})

function api(path) {
  const url = `${baseUrl.value.replace(/\/$/, '')}/api/v1/students${path}`
  return url
}

async function request(method, path, body) {
  error.value = ''
  success.value = ''
  try {
    const res = await fetch(api(path), {
      method,
      headers: { 'Content-Type': 'application/json' },
      body: body ? JSON.stringify(body) : undefined
    })
    if (!res.ok) {
      const text = await res.text().catch(() => '')
      throw new Error(text || `${res.status} ${res.statusText}`)
    }
    if (res.status !== 204) {
      return await res.json()
    }
    return null
  } catch (e) {
    error.value = e.message
    throw e
  }
}

async function loadAll() {
  localStorage.setItem('student_api_base', baseUrl.value)
  const data = await request('GET', '')
  students.value = Array.isArray(data.content) ? data.content : (Array.isArray(data) ? data : [])
}

async function submit() {
  const payload = { ...form.value }
  if (editId.value) {
    const updated = await request('PUT', `/${editId.value}`, payload)
    success.value = 'Updated successfully'
    replaceInList(updated)
  } else {
    const created = await request('POST', '', payload)
    success.value = 'Created successfully'
    students.value.unshift(created)
  }
  reset(false)
}

function replaceInList(updated) {
  const idx = students.value.findIndex(s => s.id === updated.id)
  if (idx >= 0) students.value[idx] = updated
}

function startEdit(s) {
  editId.value = s.id
  form.value = {
    code: s.code,
    semester: s.semester,
    career: s.career,
    gpa: s.gpa
  }
  success.value = ''
  error.value = ''
  window.scrollTo({ top: 0, behavior: 'smooth' })
}

async function remove(id) {
  if (!confirm('Delete this student?')) return
  await request('DELETE', `/${id}`)
  students.value = students.value.filter(s => s.id !== id)
  success.value = 'Deleted successfully'
}

function reset(clearMessages = true) {
  editId.value = null
  form.value = { code: '', semester: 1, career: '', gpa: 0.0 }
  if (clearMessages) { error.value = ''; success.value = '' }
}
</script>

<style scoped>
.student-manager { max-width: 960px; margin: 24px auto; padding: 16px; font-family: ui-sans-serif, system-ui, -apple-system, Segoe UI, Roboto, Ubuntu, Cantarell, 'Helvetica Neue', Arial, 'Noto Sans', 'Apple Color Emoji', 'Segoe UI Emoji'; }
h1 { margin: 0 0 12px; }
section { margin: 16px 0; }
.config input { width: 360px; }
.form .grid { display: grid; grid-template-columns: repeat(2, minmax(200px, 1fr)); gap: 12px; }
.form input { width: 100%; padding: 8px; }
.actions { margin-top: 12px; display: flex; gap: 8px; }
button { cursor: pointer; padding: 8px 12px; border: 1px solid #ccc; border-radius: 6px; background: #f7f7f7; }
button:hover { background: #eee; }
button.danger { border-color: #e57373; color: #c62828; }
table { width: 100%; border-collapse: collapse; }
th, td { border-bottom: 1px solid #eee; padding: 8px; text-align: left; }
.empty { text-align: center; color: #888; }
.error { color: #b71c1c; }
.success { color: #1b5e20; }
.row-actions { display: flex; gap: 8px; }
</style>
