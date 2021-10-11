import Vue from 'vue'
import Vuex from 'vuex'
import state from './state'
import getters from './getters'
import app from './modules/app'
import settings from './modules/settings'
import user from './modules/user'
import permission from './modules/permission'
import menu from './modules/menu'
import business from './modules/business'
import mutations from './mutations'
import actions from './actions'

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    app,
    settings,
    user,
    menu,
    permission,
    business
  },
  state,
  mutations,
  getters,
  actions
})

export default store
