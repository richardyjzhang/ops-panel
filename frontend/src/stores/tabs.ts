import { ref } from 'vue'
import { defineStore } from 'pinia'

// 标签页接口定义
export interface Tab {
  label: string
  path: string
}

export const useTabsStore = defineStore('tabs', () => {
  // 标签页列表
  const tabs = ref<Tab[]>([])
  
  // 当前激活的标签页路径
  const activeTabPath = ref<string>('')

  // 默认标签页配置
  const defaultTab: Tab = {
    label: '仪表盘',
    path: '/main/dashboard'
  }

  // 添加标签页
  function addTab(tab: Tab) {
    // 检查是否已存在相同路径的标签页
    const existingTab = tabs.value.find(t => t.path === tab.path)
    if (existingTab) {
      // 如果已存在，激活该标签页
      activeTabPath.value = existingTab.path
      return
    }
    
    // 添加新标签页
    tabs.value.push(tab)
    activeTabPath.value = tab.path
  }

  // 打开默认标签页
  function openDefaultTab() {
    // 如果默认标签页不存在，则添加
    if (!tabs.value.some(tab => tab.path === defaultTab.path)) {
      tabs.value.push(defaultTab)
    }
    activeTabPath.value = defaultTab.path
  }

  // 关闭标签页
  function closeTab(tabPath: string) {
    const index = tabs.value.findIndex(tab => tab.path === tabPath)
    if (index !== -1) {
      const isLastTab = tabs.value.length === 1
      tabs.value.splice(index, 1)
      
      // 如果关闭的是当前激活的标签页，需要激活其他标签页
      if (activeTabPath.value === tabPath) {
        if (tabs.value.length > 0) {
          // 优先激活右侧标签页，如果没有则激活左侧
          const newActiveTab = tabs.value[index] || tabs.value[index - 1]
          activeTabPath.value = newActiveTab?.path || ''
        } else {
          activeTabPath.value = ''
        }
      }
      
      // 如果关闭的是最后一个标签页，打开默认标签页
      if (isLastTab) {
        openDefaultTab()
      }
    }
  }

  // 关闭其他标签页
  function closeOtherTabs(currentTabPath: string) {
    tabs.value = tabs.value.filter(tab => tab.path === currentTabPath)
    activeTabPath.value = currentTabPath
    
    // 如果关闭后没有标签页，打开默认标签页
    if (tabs.value.length === 0) {
      openDefaultTab()
    }
  }

  // 关闭所有标签页
  function closeAllTabs() {
    tabs.value = []
    activeTabPath.value = ''
    
    // 关闭所有标签页后，打开默认标签页
    openDefaultTab()
  }

  // 关闭左侧标签页
  function closeLeftTabs(currentTabPath: string) {
    const currentIndex = tabs.value.findIndex(tab => tab.path === currentTabPath)
    if (currentIndex > 0) {
      tabs.value = tabs.value.slice(currentIndex)
    }
  }

  // 关闭右侧标签页
  function closeRightTabs(currentTabPath: string) {
    const currentIndex = tabs.value.findIndex(tab => tab.path === currentTabPath)
    if (currentIndex < tabs.value.length - 1) {
      tabs.value = tabs.value.slice(0, currentIndex + 1)
    }
  }

  // 设置当前激活的标签页
  function setActiveTab(tabPath: string) {
    const tab = tabs.value.find(t => t.path === tabPath)
    if (tab) {
      activeTabPath.value = tabPath
    }
  }

  // 更新标签页标题
  function updateTabLabel(tabPath: string, label: string) {
    const tab = tabs.value.find(t => t.path === tabPath)
    if (tab) {
      tab.label = label
    }
  }

  // 获取当前激活的标签页
  const activeTab = () => {
    return tabs.value.find(tab => tab.path === activeTabPath.value)
  }

  // 检查标签页是否存在
  const hasTab = (path: string) => {
    return tabs.value.some(tab => tab.path === path)
  }

  return {
    tabs,
    activeTabPath,
    defaultTab,
    addTab,
    openDefaultTab,
    closeTab,
    closeOtherTabs,
    closeAllTabs,
    closeLeftTabs,
    closeRightTabs,
    setActiveTab,
    updateTabLabel,
    activeTab,
    hasTab
  }
})