<template>
  <div class="home_index">
    <el-container>
      <el-header>
        <!-- <h3>天下武功，唯快不破</h3> -->
        <div class="home_header_user_info">
          <el-button type="text" @click="refrash">刷新</el-button>
          <span>欢迎您 {{userInfo.username}}</span>
          <el-button type="text" @click="logout">退出</el-button>
        </div>
      </el-header>
      <el-main class="home_main">
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
            <el-main class="components_main">
              <component v-if="isShow" :is="menuId"></component>
            </el-main>
          </el-container>
        </el-container>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import api from '@/webapi'
import _ from 'lodash'
import mqtt from 'mqtt'

export default {
  // 添加菜单界面  其中key为数据库中指定的menu_fun
  components: {
    deptIndex: () => ({ component: import('@/views/dept/index.vue') }),
    userIndex: () => ({ component: import('@/views/user/index.vue') }),
    roleIndex: () => ({ component: import('@/views/role/index.vue') }),
    menuIndex: () => ({ component: import('@/views/menu/index.vue') }),
    focusUsers: () => ({ component: import('@/views/wx/users/index.vue') }),
    userSign: () => ({ component: import('@/views/wx/users/signs.vue') })
  },
  name: 'HelloWorld',
  data () {
    return {
      isShow: true,
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
      api.sysUser.logout()
      sessionStorage.removeItem('user')
      sessionStorage.removeItem('jwt')
      this.$router.push('/login')
      this.$message.success('退出成功')
    },
    refrash () {
      this.isShow = false
      setTimeout(() => {
        this.isShow = true
      }, 500)
    },
    mqttConn () {
      this.client = mqtt.connect('ws://localhost:5672/mqtt', {
        username: 'guest',
        password: 'guest'
      })
      this.client.on('connect', e => {
        console.log('连接成功', e)
        this.client.subscribe(this.mtopic, err => {
          if (!err) {
            console.log('订阅成功:' + this.mtopic)
          }
        })
      })
      this.client.on('message', (topic, message) => {
        console.log('接受消息成功, 主题为:', topic)
        this.msg = message
      })
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
.home_main {
  padding: 0;
  overflow: hidden;
}
.home_header_user_info {
  position: absolute;
  right: 20px;
  top: 10px;
}
.components_main {
  width: 100%;
  height: 100%;
  padding: 20px;
  overflow: hidden;
}
</style>

<style>
.el-header, .el-footer {
  background-color: #B3C0D1;
  color: #333;
}

.el-aside {
  background-color: #D3DCE6;
  color: #333;
}

.el-main {
  height: 100%;
  width: 100%;
  background-color: #E9EEF3;
  color: #333;
}

.home_index .el-container {
  height: 100%;
}

</style>
