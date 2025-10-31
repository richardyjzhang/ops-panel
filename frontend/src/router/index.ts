import { createRouter, createWebHashHistory } from 'vue-router'
import MainLayout from '@/layouts/MainLayout.vue'

const router = createRouter({
  history: createWebHashHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      redirect: '/main/groups',
    },
    {
      path: '/main',
      name: 'main-layout',
      component: MainLayout,
      children: [
        {
          path: '/main/groups',
          name: 'group-management',
          component: () => import('../views/GroupManagement.vue'),
        },
        {
          path: '/main/machines',
          name: 'machine-management',
          component: () => import('../views/MachineManagement.vue'),
        },
        {
          path: '/main/service-types',
          name: 'service-type-management',
          component: () => import('../views/ServiceTypeManagement.vue'),
        },
      ],
    },
  ],
})

export default router
