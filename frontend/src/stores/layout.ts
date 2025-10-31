import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useMenuCollapseStore = defineStore('collapsed', () => {
  const collapsed = ref(false)
  function toggle() {
    collapsed.value = !collapsed.value
  }

  return { collapsed, toggle }
})
