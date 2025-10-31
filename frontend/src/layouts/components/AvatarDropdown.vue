<template>
  <n-dropdown :options="options" @select="handleSelect">
    <user-circle class="h-3/5" />
  </n-dropdown>
</template>

<script setup lang="ts">
import { NDropdown } from 'naive-ui'
import { UserCircle, Logout, ShieldLock } from '@vicons/tabler'
import { renderIcon } from '../utils';

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

function handleSelect(key: string | number) {
  // TODO
  console.log(key);
}
</script>