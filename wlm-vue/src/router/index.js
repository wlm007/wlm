import Vue from 'vue'
import Router from 'vue-router'
import index from '@/views/index'
import login from '@/components/login'
import register from '@/components/register'
import wechatIndex from '@/views/wechat/index'

Vue.use(Router)

const router = new Router({
  mode: 'hash',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'main',
      component: index,
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/login',
      name: 'login',
      component: login
    },
    {
      path: '/register',
      name: 'register',
      component: register
    },
    {
      path: '/index',
      name: 'index',
      component: index,
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/wechat',
      name: 'wechatIndex',
      component: wechatIndex,
      meta: {
        requireAuth: false
      }
    }
  ]
})

export default router
