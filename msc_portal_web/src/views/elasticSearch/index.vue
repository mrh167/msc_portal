/* eslint-disable vue/valid-template-root */
<template>
  <div v-loading="loading">
    <div class="information-nav">
      <div class="font-sty"><b>Elasticsearch</b></div>
      <div class="input-sty">
        <el-input v-model="ipAddress" placeholder="请输入内容" class="el-input-inner el-input-group">
          <el-button slot="append">连接</el-button>
        </el-input>
      </div>
    </div>
    <div class="eltabs-cls">
      <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
        <el-tab-pane label="概览" name="first">
          <overview />
        </el-tab-pane>
        <el-tab-pane label="索引" name="second">配置管理</el-tab-pane>
        <el-tab-pane label="数据浏览" name="third">角色管理</el-tab-pane>
        <el-tab-pane label="基本查询[+]" name="fourth">定时任务补偿</el-tab-pane>
        <el-tab-pane label="复合查询[+]" name="ffive">定时任务补偿</el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>
<script>

import overview from './components/overview.vue'

export default {
  components: {
    overview
  },
  data() {
    return {
      ipAddress: '',
      loading: false,
      activeName: 'first'
    }
  },
  methods: {
    handleClick(tab, event) {
      console.log(tab, event)
    }
  }
}
</script>
<style lang="scss">
.information-nav {
  box-shadow: 2px 2px 7px 2px #ccc;
  border-radius: 0 0 4px 4px;
  width: 100%;
  height: 30px;
  background: #dadbdff2;
  margin-bottom: 8px;
};
.font-sty{
    float: left;
    height: 30px;
    font-size: 24px;
}
.input-sty {
float: left;
}
.el-input__inner {
    -webkit-appearance: none;
    background-color: #FFFFFF;
    background-image: none;
    border-radius: 4px;
    border: 1px solid #DCDFE6;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    color: #606266;
    display: inline-block;
    font-size: inherit;
    height: 26px;
    line-height: 40px;
    outline: none;
    padding: 0 15px;
    -webkit-transition: border-color 0.2s cubic-bezier(0.645, 0.045, 0.355, 1);
    transition: border-color 0.2s cubic-bezier(0.645, 0.045, 0.355, 1);
    width: 100%;
}

.el-input-group {
    line-height: normal;
    display: inline-table;
    width: 100%;
    border-collapse: separate;
    border-spacing: 0;
    margin-left: 39px;
    margin-top: 2px;
}

</style>
