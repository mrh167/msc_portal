<template>
  <div class="search">
    <div class="configure-title">
      <div class="title-text">
        <div class="title-text-left">筛选搜索</div>
        <div>
          <el-button
            type="primary"
            style="width: 80px;"
            @click.native.prevent="handleSearch"
          >查询</el-button>
          <el-button
            style="width: 80px;"
            @click.native.prevent="handleReset"
          >重置</el-button>
        </div>
      </div>
      <div class="title-serch">
        <div
          ref="serchLeft"
          class="serch-left"
        >
          <el-form
            ref="formSearch"
            :inline="true"
            class="div-form"
            :model="formSearch"
          >
            <div ref="formSearchDiv">
              <template v-for="item in tableSearch">
                <el-form-item
                  :key="item.value"
                  :prop="item.value"
                  :label-width="item.width ? item.width : 110+'px'"
                  :label="item.label"
                  v-bind="item.form"
                  show-overflow-tooltip="true"
                >
                  <template>
                    <!-- <div style="display: flex;"> -->
                    <!-- 动态label 如果标题超出指定范围长度 则tooltip提示-->
                    <!-- <lui-tooltip v-if="item.label.length>7" class="item" effect="dark" :content="item.label" placement="top">
                        <div style="width: 115px;display: inline-block;text-align: right;color:#666;overflow: hidden;text-overflow:ellipsis;white-space: nowrap;">{{ item.label }}</div>
                      </lui-tooltip>
                      <div v-else style="width: 115px;display: inline-block;padding-right: 5px;;text-align: right;color:#666;">{{ item.label }}</div> -->
                    <!-- 普通下拉框
                          code为下拉值   name为下拉名称-->
                    <el-select
                      v-if="item.type === 'select'"
                      v-model="formSearch[item.value]"
                      clearable
                      :no-data-text="'暂无更多'+item.label"
                      :no-match-text="'暂无更多'+item.label"
                      :filterable="item.filterable ? item.filterable : false"
                      :style="{width:(item.inpWidth ? item.inpWidth+'px' : '100%')}"
                      v-bind="item.props"
                      :placeholder="`请选择${item.label}`"
                      @change="handelValue(item.label)"
                    >
                      <el-option
                        v-for="option in item.children"
                        :key="option.value"
                        :value="option[item.code]"
                        :label="option[item.name]"
                      />
                    </el-select>

                    <!-- 联级选择器 远程请求数据-->
                    <el-cascader
                      v-else-if="item.type === 'cascader'"
                      v-model.trim="formSearch[item.value]"
                      clearable
                      :props="props"
                      :filterable="item.filterable ? item.filterable : false"
                      v-bind="item.props"
                      :placeholder="`请选择${item.label}`"
                      @change="handelValue(item.label)"
                    />
                    <!-- 普通输入框 -->
                    <el-input
                      v-if="item.type === 'input'"
                      v-model.trim="formSearch[item.value]"
                      clearable
                      :style="{width:(item.inpWidth ? item.inpWidth+'px' : '100%')}"
                      v-bind="item.props"
                      :maxlength="item.maxlength ? item.maxlength : false"
                      show-word-limit
                      :placeholder="item.placeholder || `请输入${item.label}`"
                      @change="handelValue(item.label)"
                    />
                    <!-- 模糊下拉搜索
                         fetch-suggestions 返回输入建议的方法，仅当你的输入建议数据 resolve 时，通过调用 callback(data:[]) 来返回它-->
                    <el-autocomplete
                      v-if="item.type === 'autocomplete'"
                      v-model.trim="formSearch[item.value]"
                      :style="{width:(item.inpWidth ? item.inpWidth+'px' : '100%')}"
                      clearable
                      class="inline-input"
                      :fetch-suggestions="(queryString,cd)=>{queryUnit(queryString,cd)}"
                      :placeholder="item.placeholder || `请输入检索${item.label}`"
                      @input="handleautocompleteValue(formSearch[item.value],item.value)"
                      @focus="handleSelect(item)"
                    />
                    <!-- 日期时间选择器
                          type	显示类型	year/month/date/dates/ week/datetime/datetimerange/ daterange/monthrange	date
                          range-separator 选择范围时的分隔符
                          format: '', 显示类型
                          value-format: '', 输出类型
                          start-placeholder 范围选择时开始日期的占位内容
                          end-placeholder	  范围选择时结束日期的占位内容-->
                    <el-date-picker
                      v-if="item.type === 'date'"
                      v-model="formSearch[item.value]"
                      :picker-options="item.pickerOptions ? item.pickerOptions : pickerBeginDateBefore"
                      :type="item.displayType"
                      :style="{width:(item.inpWidth ? item.inpWidth+'px' : '100%')}"
                      :range-separator="item.separator"
                      :format="item.format ? item.format : yyyy - MM - dd"
                      :value-format="item.valueFormat ? item.valueFormat : yyyy-MM-dd"
                      :start-placeholder="item.startPlaceholder"
                      :end-placeholder="item.endPlaceholder"
                      :placeholder="item.placeholder || `请选择${item.label}`"
                      @change="handelValue(item.label)"
                    />
                    <!-- </div> -->

                    <div
                      v-if="item.type ==='temperature'"
                      class="container-input"
                    >
                      <el-input
                        v-model.trim="formSearch[item.left.value]"
                        clearable
                        onkeyup="value=value.replace(/[^\d]/g,'')"
                        :style="{width:(item.left.inpWidth ? item.left.inpWidth+'px' : '100%')}"
                        :placeholder="item.left.placeholder || `请输入${item.left.label}`"
                      />
                      <span>{{ item.content.value }}</span>
                      <el-input
                        v-model.trim="formSearch[item.right.value]"
                        onkeyup="value=value.replace(/[^\d]/g,'')"
                        clearable
                        :style="{width:(item.right.inpWidth ? item.left.inpWidth+'px' : '100%')}"
                        :placeholder="item.right.placeholder || `请输入${item.right.label}`"
                      />
                    </div>

                    <!-- 计数器 商家运营专用 -->
                    <div
                      v-if="item.type ==='number'"
                      class="container-input"
                    >
                      <div class="numberBox">
                        <el-input
                          v-model.number="formSearch[item.left.value]"
                          onkeyup="value=value.replace(/[^\d]/g,'')"
                          :style="{width:(item.left.inpWidth ? item.left.inpWidth+'px' : '100%')}"
                          on-keypress="return (/[\d\.]/.test(String.fromCharCode(event.keyCode)))"
                          :placeholder="item.left.placeholder || `请输入${item.left.label}`"
                          @input.native="onInput0_100"
                        />
                        <span class="spanIcon">%</span>
                      </div>
                      <span style="color:#666">{{ item.content.value }}</span>
                      <div class="numberBox">
                        <el-input
                          v-model.number="formSearch[item.right.value]"
                          onkeyup="value=value.replace(/[^\d]/g,'')"
                          :style="{width:(item.right.inpWidth ? item.left.inpWidth+'px' : '100%')}"
                          on-keypress="return (/[\d\.]/.test(String.fromCharCode(event.keyCode)))"
                          :placeholder="item.right.placeholder || `请输入${item.right.label}`"
                          @input.native="onInput0_100"
                        />
                        <span class="spanIcon">%</span>
                      </div>
                    </div>
                  </template>
                </el-form-item>
              </template>
            </div>
          </el-form>

        </div>
        <!-- <div
          v-if="gengduo"
          class="serch-right"
        > -->
        <!-- 动态按钮  -->
        <!-- <div
            v-if="gengduo"
            class="gengduo"
            @click="handelClickDiv"
          >
            <i
              v-if="show"
              class="lui-icon-d-arrow-left icons"
            />
            <i
              v-else
              class="lui-icon-d-arrow-right icons"
            />
          </div> -->
        <!-- <lui-button
            type="primary"
            style="width: 80px;"
            @click.native.prevent="handleSearch">查询</lui-button>
          <lui-button
            style="width: 80px;"
            @click.native.prevent="handleReset">重置</lui-button> -->
        <!-- </div> -->
      </div>
    </div>
  </div>

</template>

<script>
import $ from 'jquery'
// const d = new Date()
// let date1 = d.getFullYear() + '-' + (d.getMonth() > 8 ? d.getMonth() + 1 : '0' + (d.getMonth() + 1)) + '-' + (d.getDate() > 9 ? d.getDate() : '0' + d.getDate()) + ' ' + '23' + ':' + '59' + ':' + '59'
// let date0 = d.getFullYear() + '-' + (d.getMonth() > 8 ? d.getMonth() + 1 : '0' + (d.getMonth() + 1)) + '-' + '01' + ' ' + '00' + ':' + '00' + ':' + '00'

export default {
  props: {
    // 接收父组件传来的数组
    tableSearch: {
      type: Array,
      default: () => []
    }

  },

  data() {
    return {
      formSearch: {},
      show: false,
      offsetHeight: 0,
      gengduo: false,
      height: 0,
      pickerBeginDateBefore: {}
    }
  },

  // 计算属性用于回显
  computed: {
    // 可自定义配置props属性
    props() {
      if (this.$attrs.props) {
        return this.$attrs.props
      } else {
        return { checkStrictly: true }
      }
    }
  },
  beforeDestroy() {
    window.removeEventListener('resize', this.listenResize)
  },
  created() {
    document.onkeydown = e => {
      if (e.code === 'Enter') {
        this.handleSearch()
      }
    }
  },
  mounted() {
    const newDate = new Date($.ajax({ async: false }).getResponseHeader('Date'))
    // 获取标准时间
    this.dataAjax(newDate)
    window.addEventListener('resize', this.listenResize)
    setTimeout(() => {
      // 延迟加载获取当前搜索条件高度
      this.height = this.$refs.formSearchDiv.offsetHeight
      this.offsetHeight = this.$refs.serchLeft
      // 如过当前高度大于两行高度则搜索条件高度设置2行固定高度同时唤出动态按钮
      if (this.offsetHeight.offsetHeight > 115) {
        this.offsetHeight.style.height = 105 + 'px'
        this.gengduo = true
      }
    }, 500)
  },
  methods: {
    onInput0_100(e) {
      this.$message.closeAll()
      if (e.target.value) {
        if (e.target.value < 0 || e.target.value > 100) {
          this.$message.warning('只能输入[0,100]区间的数') // .replace(/[^\d]/g,"")
        }
        e.target.value = (e.target.value >= 0 && e.target.value <= 100 && e.target.value.match(/^\d{1,3}(\.\d*)?$/)) || ''
      }
    },
    // 监听浏览器变化
    listenResize() {
      // 当浏览器发生变化时获取搜索模块高度
      this.height = this.$refs.formSearchDiv.offsetHeight
      // 如果高度小于115（两行条件）隐藏动态按钮 -----else 条件相反
      if (this.height < 115) {
        this.show = false
        this.gengduo = false
        this.offsetHeight.style.height = this.height + 'px'
      } else {
        this.offsetHeight.style.height = 100 + 'px'
        this.gengduo = true
      }
      // 检测按钮方向 如果按钮朝上则总体高度恢复默认值
      if (this.show) {
        this.offsetHeight.style.height = this.height + 'px'
      }
    },
    dataAjax(date) { // 获取当前时间
      // 今天之后禁止选
      this.pickerBeginDateBefore = {
        disabledDate: (date) => {
          return date.getTime() >= (Date.now())
        }
      }
    },
    // 动态按钮操作
    handelClickDiv() {
      // 检测按钮方向 如果按钮朝上则总体高度恢复默认值 else条件相反
      if (this.show) {
        this.offsetHeight.style.height = 105 + 'px'
        this.show = false
      } else {
        this.offsetHeight.style.height = this.height + 'px'
        this.show = true
      }
    },
    // 触发值
    handelValue(label) { // label 当前操作name
      this.$emit('handelValue', this.formSearch, label)
    },
    // 搜索查询按钮
    handleSearch(type) { // type 判断是否重置
      this.$emit('handleSearch', this.formSearch, type)
    },
    // 搜索重置按钮
    handleReset() {
      this.$refs['formSearch'].resetFields()
      this.formSearch = {}
      this.handleSearch('reset')
    },
    // 模糊下拉搜索
    queryUnit(queryString, cb) {
      this.$emit('handleQueryUnit', queryString, cb)
    },
    handleSelect(item) {
      this.$emit('handleSelect', item)
    },
    handelSelectDate(item) {
      this.$emit('handelSelectDate', item, this.formSearch)
    },
    handleautocompleteValue(val, type) {
      const data = {}
      data[type] = val
      this.$emit('handleautocompleteValue', data)
    }
  }
}

</script>

<style lang="scss" scoped>
.lui-tooltip__popper {
  max-width: 30%;
}
.lui-date-editor .lui-range-separator {
  padding: 0 5px;
  line-height: 30px;
  min-width: 20px;
  color: #333;
}
.search {
  .configure-title {
    // padding: 0px 30px;
    background: #ffffff;
    width: 100%;
    border-radius: 0 0 4px 4px;
    .title-text {
      height: 60px;
      border-bottom: 1px solid #e1ebf5;
      padding: 0 25px;
      display: flex;
      justify-content: space-between;
      align-items: center;
      .title-text-left {
        font-size: 16px;
        font-weight: 600;
      }
    }
    .title-serch {
      width: 100%;
      padding-left: 20px;
      padding-right: 20px;
      padding-top: 20px;
      .serch-left {
        width: 100%;
        display: flex;
        flex-wrap: wrap;
        transition: all 0.3s ease 0s;
        overflow: hidden;
      }
    }
    .serch-right {
      display: flex;
      // align-items: flex-end;
      justify-content: center;
      width: 100%;
      padding-bottom: 30px;
      position: relative;
      .gengduo {
        width: 50px;
        height: 100%;
        text-align: center;
        cursor: pointer;
        position: absolute;
        left: 50%;
        top: 50%;
        margin-top: -20px;
        margin-left: -25px;
        animation: quan 0.8s infinite linear;
        .icons {
          transform: rotate(90deg);
          font-size: 18px;
          color: rgb(221, 14, 14);
        }
      }
      @keyframes quan {
        0% {
          top: 35%;
        }
        15% {
          top: 48%;
        }
        30% {
          top: 60%;
        }
        45% {
          top: 70%;
        }
        60% {
          top: 60%;
        }
        75% {
          top: 48%;
        }
        100% {
          top: 35%;
        }
      }
    }
  }
}

.container-input {
  display: flex;
  span {
    padding-left: 12px;
    padding-right: 12px;
    font-size: 20px;
  }
  .numberBox {
    position: relative;
    .spanIcon {
      position: absolute;
      right: 0;
      top: 0;
      font-size: 14px;
      color: #999;
    }
  }
}
</style>
