<script setup>
import { ref } from 'vue';

// Пример данных пациентов
const patients = ref([
  { id: 1, name: 'Алексей Иванов', age: 35, diagnosis: 'Грипп' },
  { id: 2, name: 'Мария Смирнова', age: 28, diagnosis: 'ОРВИ' },
  { id: 3, name: 'Иван Петров', age: 42, diagnosis: 'Ангина' },
  { id: 4, name: 'Елена Сидорова', age: 50, diagnosis: 'Пневмония' },
  { id: 5, name: 'Дмитрий Орлов', age: 31, diagnosis: 'Гастрит' },
]);
</script>

<template>
  <div class="doctor-page">
    <h1>Список пациентов</h1>
    <div class="patients-grid">
      <div v-for="patient in patients" :key="patient.id" class="patient-card">
        <h2>{{ patient.name }}</h2>
        <p><strong>Возраст:</strong> {{ patient.age }}</p>
        <p><strong>Диагноз:</strong> {{ patient.diagnosis }}</p>
      </div>
    </div>
  </div>
</template>

<style scoped>
.doctor-page {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
  font-family: Arial, sans-serif;
}

h1 {
  text-align: center;
  margin-bottom: 20px;
}

.patients-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 20px;
}

.patient-card {
  background-color: #f9f9f9;
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 15px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  text-align: center;
  transition: transform 0.2s, box-shadow 0.2s;
}

.patient-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 10px rgba(0, 0, 0, 0.15);
}

.patient-card h2 {
  font-size: 1.2rem;
  margin-bottom: 10px;
  color: #333;
}

.patient-card p {
  margin: 5px 0;
  color: #666;
}

.patient-card p strong {
  color: #333;
}
</style>
