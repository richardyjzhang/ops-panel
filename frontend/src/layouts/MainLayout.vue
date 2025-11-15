<template>
  <div class="h-screen flex flex-col">
    <nav-bar />
    <n-layout has-sider>
      <n-layout-sider
        collapse-mode="width"
        bordered
        show-trigger="arrow-circle"
        :collapsed-width="64"
        @collapse="menuCollapsed = true"
        @expand="menuCollapsed = false"
        class="pt-2"
      >
        <n-menu
          :collapsed="menuCollapsed"
          :options="menuOptions"
          :collapsed-width="64"
          :collapsed-icon-size="22"
          v-bind:value="$route.path"
          @update:value="handleMenuSelect"
        />
      </n-layout-sider>
      <n-layout-content>
        <main class="w-full h-full bg-gray-100 p-4 flex flex-col">
          <n-breadcrumb class="mb-2">
            <n-breadcrumb-item>面</n-breadcrumb-item>
            <n-breadcrumb-item>包</n-breadcrumb-item>
            <n-breadcrumb-item>Shit</n-breadcrumb-item>
          </n-breadcrumb>
          <n-card :title="$route.name?.toString()" class="grow">
            <router-view />
          </n-card>
        </main>
      </n-layout-content>
    </n-layout>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import {
  NLayout,
  NLayoutSider,
  NLayoutContent,
  NMenu,
  NCard,
  NBreadcrumb,
  NBreadcrumbItem,
} from 'naive-ui'
import { renderIcon } from './utils'
import { LayoutGrid, HeartRateMonitor, BrandDocker, DevicesPc } from '@vicons/tabler'
import NavBar from './components/NavBar.vue'

const menuCollapsed = ref(false)

const menuOptions = [
  {
    label: '设备分组管理',
    key: '/main/groups',
    icon: renderIcon(LayoutGrid),
  },
  {
    label: '计算设备管理',
    key: '/main/machines',
    icon: renderIcon(HeartRateMonitor),
  },
  {
    label: '设备类型管理',
    key: '/main/machine-types',
    icon: renderIcon(DevicesPc),
  },
  {
    label: '服务类型管理',
    key: '/main/service-types',
    icon: renderIcon(BrandDocker),
  },
]

const router = useRouter()

function handleMenuSelect(key: string) {
  router.push(key)
}
</script>
