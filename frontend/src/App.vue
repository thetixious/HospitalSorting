<script setup>
import Auth from './components/AuthComponent.vue'
import DoctorComponent from "@/components/DoctorComponent.vue";
import NurseComponent from "@/components/NurseComponent.vue";
</script>

<template>

  <div id="app">

      <Auth class="auth-container"></Auth>

  </div>

</template>

<style scoped>
header {
  line-height: 1.5;
  display: flex;
  align-items: center; /* Центрирование по вертикали */
  justify-content: center;
}

.logo {
  display: block;
  margin: 0 auto 2rem;
}

@media (min-width: 1024px) {

  header {
    display: flex;
    place-items: center;
    padding-right: calc(var(--section-gap) / 2);
  }

  .logo {
    margin: 0 2rem 0 0;
  }

  header .wrapper {
    display: flex;
    place-items: flex-start;
    flex-wrap: wrap;
  }
  #app {
    height: 100%;
    width: 100%;
  }

  html, body {
    height: 100%;
    margin: 0;
    padding: 0;
  }
}
</style>
