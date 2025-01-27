import { createRouter, createWebHistory } from 'vue-router';
import DoctorComponent from '../components/DoctorComponent.vue';
import NurseComponent from '../components/NurseComponent.vue';
import LoginComponent from '../components/AuthComponent.vue';

const routes = [
    { path: '/', component: LoginComponent, name: 'Login' },
    { path: '/doctor', component: DoctorComponent, name: 'Doctor' },
    { path: '/nurse', component: NurseComponent, name: 'Nurse' },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;