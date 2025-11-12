<template>
  <n-card class="w-2/3 max-w-lg" title="新增分组">
    <n-form ref="formRef" :model="modelRef">
      <n-form-item path="id" label="分组ID">
        <n-input-number
          disabled
          placeholder="系统自动生成"
          v-model:value="modelRef.id"
          :show-button="false"
          class="w-1/1"
        ></n-input-number>
      </n-form-item>
      <n-form-item path="name" label="分组名称">
        <n-input placeholder="请输入分组名称" v-model:value="modelRef.name"></n-input>
      </n-form-item>
    </n-form>
    <n-button @click="handleSubmitClick">确认</n-button>
  </n-card>
</template>

<script setup lang="ts">
import { NCard, NButton, NForm, NFormItem, NInput, NInputNumber, useMessage } from 'naive-ui'
import type { FormInst } from 'naive-ui'
import { ref } from 'vue'

defineProps<{ curMachineGroup: MachineGroup }>()

const emit = defineEmits<{
  add: [data: MachineGroup]
  update: [data: MachineGroup]
}>()

const formRef = ref<FormInst | null>(null)

const modelRef = ref<MachineGroup>({})

function handleSubmitClick() {
  if (modelRef.value.id !== undefined) {
    const newMachineGroup = { ...modelRef.value }
    emit('update', newMachineGroup)
  } else {
    const newMachineGroup: MachineGroup = {
      name: modelRef.value.name,
    }
    emit('add', newMachineGroup)
  }
}
</script>
