/* eslint-disable no-irregular-whitespace */
<template>
  <div class="container-content">
    <el-button type="text" @click="dialogVisible = true">点击打开 Dialog</el-button>

    <el-dialog
      title="提示"
      :visible.sync="dialogVisible"
      width="auto"
      :before-close="handleClose"
    >
      <div class="information-sty">
        <div class="addcontent">
          <draggable v-model="baseArray" force-fallback="true" style="display:flex; flex-wrap: wrap">
            <!--  -->
            <div v-for="(item,index) in baseArray" :key="index" :class="!item.search ? 'addcontenta' : 'highlight'">
              <span class="addcontentb">{{ item.label }}</span>
              <i class="el-icon-remove" @click="detaillist(item.code)" />
            </div>
          </draggable>
        </div>
      </div>
      <el-row :gutter="100">
        <el-col v-for="arr in indexArray" :key="arr.code" :span="6">
          <div class="addcontenta" @click="addBaselist(arr.code)">{{ arr.label }}</div>
        </el-col>
      </el-row>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>
<script  src =" //cdnjs.cloudflare.com/ajax/libs/vue/3.0.2/vue.min.js " > </script>
<script>
  import draggable from 'vuedraggable'
export default {
    components:{
draggable
    },
  data() {
    return {
        isFullscreen:true,
      dialogVisible: false,
      baseArray: [],
      list: [

          {
          'label': '配送',
          'path': '/delivery',
          'code': '943',
          'sorted': 0
        },
        {
          'label': '进销存报表',
          'path': '/purchase',
          'code': '942',
          'sorted': 1
        },
        {
          'label': '采购入库查询',
          'path': '/inboundDetail',
          'code': '941',
          'sorted': 2
        },
        {
          'label': '采购入库',
          'path': '/instorage',
          'code': '940',
          'sorted': 3
        },
        {
          'label': '销售出库查询',
          'path': '/stockOutDetail',
          'code': '939',
          'sorted': 4
        },
        {
          'label': '销售出库',
          'path': '/outStock',
          'code': '938',
          'sorted': 5
        },
        {
          'label': '在库商品查询',
          'path': '/stockDetail',
          'code': '937',
          'sorted': 6
        },
        {
          'label': '在库分析',
          'path': '/analysisInLibrary',
          'code': '936',
          'sorted': 7
        },
        {
          'label': '仓储',
          'path': '/storages',
          'code': '935',
          'sorted': 8
        },
        {
          'label': '五菱大屏',
          'path': '/wuLingDaPing',
          'code': '934',
          'sorted': 9
        },
        {
          'label': '数据作战室',
          'path': '/dataFightRoom1',
          'code': '933',
          'sorted': 10
        },
        {
          'label': '实时在途监控',
          'path': '/realtimeIntransit',
          'code': '932',
          'sorted': 11
        },
        {
          'label': '实时库存监控',
          'path': '/realtimeStock',
          'code': '931',
          'sorted': 12
        },
        {
          'label': '实时订单监控',
          'path': '/realtimeTracking',
          'code': '930',
          'sorted': 13
        },
        {
          'label': '数据看板',
          'path': '/kanban',
          'code': '910',
          'sorted': 14
        },
        {
          'label': '数据大屏',
          'path': '/dataScreen',
          'code': '909',
          'sorted': 15
        },
        {
          'label': 'KA看板',
          'path': '/kaPanel',
          'code': '908',
          'sorted': 16
        },
        {
          'label': '工作台',
          'path': '/home',
          'code': '907',
          'sorted': 17
        },
        {
          'label': '订单时效异常',
          'path': '/orderTime',
          'code': '945',
          'sorted': 18
        },
        {
          'label': '库存临期异常',
          'path': '/stockOnSchedule',
          'code': '946',
          'sorted': 19
        },
        {
          'label': '华东运输',
          'path': '/165149946609665',
          'code': '948',
          'sorted': 20
        },
        {
          'label': '华南运输',
          'path': '/165150124867585',
          'code': '949',
          'sorted': 21
        },
        {
          'label': '数据上传',
          'path': '/dataSourceUpload',
          'code': '950',
          'sorted': 22
        },
        {
          'label': '库存快照监控',
          'path': '/storage/offlineInventory',
          'code': '953',
          'sorted': 23
        }
      ],
      loading: false,
      listLoading: false,
      indexArray: [
        {
          'label': '配送',
          'path': '/delivery',
          'code': '943',
          'sorted': 0
        },
        {
          'label': '进销存报表',
          'path': '/purchase',
          'code': '942',
          'sorted': 1
        },
        {
          'label': '采购入库查询',
          'path': '/inboundDetail',
          'code': '941',
          'sorted': 2
        },
        {
          'label': '采购入库',
          'path': '/instorage',
          'code': '940',
          'sorted': 3
        },
        {
          'label': '销售出库查询',
          'path': '/stockOutDetail',
          'code': '939',
          'sorted': 4
        },
        {
          'label': '销售出库',
          'path': '/outStock',
          'code': '938',
          'sorted': 5
        },
        {
          'label': '在库商品查询',
          'path': '/stockDetail',
          'code': '937',
          'sorted': 6
        },
        {
          'label': '在库分析',
          'path': '/analysisInLibrary',
          'code': '936',
          'sorted': 7
        },
        {
          'label': '仓储',
          'path': '/storages',
          'code': '935',
          'sorted': 8
        },
        {
          'label': '五菱大屏',
          'path': '/wuLingDaPing',
          'code': '934',
          'sorted': 9
        },
        {
          'label': '数据作战室',
          'path': '/dataFightRoom1',
          'code': '933',
          'sorted': 10
        },
        {
          'label': '实时在途监控',
          'path': '/realtimeIntransit',
          'code': '932',
          'sorted': 11
        },
        {
          'label': '实时库存监控',
          'path': '/realtimeStock',
          'code': '931',
          'sorted': 12
        },
        {
          'label': '实时订单监控',
          'path': '/realtimeTracking',
          'code': '930',
          'sorted': 13
        },
        {
          'label': '数据看板',
          'path': '/kanban',
          'code': '910',
          'sorted': 14
        },
        {
          'label': '数据大屏',
          'path': '/dataScreen',
          'code': '909',
          'sorted': 15
        },
        {
          'label': 'KA看板',
          'path': '/kaPanel',
          'code': '908',
          'sorted': 16
        },
        {
          'label': '工作台',
          'path': '/home',
          'code': '907',
          'sorted': 17
        },
        {
          'label': '订单时效异常',
          'path': '/orderTime',
          'code': '945',
          'sorted': 18
        },
        {
          'label': '库存临期异常',
          'path': '/stockOnSchedule',
          'code': '946',
          'sorted': 19
        },
        {
          'label': '华东运输',
          'path': '/165149946609665',
          'code': '948',
          'sorted': 20
        },
        {
          'label': '华南运输',
          'path': '/165150124867585',
          'code': '949',
          'sorted': 21
        },
        {
          'label': '数据上传',
          'path': '/dataSourceUpload',
          'code': '950',
          'sorted': 22
        },
        {
          'label': '库存快照监控',
          'path': '/storage/offlineInventory',
          'code': '953',
          'sorted': 23
        }
      ],
      findMenu: [
          {
          'label': '配送',
          'path': '/delivery',
          'code': '943',
          'sorted': 0
        },
        {
          'label': '进销存报表',
          'path': '/purchase',
          'code': '942',
          'sorted': 1
        },
        {
          'label': '采购入库查询',
          'path': '/inboundDetail',
          'code': '941',
          'sorted': 2
        },
        {
          'label': '采购入库',
          'path': '/instorage',
          'code': '940',
          'sorted': 3
        },
        {
          'label': '销售出库查询',
          'path': '/stockOutDetail',
          'code': '939',
          'sorted': 4
        },
        {
          'label': '销售出库',
          'path': '/outStock',
          'code': '938',
          'sorted': 5
        }
      ]

    }
  },
  created(){

  },
  mounted() {

  },
  methods: {
detaillist(code) {
      if (this.baseArray.length > 1) {
        // 递归之后重新赋值
        let index = this.baseArray.findIndex(item => item.code === code)
        if (index > -1) {
          this.baseArray.splice(index, 1)
        }
        this.list = this.delID(this.list, code)
      } else {
        this.$showErrorMsg('最少添加1条')
      }
    },
    delID(list, code) {
      list.map(ev => {
        if (ev.code === code) {
          ev.top = false
        } else if (ev.children) {
          this.delID(ev.children, code)
        }
      })
      return list
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done()
        })
        .catch(_ => {})
    },

    queryIndex(label) {
      console.log('label====', label)
    },
    // 添加菜单
    addBaselist(code) {

      if (this.baseArray.length < 24) {
        this.list = this.lookID(this.list, code)
        this.initBaseArray(this.baseArray)
        // this.search()
      } else {
        this.$showErrorMsg('最多添加24条')
      }
    },
    lookID(list, code) {
      list.forEach(ev => {
        if (ev.code === code) {
          ev.top = true
          this.baseArray = this.baseArray.concat(ev)
          console.log('baseArray============', this.baseArray)
          this.initBaseArray(this.baseArray)
        } else if (ev.children) {
          this.lookID(ev.children, code)
        }
      })
      return list
    },
    initBaseArray(data) {
     data.map((item, index) => {
     item['top'] = true
     item['search'] = false
   })
   return data
   }
  }
}

</script>

<style lang="scss">

.addcontent {
display: flex;
margin-top: 10px;
flex-wrap: nowrap;
}
.addcontenta{
cursor: pointer;
position:relative;
width: 180px;
height:32px;
background:#EEF4F8 ;
line-height:32px;
text-align:center;
border-radius: 4px;
margin-right:20px;
margin-bottom:10px;
};
.addcontentb{
overflow: hidden;
margin-left:10px;
padding-right:10px;
};
._idnexSty{
width: 100%;
height:100%;
}
.active {
   background: #fd7522;
   border: 1px solid #fd7522;
   color: #fff;
 }
</style>
