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
    <MachineGroupForm
      :curMachineGroup="curMachineGroup"
      @add="addOneMachineGroup"
      @update="updateOneMachineGroup"
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
  fetchAllMachineGroupData,
  addOneMachineGroupData,
  updateOneMachineGroupData,
} from '@/services/MachineGroupService'
import MachineGroupForm from './components/MachineGroupForm.vue'
import MachineGroupTableAction from './components/MachineGroupTableAction.vue'

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
      return h(MachineGroupTableAction, {
        onEdit: () => {
          handleEditClick(row)
        },
      })
    },
  },
]

const machineGroups = ref<MachineGroup[]>([])

const showModal = ref(true)

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

async function fetchAllMachineGroup() {
  const response = await fetchAllMachineGroupData()
  machineGroups.value = response
}

async function addOneMachineGroup(data: MachineGroup) {
  closeModal()
  await addOneMachineGroupData(data)
  await fetchAllMachineGroup()
}

async function updateOneMachineGroup(data: MachineGroup) {
  closeModal()
  await updateOneMachineGroupData(data)
  await fetchAllMachineGroup()
}

onMounted(() => {
  fetchAllMachineGroup()
})
</script>
