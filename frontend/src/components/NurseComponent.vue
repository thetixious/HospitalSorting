<template>
  <div class="nurse-container">
    <h2>Медицинская сортировка пациентов</h2>
    <form @submit.prevent="assessPatient">
      <div class="form-group">
        <label>ФИО пациента:</label>
        <input v-model="patientData.name" required>
      </div>

      <div class="form-group">
        <label>Возраст:</label>
        <input type="number" v-model.number="patientData.age" required>
      </div>

      <div class="form-group">
        <label>Симптомы:</label>
        <textarea v-model="patientData.symptoms" required></textarea>
      </div>

      <div class="vital-signs">
        <div class="form-group">
          <label>Пульс (уд/мин):</label>
          <input type="number" v-model.number="patientData.heartRate" required>
        </div>

        <div class="form-group">
          <label>Давление (САД/ДАД):</label>
          <input v-model="patientData.bloodPressure"
                 pattern="\d+/\d+"
                 required
                 placeholder="120/80">
        </div>

        <div class="form-group">
          <label>Частота дыхания (дых/мин):</label>
          <input type="number" v-model.number="patientData.respiratoryRate" required>
        </div>
      </div>

      <button type="submit" :disabled="loading">
        {{ loading ? 'Обработка...' : 'Оценить состояние' }}
      </button>

      <div v-if="result" class="result">
        <h3>Результат сортировки:</h3>
        <p>Уровень срочности: <span :class="'triage-' + result.triageLevel">
          {{ result.triageLevel }} ({{ triageLabels[result.triageLevel] }})
        </span></p>
      </div>
    </form>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      loading: false,
      patientData: {
        name: '',
        age: null,
        symptoms: '',
        heartRate: null,
        bloodPressure: '',
        respiratoryRate: null
      },
      result: null,
      triageLabels: {
        1: 'Реанимация',
        2: 'Срочно',
        3: 'Промежуточный',
        4: 'Низкий приоритет',
        5: 'Не срочно'
      }
    }
  },
  methods: {
    async assessPatient() {
      this.loading = true;
      try {
        const response = await axios.post('/nurse/assess', this.patientData);
        this.result = response.data;
        this.patientData = { // Сброс формы
          name: '',
          age: null,
          symptoms: '',
          heartRate: null,
          bloodPressure: '',
          respiratoryRate: null
        };
        this.$toast.success('Пациент успешно добавлен в очередь!');
      } catch (error) {
        this.$toast.error('Ошибка при оценке состояния пациента');
      } finally {
        this.loading = false;
      }
    }
  }
}
</script>

<style scoped>
.nurse-container {
  max-width: 800px;
  margin: 20px auto;
  padding: 20px;
}

.form-group {
  margin-bottom: 15px;
}

.vital-signs {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 15px;
  margin: 20px 0;
}

button {
  background: #4CAF50;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.triage-1 { color: #ff0000; font-weight: bold; }
.triage-2 { color: #ff5722; }
.triage-3 { color: #ffc107; }
.triage-4 { color: #4CAF50; }
.triage-5 { color: #607d8b; }
</style>