<script setup lang="ts">
import { reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import Login from '@/entity/user/Login'
import axios from 'axios'
const state = reactive({
  login: new Login()
})
function dologin() {
  axios
    .post('/api/auth/login', state.login)
    .then((response) => {
      // 로그인 성공 시 처리
      console.log('로그인 성공:', response.data)
      // 추가적인 로직 (예: 리다이렉션, 사용자 정보 저장 등)
    })
    .catch((error) => {
      // 로그인 실패 시 처리
      console.error('로그인 실패:', error.response ? error.response.data : error.message)
      // 사용자에게 에러 메시지 표시 등
    })
}
</script>

<template>
  <el-row>
    <el-col :span="10" :offset="7">
      <el-form label-position="top">
        <el-form-item label="이메일">
          <el-input v-model="state.login.email"></el-input>
        </el-form-item>

        <el-form-item label="비밀번호">
          <el-input type="password" v-model="state.login.password"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" style="width: 100%" @click="dologin()">로그인</el-button>
        </el-form-item>
      </el-form>
    </el-col>
  </el-row>
</template>

<style scoped lang="scss"></style>
<script setup lang="ts"></script>
