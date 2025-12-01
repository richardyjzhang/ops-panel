<template>
  <nav class="w-full h-14 bg-[#18a058] text-white shadow-md flex items-center px-6 gap-4">
    <img src="@/assets/logo.png" class="h-8 rounded-md" alt="Logo" />
    <span class="text-xl font-semibold">服务器状态监控</span>
    <div class="grow"></div>
    <avatar-dropdown />
  </nav>
</template>

<script setup lang="ts">
import AvatarDropdown from './AvatarDropdown.vue'
</script>
