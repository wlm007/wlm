<template>
  <div class="home_index">
    <el-container>
      <el-header>
        <h3>天下武功，唯快不破</h3>
        <div class="home_header_user_info">
          <span>欢迎您 {{userInfo.username}}</span>
          <el-button type="text" @click="logout">退出</el-button>
        </div>
      </el-header>
      <el-main>
        <el-container>
          <el-aside width="200px">
            <el-menu>
              <template v-for="item in menuList">
                <el-submenu :index="`${item.id}`" :key="item.id">
                  <template slot="title">{{ item.menuName }}</template>
                  <el-menu-item v-for="child in item.children" :key="child.id" :index="`${child.id}`" @click="toIndexView(child.menuFun)">{{ child.menuName }}</el-menu-item>
                </el-submenu>
              </template>
            </el-menu>
          </el-aside>
          <el-container>
            <el-main>
              <component :is="menuId"></component>
            </el-main>
            <el-footer>Footer</el-footer>
          </el-container>
        </el-container>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import api from '@/webapi'
import _ from 'lodash'

export default {
  components: {
    deptIndex: () => ({ component: import('@/views/dept/index.vue') }),
    userIndex: () => ({ component: import('@/views/user/index.vue') }),
    roleIndex: () => ({ component: import('@/views/role/index.vue') })
  },
  name: 'HelloWorld',
  data () {
    return {
      menuId: '',
      menuList: [],
      userInfo: {
        username: ''
      }
    }
  },
  async mounted () {
    const u = JSON.parse(sessionStorage.getItem('user'))
    if (u) this.userInfo = u
    const res = await api.sysMenu.list()
    if (res.data.code === 200) {
      _.forEach(res.data.data, item => {
        if (item.menuType === 0) {
          item.children = []
          _.forEach(res.data.data, child => {
            if (child.parentId === item.id) {
              item.children.push(child)
            }
          })
          this.menuList.push(item)
        }
      })
    }
  },
  methods: {
    toIndexView (viewName) {
      this.menuId = viewName
    },
    logout () {
      sessionStorage.removeItem('user')
      sessionStorage.removeItem('jwt')
      this.$router.push('/login')
      this.$message.success('退出成功')
      api.sysUser.logout()
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

.home_header_user_info {
  position: absolute;
  right: 20px;
  top: 0px;
}
</style>
