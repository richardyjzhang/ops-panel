<template>
  <div class="p-4">
    <div class="flex justify-end">
      <n-button @click="handleBack">
        <template #icon>
          <n-icon>
            <arrow-left />
          </n-icon>
        </template>
        返回
      </n-button>
    </div>

    <div class="mb-6"></div>

    <!-- 机器详情内容 -->
    <n-card :title="machine ? machine.name : '计算设备详情'">
      <div v-if="machine" class="flex gap-20 items-stretch">
        <!-- 左列：设备信息 -->
        <div >
          <n-descriptions label-placement="left" :column="1">
            <n-descriptions-item label="设备ID">
              {{ machine.id }}
            </n-descriptions-item>
            <n-descriptions-item label="所在分组名">
              {{ getGroupName(machine.groupId) }}
            </n-descriptions-item>
            <n-descriptions-item label="设备类型名">
              {{ getTypeName(machine.typeId) }}
            </n-descriptions-item>
          </n-descriptions>
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
      <n-empty v-else description="暂无设备信息" />
    </n-card>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import { ref, onMounted, computed } from 'vue'
import { NCard, NButton, NIcon, NEmpty, NDescriptions, NDescriptionsItem, NProgress } from 'naive-ui'
import { ArrowLeft } from '@vicons/tabler'
import { useCurrentStore } from '@/stores/current'
import { useMachineGroupsStore, useMachineTypesStore } from '@/stores/types'

// 路由相关
const router = useRouter()

// 从store中获取数据
const currentStore = useCurrentStore()
const machineGroupsStore = useMachineGroupsStore()
const machineTypesStore = useMachineTypesStore()
const machine = ref<Machine | null>(null)

// 获取分组名
function getGroupName(groupId?: number): string {
  if (!groupId) return '未分组'
  const group = machineGroupsStore.machineGroups.find(g => g.id === groupId)
  return group?.name || '未知分组'
}

// 获取设备类型名
function getTypeName(typeId?: number): string {
  if (!typeId) return '未知类型'
  const type = machineTypesStore.machineTypes.find(t => t.id === typeId)
  return type?.name || '未知类型'
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
  if (value <= 60) return 'info'    // 蓝色
  if (value <= 80) return 'warning' // 黄色
  return 'error'                    // 红色
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
