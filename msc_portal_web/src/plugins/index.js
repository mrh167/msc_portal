import Vue from 'vue'
import { MessageBox, Notification } from 'element-ui'
import {
  showInfoMsg, showSuccessMsg, showWarningMsg, showErrorMsg
} from '@/utils/message'
import Utils from '@/utils/utils'
// Vue.use(MessageBox)
// Vue.use(Notification)

// 确认消息对话框
const showConfirmDialog = (confirmMessage,
  successCallback = Utils.emptyFn,
  cancelCallback = Utils.emptyFn) => {
  MessageBox.confirm(confirmMessage || '提示信息', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    dangerouslyUseHTMLString: true,
    type: 'warning'
  }).then(() => {
    successCallback()
  }).catch(() => {
    cancelCallback()
  })
}

// 成功通知
const showSuccessNotify = (title = '成功通知', message = '') => {
  Notification.success({
    title,
    message,
    duration: 3
  })
}

// 警告通知
const showWarningNotify = (title = '警告通知', message = '') => {
  Notification.warning({
    title,
    message,
    duration: 3
  })
}

// 错误通知
const showErrorNotify = (title = '错误通知', message = '') => {
  Notification.error({
    title,
    message,
    duration: 3
  })
}

// 一般通知
const showInfoNotify = (title = '一般通知', message = '') => {
  Notification.info({
    title,
    message,
    duration: 3
  })
}

export default {
  install: () => {
    Vue.prototype.$showInfoMsg = showInfoMsg
    Vue.prototype.$showErrorMsg = showErrorMsg
    Vue.prototype.$showSuccessMsg = showSuccessMsg
    Vue.prototype.$showWarningMsg = showWarningMsg
    Vue.prototype.$showConfirmDialog = showConfirmDialog
    Vue.prototype.$showSuccessNotify = showSuccessNotify
    Vue.prototype.$showWarningNotify = showWarningNotify
    Vue.prototype.$showErrorNotify = showErrorNotify
    Vue.prototype.$showInfoNotify = showInfoNotify
  }
}
