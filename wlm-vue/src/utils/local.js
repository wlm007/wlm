import store from '@/store'

export default {
  /**
   * 从localStorage获取数据(区分嵌入页面与正常页面)
   * @param key     key
   *
   *  */
  localStorageGetItem (key) {
    let value = null
    try {
      const isEmbed = store.state.nav.isEmbed
      if (isEmbed) {
        value = localStorage.getItem(`embed-${key}`)
      } else {
        value = localStorage.getItem(key)
      }
    } catch (e) {
      value = localStorage.getItem(key)
    }
    return value
  },
  /**
   * 从localStorage获取数据(区分嵌入页面与正常页面)
   * @param key     key
   *
   *  */
  localStorageSetItem (key, value) {
    const isEmbed = store.state.nav.isEmbed
    if (isEmbed) {
      localStorage.setItem(`embed-${key}`, value)
    } else {
      localStorage.setItem(key, value)
    }
  }
}
