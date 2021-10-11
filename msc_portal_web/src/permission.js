import router from './router'
import store from './store'
import { Message } from 'element-ui'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import { getToken } from '@/utils/auth' // get token from cookie
import getPageTitle from '@/utils/get-page-title'

NProgress.configure({ showSpinner: false }) // NProgress Configuration

const whiteList = ['/login'] // no redirect whitelist

router.beforeEach(async(to, from, next) => {
  // start progress bar
  NProgress.start()

  // set page title
  document.title = getPageTitle(to.meta.title)

  // determine whether the user has logged in
  const hasToken = getToken()

  if (hasToken) {
    if (to.path === '/login') {
      // if is logged in, redirect to the home page
      next({ path: '/' })
      NProgress.done()
    } else {
      const hasGetUserInfo = store.getters.name
      if (hasGetUserInfo) {
        next()
      } else {
        try {
          // get user info
          await store.dispatch('user/getInfo')
          console.log('permission', '已到达这里')

          next()
        } catch (error) {
          // remove token and go to login page to re-login
          await store.dispatch('user/resetToken')
          Message.error(error || 'Has Error')
          next(`/login?redirect=${to.path}`)
          NProgress.done()
        }
      }
    }
  } else {
    /* has no token*/

    if (whiteList.indexOf(to.path) !== -1) {
      // in the free login whitelist, go directly
      next()
    } else {
      // other pages that do not have permission to access are redirected to the login page.
      next(`/login?redirect=${to.path}`)
      NProgress.done()
    }
  }
})
// router.beforeEach(async(to, _from, next) => {
//   // start progress bar
//   NProgress.start()

//   // set page title
//   document.title = getPageTitle(to.meta.title)

//   // determine whether the user has logged in
//   const hasToken = getToken()

//   // 判断是否存在token,没有就重新登陆
//   if (hasToken) {
//     if (to.path === '/login') {
//       // if is logged in, redirect to the home page
//       next({ path: '/' })
//       NProgress.done()
//     } else {
//       // determine whether the user has obtained his permission roles through getInfo
//       const hasRoles = store.getters.roles && store.getters.roles.length > 0 // 这里指的是src/store/getters.js的roles
//       console.log('权限是：', hasRoles)
//       // 判断是否已经有roles了
//       if (hasRoles) {
//         console.log('权限是：', hasRoles)
//         next()
//       } else {
//         try {
//           // get user info
//           // note: roles must be a object array! such as: ['admin'] or ,['developer','editor']
//           // 获取roles
//           const { roles } = await store.dispatch('user/getInfo')// 第一步

//           // generate accessible routes map based on roles
//           // 获取通过权限验证的路由
//           const accessRoutes = await store.dispatch('permission/generateRoutes', roles.roles)// 第二步
//           // 更新加载路由
//           router.options.routes = store.getters.permission_routes// 第三步
//           // dynamically add accessible routes
//           router.addRoutes(accessRoutes)
//           console.log('返回值是：', store)
//           // hack method to ensure that addRoutes is complete
//           // set the replace: true, so the navigation will not leave a history record
//           next({ ...to, replace: true })
//         } catch (error) {
//           // remove token and go to login page to re-login
//           await store.dispatch('user/resetToken')
//           Message.error({
//             // eslint-disable-next-line no-undef
//             message: error || 'hash error'
//           })
//           next(`/login?redirect=${to.path}`)
//           NProgress.done()
//         }
//       }
//     }
//   } else {
//     /* has no token*/

//     if (whiteList.indexOf(to.path) !== -1) {
//       // in the free login whitelist, go directly
//       next()
//     } else {
//       // other pages that do not have permission to access are redirected to the login page.
//       next(`/login?redirect=${to.path}`)
//       NProgress.done()
//     }
//   }
// })

router.afterEach(() => {
  // finish progress bar
  NProgress.done()
})
