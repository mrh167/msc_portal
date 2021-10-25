/* eslint-disable vue/no-unused-components */
/* eslint-disable no-undef */
<template>
  <div v-loading="loading">
    <!-- <div
      v-if="getSellerBtnType===3"
    > -->
    <div class="information-nav">
      <Search
        :table-search="tableSearch"

        @handleSearch="handelSearchClick"
      />
    </div>
    <div class="container-content">
      <!-- 按钮 -->
      <ButtonList
        :table-button="tableButton"
        @handelClick="handelClick"
      />
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
            prop="name"
            label="类型名称"
          />
          <el-table-column
            prop="code"
            label="唯一编码"
          />
          <el-table-column
            prop="sort"
            label="排序"
          />
          <el-table-column
            prop="remark"
            label="备注"
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
              <el-button type="text" @click="dictionary(scope.$index, scope.row)">字典</el-button>
              <el-button type="text" size="small" @click="editHandle(scope.$index,scope.row)">编辑</el-button>
            </template>
          </el-table-column>
        </el-table>

      </div>
      <div class="footer">
        <el-pagination
          v-show="list.length>0"
          background
          :page-size="pageSize"
          :current-page="pageNum"
          :page-sizes="[10, 20, 50, 70, 100]"
          layout="slot, prev, pager, next, sizes, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        >
          <span class="lui-pagination__total">共 {{ total }} 条</span>
        </el-pagination>
      </div>
    </div>
    <!-- </div> -->
    <addType
      v-if="getSellerBtnType===1"
      :form-data="formData"
      :msg.sync="msg"
      @func="childenClick"
    />
    <dictionary
      v-if="getSellerBtnType===2"
      :look-list="lookList"
      @func="childenClick"
    />
    <!-- <editUser
      v-if="getSellerBtnType===5"
      :edit-list="editList"
      @getList="getList"
      @func="childenClick"
    />
    <updateSeller
      v-if="getSellerBtnType===4"
      :vsc-type-list="vscTypeList"
      @func="childenClick"
    /> -->

  </div>
</template>

<script>
// import { fetchList, fetchPv, createArticle, updateArticle } from '@/api/article'
import waves from '@/directive/waves' // waves directive
// import { parseTime } from '@/utils'
// secondary package based on el-pagination
// import pagination from '@/components/pagination'
import Search from '@/components/search'
import ButtonList from '@/components/button'
import addType from './AddType.vue'
import dictionary from './dictionary.vue'
// import editUser from './editUser.vue'
import Api from '@/api'
import { mapGetters } from 'vuex'

const calendarTypeOptions = [
  { key: 'CN', display_name: 'China' },
  { key: 'US', display_name: 'USA' },
  { key: 'JP', display_name: 'Japan' },
  { key: 'EU', display_name: 'Eurozone' }
]

// arr to obj, such as { CN : "China", US : "USA" }
const calendarTypeKeyValue = calendarTypeOptions.reduce((acc, cur) => {
  acc[cur.key] = cur.display_name
  return acc
}, {})

export default {
  name: 'UserManager',
  components: {
    // pagination,
    Search,
    ButtonList,
    addType,
    // addSeller,
    dictionary
    // editUser
    // updateSeller
  },
  directives: { waves },
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: 'success',
        draft: 'info',
        deleted: 'danger'
      }
      return statusMap[status]
    },
    typeFilter(type) {
      return calendarTypeKeyValue[type]
    }
  },
  data() {
    return {
      // 获取所选列表数据
      lookList: [],
      editList: [],
      paginationShow: true,
      tableKey: 0,
      // 列表数据
      list: [],
      // 搜索条件数据
      serchData: {},
      total: 0,
      pageNum: 1,
      pageSize: 10,
      pageSizes: [10, 20, 50, 100],
      loading: false,
      listLoading: false,
      dialogVisible: false,
      listQuery: {
        account: '',
        email: '',
        id: '',
        nickName: '',
        phone: '',
        status: '',
        username: ''
      },
      // 列表的多选
      multipleSelection: [],
      tableButton: [
        // {
        //   label: '批量下载',
        //   id: 'download',
        //   type: 'primary'
        // },
        {
          label: '手工删除',
          type: 'primary',
          id: 'deleted'
        },
        {
          label: '新增类型',
          id: 'add',
          type: 'primary'
        }
      ],
      tableSearch: [
        {
          label: '类型名称',
          type: 'input',
          value: 'name',
          inpWidth: 440 // 输入框长度
        },
        {
          label: '唯一编码',
          type: 'input',
          value: 'code',
          inpWidth: 440 // 输入框长度
        }
      ],
      listShow: true,
      formData: {},
      // 手工添加页面
      addSellerShow: false,
      // 添加用户
      addUserShow: false,
      importanceOptions: [1, 2, 3],
      calendarTypeOptions,
      sortOptions: [{ label: 'ID Ascending', key: '+id' }, { label: 'ID Descending', key: '-id' }],
      statusOptions: ['published', 'draft', 'deleted'],
      showReviewer: false,
      temp: {
        id: undefined,
        importance: 1,
        remark: '',
        timestamp: new Date(),
        title: '',
        type: '',
        status: 'published'
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: 'Edit',
        create: 'Create'
      },
      rules: {
        type: [{ required: true, message: 'type is required', trigger: 'change' }],
        timestamp: [{ type: 'date', required: true, message: 'timestamp is required', trigger: 'change' }],
        title: [{ required: true, message: 'title is required', trigger: 'blur' }]
      },
      downloadLoading: false
    }
  },
  computed: {
    ...mapGetters(['getSellerBtnType', 'getPortalSellerNo'])
  },
  created() {

  },

  mounted() {
    this.getList()
  },
  methods: {
    rowClass() {
      return 'background:#E6F1FF;color:#368CFF;font-size:13px'
    },
    set_cell_style({ row, column, rowIndex, columnIndex }) {
      // console.log(row, column, rowIndex, columnIndex,"row");
      if (columnIndex === 0) {
        return 'background: rgb(230, 241, 255);color: rgb(54, 140, 255)'
      }
    },
    // 是否显示
    switchClick(row) {
      Api.Information.appCenterSetIfShow({
        id: row.id,
        status: row.status ? 1 : 0
      }).then(() => {
        this.$showSuccessMsg('设置成功')
        this.LoadingOne = false
      }).catch((e) => {
        this.$showErrorMsg(e)
        this.LoadingOne = true
        this.getList()
      })
    },
    // 列表按钮
    handelClick(val) {
      switch (val.type) {
        case 'download': // 批量下载
          this.downloadClick()
          break
        case 'deleted': // 批量删除
          this.deleteClick()
          break
        case 'add': // 新增类型
          this.handleAddClick()
          break
      }
    },
    deleteClick() {
      if (this.multipleSelection.length === 0) {
        this.$showErrorMsg('请选择数据')
        return
      }
      var delId = []
      for (let i = 0; i < this.multipleSelection.length; i++) {
        const obj = {}
        obj.id = this.multipleSelection[i].id
        obj.code = this.multipleSelection[i].code
        delId.push(obj)
      }
      this.delete(delId)
    },
    // 手工删除
    delete(row) {
      console.log('row===', row)
      this.$confirm('是否删除该数据?', '温馨提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        closeOnClickModal: false,
        closeOnPressEscape: false,
        type: 'warning'
      }).then(() => {
        Api.Dict.deleteIds({ keyList: row }).then(res => {
          console.log('delete========', row)
          if (res.success) {
            this.$showSuccessMsg('删除成功')
            this.getList()
          } else {
            this.$showErrorMsg(res.errMessage)
          }
        }).catch((e) => {
          this.$showErrorMsg(e)
        })
      }).catch((e) => { console.error('已取消') })
    },
    // 手工添加
    handleAddClick() {
      this.formData = {
        name: '',
        code: '',
        sort: '',
        remark: ''
      }
      this.msg = '添加字典类型'
      this.$store.dispatch('sellerBtnType', 1)
    },
    getList() {
      this.listLoading = true
      Api.DictType.pageList({
        'pageNum': this.pageNum,
        'pageSize': this.pageSize,
        ...this.serchData
      }).then((res) => {
        this.listLoading = false
        if (res.data) {
          for (let i = 0; i < res.data.length; i++) {
            if (res.data[i].status === 0) {
              res.data[i].status = true
            } else if (res.data[i].status === 1) {
              res.data[i].status = false
            }
          }
        }

        this.list = res.data
        this.total = res.total
        this.pageNum = res.pageNum
        this.pageSize = res.pageSize
        this.paginationShow = true
      })
    },
    // 查询重置
    handelSearchClick(val) {
      this.pageNum = 1
      this.pageSize = 10
      this.searchform = val
      this.serchData = val
      this.paginationShow = false
      this.getList()
    },

    // 多选
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    childenClick() {
      this.getList()
    },
    // 字典的方法
    dictionary(index, row) {
      this.lookList = [row]
      this.$store.dispatch('sellerBtnType', 2)
      this.$store.dispatch('portalSellerNo', row.account)
    },
    editHandle(index, row) {
      this.formData = row
      this.msg = '编辑'
      this.$store.dispatch('sellerBtnType', 1)
    //   this.$$store.dispatch('portalSellerNo', row.account)
    },

    handleDownload() {
      this.downloadLoading = true
    },
    getSortClass: function(key) {
      const sort = this.listQuery.sort
      return sort === `+${key}` ? 'ascending' : 'descending'
    },
    // 修改这里的状态
    changeStatusTab(row) {
      console.log('zhuangtaishi=====', row)
      if (row.status) {
        this.$confirm('是否开启该账号？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          closeOnClickModal: false,
          closeOnPressEscape: false,
          type: 'warning'
        }).then(() => {
          Api.Information.appCenterSetIfShow({ id: row.id, status: 1, account: row.account }).then((res) => {
            this.$message({
              type: 'success',
              message: '修改成功!'
            })
          }).catch((e) => {
            this.$showErrorMsg(e)
            row.status = false
          })
        }).catch((e) => {
          row.status = false
        })
      } else {
        this.$confirm('是否禁用该账号？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          closeOnClickModal: false,
          closeOnPressEscape: false,
          type: 'warning'
        }).then(() => {
          Api.Information.appCenterSetIfShow({ id: row.id, status: 0, account: row.account }).then((res) => {
            this.$message({
              type: 'success',
              message: '修改成功!'
            })
          }).catch((e) => {
            this.$showErrorMsg(e)
            row.status = true
          })
        }).catch((e) => {
          row.status = true
        })
      }
    },
    // 翻页-----根据页码变换
    handleSizeChange(val) {
      this.pageSize = val
      this.getList()
    },
    // 分页条数改变
    handleCurrentChange(val) {
      this.pageNum = val
      this.getList()
    }
  }
}
</script>
<style lang="scss">
    .information-nav {
  box-shadow: 2px 2px 7px 2px #ccc;
  border-radius: 0 0 4px 4px;
  width: 100%;
  height: 225px;
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
.footer {
    padding: 20px 0 30px 0;
    text-align: right;
  }
</style>
