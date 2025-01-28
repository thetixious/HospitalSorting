<template>
  <div class="doctor-container">
    <h2>Очередь пациентов</h2>

    <div v-if="loading">Загрузка...</div>

    <table v-else>
      <thead>
      <tr>
        <th>Уровень</th>
        <th>ФИО</th>
        <th>Возраст</th>
        <th>Симптомы</th>
        <th>Время поступления</th>
        <th>Действия</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="patient in sortedQueue" :key="patient.id"
          :class="'triage-' + patient.triageLevel">
        <td>{{ patient.triageLevel }}</td>
        <td>{{ patient.name }}</td>
        <td>{{ patient.age }}</td>
        <td>{{ patient.symptoms }}</td>
        <td>{{ formatDate(patient.admissionTime) }}</td>
        <td>
          <select v-model="patient.newTriage" @change="updateTriage(patient)">
            <option v-for="n in 5" :value="n" :selected="n === patient.triageLevel">
              {{ n }} ({{ triageLabels[n] }})
            </option>
          </select>
          <button @click="dischargePatient(patient.id)">Выписать</button>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      loading: true,
      patients: [],
      triageLabels: {
        1: 'Реанимация',
        2: 'Срочно',
        3: 'Промежуточный',
        4: 'Низкий приоритет',
        5: 'Не срочно'
      }
    }
  },
  computed: {
    sortedQueue() {
      return [...this.patients].sort((a, b) => {
        if (a.triageLevel === b.triageLevel) {
          return new Date(a.admissionTime) - new Date(b.admissionTime);
        }
        return a.triageLevel - b.triageLevel;
      });
    }
  },
  async created() {
    await this.fetchQueue();
  },
  methods: {
    async fetchQueue() {
      try {
        const response = await axios.get('/doctor/queue');
        this.patients = response.data.map(p => ({
          ...p,
          newTriage: p.triageLevel
        }));
      } catch (error) {
        this.$toast.error('Ошибка загрузки очереди');
      } finally {
        this.loading = false;
      }
    },

    async updateTriage(patient) {
      try {
        await axios.put(`/doctor/triage/${patient.id}`, null, {
          params: { newLevel: patient.newTriage }
        });
        this.$toast.success('Уровень срочности обновлён');
        await this.fetchQueue();
      } catch (error) {
        this.$toast.error('Ошибка обновления');
      }
    },

    async dischargePatient(id) {
      try {
        await axios.delete(`/doctor/triage/${id}`);
        this.$toast.success('Пациент выписан');
        await this.fetchQueue();
      } catch (error) {
        this.$toast.error('Ошибка выписки');
      }
    },

    formatDate(dateString) {
      const options = {
        year: 'numeric',
        month: 'short',
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
      };
      return new Date(dateString).toLocaleString('ru-RU', options);
    }
  }
}
</script>

<style scoped>
.doctor-container {
  max-width: 1200px;
  margin: 20px auto;
  padding: 20px;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

th, td {
  padding: 12px;
  text-align: left;
  border-bottom: 1px solid #ddd;
}

tr:hover {
  background-color: #f5f5f5;
}

select {
  padding: 5px;
  margin-right: 10px;
}

button {
  background: #f44336;
  color: white;
  border: none;
  padding: 8px 12px;
  border-radius: 4px;
  cursor: pointer;
}

.triage-1 { background-color: #ffebee; }
.triage-2 { background-color: #fff3e0; }
.triage-3 { background-color: #fffde7; }
.triage-4 { background-color: #f1f8e9; }
.triage-5 { background-color: #f5f5f5; }
</style>