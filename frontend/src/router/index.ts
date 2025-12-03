import { createRouter, createWebHashHistory } from 'vue-router'
import MainLayout from '@/layouts/MainLayout.vue'

const router = createRouter({
  history: createWebHashHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      redirect: '/login',
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('../views/LoginPage.vue'),
    },
    {
      path: '/main',
      name: 'main-layout',
      component: MainLayout,
      children: [
        {
          path: '/main',
          redirect: '/main/groups',
        },
        {
          path: '/main/groups',
          name: '设备分组管理',
          component: () => import('../views/MachineGroupManagement.vue'),
        },
        {
          path: '/main/machine-types',
          name: '设备类型管理',
          component: () => import('../views/MachineTypeManagement.vue'),
        },
        {
          path: '/main/machines',
          name: '计算设备管理',
          component: () => import('../views/MachineManagement.vue'),
        },
        {
          path: '/main/machine-detail',
          name: '计算设备详情',
          component: () => import('../views/MachineDetail.vue'),
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
