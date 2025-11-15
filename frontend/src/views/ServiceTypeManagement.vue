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
  <n-data-table :data="serviceTypes" :columns="columns" />
  <n-modal v-model:show="showModal">
    <MyEasyForm
      :resource="`服务类型`"
      :curData="curServiceType"
      @add="addOneServiceType"
      @update="updateOneServiceType"
      @cancel="closeModal"
    />
  </n-modal>
</template>

<script setup lang="ts">
import type { DataTableColumns } from 'naive-ui'
import { NDataTable, NButton, NIcon, NModal, useLoadingBar } from 'naive-ui'
import { Plus } from '@vicons/tabler'
import { h, onMounted, ref } from 'vue'
import {
  fetchAllServiceTypeData,
  addOneServiceTypeData,
  updateOneServiceTypeData,
  deleteOneServiceTypeData,
} from '@/services/ServiceTypeService'
import MyEasyForm from './components/MyEasyForm.vue'
import MyTableAction from './components/MyTableAction.vue'

const loadingBar = useLoadingBar()

const columns: DataTableColumns<ServiceType> = [
  {
    title: '服务类型ID',
    key: 'id',
  },
  {
    title: '服务类型名称',
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
          deleteOneServiceType(row)
        },
      })
    },
  },
]

const serviceTypes = ref<ServiceType[]>([])

const showModal = ref(false)

const curServiceType = ref<ServiceType>({})

function handleAddClick() {
  curServiceType.value = {}
  showModal.value = true
}

function handleEditClick(data: ServiceType) {
  curServiceType.value = data
  showModal.value = true
}

function closeModal() {
  showModal.value = false
}

async function refreshData() {
  const response = await fetchAllServiceTypeData()
  serviceTypes.value = response
}

async function fetchAllServiceType() {
  loadingBar.start()
  const response = await fetchAllServiceTypeData()
  serviceTypes.value = response
  loadingBar.finish()
}

async function addOneServiceType(data: ServiceType) {
  loadingBar.start()
  closeModal()
  await addOneServiceTypeData(data)
  await refreshData()
  loadingBar.finish()
}

async function updateOneServiceType(data: ServiceType) {
  loadingBar.start()
  closeModal()
  await updateOneServiceTypeData(data)
  await refreshData()
  loadingBar.finish()
}

async function deleteOneServiceType(data: ServiceType) {
  loadingBar.start()
  await deleteOneServiceTypeData(data)
  await refreshData()
  loadingBar.finish()
}

onMounted(() => {
  fetchAllServiceType()
})
</script>
