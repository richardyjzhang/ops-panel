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
          <div class="mb-2">
            <custom-tabs
              :tabs="tabsStore.tabs"
              v-model:active-tab-path="tabsStore.activeTabPath"
              @close="handleCloseTab"
              @update="handleTabChange"
            >
              <n-card :bordered="false" class="grow">
                <router-view />
              </n-card>
            </custom-tabs>
          </div>
        </main>
      </n-layout-content>
    </n-layout>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import {
  NLayout,
  NLayoutSider,
  NLayoutContent,
  NMenu,
  NCard
} from 'naive-ui'
import { renderIcon } from './utils'
import { LayoutGrid, HeartRateMonitor, BrandDocker, DevicesPc } from '@vicons/tabler'
import NavBar from './components/NavBar.vue'
import CustomTabs from './components/CustomTabs.vue'
import { useTabsStore } from '@/stores/tabs'

const menuCollapsed = ref(false)
const router = useRouter()
const route = useRoute()
const tabsStore = useTabsStore()

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

function handleMenuSelect(key: string) {
  // 添加标签页
  const currentMenu = menuOptions.find(menu => menu.key === key)
  if (currentMenu) {
    tabsStore.addTab({
      label: currentMenu.label,
      path: key
    })
  }
  router.push(key)
}

function handleCloseTab(tabPath: string) {
  tabsStore.closeTab(tabPath)
  // 如果关闭的是当前激活的标签页，需要跳转到新的激活标签页
  if (tabPath === route.path && tabsStore.activeTabPath) {
    router.push(tabsStore.activeTabPath)
  }
}

function handleTabChange(tabPath: string) {
  tabsStore.setActiveTab(tabPath)
  router.push(tabPath)
}

// 初始化时处理当前路由对应的标签页
onMounted(() => {
  // 添加当前路由对应的标签页
  if (route.path && route.name) {
    tabsStore.addTab({
      label: route.name.toString(),
      path: route.path
    })
  }
})
</script>
