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
      :curData="curMachine"
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
import { useRouter } from 'vue-router'
import { useMachineGroupsStore, useMachineTypesStore, useServiceTypesStore } from '@/stores/types'
import {
  fetchAllMachineData,
  addOneMachineData,
  updateOneMachineData,
  deleteOneMachineData,
} from '@/services/MachineService'
import { fetchAllMachineGroupData } from '@/services/MachineGroupService'
import { fetchAllMachineTypeData } from '@/services/MachineTypeService'
import { fetchAllServiceTypeData } from '@/services/ServiceTypeService'
import { useCurrentStore } from '@/stores/current'
import MachineAddEditForm from './components/MachineAddEditForm.vue'
import MyTableAction from './components/MyTableAction.vue'


const router = useRouter()

const loadingBar = useLoadingBar()

const loading = ref(false)

const machineGroups = useMachineGroupsStore()
const machineTypes = useMachineTypesStore()
const serviceTypes = useServiceTypesStore()

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
      const _find = machineGroups.machineGroups.find((v) => v.id === row.groupId)
      if (_find !== undefined) {
        return _find.name
      }
      return '默认分组'
    },
  },
  {
    title: '计算设备类型',
    key: 'typeId',
    render: (row) => {
      const _find = machineTypes.machineTypes.find((v) => v.id === row.typeId)
      if (_find !== undefined) {
        return _find.name
      }
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
        onDetail: () => {
          handleShowDetailClick(row)
        },
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

const curMachine = ref<ServiceType>({})

// 综合判断设备运行状态
function isMachineOK(machine: Machine) {
  const THRESHOLD = 0.9

  if (!machine.online) return false

  if (machine.cpuUsage && machine.cpuUsage > THRESHOLD) return false

  if (machine.ramUsage && machine.ramUsage > THRESHOLD) return false

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

// 添加机器，弹出弹窗
function handleAddClick() {
  curMachine.value = {}
  showModal.value = true
}

// 修改机器，弹出弹窗
function handleEditClick(data: Machine) {
  curMachine.value = data
  showModal.value = true
}

// 查看机器详情
function handleShowDetailClick(data: Machine) {
  const currentStore = useCurrentStore()
  currentStore.setCurMachine(data)
  router.push('/main/machine-detail')
}

// 关闭新增/修改弹窗
function closeModal() {
  showModal.value = false
}

// 增删改后刷新数据
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

// 获取类型枚举数据
async function fetchAllTypesData() {
  const groups = await fetchAllMachineGroupData()
  machineGroups.setMachineGroups(groups)

  const types1 = await fetchAllMachineTypeData()
  machineTypes.setMachineTypes(types1)

  const types2 = await fetchAllServiceTypeData()
  serviceTypes.setServiceTypes(types2)
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

onMounted(async () => {
  loadingWrapper(async () => {
    await fetchAllTypesData()

    const response = await fetchAllMachineData()
    machines.value = response
  })
})
</script>
