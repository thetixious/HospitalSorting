<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';

const router = useRouter();

const username = ref('');
const password = ref('');
const errorMessage = ref('');

const handleLogin = async () => {
  try {
    const response = await axios.post('http://localhost:8080/auth/logIn', {
      username: username.value,
      password: password.value
    });

    // Предполагаем, что сервер возвращает объект с пользователем и токеном
    const { position, token } = response.data;

    // Сохраняем токен в localStorage
    localStorage.setItem('authToken', token);

    // Сохраняем роль
    localStorage.setItem('userRole', position);

    // Редирект по роли
    if (position === 'doctor') {
      await router.push('/doctor');
    } else if (position === 'nurse') {
      await router.push('/nurse');
    }

  } catch (error) {
    errorMessage.value = error.response?.data?.message || 'Ошибка авторизации';
  }
};
</script>

<template>
  <div class="auth-container">
    <h2>Вход в систему</h2>

    <form @submit.prevent="handleLogin">
      <div class="form-group">
        <label>Логин:</label>
        <input v-model="username" type="text" required>
      </div>

      <div class="form-group">
        <label>Пароль:</label>
        <input v-model="password" type="password" required>
      </div>

      <button type="submit">Войти</button>

      <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
    </form>
  </div>
</template>

<style scoped>
.auth-container {
  max-width: 400px;
  margin: 2rem auto;
  padding: 2rem;
  border: 1px solid #ddd;
  border-radius: 8px;
}

.form-group {
  margin-bottom: 1rem;
}

label {
  display: block;
  margin-bottom: 0.5rem;
}

input {
  width: 100%;
  padding: 0.5rem;
}

button {
  width: 100%;
  padding: 0.75rem;
  background: #2196F3;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.error {
  color: red;
  margin-top: 1rem;
}
</style>