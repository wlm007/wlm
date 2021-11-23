import webapi from './api'

export default {
  // 登录
  login (params, config) {
    return webapi.post(`/sysUser/login`, params, config)
  },
  logout () {
    return webapi.post('/logout')
  },
  register (params, config) {
    return webapi.post(`/sysUser/register`, params, config)
  },
  getOne (userId) {
    return webapi.get(`/sysUser/getOne/${userId}`)
  },
  getList (params, config) {
    return webapi.post(`/sysUser/list`, params, config)
  },
  update (params, config) {
    return webapi.post(`/sysUser/update`, params, config)
  },
  deleteUser (userId) {
    return webapi.get(`/sysUser/delete/${userId}`)
  }
}
