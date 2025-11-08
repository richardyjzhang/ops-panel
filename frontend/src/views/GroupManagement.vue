<template>
  <n-data-table :data="machineGroups" :columns="columns" :bordered="false" />
</template>

<script setup lang="ts">
import type { DataTableColumns } from 'naive-ui'
import { NDataTable, NButton, useMessage } from 'naive-ui'
import { h, onMounted, ref } from 'vue'
import { fetchAllMachineGroupsData } from '@/services/MachineGroupService'

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
        onClick: () => message.info(row.name),
      })
    },
  },
]

const machineGroups = ref<MachineGroup[]>([])

async function fetchAllMachineGroups() {
  const response = await fetchAllMachineGroupsData()
  machineGroups.value = response
}

onMounted(() => {
  fetchAllMachineGroups()
})
</script>
