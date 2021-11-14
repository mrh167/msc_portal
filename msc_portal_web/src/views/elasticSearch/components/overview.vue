/* eslint-disable vue/valid-v-model */
<template>
  <div v-loading="loading">
    <div class="container-content">

      <!--
        <el-row v-for="arr in indexArray" :key="arr" :gutter="35" justify="space-around">
          <el-col :span="10" :offset="6"><div class="grid-content bg-purple">  {{ arr }}   </div></el-col>
        </el-row>
      -->
      <div class="information-sty">
        <div class="_idnexSty">
          <el-row :gutter="100">
            <el-col v-for="arr in indexArray" :key="arr" :span="6">
              <div class="grid-content bg-purple">
                <el-button type="text" @click="queryIndex(arr)">{{ arr }}</el-button>
              </div>
            </el-col>
          </el-row>
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

          <input
            type="hidden"
            prop="id"
          >
          <el-table-column
            prop="account"
            label="账号"
          />
          <el-table-column
            prop="username"
            label="用户名"
          />
          <el-table-column
            prop="nickName"
            label="昵称"
          />
          <el-table-column
            prop="phone"
            label="电话"
          />
          <el-table-column
            prop="email"
            label="邮箱"
          />
          <el-table-column
            prop="status"
            width="90"
            label="状态"
          >
            <template slot-scope="scope">
              <el-tag :type="scope.row.status===true?'success':(scope.row.status===false?'info':'')">{{ scope.row.status=== true?'正常':'禁用' }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column
            prop="status"
            width="90"
            label="状态修改"
          >
            <template slot-scope="scope">
              <el-switch
                v-model="scope.row.status"
                active-circle-class="1"
                active-color="#409EFF"
                inactive-color="#C0CCDA"
                @change="changeStatusTab(scope.row)"
              />
            </template>
          </el-table-column>
          <el-table-column
            fixed="right"
            label="操作"
          >
            <template slot-scope="scope">
              <el-button type="text" @click="lookHandle(scope.$index, scope.row)">查看</el-button>
              <el-button type="text" size="small" @click="editHandle(scope.$index,scope.row)">编辑</el-button>
            </template>
          </el-table-column>
        </el-table>

      </div>
    </div>
  </div>

</template>
<script>
import Api from '@/api'

export default {
  components: {

  },
  data() {
    return {
      list: [],
      loading: false,
      listLoading: false,
      indexArray: []
    }
  },
  mounted() {
    this.getIndexList()
  },
  methods: {
    // 多选
    handleSelectionChange(val) {
      this.multipleSelection = val
    },

    // 根据索引名称查询当前索引的详细信息
    queryIndex(indexQry) {
      console.log('indexQry========', indexQry)
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

</style>
