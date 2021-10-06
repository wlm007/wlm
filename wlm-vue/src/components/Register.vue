<template>
  <div class="register">
    <el-form ref="registerForm" v-model="form" label-width="80px">
      <el-form-item label="用户名">
        <el-input v-model="form.username"></el-input>
      </el-form-item>
      <el-form-item label="密码">
        <el-input type="password" v-model="form.password"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="register">注册</el-button>
        <el-button type="text" @click="toLogin">去登录</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import api from '@/webapi'

export default {
  data () {
    return {
      form: {
        username: '',
        password: ''
      }
    }
  },
  methods: {
    register () {
      api.sysUser.register(this.form, {})
        .then(res => {
          if (res.data.code === 200) {
            this.$message('注册成功, 请登录')
            this.$router.push('/login')
          }
        })
        .catch()
    },
    toLogin () {
      this.$router.push('/login')
    }
  }
}
</script>

<style scoped>

</style>
