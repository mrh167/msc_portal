<template>
  <el-dialog
    title="提示"
    :visible.sync="centerDialogVisible"
    :before-close="onClose"
    :close-on-click-modal="false"
    width="50%"
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
      </el-table>
    </div>
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
    this.getList()
  },
  methods: {
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
