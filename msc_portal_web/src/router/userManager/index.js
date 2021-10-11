import Layout from '@/layout'

export default [
  {
    path: '/userManager',
    component: Layout,
    redirect: '/userManager',
    name: '系统管理',
    meta: { title: '系统管理', icon: 'el-icon-s-help' },
    children: [
      {
        path: 'userManager',
        name: 'userManager',
        component: () => import('@/views/userManager/index'),
        meta: { title: '用户管理', icon: 'table' }
      },
      {
        path: 'tree',
        name: 'Tree',
        component: () => import('@/views/tree/index'),
        meta: { title: '字典管理', icon: 'tree' }
      }
    ]
  }
]
