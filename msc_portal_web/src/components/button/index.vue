<template>
  <div class="container">
    <div class="container-top">
      <div class="container-top-left">
        <span class="header-border" />
        <span class="header-title">数据列表</span>
      </div>
      <div class="container-top-right">
        <div
          v-for="(item, index) in tableButton"
          :key="index"
          class="container-top-right-but"
        >
          <el-button
            v-if="item.genre === 'upload'"
            :key="item.id"
            class="btn-upload"
            :type="item.type"
            :icon="item.icon"
            @click="handelClick(item)"
          >{{ item.label }}</el-button>

          <el-badge
            v-else-if="item.genre === 'badge'"
            :value="item.value ? item.value : null"
            :max="item.max ? item.max : null"
            :is-dot="item.isdot ? item.isdot : false"
            :hidden="item.hidden ? item.hidden : false"
            :type="item.dotType ? item.dotType : danger"
            class="lui-badge-item"
          >
            <el-button
              :key="item.id"
              :type="item.type"
              :icon="item.icon"
              @click="handelClick(item)"
            >{{ item.label }}</el-button>
          </el-badge>

          <el-button
            v-else
            :key="item.id"
            :type="item.type"
            :icon="item.icon"
            @click="handelClick(item)"
          >
            {{ item.label }}
          </el-button>
        </div>
      </div>
    </div>

    <!-- 批量上传 -->
    <el-dialog
      v-if="buttons.upload"
      :title="buttons.upload.label ? buttons.upload.label : '批量上传'"
      :visible.sync="isUploadModalShow"
    >
      <div
        v-if="uploading"
        v-loading="true"
        style="width:100%;height:200px"
      />
      <el-upload
        v-else
        ref="upload"
        class="upload"
        drag
        :name="buttons.fileName"
        :action="UploadUrl()"
        :data="uploadData"
        :headers="header"
        close-on-click-modal="false"
        width="45%"
        :auto-upload="false"
        :file-list="fileList"
        :accept="defaultUploadConfig.accept"
        :limit="defaultUploadConfig.limit"
        :before-upload="beforeAvatarUpload"
        :on-change="changeUpload"
        :before-remove="beforeRemove"
        :on-remove="defaultUploadConfig['on-remove']"
        :on-success="defaultUploadConfig['on-success']"
        :on-error="defaultUploadConfig['on-error']"
        :on-exceed="defaultUploadConfig['on-exceed']"
      >
        <i class="lui-icon-upload" />
        <div class="lui-upload__text">
          将文件拖到此处，或
          <em>点击上传</em>
        </div>
        <div
          slot="tip"
          class="lui-upload__tip"
        >{{ defaultUploadConfig.Uploadtips }}</div>
        <div
          slot="tip"
          class="lui-upload__tip red"
        >{{ defaultUploadConfig.uploadtipsadd }}</div>
      </el-upload>
      <template slot="footer">
        <span
          v-if="buttons.upload.templateUrl"
          style="float: left"
        >
          <el-link
            type="info"
            :underline="false"
            icon="lui-icon-download"
            @click="downLoadTemplate"
          >下载模板</el-link>
        </span>
        <el-button
          size="small"
          class="cancel"
          @click="isUploadModalShow = false"
        >取消</el-button>
        <el-button
          v-if="buttons.upload.checkUrl"
          type="primary"
          size="small "
          @click="uploadCheckFile"
        >校验</el-button>
        <el-button
          v-waves
          type="primary"
          size="small "
          @click="uploadFile"
        >上传</el-button>
      </template>
    </el-dialog>
    <el-dialog
      class="error-dialog"
      title="上传结果反馈:"
      :visible.sync="dialogTableVisible"
      :close-on-click-modal="false"
    >
      <div class="dialog-table-list">
        <div
          v-show="moreErr"
          style="color: red;font-size: 12px"
        >异常太多，最多显示300条</div>
        <el-table
          stripe
          border
          size="mini"
          max-height="310px"
          :data="gridData"
        >
          <el-table-column
            align="center"
            property="msg"
            label="反馈明细"
            width="350"
          >
            <template slot-scope="{row}">
              {{ row.msg }}
            </template>
          </el-table-column>
          <el-table-column
            align="center"
            property="rowNo"
            label="行号列表"
          />
        </el-table>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import Http from '@/utils/http'
import { exportExcel } from '@/utils/downloadRequest'
export default {

  props: {
    tableButton: {
      type: Array,
      default: () => []
    },
    buttons: {
      type: Object,
      default() {
        return {}
      }
    },
    configdeptNo: {
      type: String,
      default() {
        return ''
      }
    },
    configdeptName: {
      type: String,
      default() {
        return ''
      }
    },
    checkDeptNo: {
      type: Boolean
    }
  },
  data() {
    return {
      baseURL: Http.baseContextUrl,
      isUploadModalShow: false, // 上传模态窗
      dialogTableVisible: false,
      gridData: [],
      uploading: false, // 上传文件loading
      fileList: [], // 上传列表
      onlyCheck: false,
      moreErr: false,
      header: { 'X-Requested-With': 'XMLHttpRequest' },
      uploadData: {
        async: true,
        deptNo: '',
        deptName: ''
      },
      fileSizeM: 0,
      uploadLoading: {}
    }
  },
  computed: {
    // eslint-disable-next-line vue/return-in-computed-property
    defaultUploadConfig() {
      if (this.buttons.upload && this.buttons.upload.uploadConfig) {
        return {
          uploadActionUrl: '请设置uploadURL',
          Uploadtips: this.buttons.upload.uploadtips || '只能上传xlsx文件，文件大小不超过50M，大文件可选后台上传',
          uploadtipsadd: this.buttons.upload.uploadtipsadd,
          limit: 2, // 最大允许上传个数
          'on-remove': () => {
            // this.$refs.upload.clearFiles()
          },
          'before-remove': (response) => {
            this.$refs.upload.clearFiles()
          },
          // 文件列表移除文件时的钩子,一般不用
          'on-success': (response) => {
            //  debugger
            this.uploadLoading.close()
            this.isUploadModalShow = false
            this.$refs.upload.clearFiles()
            // 这里开始修改逻辑
            if (!response.success) {
              this.$message({
                message: response.errMessage,
                type: 'error',
                duration: 3 * 1000
              })
              return
            } else {
              if (response.data.errorCount === 0) {
                this.$message({
                  message: '数据全部上传成功！',
                  type: 'success',
                  duration: 3 * 1000
                })
              }
              if (response.data.errorCount > 0) {
                this.dialogTableVisible = true
                this.gridData = response.data.detaileds
                /* this.gridData.forEach((item) => {
                 item.msg = utils.escape2Html(item.msg)
               })*/
                this.moreErr = response.data.detaileds.length > 300
              }
              this.$emit('uploadSuccess')
              this.isUploadModalShow = false
            }
          }, // 文件上传成功时的钩子
          'on-error': (response) => {
            this.uploadLoading.close()
            this.$message({
              message: '上传异常',
              type: 'error',
              duration: 3000
            })
          }, // 文件上传失败时的钩子
          'on-exceed': () => {
            this.uploadLoading.close()
            this.$message({
              message: '上传文件数量超过限制，不能超过1个',
              type: 'error',
              duration: 3000
            })
          },
          ...this.buttons.upload.uploadConfig
        }
      }
    }
  },
  watch: {
    configdeptNo(newval, oldval) {
      this.configdeptNo = newval
      this.uploadData.deptNo = newval
    },
    configdeptName(newval, oldval) {
      this.configdeptName = newval
      this.uploadData.deptName = newval
    }
  },

  methods: {
    handelClick(item) {
      this.$emit('handelClick', { type: item.id })
      if (item.id === 'upload') {
        if (this.checkDeptNo) {
          if (this.uploadData.deptNo === '' || this.uploadData.deptNo === null) {
            this.$message.error('请输入事业部！')
            return
          } else {
            this.isUploadModalShow = true
          }
        } else {
          delete this.uploadData.deptNo
          delete this.uploadData.deptName
          this.isUploadModalShow = true
        }
      }
    },
    // 下载空模板
    downLoadTemplate() {
      const actionUrl = this.buttons.upload.templateUrl
      exportExcel(actionUrl, {})
    },
    beforeRemove(file) {
      file = ''
      this.fileList = []
    },
    beforeAvatarUpload(file) {
      const Xls = file.name.split('.')
      if (Xls[1] === 'xlsx') {
        return file
      } else {
        this.$message.error('上传文件只能是 xlsx 格式')
        return false
      }
    },
    changeUpload(file, fileList) {
      const fileSizeM = file.size / 1024 / 1024
      let maxM = 50
      if (this.buttons.upload.maxM) {
        maxM = this.buttons.upload.maxM
      }
      if (fileSizeM > maxM) {
        this.$message({
          message: '上传的文件大小超出限制',
          type: 'error',
          duration: 3000
        })
        this.fileList = []
        return
      }
      const Xls = file.name.split('.')
      if (!(Xls[1] === 'xlsx')) {
        this.$message({
          message: '上传文件只能是xlsx 格式',
          type: 'error',
          duration: 3000
        })
        this.fileList = []
        return
      }
      this.fileSizeM = fileSizeM
      if (fileList.length > 0) {
        this.fileList = [fileList[fileList.length - 1]]
      }
    },
    UploadUrl() {
      return this.buttons.upload.uploadConfig.uploadActionUrl
    },
    uploadCheckFile() {
      if (this.fileList.length < 1) {
        this.$message({
          message: '请选择上传的文件',
          type: 'error',
          duration: 2000
        })
        return
      }
      this.onlyCheck = true
      this.defaultUploadConfig.uploadActionUrl = this.buttons.upload.checkUrl
      this.$refs.upload.submit()
    },
    uploadFile(params) {
      if (this.fileList.length < 1) {
        this.$message({
          message: '请选择上传的文件',
          type: 'error',
          duration: 2000
        })
        return
      }
      this.onlyCheck = false
      this.uploadData.async = true
      if (this.fileSizeM > 1) {
        this.$message({
          message: '文件请控制到1M以内！',
          type: 'error',
          duration: 2000
        })
        return false
      }
      this.uploadLoading = this.$loading({
        lock: true,
        text: '上传中...',
        spinner: 'lui-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)',
        customClass: 'downloadClassLoading'
      })
      this.defaultUploadConfig.uploadActionUrl = this.buttons.upload.uploadConfig.uploadActionUrl
      this.$refs.upload.submit()
    }
  }
}

</script>
<style>
.error-dialog .lui-dialog__body {
  padding-top: 10px !important;
}
.red {
  color: #ff0000;
}
</style>
<style lang="scss" scoped>
@import '@/assets/stylus/main';
.upload {
  text-align: center;
}
.lui-link.lui-link--info {
  color: $--gl-blue !important;
}
.lui-upload-dragger .lui-upload__text em {
  color: $--gl-blue !important;
}
.upload .lui-dialog__body {
  border-top: 1px solid #e8eaec !important;
}
.cancel {
  margin-right: 8px;
}
.container {
  width: 100%;
  .container-top {
    width: 100%;
    // padding-top: 30px;
    // padding-bottom: 30px;
    margin-bottom: 20px;
    border-bottom: 1px solid #e1ebf5;
    display: flex;
    justify-content: space-between;
    .container-top-left {
      height: 60px;
      line-height: 60px;
      padding-left: 20px;
      //  border-bottom: 1px solid #E1EBF5;
      .header-border {
        width: 3px;
        height: 13px;
        display: inline-block;
      }
      .header-title {
        font-weight: 600;
        font-size: 16px;
        color: #333;
      }
    }
    .container-top-right {
      padding-right: 20px;
      height: 60px;
      line-height: 60px;
      //  text-align: right;
      display: flex;
      .container-top-right-but {
        margin-left: 12px;
      }
      .container-top-right {
        padding-right: 20px;
        height: 60px;
        line-height: 60px;
        //  text-align: right;
        display: flex;
        .container-top-right-but {
          margin-left: 12px;
        }
        .button-upload {
          position: relative;
          overflow: hidden;
          input {
            position: absolute;
            top: 0;
            bottom: 0;
            left: 0;
            right: 0;
            opacity: 0;
          }
        }
      }
    }
  }
 .lui-badge__content.is-fixed {
    position: absolute;
    top: 10px !important;
    right: 20px !important;
    -webkit-transform: translateY(-50%) translateX(100%);
    transform: translateY(-50%) translateX(100%);
  }
}
 .lui-badge {
    display: block!important;
}
</style>
