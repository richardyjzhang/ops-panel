<template>
  <div class="flex gap-4">
    <n-button v-if="showDetail" type="primary" text @click="handleEdit">
      <template #icon>
        <n-icon>
          <DeviceDesktop />
        </n-icon>
      </template>
      详情
    </n-button>
    <n-button type="primary" text @click="handleEdit">
      <template #icon>
        <n-icon>
          <Edit />
        </n-icon>
      </template>
      编辑
    </n-button>
    <n-popconfirm positive-text="确认" negative-text="取消" @positive-click="handleDelete">
      <template #icon>
        <n-icon color="red">
          <AlertCircle />
        </n-icon>
      </template>
      <template #trigger>
        <n-button type="error" text>
          <template #icon>
            <n-icon>
              <Trash />
            </n-icon>
          </template>
          删除
        </n-button>
      </template>
      确认删除该项？
    </n-popconfirm>
  </div>
</template>

<script setup lang="ts">
import { NButton, NPopconfirm, NIcon } from 'naive-ui'
import { AlertCircle, Edit, Trash, DeviceDesktop } from '@vicons/tabler'

const props = defineProps<{ showDetail?: boolean }>()

const emit = defineEmits<{
  detail: []
  edit: []
  delete: []
}>()

function handleDetail() {
  emit('detail')
}

function handleEdit() {
  emit('edit')
}

function handleDelete() {
  emit('delete')
}
</script>
