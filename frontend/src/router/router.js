import { createRouter, createWebHistory } from 'vue-router'
import Auth from '../components/AuthComponent.vue'
import Doctor from '../components/DoctorComponent.vue'
import Nurse from '../components/NurseComponent.vue'

const routes = [
    {
        path: '/',
        redirect: '/login'
    },
    {
        path: '/login',
        component: Auth
    },
    {
        path: '/doctor',
        component: Doctor,
        meta: { requiresAuth: true, role: 'doctor' }
    },
    {
        path: '/nurse',
        component: Nurse,
        meta: { requiresAuth: true, role: 'nurse' }
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

// Навигационный гард
router.beforeEach((to, from, next) => {
    const isAuthenticated = localStorage.getItem('authToken')
    const userRole = localStorage.getItem('userRole')

    if (to.meta.requiresAuth && !isAuthenticated) {
        next('/login')
    } else if (to.meta.requiresAuth && to.meta.role !== userRole) {
        next('/login')
    } else {
        next()
    }
})

export default router