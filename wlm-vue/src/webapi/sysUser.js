import webapi from './api'

export default {
  // 登录
  login (params, config) {
    return webapi.post(`/sysUser/login`, params, config)
  },
  register (params, config) {
    return webapi.post(`/sysUser/register`, params, config)
  },
  getOne (userId) {
    return webapi.get(`/sysUser/getOne/${userId}`)
  }
}
