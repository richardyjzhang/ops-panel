<template>
  <div class="flex gap-4">
    <n-button type="primary" text @click="handleEdit">编辑</n-button>
    <n-button type="error" text @click="handleDelete">删除</n-button>
  </div>
</template>

<script setup lang="ts">
import { NButton } from 'naive-ui'

const emit = defineEmits<{
  edit: []
  delete: []
}>()

function handleEdit() {
  emit('edit')
}

function handleDelete() {
  emit('delete')
}
</script>
