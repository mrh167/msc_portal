import Http from '@/utils/http'

const API = {
  pageList: params => Http.postPayload('/pageList', params), // 分页查询
  checkAccount: params => Http.get('/checkAccount', params), // 校验账号是否重复
  addUser: params => Http.postPayload('/addUser', params), // 添加用户
  deleteIds: params => Http.postPayload('/deleteIds', params), // 批量删除
  editUser: params => Http.postPayload('/editUser', params) // 编辑数据回显
}
export default API
