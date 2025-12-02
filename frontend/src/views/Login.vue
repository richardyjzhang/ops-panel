<template>
  <div class="min-h-screen bg-gray-100 flex items-center justify-center p-4">
    <div class="w-full max-w-md bg-white rounded-lg shadow-lg p-8">
      <div class="text-center mb-8">
        <div class="flex items-center justify-center w-16 h-16 bg-white rounded-full mx-auto mb-4 shadow-md">
          <img src="@/assets/logo-green.png" class="w-10 h-10 rounded" alt="Logo" />
        </div>
        <h1 class="text-2xl font-bold text-gray-800">服务器状态监控</h1>
        <p class="text-gray-500 mt-2">请登录以继续</p>
      </div>
      
      <form @submit.prevent="handleLogin">
        <div class="mb-4">
          <label for="username" class="block text-sm font-medium text-gray-700 mb-1">用户名</label>
          <input
            type="text"
            id="username"
            v-model="username"
            placeholder="请输入用户名"
            class="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-[#18a058] focus:border-transparent"
            required
          />
        </div>
        
        <div class="mb-6">
          <label for="password" class="block text-sm font-medium text-gray-700 mb-1">密码</label>
          <input
            type="password"
            id="password"
            v-model="password"
            placeholder="请输入密码"
            class="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-[#18a058] focus:border-transparent"
            required
          />
        </div>
        
        <button
          type="submit"
          class="w-full bg-[#18a058] hover:bg-[#36ad6a] text-white font-medium py-2 px-4 rounded-md focus:outline-none focus:ring-2 focus:ring-[#18a058] focus:ring-offset-2 cursor-pointer"
        >
          <span>登录</span>
        </button>
        
        <div class="mt-6 text-center text-sm text-gray-500">
          <p>忘记密码？<a href="#" class="text-[#18a058] hover:underline">联系管理员</a></p>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useNotification  } from 'naive-ui'
import { login } from '@/services/AuthService'

const router = useRouter()
const notification = useNotification()
const username = ref('')
const password = ref('')


async function handleLogin() {
  
    const response = await login({
      username: username.value,
      password: password.value
    })
    
    if (response.success) {
      notification['success']({
        content: '登录成功',
        meta: '欢迎使用本系统'
      })
      router.push('/main')
    } else {
      notification['error']({
        content: '登录失败',
        meta: '请检查用户名和密码',
        duration: 2500
      })
    }
}
</script>