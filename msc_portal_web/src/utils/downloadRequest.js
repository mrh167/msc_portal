import axios from 'axios'
import { Message, Loading } from 'element-ui'
// 创建axios实例
const service = axios.create({
  // eslint-disable-next-line no-undef
  baseURL: process.env.NODE_ENV === 'development' ? process.env.BASE_API : '', // api的base_url
  timeout: 1200000 // 请求超时时间长一些
})

export function exportExcel(url, options = {}) {
  if (!url) {
    Message({
      message: '下载URL不能是空',
      type: 'error',
      duration: 3 * 1000
    })
  }
  const loading = Loading.service({
    lock: true,
    text: '下载中,请等待',
    spinner: 'lui-icon-loading',
    background: 'rgba(0, 0, 0, 0.6)',
    customClass: 'downloadClassLoading'
  })
  return new Promise((resolve, reject) => {
    service.defaults.headers['content-type'] = 'application/json;charset=UTF-8'
    service.defaults.headers['X-Requested-With'] = 'XMLHttpRequest'
    service({
      method: 'post',
      url: url, // 请求地址
      data: options, // 参数
      responseType: 'blob' // 表明返回服务器返回的数据类型
    }).then(
      response => {
        loading.close()
        const data = response.data
        // 有可能下载失败，返回{code: '500'},但responseType: 'blob'会把data强制转为blob，导致下载undefined.excel
        // 解决：将已转为blob类型的data转回json格式，判断是否下载成功

        // 获取文件名
        let fileName = response.headers['filename'] || new Date().getTime() + '.xls' || new Date().getTime() + '.xlsx'
        fileName = decodeURIComponent(fileName)
        // 兼容ie11
        if (window.navigator.msSaveOrOpenBlob) {
          try {
            const blobObject = new Blob([data])
            window.navigator.msSaveOrOpenBlob(blobObject, fileName)
          } catch (e) {
            console.error(e)
          }
          return
        }
        const url = window.URL.createObjectURL(new Blob([data]))
        const link = document.createElement('a')
        link.style.display = 'none'
        link.href = url
        link.setAttribute('download', fileName)
        document.body.appendChild(link)
        link.click()
        resolve(fileName)
      },
      err => {
        loading.close()
        reject(err)
      }
    )
  })
}
