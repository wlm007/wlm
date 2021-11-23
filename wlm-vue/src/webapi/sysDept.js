import api from './api'

export default {
  // 获取所有部门
  list (params, config) {
    return api.post(`/sysDept/list`, params, config)
  },
  // 获取树状部门
  treeList () {
    return api.get(`/sysDept/listOfTree`)
  },
  save (params, config) {
    return api.post(`/sysDept/save`, params, config)
  },
  delete (id) {
    return api.get(`/sysDept/delete/${id}`)
  }
}
