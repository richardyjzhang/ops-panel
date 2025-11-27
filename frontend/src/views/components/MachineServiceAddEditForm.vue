<template>
  <n-card
    class="w-2/3 max-w-lg"
    :title="`${curData.id === undefined ? '添加' : '编辑'}${resource}`"
  >
    <n-form ref="formRef" :rules="rules" :model="formModel">
      <n-form-item path="id" :label="`${resource}ID`">
        <n-input-number
          disabled
          placeholder="系统自动生成"
          v-model:value="formModel.id"
          :show-button="false"
          class="w-1/1"
        ></n-input-number>
      </n-form-item>
      <n-form-item path="name" :label="`${resource}名称`">
        <n-input :placeholder="`请输入${resource}名称`" v-model:value="formModel.name"></n-input>
      </n-form-item>
      <n-form-item path="typeId" label="服务类型">
        <n-select
          v-model:value="formModel.typeId"
          :options="typeOptions"
          placeholder="请选择服务类型"
        />
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
import { NCard, NButton, NForm, NFormItem, NInput, NInputNumber, NIcon, NSelect } from 'naive-ui'
import type { FormInst, FormRules, SelectOption } from 'naive-ui'
import { Check, X } from '@vicons/tabler'
import { ref } from 'vue'
import { useServiceTypesStore } from '@/stores/types'

const props = defineProps<{ curData: MachineService; resource: string }>()

const emit = defineEmits<{
  add: [data: MachineService]
  update: [data: MachineService]
  cancel: []
}>()

const formRef = ref<FormInst | null>(null)

const formModel = ref<Machine>(props.curData)

const serviceTypes = useServiceTypesStore()

const rules: FormRules = {
  name: {
    required: true,
    message: '请输入名称',
  },
}

const typeOptions = ref<SelectOption[]>(
  serviceTypes.serviceTypes.map((m) => ({
    label: m.name,
    value: m.id,
  })),
)

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
    const newService = { ...formModel.value }
    emit('update', newService)
  } else {
    const newService: MachineService = {
      ...formModel.value,
      online: false,
    }
    emit('add', newService)
  }
}
</script>
