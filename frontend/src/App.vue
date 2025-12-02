<template>
  <n-message-provider>
    <n-loading-bar-provider>
      <n-notification-provider>
        <router-view />
      </n-notification-provider>
    </n-loading-bar-provider>
  </n-message-provider>
</template>

<style>
@import 'tailwindcss';
</style>

<script setup lang="ts">
import { NMessageProvider, NLoadingBarProvider, NNotificationProvider } from 'naive-ui'
</script>
