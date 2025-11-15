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
  <n-spin :show="loading">
    <n-data-table :data="machineTypes" :columns="columns" />
  </n-spin>
  <n-modal v-model:show="showModal">
    <MyEasyForm
      :resource="`设备类型`"
      :curData="curMachineType"
      @add="addOneMachineType"
      @update="updateOneMachineType"
      @cancel="closeModal"
    />
  </n-modal>
</template>

<script setup lang="ts">
import type { DataTableColumns } from 'naive-ui'
import { NDataTable, NButton, NIcon, NModal, useLoadingBar, NSpin } from 'naive-ui'
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

const loadingBar = useLoadingBar()

const loading = ref(false)

const columns: DataTableColumns<MachineType> = [
  {
    title: '设备类型ID',
    key: 'id',
  },
  {
    title: '设备类型名称',
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

async function refreshData() {
  const response = await fetchAllMachineTypeData()
  machineTypes.value = response
}

async function loadingWrapper(actions: () => {}) {
  loading.value = true
  loadingBar.start()
  await actions()
  loadingBar.finish()
  loading.value = false
}

async function fetchAllMachineType() {
  loadingWrapper(async () => {
    const response = await fetchAllMachineTypeData()
    machineTypes.value = response
  })
}

async function addOneMachineType(data: MachineType) {
  loadingWrapper(async () => {
    closeModal()
    await addOneMachineTypeData(data)
    await refreshData()
  })
}

async function updateOneMachineType(data: MachineType) {
  loadingWrapper(async () => {
    closeModal()
    await updateOneMachineTypeData(data)
    await refreshData()
  })
}

async function deleteOneMachineType(data: MachineType) {
  loadingWrapper(async () => {
    await deleteOneMachineTypeData(data)
    await refreshData()
  })
}

onMounted(() => {
  fetchAllMachineType()
})
</script>
