import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useCurrentStore = defineStore('current', () => {
  const curMachine = ref<Machine | undefined>(undefined)

  function setCurMachine(machine: Machine | undefined) {
    curMachine.value = machine
  }

  return { curMachine, setCurMachine }
})
