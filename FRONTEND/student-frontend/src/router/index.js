import { createRouter, createWebHistory } from 'vue-router'
import Home from '../components/Home.vue'
import StudentManager from '../components/StudentManager.vue'

const routes = [
  { path: '/', component: Home },
  { path: '/students', component: StudentManager }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
