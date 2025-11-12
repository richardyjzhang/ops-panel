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
    <MachineGroupForm :curMachineGroup="curMachineGroup" @add="addOneMachineGroup" />
  </n-modal>
</template>

<script setup lang="ts">
import type { DataTableColumns } from 'naive-ui'
import { NDataTable, NButton, NIcon, NModal, useMessage } from 'naive-ui'
import { Plus } from '@vicons/tabler'
import { h, onMounted, ref } from 'vue'
import { fetchAllMachineGroupData, addOneMachineGroupData } from '@/services/MachineGroupService'
import MachineGroupForm from './components/MachineGroupForm.vue'

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

const curMachineGroup = ref<MachineGroup>({})

function handleAddClick() {
  showModal.value = true
  curMachineGroup.value = {}
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
