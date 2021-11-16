import Http from '@/utils/http'

const API = {
  findAllIndex: params => Http.postPayload('/testEsData/findAllIndex2', params), // 分页查询
  matchAllQuery: params => Http.get('/testEsData/matchAllQuery', params)
}
export default API
