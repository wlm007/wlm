<template>
  <div class="home_index">
    <el-container>
      <el-header>surprise mother fuck</el-header>
      <el-main>
        <el-container>
          <el-aside width="200px">
            <el-menu :default-openeds="['1', '3']">
              <el-submenu index="1">
                <template slot="title"><i class="el-icon-message"></i>标签1</template>
                <el-menu-item index="1-1">选项1</el-menu-item>
                <el-menu-item index="1-2">选项2</el-menu-item>
              </el-submenu>
              <el-submenu index="2">
                <template slot="title"><i class="el-icon-menu"></i>标签2</template>
                <el-menu-item index="2-1">选项1</el-menu-item>
                <el-menu-item index="2-2">选项2</el-menu-item>
              </el-submenu>
              <el-submenu index="3">
                <template slot="title"><i class="el-icon-setting"></i>系统管理</template>
                <el-menu-item index="3-1" @click="toDeptList()">部门管理</el-menu-item>
                <el-menu-item index="3-2">角色管理</el-menu-item>
                <el-menu-item index="3-3">用户管理</el-menu-item>
              </el-submenu>
            </el-menu>
          </el-aside>
          <el-container>
            <el-main></el-main>
            <el-footer>Footer</el-footer>
          </el-container>
        </el-container>
      </el-main>
    </el-container>
  </div>
</template>

<script>
export default {
  name: 'HelloWorld',
  data () {
    return {
      msg: 'Welcome to Your Vue.js App'
    }
  },
  methods: {
    toDeptList () {
      console.log('前往部门管理')
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.home_index {
  width: 100%;
  height: 100%;
}
</style>

<style>
.el-header, .el-footer {
  background-color: #B3C0D1;
  color: #333;
  text-align: center;
  line-height: 60px;
}

.el-aside {
  background-color: #D3DCE6;
  color: #333;
  text-align: center;
  line-height: 200px;
}

.el-main {
  background-color: #E9EEF3;
  color: #333;
  text-align: center;
  line-height: 160px;
}

.home_index .el-container {
  height: 100%;
}

.home_index .el-main {
  padding: 0;
}

.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
  line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
  line-height: 320px;
}
</style>
