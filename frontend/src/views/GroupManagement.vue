<template>
    <n-data-table
        :data="data"
        :columns="columns"
        :bordered="false"
    />
</template>

<script setup lang="ts">
import type { DataTableColumns } from 'naive-ui';
import { NDataTable, NButton, useMessage } from 'naive-ui';
import { h } from 'vue';

interface MachineGroup {
    id: number
    name: string
}

const message = useMessage();
const columns: DataTableColumns<MachineGroup> = [
    {
        title: '分组ID',
        key: 'id',
    }, 
    {
        title: '分组名称',
        key: 'name',
    },
    {
        title: '操作',
        key: 'actions',
        render: (row) => {
            return h(NButton, {
                onClick: () => message.info(row.name),
            })
        }
    }
];

const data: MachineGroup[] = [
    {
        id: 1,
        name: '傻逼项目1',
    },
    {
        id: 2,
        name: '傻逼项目2',
    }
]

</script>