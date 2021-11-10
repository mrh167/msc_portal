import Layout from '@/layout'

// export default [
//   { path: '/esHead', component: () => import('@/views/elasticSearch/index') }
// ]
export default [
  {
    path: '/esHead',
    component: Layout,
    redirect: '/esHead/index', // 重定向地址，在面包屑中点击会重定向去的地址
    // hidden: true, // 不在侧边栏显示
    alwaysShow: true, // 一直显示根路由
    name: 'ES可视化',
    meta: { title: 'ES可视化', icon: 'el-icon-s-help' },
    // component: () => import('@/views/elasticSearch/index')
    // meta: { roles: ['admin','editor'] }, //你可以在根路由设置权限，这样它下面所有的子路由都继承了这个权限
    children: [{
      path: 'index',
      component: () => import('@/views/elasticSearch/index'),
      name: 'esHead',
      meta: {
        title: 'ES查询',
        icon: 'lock', // 图标
        // roles: ['admin','editor'], //或者你可以给每一个子路由设置自己的权限
        noCache: true // 不会被 <keep-alive> 缓存
      }
    }]
  }
]
