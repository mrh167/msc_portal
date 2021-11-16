/* eslint-disable vue/order-in-components */
/* eslint-disable vue/valid-v-model */
<template>
  <div v-loading="loading">
    <div class="container-content">
      <div class="information-sty">
        <div class="addcontent">
          <div v-for="arr in indexArray" :key="arr" :class="true ? 'addcontenta' : 'highlight'">
            <!-- <span class="addcontentb">{{ arr }}</span> -->
            <el-button type="text" class="addcontentb" @click="queryIndex(arr)"> {{ arr }} </el-button>
          </div>
        </div>
      </div>

      <!--table列表-->
      <div class="container-table">
        <el-table
          v-loading="listLoading"
          :data="list"
          border
          style="width: 100%"
          @selection-change="handleSelectionChange"
        >
          <el-table-column
            type="selection"
            width="55"
            fixed="left"
            align="center"
          />

          <el-table-column
            prop="_id"
            label="_id"
          />
          <el-table-column
            prop="_index"
            label="_index"
          />
          <el-table-column
            prop="_score"
            label="_score"
          />
          <el-table-column
            fixed="right"
            label="操作"
          >
            <template slot-scope="scope">
              <el-button type="text" @click="lookHandle(scope.$index, scope.row)">查看索引</el-button>
              <!-- <el-button type="text" size="small" @click="editHandle(scope.$index,scope.row)">编辑</el-button> -->
            </template>
          </el-table-column>
        </el-table>

      </div>
    </div>
    <lookIndex
      v-if="getSellerBtnType===1"
      :look-list="lookList"
    />
  </div>

</template>
<script>
import Api from '@/api'
import qs from 'qs'
import { mapGetters } from 'vuex'
import lookIndex from './lookIndex.vue'

export default {
  components: {
    lookIndex
  },
  data() {
    return {
      list: [],
      lookList: [],
      loading: false,
      listLoading: false,
      indexArray: []
    }
  },
  computed: {
    ...mapGetters(['getSellerBtnType', 'getPortalSellerNo'])
  },
  mounted() {
    this.getIndexList()
  },

  methods: {

    // 查看索引的方法
    lookHandle(index, row) {
      this.lookList = [row._source]
      console.log('lookList=======', this.lookList)
      this.$store.dispatch('sellerBtnType', 1)
    //   this.$store.dispatch('portalSellerNo', row.account)
    },
    // 多选
    handleSelectionChange(val) {
      this.multipleSelection = val
    },

    // 根据索引名称查询当前索引的详细信息
    queryIndex(index) {
      const params = {
        index
      }
      Api.findAllIndex.matchAllQuery(params).then(res => {
        if (res.success) {
          console.log('res========', res)
          this.list = res.data
        }
      })
      console.log('indexQry========', index)
    },

    // 获取所有索引库的集合
    getIndexList() {
      Api.findAllIndex.findAllIndex().then(res => {
        if (res.success) {
          console.log('resdata====', res.data)
          for (let i = 0; i < res.data.length; i++) {
            this.indexArray = res.data
          }
        }
      })
    }
  }
}
</script>

<style lang="scss">
 .information-sty {
  box-shadow: 2px 2px 7px 2px #ccc;
  border-radius: 0 0 4px 4px;
  width: 100%;
  height: 80px;
  background: #ffffff;
  margin-bottom: 20px;
}
.container-content{
  background: #fff;
  width: 100%;
  border-radius: 4px;
  box-shadow: 2px 2px 7px 2px #ccc;
   .container-table {
      padding: 0 20px 20px 20px;
    }
}
._idnexSty{
    width: 100%;
    height:100%;
    }
  .el-row {
    margin-bottom: 20px;
    &:last-child {
      margin-bottom: 0;
    }
  }
  .el-col {
    border-radius: 4px;
  }
  .bg-purple-dark {
    background: #99a9bf;
  }
  .bg-purple {
    background: #d3dce6;
  }
  .bg-purple-light {
    background: #e5e9f2;
  }
  .grid-content {
    border-radius: 4px;
    min-height: 36px;
    text-align: center;
  }
  .row-bg {
    padding: 10px 0;
    background-color: #f9fafc;
  }
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
 };

</style>
