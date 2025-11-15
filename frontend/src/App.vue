<template>
  <n-message-provider>
    <n-loading-bar-provider>
      <router-view />
    </n-loading-bar-provider>
  </n-message-provider>
</template>

<style>
@import 'tailwindcss';
</style>

<script setup lang="ts">
import { NMessageProvider, NLoadingBarProvider } from 'naive-ui'
</script>
