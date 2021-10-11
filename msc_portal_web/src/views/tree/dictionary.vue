/* eslint-disable vue/no-dupe-keys */
<template>
  <el-dialog
    title="提示"
    :visible.sync="centerDialogVisible"
    :before-close="onClose"
    :close-on-click-modal="false"
    width="70%"
    center
  >
    <div>

      <el-table
        v-loading="listLoading"
        :data="list"
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
          prop="value"
          label="字典值"
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
      </el-table>
    </div>
    <div class="footer">
      <el-pagination
        v-show="list.length>0"
        background
        :page-size="pageSize"
        :current-page="pageNum"
        :page-sizes="[5, 10, 20, 50, 70, 100]"
        layout="slot, prev, pager, next, sizes, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      >
        <span class="lui-pagination__total">共 {{ total }} 条</span>
      </el-pagination>
    </div>
  </el-dialog>
</template>
<script>
import Api from '@/api'

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
  props: {
    lookList: {
      type: Array,
      default: () => []
    }
  },
  data() {
    return {
      list: [],
      dictList: Object.assign({}, this.lookList[0]),
      centerDialogVisible: true,
      listLoading: false,
      total: 0,
      pageNum: 1,
      pageSize: 5,
      pageSizes: [5, 10, 20, 50, 100]
    }
  },
  mounted() {
    console.log('dictList=========', this.dictList)
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      Api.dictData.pageList({
        'pageNum': this.pageNum,
        'pageSize': this.pageSize,
        'id': this.dictList.id,
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
    // X掉
    onClose() {
      this.$store.dispatch('sellerBtnType', 3)
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
    }
  }
}
</script>

<style>

</style>
