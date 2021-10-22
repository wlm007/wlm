import webapi from './api'

export default {

  list () {
    return webapi.get(`/sysMenu/list`)
  }
}
