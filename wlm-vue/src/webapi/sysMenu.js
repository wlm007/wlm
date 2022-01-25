import webapi from './api'

export default {

  list () {
    return webapi.get(`/sysMenu/list`)
  },
  delete (id) {
    return webapi.get(`/sysMenu/delete/${id}`)
  },
  save (params, config) {
    return webapi.post(`/sysMenu/save`, params, config)
  }
}
