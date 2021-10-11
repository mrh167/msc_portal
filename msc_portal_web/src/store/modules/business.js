import Api from '@/api'

export default {
  state: {
    businessInfo: {
      'deptName': '',
      'deptNo': '',
      'id': '',
      'moduleInfo': 0,
      'sellerEntryAddOneCo': {},
      'sellerEntryAddThreeCo': {},
      'sellerEntryAddTwoCo': {},
      'sellerEntryEditFourCo': {},
      'sellerName': '',
      'sellerNo': '',
      'configFinishFlag': '',
      'takeEffectDate': ''
    }, // 商家信息
    isNewSeller: false
  },
  getters: {
    getBusinessInfo(state) {
      return state.businessInfo
    },
    getIsNewSeller(state) {
      return state.isNewSeller
    }
  },
  mutations: {
    Set_IsNewSeller(state, isNewSeller) {
      state.isNewSeller = isNewSeller
      console.log(isNewSeller, 'isNewSeller')
    },
    Set_BusinessInfo(state, businessInfo) {
      state.businessInfo.deptName = businessInfo.deptName
      state.businessInfo.deptNo = businessInfo.deptNo
      state.businessInfo.id = businessInfo.id ? businessInfo.id : ''
      state.businessInfo.moduleInfo = businessInfo.moduleInfo
      state.businessInfo.sellerName = businessInfo.sellerName
      state.businessInfo.sellerNo = businessInfo.sellerNo
      state.businessInfo.configFinishFlag = businessInfo.configFinishFlag
      state.businessInfo.takeEffectDate = businessInfo.takeEffectDate
      // console.log(state.businessInfo, 'store')
    },
    Set_SellerEntryAddOneCo(state, sellerEntryAddOneCo) {
      state.businessInfo.sellerEntryAddOneCo = sellerEntryAddOneCo
    },
    Set_SellerEntryAddThreeCo(state, sellerEntryAddThreeCo) {
      state.businessInfo.sellerEntryAddThreeCo = sellerEntryAddThreeCo
    },
    Set_SellerEntryAddTwoCo(state, sellerEntryAddTwoCo) {
      state.businessInfo.sellerEntryAddTwoCo = sellerEntryAddTwoCo
    },
    Set_SellerEntryEditFourCo(state, sellerEntryEditFourCo) {
      state.businessInfo.sellerEntryEditFourCo = sellerEntryEditFourCo
    }
  },
  actions: {
    changeIsNewSeller(context, isNewSeller) {
      context.commit('Set_IsNewSeller', isNewSeller)
    },
    changeBusinessInfo(context, businessInfo) {
      context.commit('Set_BusinessInfo', businessInfo)
    },
    changeSellerEntryAddOneCo(context, sellerEntryAddOneCo) {
      context.commit('Set_SellerEntryAddOneCo', sellerEntryAddOneCo)
    },
    changeSellerEntryAddThreeCo(context, sellerEntryAddThreeCo) {
      context.commit('Set_SellerEntryAddThreeCo', sellerEntryAddThreeCo)
    },
    changeSellerEntryAddTwoCo(context, sellerEntryAddTwoCo) {
      context.commit('Set_SellerEntryAddTwoCo', sellerEntryAddTwoCo)
    },
    changeSellerEntryEditFourCo(context, sellerEntryEditFourCo) {
      context.commit('Set_SellerEntryEditFourCo', sellerEntryEditFourCo)
    }

  }
}
