<template>
  <div class="w-full">
    <!-- 标签页头部 -->
    <div class="flex bg-gray-100 pt-1 pb-0">
      <!-- 隐藏滚动条但保持滚动功能 -->
      <div class="flex-shrink-0 flex">
        <div
          v-for="tab in tabs"
          :key="tab.path"
          class="flex items-center px-4 py-2 text-sm whitespace-nowrap cursor-pointer user-select-none relative"
          :class="activeTabPath === tab.path
            ? 'bg-white text-[#18a058] font-medium shadow-xs rounded-t-lg border-x border-t border-gray-300'
            : 'bg-gray-40 text-gray-700 hover:bg-gray-100 hover:text-[#18a058] rounded-t-lg border-x border-t border-gray-300 -mb-px'
          "
          @click="handleTabClick(tab.path)"
        >
          <span class="mr-2">{{ tab.label }}</span>
          <!-- 关闭按钮，只有一个标签页时不显示 -->
          <button
            v-if="tabs.length > 1"
            class="flex items-center justify-center w-5 h-5 text-gray-400 hover:bg-gray-200 hover:text-gray-600 hover:shadow-sm rounded-full transition-all duration-200 text-xs opacity-100"
            @click.stop="handleTabClose(tab.path)"
            title="关闭标签页"
          >
            ×
          </button>
        </div>
      </div>
    </div>
    <!-- 标签页内容 -->
    <div class="bg-white border border-gray-200 shadow-md">
      <slot></slot>
    </div>
  </div>
</template>

<script setup lang="ts">
import type { Tab } from '@/stores/tabs'

defineProps<{
  tabs: Tab[]
  activeTabPath: string
}>()

const emit = defineEmits<{
  (e: 'update', value: string): void
  (e: 'close', tabPath: string): void
}>()

// 处理标签页点击
function handleTabClick(tabPath: string) {
  emit('update', tabPath)
}

// 处理标签页关闭
function handleTabClose(tabPath: string) {
  emit('close', tabPath)
}
</script>