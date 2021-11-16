import Login from './login'
import UserManager from './userManager'
import Information from './Information'
import DictType from './dict'
import dictData from './dict/dictData'
import findAllIndex from './indexAll/findAllIndex'

export default {
  Login: {
    ...Login
  },
  UserManager: {
    ...UserManager
  },
  Information: {
    ...Information
  },
  DictType: {
    ...DictType
  },
  dictData: {
    ...dictData
  },
  findAllIndex: {
    ...findAllIndex
  }
}
