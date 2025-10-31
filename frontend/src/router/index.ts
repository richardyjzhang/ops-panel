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
          name: '设备分组管理',
          component: () => import('../views/GroupManagement.vue'),
        },
        {
          path: '/main/machines',
          name: '计算设备管理',
          component: () => import('../views/MachineManagement.vue'),
        },
        {
          path: '/main/service-types',
          name: '服务类型管理',
          component: () => import('../views/ServiceTypeManagement.vue'),
        },
      ],
    },
  ],
})

export default router
