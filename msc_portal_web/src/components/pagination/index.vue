<template>
  <div v-if="total>0" class="pagination">
    <el-pagination
      background
      :current-page.sync="currentPage"
      :page-sizes="pageSizes"
      layout="total,prev, pager, next, sizes, jumper"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
  </div>
</template>

<script>
export default {
  props: {
    pageSizes: {
      type: Array,
      default() {
        return [10, 20, 50, 100]
      }
    },
    total: {
      type: Number,
      default: 0
    },
    pageSize: {
      type: Number,
      default: 0
    }
  },
  data() {
    return {
      currentPage: 1
    }
  },

  methods: {
    // 点击重置、查询时页数重置为1
    changeCurrentPage() {
      this.currentPage = 1
    },
    handleSizeChange(val) {
      this.$emit('handleSizeChange', val)
    },
    handleCurrentChange(val) {
      this.$emit('handleCurrentChange', val)
    }
  }
}
</script>

<style lang="scss" scoped>
  .pagination{
    width: 100%;
    // margin-top: 20px;
    padding-bottom: 30px;
    padding-right: 20px;
    text-align: right;
  }
</style>
