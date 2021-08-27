import axios from 'axios'
import { MessageBox } from 'element-ui'

// 创建全局api访问的实列
const webapi = axios.create({
  baseURL: 'http://localhost:8001/wlm',
  timeout: 5000,
  headers: {
    'Content-Type': 'application/json'
  }
})

webapi.interceptors.request.use(
  config => {
    const jwt = localStorage.getItem('jwt')
    if (jwt) {
      config.headers['x-access-token'] = jwt
    }
    return config
  },
  error => {
    error.env = 'request'
    return Promise.reject(error)
  }
)

webapi.interceptors.response.use(
  response => {
    // 响应数据统一处理
    if (response.data.code !== 200) {
      MessageBox.alert(response.data.msg, '提示', {
        type: 'error'
      })
    }
    return response
  },
  error => {
    // Do something with response error
    error.env = 'response'
    if (error.message === 'Network Error') {
      error.message = '抱歉，系统正在维护中，请稍后重试！'
    } else if (error.code === 'ECONNABORTED') {
      // 关闭所有loading
      // store.commit('closeLoadingAll');
      // return new Promise(() => {});
      error.message = '后端数据操作超时，请稍后重试！'
    } else {
      error.message = '未知错误，请联系管理员'
    }
    MessageBox.alert(error.message, '提示', {
      type: 'error'
    })
    return Promise.reject(error)
  }
)

export default webapi
