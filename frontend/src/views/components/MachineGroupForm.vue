<template>
  <n-card class="w-2/3 max-w-lg" title="新增分组">
    <n-form ref="formRef" :rules="rules" :model="formModel">
      <n-form-item path="id" label="分组ID">
        <n-input-number
          disabled
          placeholder="系统自动生成"
          v-model:value="formModel.id"
          :show-button="false"
          class="w-1/1"
        ></n-input-number>
      </n-form-item>
      <n-form-item path="name" label="分组名称">
        <n-input placeholder="请输入分组名称" v-model:value="formModel.name"></n-input>
      </n-form-item>
    </n-form>
    <div class="w-full mt-4 flex flex-row-reverse gap-4">
      <n-button @click="handleCancel">
        <template #icon>
          <n-icon> <X /> </n-icon>
        </template>
        取消
      </n-button>
      <n-button type="primary" @click="handleSubmit">
        <template #icon>
          <n-icon> <Check /> </n-icon>
        </template>
        确认
      </n-button>
    </div>
  </n-card>
</template>

<script setup lang="ts">
import { NCard, NButton, NForm, NFormItem, NInput, NInputNumber, NIcon } from 'naive-ui'
import type { FormInst, FormRules } from 'naive-ui'
import { Check, X } from '@vicons/tabler'
import { ref } from 'vue'

const props = defineProps<{ curMachineGroup: MachineGroup }>()

const emit = defineEmits<{
  add: [data: MachineGroup]
  update: [data: MachineGroup]
  cancel: []
}>()

const formRef = ref<FormInst | null>(null)

const formModel = ref<MachineGroup>(props.curMachineGroup)

const rules: FormRules = {
  name: {
    required: true,
    message: '请输入名称',
  },
}

function handleCancel(e: MouseEvent) {
  e.preventDefault()
  emit('cancel')
}

function handleSubmit(e: MouseEvent) {
  e.preventDefault()

  formRef.value?.validate((errors) => {
    if (errors !== undefined) {
      return
    }
  })

  if (formModel.value.id !== undefined) {
    const newMachineGroup = { ...formModel.value }
    emit('update', newMachineGroup)
  } else {
    const newMachineGroup: MachineGroup = {
      name: formModel.value.name,
    }
    emit('add', newMachineGroup)
  }
}
</script>
