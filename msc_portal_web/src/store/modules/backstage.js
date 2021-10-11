const state = {
  activeIndex: null // 获取一级的id
}
const getters = {
  getMenuParentId(state) {
    return state.activeIndex || sessionStorage.getItem('activeIndex')
  }
}
const mutations = {
  SET_MENUPARENTID(state, activeIndex) {
    state.activeIndex = activeIndex
  }
}
const actions = {
  activeIndex(context, activeIndex) {
    context.commit('SET_MENUPARENTID', activeIndex)
  }
}

export default {
  state,
  actions,
  getters,
  mutations
}
