import { createRouter, createWebHistory } from 'vue-router'
import Home from '../components/Home.vue'
import StudentManager from '../components/StudentManager.vue'
import StudentGrades from '../components/StudentGrades.vue'

const routes = [
  { path: '/', component: Home },
  { path: '/students', component: StudentManager },
  { path: '/grades', component: StudentGrades }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})
export default router
