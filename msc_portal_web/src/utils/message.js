import { Message } from 'element-ui'

export const showErrorMessage = (exception,
  defaultMessage,
  errorCode,
  errorMessage) => {
  if (exception !== null && exception.code !== null && exception.messages !== null) {
    const message = errorCode + exception.code + errorMessage + exception.messages
    Message({
      message,
      type: 'error',
      showClose: true,
      duration: 4000
    })
  } else {
    if (defaultMessage === '') return
    Message({
      message: defaultMessage,
      type: 'error',
      showClose: true,
      duration: 4000
    })
  }
}
export const showInfoMsg = (message) => {
  Message.closeAll()
  Message({ message, type: 'info', showClose: true })
}
export const showSuccessMsg = (message) => {
  Message.closeAll()
  Message({ message, type: 'success', showClose: true })
}
export const showWarningMsg = (message) => {
  Message.closeAll()
  Message({ message, type: 'warning', showClose: true })
}
export const showErrorMsg = (message) => {
  Message.closeAll()
  Message({ message, type: 'error', showClose: true })
}
