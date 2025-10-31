<template>
  <n-message-provider>
    <router-view />
  </n-message-provider>
</template>

<style>
@import 'tailwindcss';
</style>

<script setup lang="ts">
import { NMessageProvider } from 'naive-ui';
</script>