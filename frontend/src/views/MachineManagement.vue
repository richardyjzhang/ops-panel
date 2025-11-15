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
    <n-data-table :data="machines" :columns="columns" />
  </n-spin>
  <n-modal v-model:show="showModal">
    <MachineAddEditForm
      :resource="`计算设备`"
      :curData="curServiceType"
      @add="addOneServiceType"
      @update="updateOneServiceType"
      @cancel="closeModal"
    />
  </n-modal>
</template>

<script setup lang="ts">
import type { DataTableColumns } from 'naive-ui'
import { NDataTable, NButton, NIcon, NModal, useLoadingBar, NSpin, NTag } from 'naive-ui'
import { Plus, AlertCircle, CircleCheck } from '@vicons/tabler'
import { h, onMounted, ref } from 'vue'
import {
  fetchAllMachineData,
  addOneMachineData,
  updateOneMachineData,
  deleteOneMachineData,
} from '@/services/MachineService'
import MachineAddEditForm from './components/MachineAddEditForm.vue'
import MyTableAction from './components/MyTableAction.vue'

const loadingBar = useLoadingBar()

const loading = ref(false)

const columns: DataTableColumns<Machine> = [
  {
    title: '计算设备ID',
    key: 'id',
  },
  {
    title: '计算设备名称',
    key: 'name',
  },
  {
    title: '所在设备分组',
    key: 'groupId',
    render: (row) => {
      if (row.id) {
        return machineGroupId2Name.value.get(row.id)
      }
      return '默认分组'
    },
  },
  {
    title: '计算设备类型',
    key: 'typeId',
    render: (row) => {
      if (row.id) {
        return machineTypeId2Name.value.get(row.id)
      }
      return '默认分组'
    },
  },
  {
    title: '运行状态',
    key: 'online',
    render: (row) => {
      if (isMachineOK(row)) {
        return h(
          NTag,
          {
            type: 'success',
          },
          {
            default: () => '运行正常',
            icon: () => h(NIcon, { component: CircleCheck }),
          },
        )
      }
      return h(
        NTag,
        {
          type: 'error',
        },
        {
          default: () => '运行异常',
          icon: () => h(NIcon, { component: AlertCircle }),
        },
      )
    },
  },
  {
    title: '操作',
    key: 'actions',
    render: (row) => {
      return h(MyTableAction, {
        showDetail: true,
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

const machines = ref<Machine[]>([])

const showModal = ref(false)

const machineGroupId2Name = ref<Map<number, string>>(new Map())

const machineTypeId2Name = ref<Map<number, string>>(new Map())

const curServiceType = ref<ServiceType>({})

function isMachineOK(machine: Machine) {
  const THRESHOLD = 0.9

  if (!machine.online) return false

  if (machine.disks !== undefined) {
    for (let i = 0; i < machine.disks.length; ++i) {
      const disk = machine.disks[i]
      if (disk !== undefined && disk.diskUsage !== undefined && disk.diskUsage > THRESHOLD) {
        return false
      }
    }
  }

  if (machine.services !== undefined) {
    for (let i = 0; i < machine.services.length; ++i) {
      const service = machine.services[i]
      if (service !== undefined && !service.online) {
        return false
      }
    }
  }

  return true
}

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
  const response = await fetchAllMachineData()
  machines.value = response
}

async function loadingWrapper(actions: () => void) {
  loading.value = true
  loadingBar.start()
  await actions()
  loadingBar.finish()
  loading.value = false
}

async function fetchAllMachine() {
  loadingWrapper(async () => {
    const response = await fetchAllMachineData()
    machines.value = response
  })
}

async function addOneServiceType(data: ServiceType) {
  loadingWrapper(async () => {
    closeModal()
    await addOneMachineData(data)
    await refreshData()
  })
}

async function updateOneServiceType(data: ServiceType) {
  loadingWrapper(async () => {
    closeModal()
    await updateOneMachineData(data)
    await refreshData()
  })
}

async function deleteOneServiceType(data: ServiceType) {
  loadingWrapper(async () => {
    await deleteOneMachineData(data)
    await refreshData()
  })
}

onMounted(() => {
  fetchAllMachine()
})
</script>
