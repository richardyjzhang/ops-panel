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
      <n-form-item path="groupId" label="所在分组">
        <n-select
          v-model:value="formModel.groupId"
          :options="machineGroupOptions"
          placeholder="请选择所在分组"
        />
      </n-form-item>
      <n-form-item path="typeId" label="设备类型">
        <n-select
          v-model:value="formModel.typeId"
          :options="machineTypeOptions"
          placeholder="请选择设备类型"
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
import { useMachineGroupsStore, useMachineTypesStore } from '@/stores/types'

const props = defineProps<{ curData: Machine; resource: string }>()

const emit = defineEmits<{
  add: [data: Machine]
  update: [data: Machine]
  cancel: []
}>()

const formRef = ref<FormInst | null>(null)

const formModel = ref<Machine>(props.curData)

const machineGroups = useMachineGroupsStore()

const machineTypes = useMachineTypesStore()

const rules: FormRules = {
  name: {
    required: true,
    message: '请输入名称',
  },
}

const machineGroupOptions = ref<SelectOption[]>(
  machineGroups.machineGroups.map((m) => ({
    label: m.name,
    value: m.id,
  })),
)

const machineTypeOptions = ref<SelectOption[]>(
  machineTypes.machineTypes.map((m) => ({
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
    const newMachine = { ...formModel.value }
    emit('update', newMachine)
  } else {
    const newMachine: Machine = {
      ...formModel.value,
      cpuUsage: 0.0,
      ramUsage: 0.0,
      online: false,
    }
    emit('add', newMachine)
  }
}
</script>
