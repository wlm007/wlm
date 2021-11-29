import api from './api'

export default {
  // 获取所有角色
  list (params, config) {
    return api.post(`/sysRole/list`, params, config)
  },
  save (params, config) {
    return api.post(`/sysRole/save`, params, config)
  },
  delete (id) {
    return api.get(`/sysRole/delete/${id}`)
  }
}
