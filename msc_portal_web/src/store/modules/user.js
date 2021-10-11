// import { login, logout, getInfo } from '@/api/user'
import { getToken, setToken, removeToken } from '@/utils/auth'
import { resetRouter } from '@/router'
import Api from '@/api'

// const getDefaultState = () => {
//   return {
//     token: '',
//     name: '',
//     avatar: ''
//   }
// }

// const state = getDefaultState()

// const mutations = {
//   RESET_STATE: (state) => {
//     Object.assign(state, getDefaultState())
//   },
//   SET_TOKEN: (state, token) => {
//     state.token = token
//   },
//   SET_NAME: (state, name) => {
//     state.name = name
//   },
//   SET_AVATAR: (state, avatar) => {
//     state.avatar = avatar
//   }
// }
const getDefaultState = () => {
  return {
    token: getToken(),
    name: '',
    avatar: '',
    introduction: '',
    roles: []
  }
}

const state = getDefaultState()

const mutations = {
  RESET_STATE: (state) => {
    Object.assign(state, getDefaultState())
  },
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_INTRODUCTION: (state, introduction) => {
    state.introduction = introduction
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  },
  SET_ROLES: (state, roles) => {
    state.roles = roles
  }
}

const actions = {
  // user login
  login({ commit }, userInfo) {
    // const { username, password } = userInfo
    return new Promise((resolve, reject) => {
    //   login({ username: username.trim(), password: password }).then(response => {
      const { data } = userInfo
      commit('SET_TOKEN', data.tokenMap.token)

      setToken(data.tokenMap.token)

      resolve()
    //   }).catch(error => {
    //     reject(error)
    //   })
    })
  },

  //   //   // get user info
  getInfo({ commit, state }) {
    return new Promise((resolve, reject) => {
      Api.Login.getInfo().then(response => {
        const { data } = response

        if (!data) {
          return reject('Verification failed, please Login again.')
        }

        const { name, avatar } = data

        commit('SET_NAME', name)
        commit('SET_AVATAR', avatar)
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },
  // get user info
  //   getInfo({ commit, state }) {
  //     return new Promise((resolve, reject) => {
  //       // 我这里用的是post请求获取个人信息
  //       getInfo(state.token).then(response => {
  //         // 注意这个data,有一些初学者或者粗心的人会被这里卡一下这里涉及到es6的解构函数；
  //         const { data } = response
  //         console.log('报错了', data)
  //         if (!data) {
  //           console.log('报错了')
  //           reject('Verification failed, please Login again.')
  //         }
  //         // 返回的info最少需要包含以下内容（角色，名称，头像，简介）个人需求
  //         // 大家可以只返回roles就行了
  //         const { roles, name, avatar, introduction } = data

  //         if (!roles || roles.length <= 0) {
  //           reject('getInfo: roles must be a non-null array!')
  //         }
  //         console.log(roles)
  //         // 存起来
  //         commit('SET_ROLES', roles)
  //         commit('SET_NAME', name)
  //         commit('SET_AVATAR', avatar)
  //         commit('SET_INTRODUCTION', introduction)
  //         resolve(data)
  //       }).catch(error => {
  //         reject(error)
  //       })
  //     })
  //   },

  // user logout
  logout({ commit, state }) {
    return new Promise((resolve, reject) => {
      // logout(state.token).then(() => {
      removeToken() // must remove  token  first
      resetRouter()
      commit('RESET_STATE')
      resolve()
      // }).catch(error => {
      //   reject(error)
      // })
    })
  },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      removeToken() // must remove  token  first
      commit('RESET_STATE')
      resolve()
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

