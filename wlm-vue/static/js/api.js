import axios from 'axios'
import { MessageBox } from 'element-ui'

const baseUrl = '/wlm'
const api = {
  get: (url, params, success) => {
    axios.get(baseUrl + url, params)
      .then(res => {
        if (res.status === 200) {
          success(res.data)
        } else {
          MessageBox.alert(res.statusText)
        }
      })
      .catch(error => {
        MessageBox.alert(error.statusText)
      })
  },
  post: (url, contentType, params, success) => {
    const config = {
      Headers: {'Content-Type': contentType || 'application/json'}
    }
    axios.post(baseUrl + url, params, config)
      .then(res => {
        if (res.status === 200) {
          success(res.data)
        } else {
          MessageBox.alert(res.statusText)
        }
      })
      .catch(error => {
        MessageBox.alert(error.statusText)
      })
  }
}

export default api
