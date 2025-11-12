<template>
  <n-button type="primary" @click="handleAddClick">
    <template #icon>
      <n-icon>
        <plus />
      </n-icon>
    </template>
    新增
  </n-button>
  <div class="mb-4"></div>
  <n-data-table :data="machineGroups" :columns="columns" />
  <n-modal v-model:show="showModal">
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
  </n-modal>
</template>

<script setup lang="ts">
import type { DataTableColumns } from 'naive-ui'
import {
  NDataTable,
  NCard,
  NButton,
  NIcon,
  NModal,
  NForm,
  NFormItem,
  NInput,
  NInputNumber,
  useMessage,
} from 'naive-ui'
import type { FormInst } from 'naive-ui'
import { Plus } from '@vicons/tabler'
import { h, onMounted, ref } from 'vue'
import { fetchAllMachineGroupData, addOneMachineGroupData } from '@/services/MachineGroupService'

const message = useMessage()
const columns: DataTableColumns<MachineGroup> = [
  {
    title: '分组ID',
    key: 'id',
  },
  {
    title: '分组名称',
    key: 'name',
  },
  {
    title: '操作',
    key: 'actions',
    render: (row) => {
      return h(NButton, {
        onClick: () => message.info(row.name || ''),
      })
    },
  },
]

const machineGroups = ref<MachineGroup[]>([])

const showModal = ref(true)

const formRef = ref<FormInst | null>(null)

const modelRef = ref<MachineGroup>({})

function handleAddClick() {
  modelRef.value = {}
  showModal.value = true
}

function handleSubmitClick() {
  const newMachineGroup: MachineGroup = {
    name: modelRef.value.name,
  }
  addOneMachineGroup(newMachineGroup)
}

async function fetchAllMachineGroup() {
  const response = await fetchAllMachineGroupData()
  machineGroups.value = response
}

async function addOneMachineGroup(data: MachineGroup) {
  await addOneMachineGroupData(data)
  fetchAllMachineGroup()
}

onMounted(() => {
  fetchAllMachineGroup()
})
</script>
