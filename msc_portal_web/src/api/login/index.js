import Http from '@/utils/http'

const API = {
  login: params => Http.postPayload('/login', params),
  getInfo: params => Http.postPayload('/getInfo', params)
}

export default API
