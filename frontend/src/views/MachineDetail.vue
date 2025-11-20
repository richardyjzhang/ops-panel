<template>
  <div class="machine-detail-page">
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
    <n-card title="计算设备详情">
      <div v-if="machine">
        <div class="info-item">
          <span class="info-label">设备名称：</span>
          <span class="info-value">{{ machine.name }}</span>
        </div>
      </div>
      <n-empty v-else description="暂无设备信息" />
    </n-card>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import { ref, onMounted } from 'vue'
import { NCard, NButton, NIcon, NEmpty } from 'naive-ui'
import { ArrowLeft } from '@vicons/tabler'
import { useCurrentStore } from '@/stores/current'

// 路由相关
const router = useRouter()

// 从store中获取当前机器数据
const currentStore = useCurrentStore()
const machine = ref<Machine | null>(null)

// 返回列表
function handleBack() {
  router.push('/main/machines')
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

<style scoped>
.machine-detail-page {
  padding: 16px;
}

.info-item {
  display: flex;
  margin-bottom: 12px;
}

.info-label {
  width: 100px;
  font-weight: 500;
  color: #666;
}

.info-value {
  flex: 1;
  color: #333;
}
</style>
