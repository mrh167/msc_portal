import Http from '@/utils/http'

const API = {
  appCenterSetIfShow: params => Http.postPayload('/updateStatus', params)
}

export default API
