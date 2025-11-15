import {ref} from 'vue';
import {defineStore} from 'pinia';

export const useMachineGroupsStore = defineStore("machineGroups", () => {
  const machineGroups = ref<MachineGroup[]>([]);

  function setMachineGroups(datas: MachineGroup[]) {
    machineGroups.value = [...datas]
  }

  return {machineGroups, setMachineGroups}
})

export const useMachineTypesStore = defineStore("machineTypes", () => {
  const machineTypes = ref<MachineType[]>([]);

  function setMachineTypes(datas: MachineType[]) {
    machineTypes.value = [...datas]
  }

  return {machineTypes, setMachineTypes}
})

export const useServiceTypesStore = defineStore("serviceTypes", () => {
  const serviceTypes = ref<ServiceType[]>([]);

  function setServiceTypes(datas: ServiceType[]) {
    serviceTypes.value = [...datas]
  }

  return {serviceTypes, setServiceTypes}
})
