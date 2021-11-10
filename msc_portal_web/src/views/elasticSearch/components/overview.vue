/* eslint-disable vue/valid-v-model */
<template>
  <div v-loading="loading">
    <div class="information-sty">
      <Search
        :table-search="tableSearch"

        @handleSearch="handelSearchClick"
      />
    </div>
    <div class="container-content">

      <div class="_idnexSty">
        <el-row v-for="arr in indexArray" :key="arr" :gutter="35" justify="space-between">
          <el-col :span="10" :offset="6"><div class="grid-content bg-purple">  {{ arr }}   </div></el-col>
        </el-row>
      </div>

    </div>
  </div>

</template>
<script>
import Search from '@/components/search'
import Api from '@/api'

export default {
  components: {
    Search

  },
  data() {
    return {
      indexArray: []
    }
  },
  mounted() {
    this.getIndexList()
  },
  methods: {
    // 获取所有索引库的集合
    getIndexList() {
      Api.findAllIndex.findAllIndex().then(res => {
        if (res.success) {
          console.log('resdata====', res.data)
          for (let i = 0; i < res.data.length; i++) {
            this.indexArray[i] = res.data
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
  height: 141px;
  background: #d3ddf1;
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
    display: flex;
    flex-wrap: wrap;
    width: 100%;
    height:100%;
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
    width: 100px;
    background: #d3dce6;
  }
  .bg-purple-light {
    background: #e5e9f2;
  }
  .grid-content {
    border-radius: 4px;
    min-height: 72px;
    margin-left: 64px;
  }
  .row-bg {
    min-height: 72px;
    background-color: #767686;
  }
}

</style>
