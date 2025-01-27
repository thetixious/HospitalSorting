<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';
import doctor from '../components/DoctorComponent.vue';
import nurse from '../components/NurseComponent.vue';

const router = useRouter();
// Form data references
const username = ref('');
const password = ref('');
const confirmPassword = ref('');
const errorMessage = ref('');
const successMessage = ref('');

const registerUser = async () => {
  errorMessage.value = '';
  successMessage.value = '';

  if (password.value !== confirmPassword.value) {
    errorMessage.value = 'Passwords do not match';
    return;
  }

  try {
    const response = await axios.post('http://localhost:8080/auth/logIn', {
      username: username.value,
      password: password.value,
    });

    if (response.status === 200) {
      successMessage.value = 'Registration successful!';
      const role = response.data; // Получаем роль из ответа сервера

      if (role === 'doctor') {
        await router.push('/doctor'); // Редирект на страницу врача

      } else if (role === 'nurse') {
        await router.push('/nurse'); // Редирект на страницу медсестры
      } else {
        successMessage.value = 'Unknown role, login successful!';
      }

    }
  } catch (error) {
    errorMessage.value = error.response?.data?.message || 'An error occurred during registration.';
  }
};
</script>

<template>
  <div class="registration-container">
    <h2>Sign In</h2>

    <form @submit.prevent="registerUser" class="registration-form">
      <div class="form-group">
        <label for="username">Username</label>
        <input id="username" v-model="username" type="text" required placeholder="Enter your username" />
      </div>

      <div class="form-group">
        <label for="password">Password</label>
        <input id="password" v-model="password" type="password" required placeholder="Enter your password" />
      </div>

      <div class="form-group">
        <label for="confirm-password">Confirm Password</label>
        <input id="confirm-password" v-model="confirmPassword" type="password" required placeholder="Confirm your password" />
      </div>


      <button type="submit" class="register-button">Register</button>
      <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>
      <p v-if="successMessage" class="success-message">{{ successMessage }}</p>
    </form>
  </div>
</template>

<style scoped>
.registration-container {
  max-width: 400px;
  margin: 0 auto;
  padding: 2rem;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  background-color: #ffffff;
}

h2 {
  text-align: center;
  margin-bottom: 1rem;
  font-weight: bold;
  color: #333;
}

.form-group {
  margin-bottom: 1rem;
}

label {
  display: block;
  margin-bottom: 0.5rem;
  font-weight: bold;
  color: #333;

}

input {
  width: 100%;
  padding: 0.5rem;
  border: 1px solid #d5d5d5;
  border-radius: 4px;
  font-size: 1rem;
}

.register-button {
  width: 100%;
  padding: 0.75rem;
  background-color: #007bff;
  color: #090909;
  border: none;
  border-radius: 4px;
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.register-button:hover {
  background-color: #0056b3;
}

.error-message {
  color: #ff0000;
  margin-top: 1rem;
  text-align: center;
}

.success-message {
  color: #28a745;
  margin-top: 1rem;
  text-align: center;
}
</style>
