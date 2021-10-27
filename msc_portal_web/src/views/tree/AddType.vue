<template>
  <el-dialog
    title="提示"
    :visible="centerDialogVisible"
    :before-close="onClose"
    :close-on-click-modal="false"
    width="30%"
    center
  >
    <span>
      <el-form ref="form" :model="form" status-icon :rules="rules" label-width="80px">
        <el-form-item label="类型名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入类型名称" />
        </el-form-item>
        <el-form-item label="唯一编码" prop="code">
          <el-input v-model="form.code" placeholder="请输入唯一编码" />
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input v-model="form.sort" placeholder="请输入sort" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" />
        </el-form-item>
        <el-form-item>
          <template>
            <span class="dialog-footer">
              <el-button @click="onBack('form')">取 消</el-button>
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
    formData: {
      type: Object,
      default: () => {}
    },
    msg: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      form: {},
      centerDialogVisible: true,
      resetForm: '',
      //   passwordType: 'password',
      //   form: {
      //     name: '',
      //     code: '',
      //     sort: '',
      //     remark: ''
      //   },
      rules: {
        /* createType: [
            { required: true, message: '创建类型不能为空', trigger: 'blur' }
          ]*/
        name: [
        //   { validator: checkAccounts, trigger: 'blur' },
          { required: true, message: '类型名称不能为空', trigger: 'change' }
        ],
        code: [
          { required: true, message: '唯一编码不能为空', trigger: 'blur' }
        //   { pattern: /(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{8,12}/,
        //     message: '密码必须是大写字母，小写字母，数字，特殊字符组成，且长度为8到12位！',
        //     trigger: 'blur' }
        ],
        sort: [
          { required: true, message: '排序不能为空', trigger: 'blur' }
        ],
        remark: [
        //   { required: true, validator: checkPhoneEmail, trigger: 'blur' }
        //   { pattern: /^1[3456789]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }
          { required: true, message: '备注不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  mounted() {
    console.log('this.editList=====', this.formData)
    console.log('msc=====', this.msg)
    const defaultForm = this.formData
    this.$nextTick(function() {
      this.form = { ...defaultForm }
    })
  },
  methods: {

    // 取消
    onBack(formName) {
      this.$refs[formName].resetFields()
      Object.assign(this.$data, this.$options.data())
      this.$store.dispatch('sellerBtnType', 3)
    },
    // X掉
    onClose() {
      this.$store.dispatch('sellerBtnType', 3)
    },
    submitForm(formName) {
      console.log('forName', formName)
      this.$refs[formName].validate((valid) => {
        if (this.msg === '添加') {
          if (valid) {
            Api.DictType.add(this.form).then(res => {
              if (res.success) {
                this.$store.dispatch('sellerBtnType', 3)
                this.$emit('getList')
                this.$showSuccessMsg('添加成功')
              } else {
                this.$showErrorMsg(res.message)
              }
            }).catch((e) => {
              this.$showErrorMsg(e)
            })
          }
        } else {
          if (valid) {
            let formParams = {
                id: this.form.id,
                
            }

            Api.DictType.edit(this.form).then(res => {
              console.log('form=====', this.form)
              if (res.success) {
                alert(1111)
                this.$store.dispatch('sellerBtnType', 3)
                this.$emit('getList')
                this.$showSuccessMsg('添加成功')
              } else {
                this.$showErrorMsg(res.message)
              }
            }).catch((e) => {
              this.$showErrorMsg(e)
            })
          }
        }
      })
    }
  }
}
</script>
