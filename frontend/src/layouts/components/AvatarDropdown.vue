<template>
  <n-dropdown :options="options" @select="handleSelect">
    <user-circle class="h-3/5" />
  </n-dropdown>
</template>

<script setup lang="ts">
import { NDropdown } from 'naive-ui'
import { UserCircle, Logout, ShieldLock } from '@vicons/tabler'
import { renderIcon } from '../utils';
import { logout } from '@/services/AuthService'
import { useRouter } from 'vue-router'

const router = useRouter()

const options = [
  {
    label: '修改密码',
    key: 'change-password',
    icon: renderIcon(ShieldLock)
  }, {
    label: '退出登录',
    key: 'logout',
    icon: renderIcon(Logout)
  }
]

async function handleSelect(key: string | number) {
  if (key === 'logout') {
      await logout()
      router.push('/login')
  } else if (key === 'change-password') {
    // TODO: 实现修改密码功能
    console.log('修改密码')
  }
}
</script>