<template>
  <n-spin :show="loading">
    <div class="p-4">
      <div class="flex justify-between items-center mb-6">
        <h2 class="text-lg font-medium">{{ machine ? machine.name : '计算设备详情' }}</h2>
        <n-button @click="handleBack">
          <template #icon>
            <n-icon>
              <arrow-left />
            </n-icon>
          </template>
          返回
        </n-button>
      </div>
      <div v-if="machine" class="flex gap-20 items-stretch">
        <!-- 左列：设备信息 -->
        <div class="flex flex-col gap-4">
          <div class="flex gap-4">
            <span class="text-sm text-gray-600 font-medium min-w-[100px]">设备编号</span>
            <span class="text-sm text-gray-900">{{ machine.id }}</span>
          </div>
          <div class="flex gap-4">
            <span class="text-sm text-gray-600 font-medium min-w-[100px]">所在分组</span>
            <span class="text-sm text-gray-900">{{ getGroupName(machine.groupId) }}</span>
          </div>
          <div class="flex gap-4">
            <span class="text-sm text-gray-600 font-medium min-w-[100px]">设备类型</span>
            <span class="text-sm text-gray-900">{{ getMachineTypeName(machine.typeId) }}</span>
          </div>
        </div>

        <!-- 右列：资源使用情况 -->
        <div class="flex flex-1 flex-col gap-3 justify-center">
          <div class="flex flex-col gap-1">
            <div class="flex gap-4 items-center">
              <span class="text-sm text-gray-600 font-medium">CPU使用率</span>
            </div>
            <n-progress
              type="line"
              :percentage="machine.cpuUsage ? machine.cpuUsage * 100 : 0"
              indicator-placement="inside"
              :status="getProgressStatus(machine.cpuUsage)"
            />
          </div>
          <div class="flex flex-col gap-1">
            <div class="flex gap-4 items-center">
              <span class="text-sm text-gray-600 font-medium">内存使用率</span>
            </div>
            <n-progress
              type="line"
              :percentage="machine.ramUsage ? machine.ramUsage * 100 : 0"
              indicator-placement="inside"
              :status="getProgressStatus(machine.ramUsage)"
            />
          </div>
        </div>
      </div>

      <!-- 分割线 -->
      <div class="w-full h-[1px] bg-gray-300 my-12"></div>

      <!-- 硬盘使用情况 -->
      <div v-if="machine">
        <h2 class="text-lg font-medium mb-4">硬盘使用情况</h2>
        <n-button type="primary" size="small" @click="handleDiskAddClick">
          <template #icon>
            <n-icon>
              <plus />
            </n-icon>
          </template>
          新增
        </n-button>
        <div class="w-full my-2" />
        <n-data-table
          :columns="diskColumns"
          :data="machine.disks || []"
          :bordered="false"
          size="small"
        />
      </div>

      <!-- 分割线 -->
      <div class="w-full h-[1px] bg-gray-300 my-12"></div>

      <!-- 服务运行情况 -->
      <div v-if="machine">
        <h2 class="text-lg font-medium mb-4">服务运行情况</h2>
        <n-button type="primary" size="small" @click="handleServiceAddClick">
          <template #icon>
            <n-icon>
              <plus />
            </n-icon>
          </template>
          新增
        </n-button>
        <div class="w-full my-2" />
        <n-data-table
          :columns="serviceColumns"
          :data="machine.services || []"
          :bordered="false"
          size="small"
        />
      </div>
    </div>
    <n-modal :show="showDiskModal">
      <MyEasyForm
        :resource="`磁盘`"
        :cur-data="curMachineDisk"
        @add="addOneDisk"
        @update="updateOneDisk"
        @cancel="closeModal"
      />
    </n-modal>
    <n-modal :show="showServiceModal">
      <MachineServiceAddEditForm
        :resource="`服务`"
        :cur-data="curMachineService"
        @add="addOneService"
        @update="updateOneService"
        @cancel="closeModal"
      />
    </n-modal>
  </n-spin>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import { ref, onMounted, h } from 'vue'
import { NButton, NIcon, NModal, NProgress, NDataTable, NSpin, useLoadingBar } from 'naive-ui'
import type { DataTableColumns } from 'naive-ui'
import { ArrowLeft, Plus } from '@vicons/tabler'
import { useCurrentStore } from '@/stores/current'
import { useMachineGroupsStore, useMachineTypesStore, useServiceTypesStore } from '@/stores/types'
import {
  fetchOneMachineData,
  addOneMachineDiskData,
  updateOneMachineDiskData,
  deleteOneMachineDiskData,
  addOneMachineServiceData,
  updateOneMachineServiceData,
  deleteOneMachineServiceData,
} from '@/services/MachineService'
import MyTableAction from './components/MyTableAction.vue'
import MyEasyForm from './components/MyEasyForm.vue'
import MachineServiceAddEditForm from './components/MachineServiceAddEditForm.vue'

// 路由相关
const router = useRouter()

// 从store中获取数据
const currentStore = useCurrentStore()
const machineGroupsStore = useMachineGroupsStore()
const machineTypesStore = useMachineTypesStore()
const serviceTypesStore = useServiceTypesStore()

const machine = ref<Machine | null>(null)

const showDiskModal = ref(false)
const showServiceModal = ref(false)
const curMachineDisk = ref({})
const curMachineService = ref({})

const loadingBar = useLoadingBar()

const loading = ref(false)

// 获取分组名
function getGroupName(groupId?: number): string {
  if (!groupId) return '未分组'
  const group = machineGroupsStore.machineGroups.find((g) => g.id === groupId)
  return group?.name || '未知分组'
}

// 获取设备类型名
function getMachineTypeName(typeId?: number): string {
  if (!typeId) return '未知类型'
  const type = machineTypesStore.machineTypes.find((t) => t.id === typeId)
  return type?.name || '未知类型'
}

// 获取服务类型名
function getServiceTypeName(typeId?: number): string {
  if (!typeId) return '未知服务类型'
  const type = serviceTypesStore.serviceTypes.find((t) => t.id === typeId)
  return type?.name || '未知服务类型'
}

// 返回列表
function handleBack() {
  router.push('/main/machines')
}

// 根据CPU或内存占用的数值返回进度条状态
function getProgressStatus(value: number | undefined) {
  if (value === undefined) return 'error' // 缺失数据时显示错误状态

  value = value * 100
  if (value <= 30) return 'success' // 绿色
  if (value <= 60) return 'info' // 蓝色
  if (value <= 80) return 'warning' // 黄色
  return 'error' // 红色
}

// 硬盘数据表格列定义
const diskColumns: DataTableColumns<MachineDisk> = [
  {
    title: '硬盘ID',
    key: 'id',
    width: 100,
  },
  {
    title: '挂载路径',
    key: 'name',
    width: 200,
  },
  {
    title: '使用率',
    key: 'diskUsage',
    render(row: MachineDisk) {
      const usage = row.diskUsage ? row.diskUsage * 100 : 0
      return h('div', { class: 'flex grow items-center gap-2' }, [
        h('span', { class: 'text-gray-600 min-w-15' }, `${usage.toFixed(1)}%`),
        h(NProgress, {
          type: 'line',
          percentage: usage,
          status: getProgressStatus(row.diskUsage),
          showIndicator: false,
        }),
      ])
    },
  },
  {
    title: '操作',
    key: 'actions',
    width: 100,
    render: (row) => {
      return h(MyTableAction, {
        onEdit: () => {
          handleDiskEditClick(row)
        },
        onDelete: () => {
          deleteOneMachineDisk(row)
        },
      })
    },
  },
]

// 服务数据表格列定义
const serviceColumns: DataTableColumns<MachineService> = [
  {
    title: '服务ID',
    key: 'id',
    width: 100,
  },
  {
    title: '服务名称',
    key: 'name',
    width: 200,
  },
  {
    title: '服务类型',
    key: 'typeId',
    width: 200,
    render(row: MachineService) {
      return getServiceTypeName(row.typeId)
    },
  },
  {
    title: '运行状态',
    key: 'online',
    render(row: MachineService) {
      const isOnline = row.online || false
      return h('div', { class: 'flex items-center gap-2' }, [
        h('div', {
          class: `w-2 h-2 rounded-full ${isOnline ? 'bg-green-500' : 'bg-red-500'}`,
        }),
        h(
          'span',
          { class: isOnline ? 'text-green-600' : 'text-red-600' },
          isOnline ? '在线' : '离线',
        ),
      ])
    },
  },
  {
    title: '操作',
    key: 'actions',
    width: 100,
    render: (row) => {
      return h(MyTableAction, {
        onEdit: () => {
          handleServiceEditClick(row)
        },
        onDelete: () => {
          deleteOneMachineService(row)
        },
      })
    },
  },
]

async function loadingWrapper(actions: () => void) {
  loading.value = true
  loadingBar.start()
  await actions()
  loadingBar.finish()
  loading.value = false
}

// 关闭新增/编辑对话框
function closeModal() {
  showDiskModal.value = false
  showServiceModal.value = false
}

// 点击编辑，展开编辑磁盘对话框
function handleDiskEditClick(data: MachineDisk) {
  curMachineDisk.value = data
  showDiskModal.value = true
}

// 点击新增，展开新增磁盘对话框
function handleDiskAddClick() {
  curMachineDisk.value = {}
  showDiskModal.value = true
}

// 点击编辑，展开编辑服务对话框
function handleServiceEditClick(data: MachineService) {
  curMachineService.value = data
  showServiceModal.value = true
}

// 点击新增，展开添加服务对话框
function handleServiceAddClick() {
  curMachineService.value = {}
  showServiceModal.value = true
}

// 增删改后刷新数据
async function refreshData() {
  if (machine.value?.id) {
    const response = await fetchOneMachineData(machine.value.id)
    machine.value = response
  }
}

// 点击删除，删除一个磁盘
async function deleteOneMachineDisk(data: MachineDisk) {
  loadingWrapper(async () => {
    await deleteOneMachineDiskData(data)
    await refreshData()
  })
}

// 点击删除，删除一个服务
async function deleteOneMachineService(data: MachineService) {
  loadingWrapper(async () => {
    await deleteOneMachineServiceData(data)
    await refreshData()
  })
}

// 调用接口添加一个磁盘
async function addOneDisk(data: MachineDisk) {
  loadingWrapper(async () => {
    closeModal()
    data.diskUsage = 0.0
    data.machineId = machine.value?.id
    await addOneMachineDiskData(data)
    await refreshData()
  })
}

// 调用接口编辑一个磁盘
async function updateOneDisk(data: MachineDisk) {
  loadingWrapper(async () => {
    closeModal()
    await updateOneMachineDiskData(data)
    await refreshData()
  })
}

// 调用接口添加一个服务
async function addOneService(data: MachineService) {
  loadingWrapper(async () => {
    closeModal()
    data.online = false
    data.machineId = machine.value?.id
    await addOneMachineServiceData(data)
    await refreshData()
  })
}

// 调用接口编辑一个服务
async function updateOneService(data: MachineService) {
  loadingWrapper(async () => {
    closeModal()
    await updateOneMachineServiceData(data)
    await refreshData()
  })
}

onMounted(() => {
  // 从store获取当前机器信息
  const curMachine = currentStore.curMachine
  if (!curMachine) {
    // 如果store中没有机器信息，自动返回到列表页
    handleBack()
    return
  }
  machine.value = curMachine
})
</script>
