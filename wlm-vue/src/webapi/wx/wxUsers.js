import webapi from '../api'

export default {

  list (pageNo, pageSize) {
    return webapi.get(`/wx/get_user_list?pageNo=${pageNo}&pageSize=${pageSize}`)
  },
  getBlackList (pageNo, pageSize) {
    return webapi.get(`/wx/get_black_list?pageNo=${pageNo}&pageSize=${pageSize}`)
  },
  userRemark (openId, remark) {
    return webapi.get(`/wx/user_remark?openId=${openId}&remark=${remark}`)
  },
  addUserSign (signName) {
    return webapi.get(`/wx/add_user_sign?signName=${signName}`)
  },
  getUserSignlist (pageNo, pageSize) {
    return webapi.get(`/wx/get_user_sign_list?pageNo=${pageNo}&pageSize=${pageSize}`)
  },
  editUserSign (id, signName) {
    return webapi.get(`/wx/edit_user_sign?id=${id}&signName=${signName}`)
  },
  deleteUserSign (id) {
    return webapi.get(`/wx/delete_user_sign/${id}`)
  }
}
