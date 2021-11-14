import Http from '@/utils/http'

const API = {
  findAllIndex: params => Http.postPayload('/testEsData/findAllIndex2', params) // 分页查询

}
export default API
