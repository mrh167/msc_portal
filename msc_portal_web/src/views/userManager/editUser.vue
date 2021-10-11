<template>
  <el-dialog
    title="提示"
    :visible.sync="centerDialogVisible"
    :before-close="onClose"
    :close-on-click-modal="false"
    width="30%"
    center
  >
    <span>
      <el-form ref="form" :model="form" status-icon :rules="rules" label-width="80px">
        <el-form-item label="账号" prop="account">
          <el-input v-model="form.account" placeholder="请输入账号" />
        </el-form-item>
        <!-- <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" placeholder="请输入密码" show-password />
        </el-form-item> -->
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="用户昵称" prop="nickName">
          <el-input v-model="form.nickName" placeholder="请输入昵称" />
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入手机号" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item>
          <template>
            <span class="dialog-footer">
              <el-button @click="onBack">取 消</el-button>
              <el-button type="primary" @click="submitForm('form')">确 定</el-button>
            </span>
          </template>
        </el-form-item>
      </el-form>
    </span>

  </el-dialog>
</template>

<script>
import Api from '@/api'

export default {
  props: {
    editList: {
      type: Array,
      default: () => []
    },
    getList: {
      type: Array,
      default: () => []
    }
  },
  data() {
    var checkAccounts = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入账号!!!!'))
      } else {
        if (this.form.account !== '') {
          Api.UserManager.checkAccount({ account: this.form.account }).then(res => {
            console.log('响应结果是：', this.form.account)
            if (res.success) {
              callback(new Error('账号重复，请重新输入'))
            }
          }).catch((e) => {
            this.$showErrorMsg(e)
          })
        }
      }
    }
    var checkPhoneEmail = (rule, value, callback) => {
      if (!this.form.phone) {
        callback(new Error('请输手机号'))
      }
      callback()
    }
    var checkEmail = (role, value, callback) => {
      if (!this.checkEmail(this.form.email)) {
        callback(new Error('请输正确的邮箱'))
      }
      callback()
    }

    return {
      centerDialogVisible: true,
      listLoading: false,
      form: Object.assign({}, this.editList[0]),
      rules: {
        /* createType: [
            { required: true, message: '创建类型不能为空', trigger: 'blur' }
          ]*/
        account: [
        //   { validator: checkAccounts, trigger: 'blur' },
          { required: true, message: '账号不能为空', trigger: 'change' }
        ],
        password: [
          { required: true, message: '密码不能为空', trigger: 'blur' }
        //   { pattern: /(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{8,12}/,
        //     message: '密码必须是大写字母，小写字母，数字，特殊字符组成，且长度为8到12位！',
        //     trigger: 'blur' }
        ],
        nickName: [
          { required: true, message: '昵称不能为空', trigger: 'blur' }
        ],
        phone: [
        //   { required: true, validator: checkPhoneEmail, trigger: 'blur' }
        //   { pattern: /^1[3456789]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }
          { required: true, message: '邮箱不能为空', trigger: 'blur' }
        ],
        email: [
        //   { required: true, validator: checkEmail, trigger: 'blur' }
        //   { min: 2, max: 11, message: '长度在 2 到 11 个字符', trigger: 'blur' }
          { required: true, message: '邮箱不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {

    // 取消
    onBack() {
      this.$store.dispatch('sellerBtnType', 3)
    },
    // X掉
    onClose() {
      this.$store.dispatch('sellerBtnType', 3)
    },
    editForm() {
      this.listLoading = true
      Api.UserManager.editGetUser(
        { id: this.editList.id,
          account: this.editList.account
        }
      ).then(res => {

      })
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const params = {
            id: this.form.id,
            account: this.form.account,
            username: this.form.username,
            password: this.form.password,
            nickName: this.form.nickName,
            phone: this.form.phone,
            email: this.form.email
          }
          Api.UserManager.editUser(params).then(res => {
            if (res.success) {
              this.$store.dispatch('sellerBtnType', 3)
              this.$emit('getList')
              this.$showSuccessMsg('修改成功')
            } else {
              this.$showErrorMsg(res.message)
            }
          }).catch((e) => {
            this.$showErrorMsg(e)
          })
        }
      })
    },
    // 验证邮箱和手机号
    checkEmail(email) {
      var pattern = /^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/
      if (email) {
        if (!pattern.test(email)) {
          return false
        }
        return true
      }
    }
  }
}
</script>

<style lang="scss" scoped>
    .dialog-footer{
        float: right;
    };
</style>
