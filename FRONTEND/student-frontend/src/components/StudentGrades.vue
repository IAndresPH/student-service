<template>
  <section>
    <h2>Student Grades</h2>
    <form @submit.prevent="load">
      <label>Student ID
        <input v-model.number="studentId" type="number" min="1" required />
      </label>
      <button>Load</button>
    </form>

    <div v-if="error" class="error">{{ error }}</div>

    <div v-if="summary">
      <div class="cards">
        <div class="card">
          <h3>My Average</h3>
          <div class="kpi">{{ summary.myAverage ?? '—' }}</div>
        </div>
        <div class="card">
          <h3>Group Average</h3>
          <div class="kpi">{{ summary.groupAverage ?? '—' }}</div>
        </div>
      </div>

      <table>
        <thead>
          <tr>
            <th>Subject</th>
            <th>My Score</th>
            <th>Group Avg</th>
            <th>Rank</th>
            <th>Population</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="s in summary.subjects" :key="s.subject">
            <td>{{ s.subject }}</td>
            <td>{{ s.myScore }}</td>
            <td>{{ s.groupAverage ?? '—' }}</td>
            <td>{{ s.rank ?? '—' }}</td>
            <td>{{ s.total ?? '—' }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </section>
</template>

<script setup>
import { ref } from 'vue'

const studentId = ref()
const summary = ref(null)
const error = ref('')

const BASE = (import.meta.env.VITE_API_BASE || '/api').replace(/\/$/, '')

async function load() {
  error.value = ''
  summary.value = null
  try {
    const res = await fetch(`${BASE}/v1/students/${studentId.value}/grades/summary`)
    if (!res.ok) throw new Error(`${res.status} ${res.statusText}`)
    summary.value = await res.json()
  } catch (e) {
    error.value = e.message
  }
}
</script>

<style scoped>
form{display:flex;gap:8px;align-items:center;margin:8px 0}
input{padding:6px}
button{padding:6px 10px}
.cards{display:flex;gap:12px;margin:12px 0}
.card{border:1px solid #eee;border-radius:8px;padding:12px;background:#fff}
.kpi{font-size:24px;font-weight:700}
table{width:100%;border-collapse:collapse}
th,td{border-bottom:1px solid #eee;padding:8px;text-align:left}
.error{color:#b71c1c;margin-top:8px}
</style>
