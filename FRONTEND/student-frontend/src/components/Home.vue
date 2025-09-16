<template>
  <section class="home">
    <h1>University Portal</h1>
    <nav>
      <router-link to="/students">📋 Gestionar Estudiantes</router-link>
      <router-link to="/grades">📊 Consultar Notas</router-link>
    </nav>
  </section>
</template>

<script setup>
</script>

<style scoped>
.home {
  display: flex;
  flex-direction: column;
  gap: 16px;
  align-items: center;
  justify-content: center;
  height: 100%;
}
nav {
  display: flex;
  gap: 20px;
}
nav a {
  padding: 10px 14px;
  border: 1px solid #ccc;
  border-radius: 6px;
  text-decoration: none;
  color: #333;
  font-weight: 600;
}
nav a.router-link-exact-active {
  background: #f5f5f5;
  border-color: #999;
}
</style>
