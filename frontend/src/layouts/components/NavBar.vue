<template>
  <nav class="w-full h-12 bg-green-300 border-b border-b-gray-300 px-4 flex gap-4 items-center">
    <img src="@/assets/logo.png" class="h-2/3" />
    <span class="text-xl">服务器状态监控</span>
    <div class="grow"></div>
    <avatar-dropdown />
  </nav>
</template>

<script setup lang="ts">
import AvatarDropdown from './AvatarDropdown.vue'
</script>
