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
import { fetchAllMachineGroupData } from '@/services/MachineGroupService'
import { fetchAllMachineTypeData } from '@/services/MachineTypeService'
import MyEasyForm from './components/MyEasyForm.vue'
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

const machineGroupId2Name = ref<Map<number, string>>(new Map())

const machineTypeId2Name = ref<Map<number, string>>(new Map())

const curServiceType = ref<ServiceType>({})

function isMachineOK(machine: Machine) {
  // TODO 需要用在线、服务、硬盘等综合判断
  return machine.online
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
  serviceTypes.value = response
}

async function loadingWrapper(actions: () => {}) {
  loading.value = true
  loadingBar.start()
  await actions()
  loadingBar.finish()
  loading.value = false
}

async function fetchAllMachine() {
  loadingWrapper(async () => {
    const response = await fetchAllMachineData()
    serviceTypes.value = response
  })
}

async function initAllData() {
  const machineGroups = await fetchAllMachineGroupData()
  machineGroupId2Name.value.clear()
  machineGroups.forEach((v) => {
    if (v.id !== undefined && v.name !== undefined) {
      machineGroupId2Name.value.set(v.id, v.name)
    }
  })

  const machineTypes = await fetchAllMachineTypeData()
  machineTypeId2Name.value.clear()
  machineTypes.forEach((v) => {
    if (v.id !== undefined && v.name !== undefined) {
      machineTypeId2Name.value.set(v.id, v.name)
    }
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
  initAllData()
  fetchAllMachine()
})
</script>
