const state = {
  isEclp: false
}
const getters = {
  getIsEclp(state) {
    return localStorage.getItem('isEclp')
  }
}
const mutations = {
  setEclp(state, isEclp) {
    state.isEclp = isEclp
  }
}
const actions = {
  isEclp(context, isEclp) {
    context.commit(state, isEclp)
  }
}
export default
{
  state,
  actions,
  getters,
  mutations
}
