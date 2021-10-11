import Http from '@/utils/http'

const API = {

  pageList: params => Http.postPayload('/sysDictType/pageList', params), // 分页查询
  add: params => Http.postPayload('/sysDictType/add', params), // 添加类型
  deleteIds: params => Http.postPayload('/sysDictType/deleteBatch', params) // 批量删除
}
export default API
