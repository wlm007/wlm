<template>
  <div class="login">
    <div class="login_form">
      <el-form ref="loginForm" v-model="form" label-width="80px">
        <el-form-item label="用户名">
          <el-input v-model="form.username"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input type="password" v-model="form.password"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="login">登录</el-button>
          <el-button type="text" @click="toRegister">去注册</el-button>
        </el-form-item>
      </el-form>
    </div>
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
  mounted () {
    this.getUser()
  },
  methods: {
    async getUser () {
      const res = await api.sysUser.getOne(1)
      console.log(res)
    },
    async login () {
      const res = await api.sysUser.login(this.form, {dismissLoading: false})
      if (res.data.code === 200) {
        sessionStorage.setItem('user', JSON.stringify(res.data.data))
        this.$router.push('/index')
      } else {
        this.$message.error(res.data.msg)
      }
    },
    toRegister () {
      this.$router.push('/register')
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.login {
  position: relative;
  height: 100%;
  background-size: cover;
  background-image: url('../../static/images/index.jpg');
}
.login_form {
  width: 400px;
  position: absolute;
  top: 40%;
  right: 10%;
}
</style>
