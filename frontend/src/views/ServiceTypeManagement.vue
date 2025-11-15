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
    <n-data-table :data="serviceTypes" :columns="columns" />
  </n-spin>
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
import { NDataTable, NButton, NIcon, NModal, useLoadingBar, NSpin } from 'naive-ui'
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

const loading = ref(false)

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

async function loadingWrapper(actions: () => {}) {
  loading.value = true
  loadingBar.start()
  await actions()
  loadingBar.finish()
  loading.value = false
}

async function fetchAllServiceType() {
  loadingWrapper(async () => {
    const response = await fetchAllServiceTypeData()
    serviceTypes.value = response
  })
}

async function addOneServiceType(data: ServiceType) {
  loadingWrapper(async () => {
    closeModal()
    await addOneServiceTypeData(data)
    await refreshData()
  })
}

async function updateOneServiceType(data: ServiceType) {
  loadingWrapper(async () => {
    closeModal()
    await updateOneServiceTypeData(data)
    await refreshData()
  })
}

async function deleteOneServiceType(data: ServiceType) {
  loadingWrapper(async () => {
    await deleteOneServiceTypeData(data)
    await refreshData()
  })
}

onMounted(() => {
  fetchAllServiceType()
})
</script>
