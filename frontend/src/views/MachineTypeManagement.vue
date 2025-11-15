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
  <n-data-table :data="machineTypes" :columns="columns" />
  <n-modal v-model:show="showModal">
    <MyEasyForm
      :curData="curMachineType"
      @add="addOneMachineType"
      @update="updateOneMachineType"
      @cancel="closeModal"
    />
  </n-modal>
</template>

<script setup lang="ts">
import type { DataTableColumns } from 'naive-ui'
import { NDataTable, NButton, NIcon, NModal } from 'naive-ui'
import { Plus } from '@vicons/tabler'
import { h, onMounted, ref } from 'vue'
import {
  fetchAllMachineTypeData,
  addOneMachineTypeData,
  updateOneMachineTypeData,
  deleteOneMachineTypeData,
} from '@/services/MachineTypeService'
import MyEasyForm from './components/MyEasyForm.vue'
import MyTableAction from './components/MyTableAction.vue'

const columns: DataTableColumns<MachineType> = [
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
      return h(MyTableAction, {
        onEdit: () => {
          handleEditClick(row)
        },
        onDelete: () => {
          deleteOneMachineType(row)
        },
      })
    },
  },
]

const machineTypes = ref<MachineType[]>([])

const showModal = ref(false)

const curMachineType = ref<MachineType>({})

function handleAddClick() {
  curMachineType.value = {}
  showModal.value = true
}

function handleEditClick(data: MachineType) {
  curMachineType.value = data
  showModal.value = true
}

function closeModal() {
  showModal.value = false
}

async function fetchAllMachineType() {
  const response = await fetchAllMachineTypeData()
  machineTypes.value = response
}

async function addOneMachineType(data: MachineType) {
  closeModal()
  await addOneMachineTypeData(data)
  await fetchAllMachineType()
}

async function updateOneMachineType(data: MachineType) {
  closeModal()
  await updateOneMachineTypeData(data)
  await fetchAllMachineType()
}

async function deleteOneMachineType(data: MachineType) {
  await deleteOneMachineTypeData(data)
  await fetchAllMachineType()
}

onMounted(() => {
  fetchAllMachineType()
})
</script>
