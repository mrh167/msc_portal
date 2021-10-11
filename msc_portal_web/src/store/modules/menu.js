const state = {
  checkMenuList: [],
  menuParentId: null, // 获取一级的id
  sellerBtnType: 3, // 获取当前按钮点击的状态值
  portalSellerNo: '', // 获取当前的sellerNO
  portalRowId: '', // 获取当前列表行的id
  PublicPage: {}, // 动态菜单、路由
  VscSellerType: null, // 判断是否诸葛融合
  depList: [], // 存储事业部数据
  nodeList: [], // 存储库节点数据
  frontFlag: null // 判断商家情况得标识
}
const getters = {
  getMenuParentId(state) {
    return state.menuParentId || sessionStorage.getItem('menuParentId')
  },
  getSellerBtnType(state) {
    return state.sellerBtnType || sessionStorage.getItem('sellerBtnType')
  },
  getPortalSellerNo(state) {
    return state.portalSellerNo || sessionStorage.getItem('portalSellerNo')
  },
  getPortalRowId(state) {
    return state.portalRowId || sessionStorage.getItem('portalRowId')
  },
  getPublicPage(state) {
    return state.PublicPage || JSON.parse(sessionStorage.getItem('PublicPage'))
  },
  getVscSellerType(state) {
    return state.VscSellerType || JSON.parse(sessionStorage.getItem('VscSellerType'))
  },
  getdepList(state) {
    return state.depList
  },
  getnodeList(state) {
    return state.nodeList
  },
  getfrontFlag(state) {
    return state.frontFlag || sessionStorage.getItem('frontFlag')
  }
}
const mutations = {
  SET_MENUPARENTID(state, menuParentId) {
    state.menuParentId = menuParentId
  },
  SET_SELLERBTNTYPE(state, sellerBtnType) {
    state.sellerBtnType = sellerBtnType
  },
  SET_PORTALSELLERNO(state, portalSellerNo) {
    state.portalSellerNo = portalSellerNo
  },
  SET_PORTALROWID(state, portalRowId) {
    state.portalRowId = portalRowId
  },
  SET_PUBLICPAGE(state, PublicPage) {
    state.PublicPage = PublicPage
  },
  SET_VSCSELLERTYPE(state, VscSellerType) {
    state.VscSellerType = VscSellerType
  },
  SET_DEPLIST(state, depList) {
    state.depList = depList
  },
  SET_NODELIST(state, nodeList) {
    state.nodeList = nodeList
  },
  SET_FRONTFLAG(state, frontFlag) {
    state.frontFlag = frontFlag
  }

}
const actions = {
  menuParentId(context, menuParentId) {
    context.commit('SET_MENUPARENTID', menuParentId)
  },
  sellerBtnType(context, sellerBtnType) {
    context.commit('SET_SELLERBTNTYPE', sellerBtnType)
  },
  portalSellerNo(context, portalSellerNo) {
    context.commit('SET_PORTALSELLERNO', portalSellerNo)
  },
  portalRowId(context, portalRowId) {
    context.commit('SET_PORTALROWID', portalRowId)
  },
  PublicPage(context, PublicPage) {
    context.commit('SET_PUBLICPAGE', PublicPage)
  },
  VscSellerType(context, VscSellerType) {
    context.commit('SET_VSCSELLERTYPE', VscSellerType)
  },
  depList(context, depList) {
    context.commit('SET_DEPLIST', depList)
  },
  nodeList(context, nodeList) {
    context.commit('SET_NODELIST', nodeList)
  },
  frontFlag(context, frontFlag) {
    context.commit('SET_FRONTFLAG', frontFlag)
  }
}

export default {
  state,
  actions,
  getters,
  mutations
}

