import * as _ from 'lodash'
// 获取url中的参数
const getParam = (param, back) => {
  back = typeof back === 'undefined' ? '--' : back
  try {
    var reg = new RegExp('(^|&)' + param + '=([^&]*)(&|$)')
    var r = window.location.search.substr(1).match(reg)
    if (r != null) return unescape(r[2])
    return back
  } catch (e) {
    return back
  }
}
// 邮箱校验
const checkEmail = email => {
  var pattern = /^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/
  if (email) {
    if (!pattern.test(email)) {
      return false
    }
    return true
  }
}
// 匹配双字节符
const getBLen = str => {
  if (str == null) return 0
  if (typeof str !== 'string') {
    str += ''
  }
  return str.replace(/[^x00-xff]/g, '01').length
}
// 求echars折线图最大最小值
const calMaxMin = arr => {
  var newArr = JSON.parse(JSON.stringify(arr))
  newArr.sort(function(a, b) {
    return a - b
  })
  var number = {
    max: '',
    min: ''
  }
  number.min = Math.floor(newArr[0] * 0.8)
  number.max = Math.ceil(newArr[newArr.length - 1] * 1.2)
  return number
}
// 转换为万并保留2位小数
const tenThousand = num => {
  if (num != null && num !== undefined && num > 10000) {
    num = num / 10000
    num = num.toFixed(2)
    return num + '  万件'
  }
  return num + '  件'
}
const tenThousand1 = num => {
  if (num != null && num !== undefined && num > 10000) {
    num = num / 10000
    num = num.toFixed(2)
    return num
  }
  return num
}
// 每三位数加','号
const toThousands = num => {
  return (num || 0).toString().replace(/(\d)(?=(?:\d{3})+$)/g, '$1,')
}
// 空函数
const emptyFn = () => {}

// 取折线图数据
const formatEcharsArr = arr => {
  const obj = {}
  obj.segment = []
  obj.number = []
  for (var i in arr) {
    let date = arr[i].segment
    date = date.replace(/-/g, '/')
    obj.segment.push(date)
    obj.number.push(arr[i].number)
  }
  return obj
}
// 反转义
const escape2Html = str => {
  var arrEntities = { lt: '<', gt: '>', nbsp: ' ', amp: '&', quot: '"' }
  return str.replace(/&(lt|gt|nbsp|amp|quot);/gi, function(all, t) {
    return arrEntities[t]
  })
}
// 处理经过转义的html字符串
const unescape = (str = '') =>
  str
    .replace(/&lt;/g, '<')
    .replace(/&gt;/g, '>')
    .replace(/&quot;/g, '"')
    .replace(/&amp;/g, '&')
    .replace(/&#39;/g, "'")

// 数值精确到几位
const numberFix = (str, fix = 2) => {
  if (typeof str === 'undefined') return str
  if (typeof str === 'string') {
    if (str === '') return str

    const num = parseFloat(str) || 0
    return num.toFixed(fix)
  }
  return str.toFixed(fix)
}

// 空字符串转换为-
const formatEmptyStr = str => (str === '' ? '-' : str)

// 字符串长度大于指定长度，则截取
const ellipse = (str, maxLen = 10) => {
  const len = str ? str.length : 0
  if (len > maxLen) {
    return `${str.substring(0, maxLen)}...`
  }
  return str
}

const isArray = obj => Object.prototype.toString.call(obj) === '[object Array]'

const isString = obj => Object.prototype.toString.call(obj) === '[object String]'

const isNumber = obj => Object.prototype.toString.call(obj) === '[object Number]'

const isFunction = obj => Object.prototype.toString.call(obj) === '[object Function]'

const isObject = obj => Object.prototype.toString.call(obj) === '[object Object]'

// 删除对象中的值为null的属性
const removeNullProperty = function(option) {
  if (!option) {
    return
  }
  const optionInternal = option
  Object.keys(optionInternal).forEach(attr => {
    if (Object.prototype.hasOwnProperty.call(optionInternal, attr)) {
      if (optionInternal[attr] === null || typeof optionInternal[attr] === 'undefined') {
        delete optionInternal[attr]
        return
      }
      if (typeof optionInternal[attr] === 'object') {
        removeNullProperty(optionInternal[attr])
      }
    }
  })
}

// 将颜色转为透明颜色值， 比如： #ff0000 转 rgba(123,123,123, 0.4)
const hexToRgba = (hex, opacity) =>
  hex && hex.replace(/\s+/g, '').length === 7 ? `rgba(${parseInt(`0x${hex.slice(1, 3)}`, 10)},${parseInt(`0x${hex.slice(3, 5)}`, 10)},${parseInt(`0x${hex.slice(5, 7)}`, 10)},${opacity})` : ''

// 数组深拷贝
const arrayDeepClone = arr => {
  const arrClone = new Array(0)
  arr.forEach(goods => {
    arrClone.push(_.cloneDeep(goods))
  })
  return arrClone
}
// 特殊字符转义
function htmlDecode(str) {
  let s = ''
  if (str.length === 0) return ''
  s = str.replace(/&amp;/g, '&')
  s = s.replace(/&lt;/g, '<')
  s = s.replace(/&emsp;/g, '　')
  s = s.replace(/&gt;/g, '>')
  s = s.replace(/&copy;/g, '©')
  s = s.replace(/&reg;/g, '®')
  s = s.replace(/&times;/g, '×')
  s = s.replace(/&divide;/g, '÷')
  s = s.replace(/&nbsp;/g, ' ')
  s = s.replace(/&hellip;/g, '...')
  s = s.replace(/&#39;/g, "'")
  s = s.replace(/&mdash;/g, '—')
  s = s.replace(/&quot;/g, '"')
  s = s.replace(/< img/g, '<img')
  s = s.replace(/&ldquo;/g, '“')
  s = s.replace(/&rdquo;/g, '”')
  s = s.replace(/<br\/>/g, '\n')
  s = s.replace(/&lsquo;/g, '‘')
  s = s.replace(/&rsquo;/g, '’')

  return s
}

// 存储localStoraget
function setlocalStorage(key, value, expires) {
  const params = { key, value, expires }
  if (expires) {
    // 记录何时将值存入缓存，毫秒级
    var data = Object.assign(params, { startTime: new Date().getTime() })
    localStorage.setItem(key, JSON.stringify(data))
  } else {
    if (Object.prototype.toString.call(value) === '[object Object]') {
      value = JSON.stringify(value)
    }
    if (Object.prototype.toString.call(value) === '[object Array]') {
      value = JSON.stringify(value)
    }
    localStorage.setItem(key, value)
  }
}
// 获取localStoraget
function getlocalStorage(key) {
  let item = localStorage.getItem(key)
  // 先将拿到的试着进行json转为对象的形式
  try {
    item = JSON.parse(item)
  } catch (error) {
    // eslint-disable-next-line no-self-assign
    item = item
  }
  // 如果有startTime的值，说明设置了失效时间
  if (item && item.startTime) {
    const date = new Date().getTime()
    // 如果大于就是过期了，如果小于或等于就还没过期
    if (date - item.startTime > item.expires) {
      localStorage.removeItem(name)
      return false
    } else {
      return item.value
    }
  } else {
    return item
  }
}
// // 设置cookie,增加到vue实例方便全局调用
// function setCookie(c_name, value, expiredays) {
//   // 参数名，参数，时间
//   var exdate = new Date()
//   exdate.setDate(exdate.getDate() + expiredays)
//   document.cookie = c_name + '=' + escape(value) + (expiredays == null ? '' : ';expires=' + exdate.toGMTString()) + ';path=/'
// }

// // 获取cookie，进行修改
// function getCookie(name) {
//   var arrs
//   var reg = new RegExp('(^| )' + name + '=([^;]*)(;|$)')
//   arrs = document.cookie.match(reg)
//   if (arrs) return arrs[2]
//   else return null
// }

// // 删除cookie
// function deleteCookie(name) {
//   var date = new Date()
//   date.setTime(date.getTime() - 10000)
//   document.cookie = name + '=v; expires=' + date.toGMTString()
// }
// // 清除当前域名下cookie函数
// function clearErpCookie() {
//   const arr = window.location.host.split('.')
//   const domain = arr[arr.length - 2] + '.' + arr[arr.length - 1]
//   const date = new Date()
//   date.setTime(date.getTime() - 10000)
//   document.cookie = 'sso.jd.com=0;path=/;domain=' + domain + ';expires=' + date.toUTCString() // 清除一级域名下的或指定的，例如 .jdl.com
// }
// 腾讯转百度
function TxMapTransBMap(lng, lat) {
  const xpi = (3.14159265358979324 * 3000.0) / 180.0
  const x = lng
  const y = lat
  const z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * xpi)
  const theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * xpi)
  const lngs = z * Math.cos(theta) + 0.0065
  const lats = z * Math.sin(theta) + 0.006
  return {
    lng: lngs,
    lat: lats
  }
}
// 百度转腾讯
function BMapTransTxMap(lng, lat) {
  const xpi = (3.14159265358979324 * 3000.0) / 180.0
  const x = lng - 0.0065
  const y = lat - 0.006
  const z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * xpi)
  const theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * xpi)
  const lngs = z * Math.cos(theta)
  const lats = z * Math.sin(theta)
  return {
    lng: lngs,
    lat: lats
  }
}
// 获取当前时间的前一天数据 格式为2020-09-14
function getLastFormatDate(date) {
  var seperator1 = '-'
  var year = date.getFullYear()
  var month = date.getMonth() + 1
  var strDate = date.getDate() - 1
  if (month >= 1 && month <= 9) {
    month = '0' + month
  }
  if (strDate >= 0 && strDate <= 9) {
    strDate = '0' + strDate
  }
  var currentdate = year + seperator1 + month + seperator1 + strDate
  return currentdate
}
// 获取当前时间的前一年数据 格式为2019-09-15
function getLastFormatYear() {
  var date = new Date()
  var seperator1 = '-'
  var year = date.getFullYear() - 1
  var month = date.getMonth() + 1
  var strDate = date.getDate()
  if (month >= 1 && month <= 9) {
    month = '0' + month
  }
  if (strDate >= 0 && strDate <= 9) {
    strDate = '0' + strDate
  }
  var currentdate = year + seperator1 + month + seperator1 + strDate
  return currentdate
}
// 获取上个月第一天和最后一天
function getLastMonthStartEndDate() {
  var nowdays = new Date()
  var year = nowdays.getFullYear()
  var month = nowdays.getMonth()
  if (month === 0) {
    month = 12
    year = year - 1
  }
  if (month < 10) {
    month = '0' + month
  }
  var myDate = new Date(year, month, 0)
  var startDate = year + '-' + month + '-01 00:00:00' // 上个月第一天
  var endDate = year + '-' + month + '-' + myDate.getDate() + ' 23:59:59' // 上个月最后一天
  return [startDate, endDate]
}
// 获取最近7天日期
function getDay(day) {
  var today = new Date()
  var targetday_milliseconds = today.getTime() + 1000 * 60 * 60 * 24 * day
  today.setTime(targetday_milliseconds) // 注意，这行是关键代码
  var tYear = today.getFullYear()
  var tMonth = today.getMonth()
  var tDate = today.getDate()
  tMonth = doHandleMonth(tMonth + 1)
  tDate = doHandleMonth(tDate)
  return tYear + '-' + tMonth + '-' + tDate
}
// 获取最近7天日期(判断月份)
function doHandleMonth(month) {
  var m = month
  if (month.toString().length === 1) {
    m = '0' + month
  }
  return m
}
// 获取当前页面域名
function getDomainhost() {
  const arr = window.location.host.split('.')
  const domain = arr[arr.length - 2] + '.' + arr[arr.length - 1]
  return domain
}
// 将20200102转化成2020年01月01日
function getFomateDateStr(str) {
  const year = str.substring(0, 4)
  const month = str.substring(4, 6)
  const date = str.substring(6, 8)
  return year + '年' + month + '月' + date + '日'
}
// 根据当前页面path获取对应页面 按钮list
function getUserCurrentBtns(pathName) {
  const btnMap = sessionStorage.getItem('buttonPageMap') ? (JSON.parse(sessionStorage.getItem('buttonPageMap'))[pathName] ? JSON.parse(sessionStorage.getItem('buttonPageMap'))[pathName] : []) : []
  // 排序 展示
  btnMap.sort().reverse()
  return btnMap
}
// 根据当前用户权限判断所属页面按钮展示项
// type: 1 表格外按钮   2 表格行内按钮
// btnList 当前用户全量权限按钮
// arr  当前页面全量按钮池
function getPageBtn(btnList, type, arr) {
  const btnArr = []
  if (type === 1) {
    btnList.map(item => {
      switch (item) {
        case 'add':
          arr.map(btnItem => {
            if (btnItem.id === 'upload' || btnItem.id === 'add') {
              btnArr.push(btnItem)
            }
          })
          break
        case 'download':
          arr.map(btnItem => {
            if (btnItem.id === 'download') {
              btnArr.push(btnItem)
            }
          })
          break
        case 'delete':
          arr.map(btnItem => {
            if (btnItem.id === 'deleted') {
              btnArr.push(btnItem)
            }
          })
          break
      }
    })
  } else if (type === 2) {
    btnList.map(item => {
      switch (item) {
        case 'add':
          arr.map(btnItem => {
            if (btnItem.id === 'copy') {
              btnArr.push(btnItem)
            }
          })
          break
        case 'edit':
          arr.map(btnItem => {
            if (btnItem.id === 'edit') {
              btnArr.push(btnItem)
            }
          })
          break
      }
    })
  }
  // 前台对 按钮进行二次排序 btnSort 排序用
  btnArr.sort((a, b) => {
    return a.btnSort - b.btnSort
  })
  return btnArr
}
export default {
  getParam,
  //   setCookie,
  //   getCookie,
  //   deleteCookie,
  //   clearErpCookie,
  emptyFn,
  setlocalStorage,
  getlocalStorage,
  calMaxMin,
  toThousands,
  tenThousand,
  tenThousand1,
  escape2Html,
  unescape,
  htmlDecode,
  numberFix,
  formatEmptyStr,
  ellipse,
  isArray,
  isString,
  isNumber,
  isFunction,
  isObject,
  removeNullProperty,
  hexToRgba,
  arrayDeepClone,
  TxMapTransBMap,
  BMapTransTxMap,
  formatEcharsArr,
  checkEmail,
  getLastFormatDate,
  getLastFormatYear,
  getDay,
  getLastMonthStartEndDate,
  getDomainhost,
  getFomateDateStr,
  getUserCurrentBtns,
  getPageBtn,
  getBLen
}
