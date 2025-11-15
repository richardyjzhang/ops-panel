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
    <MyEasyForm
      :curData="curMachineGroup"
      @add="addOneMachineGroup"
      @update="updateOneMachineGroup"
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
  fetchAllMachineGroupData,
  addOneMachineGroupData,
  updateOneMachineGroupData,
  deleteOneMachineGroupData,
} from '@/services/MachineGroupService'
import MyEasyForm from './components/MyEasyForm.vue'
import MyTableAction from './components/MyTableAction.vue'

const loadingBar = useLoadingBar()

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
      return h(MyTableAction, {
        onEdit: () => {
          handleEditClick(row)
        },
        onDelete: () => {
          deleteOneMachineGroup(row)
        },
      })
    },
  },
]

const machineGroups = ref<MachineGroup[]>([])

const showModal = ref(false)

const curMachineGroup = ref<MachineGroup>({})

function handleAddClick() {
  curMachineGroup.value = {}
  showModal.value = true
}

function handleEditClick(data: MachineGroup) {
  curMachineGroup.value = data
  showModal.value = true
}

function closeModal() {
  showModal.value = false
}

async function refreshData() {
  const response = await fetchAllMachineGroupData()
  machineGroups.value = response
}

async function fetchAllMachineGroup() {
  loadingBar.start()
  const response = await fetchAllMachineGroupData()
  machineGroups.value = response
  loadingBar.finish()
}

async function addOneMachineGroup(data: MachineGroup) {
  loadingBar.start()
  closeModal()
  await addOneMachineGroupData(data)
  await refreshData()
  loadingBar.finish()
}

async function updateOneMachineGroup(data: MachineGroup) {
  loadingBar.start()
  closeModal()
  await updateOneMachineGroupData(data)
  await refreshData()
  loadingBar.finish()
}

async function deleteOneMachineGroup(data: MachineGroup) {
  loadingBar.start()
  await deleteOneMachineGroupData(data)
  await refreshData()
  loadingBar.finish()
}

onMounted(() => {
  fetchAllMachineGroup()
})
</script>
