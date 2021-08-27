import Vue from 'vue'
import Router from 'vue-router'
import index from '@/views/index'
import login from '@/components/login'
import register from '@/components/register'

Vue.use(Router)

const router = new Router({
  routes: [
    {
      path: '/',
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
    }
  ]
})

router.beforeEach((to, from, next) => {
  if (to.meta.requireAuth) {
    const userInfo = JSON.parse(sessionStorage.getItem('user'))
    if (userInfo && userInfo.token) {
      next()
    } else {
      next({
        path: '/login'
      })
    }
  } else {
    next()
  }
})

export default router
