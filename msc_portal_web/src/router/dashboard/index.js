/* Layout */
import Layout from '@/layout'

export default [
  { path: '/', component: Layout, redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/index'),
      meta: { title: '首页', icon: 'dashboard' }
    }]
  }
]
