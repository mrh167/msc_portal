<template>
  <el-dialog
    title="提示"
    :visible.sync="centerDialogVisible"
    :before-close="onClose"
    :close-on-click-modal="false"
    width="40%"
    center
  >
    <div>

      <el-table
        v-loading="listLoading"
        :data="gridData"
      >
        <el-table-column
          type="selection"
          width="55"
          fixed="left"
          align="center"
        />

        <el-table-column
          prop="id"
        />
        <el-table-column
          prop="num_reviews"
        />
        <el-table-column
          prop="publish_date"
        />
        <el-table-column
          prop="publisher"
        />
        <el-table-column
          prop="summary"
        />
        <el-table-column
          prop="title"
        />
      </el-table>
    </div>
    <template>
      <span class="dialog-footer">
        <el-button @click="onBack">取 消</el-button>
        <el-button type="primary" @click="submitForm('form')">确 定</el-button>
      </span>
    </template>

  </el-dialog>
</template>

<script>
export default {
  props: {
    lookList: {
      type: Array,
      default: () => []
    }
  },
  data() {
    return {
      gridData: this.lookList,
      centerDialogVisible: true,
      listLoading: false
    }
  },
  mounted() {
    // this.getList()
  },
  methods: {
    // 取消
    onBack() {
      this.$store.dispatch('sellerBtnType', 3)
    },
    getList() {
      console.log('gridData', this.gridData)
    },
    // X掉
    onClose() {
      this.$store.dispatch('sellerBtnType', 3)
    }
  }
}
</script>

<style>

</style>
