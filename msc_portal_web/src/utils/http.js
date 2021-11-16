/* eslint-disable no-undef */
/* eslint-disable eqeqeq */
import axios from 'axios'
import qs from 'qs'
import utils from '@/utils/utils'
import { getToken, setToken, removeToken } from '@/utils/auth'
import store from '@/store'

window.qs = qs
let baseContextUrl = '/api/'
// console.log(`process.env.NODE_ENV: ${process.env.NODE_ENV}`)
if (process.env.NODE_ENV === 'mock') {
  baseContextUrl = '/api/'
} else if (process.env.NODE_ENV === 'production') {
  baseContextUrl = '/api/'
} else if (process.env.NODE_ENV === 'development') {
  // baseContextUrl = 'http://11.50.64.241/api/'
  // baseContextUrl = 'http://11.91.152.190/api/'
  baseContextUrl = 'http://localhost:8068/api/'
  // baseContextUrl = 'http://192.168.137.81/api/'
  // baseContextUrl = 'http://192.168.137.17/api/'
  // baseContextUrl = 'http://10.170.233.98/api/'
} else {
  baseContextUrl = '/api/'
}
axios.defaults.timeout = 60000
axios.defaults.baseURL = baseContextUrl

// http request 拦截器
axios.interceptors.request.use(
  config => {
    // 让每个请求携带token--['Authorization']为自定义key 请根据实际情况自行修改
    if (window.sessionStorage.getItem('msc') !== null) {
      config.headers['Authorization'] = 'msc ' + window.sessionStorage.getItem('msc')
      console.log('tag', qs.stringify('msc ' + window.sessionStorage.getItem('msc')))
    }
    // NProgress.start()
    if (config.method === post) {
      if (config.data._useRequestBody) {
        // 如果_useRequestBody=true, 支持Request Payload，
        // SpringMVC Controller 中需要使用@RequestBody
        delete config.data._useRequestBody
        config.headers = {
          'Content-Type': 'application/json;charset=UTF-8'
        }
      } else {
        config.data = qs.stringify(config.data, { indices: false })
        config.headers = {
          'Content-Type': 'multipart/form-data;charset=UTF-8'
          // 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
        }
      }
    } else if (config.method === 'get') {
      const newParams = {}
      for (const key in config.params) {
        newParams[key] = encodeURIComponent(config.params[key])
      }
      config.params = newParams
      config.headers = {
        'Content-Type': 'application/json;charset=UTF-8',
        'Authorization': 'msc ' + window.sessionStorage.getItem('msc')
      }
    }

    return config
  },
  error => Promise.reject(error)
)

// http response 拦截器
axios.interceptors.response.use(
  response =>
    // NProgress.done()
    response,
  error => Promise.reject(error)
)

/**
 * 封装get方法
 * @param url
 * @param data
 * @returns {Promise}
 */

const handleResponse = (response, resolve, reject) => {
  // debugger
  const data = response.data
  // 首先判断接口返回的status是否为200,200---成功，否则异常
  if (response.status === 200) {
    if (data.success === true) {
      return resolve(data)
    } else if (data.success === false) {
      var httpCode = data.httpCode
      // console.log(httpCode)
      if (httpCode == 401) {
        // var url = window.location.href
        utils.clearErpCookie()
        // 目前是想获取'jdwl.com'和'jdl.cn'
        // window.location.href = 'https://sso.jdl.cn/sso/login?ReturnUrl=' + encodeURIComponent(url)
      } else if (httpCode == 403) {
        window.location.href = location.protocol + '//' + window.location.host + '/#/403'
      } else {
        return reject(data.errMessage)
      }
    }
  } else {
    return reject('服务端返回错误信息')
  }
}

const get = function get(url, params = {}) {
  return new Promise((resolve, reject) => {
    axios
      .get(url, {
        params
      })
      .then(response => handleResponse(response, resolve, reject))
      .catch(err => {
        reject(err)
      })
  })
}

/**
 * 封装post请求(以formData方式post提交对象)
 * @param url
 * @param params
 * @returns {Promise}
 */
const post = function post(url, params = {}) {
  return new Promise((resolve, reject) => {
    axios.post(url, params).then(
      response => handleResponse(response, resolve, reject),
      err => {
        resolve(err)
        // reject(err)
      }
    )
  })
}

/**
 * 封装post请求(以payload方式post提交Json对象)
 * @param url
 * @param params
 * @returns {Promise}
 */
const postPayload = function post(url, params = {}) {
  if (params) {
    params._useRequestBody = true
  }
  return new Promise((resolve, reject) => {
    axios.post(url, params).then(
      response =>
        //  debugger
        handleResponse(response, resolve, reject),
      err => {
        resolve(err)
        // reject(err)
      }
    )
  })
}

/**
 * 封装patch请求
 * @param url
 * @param data
 * @returns {Promise}
 */

const patch = function patch(url, data = {}) {
  return new Promise((resolve, reject) => {
    axios.patch(url, data).then(
      response => handleResponse(response, resolve, reject),
      err => {
        reject(err)
      }
    )
  })
}
/**
 * 封装upload请求
 * @param url
 * @param data
 * @returns {Promise}
 */
/* const upload = function upload(url, data = {}) {
  const instance = axios.create()
  return new Promise((resolve, reject) => {
    instance.post(url, data)
      .then(response => {
        if (response.headers['content-type'] === 'application/x-www-form-urlencoded;charset=UTF-8') {
          if (data.success === true) {
            return resolve(data)
          }
          return reject(new Error(data.msg || '服务端返回错误信息'))

        }
      }, err => {
        reject(err)
      })
  })
}*/

/**
 * 封装put请求
 * @param url
 * @param data
 * @returns {Promise}
 */

const put = function put(url, data = {}) {
  return new Promise((resolve, reject) => {
    axios.put(url, data).then(
      response => handleResponse(response, resolve, reject),
      err => {
        reject(err)
      }
    )
  })
}
/**
 * 封装上传图片upload的方法
 * @param url
 * @param data
 * @returns {Promise}
 */
const upload = function upload(url, data = {}) {
  const instance = axios.create()
  return new Promise((resolve, reject) => {
    instance.post(url, data).then(
      response => {
        if (response.data) resolve(response.data)
      },
      err => {
        reject(err)
      }
    )
  })
}
export default {
  post,
  postPayload,
  get,
  patch,
  put,
  upload,
  baseContextUrl
}
