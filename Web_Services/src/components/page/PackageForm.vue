<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-calendar"></i> 表单
        </el-breadcrumb-item>
        <el-breadcrumb-item> 新增包裹信息</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <div class="form-box">
        <el-form ref="packageForm" :model="form" label-width="100px" :rules='rules'>
          <el-form-item label='快递单号' prop='expressNumber'>
            <el-input v-model='form.expressNumber'></el-input>
          </el-form-item>
          <el-form-item label='快递公司' prop='expressCompany'>
            <el-input v-model='form.expressCompany'></el-input>
          </el-form-item>
          <el-form-item label='收件人电话' prop='receiverPhone'>
            <el-input v-model='form.receiverPhone'></el-input>
          </el-form-item>
          <el-form-item label='收件人地址' prop='receiverAddress'>
            <el-input v-model='form.receiverAddress' type='text'></el-input>
          </el-form-item>
          <el-form-item label='收件人姓名' prop='receiverName'>
            <el-input v-model='form.receiverName'></el-input>
          </el-form-item>
          <el-form-item label='寄件人电话' prop='senderPhone'>
            <el-input v-model='form.senderPhone'></el-input>
          </el-form-item>
          <el-form-item label='寄件人地址' prop='senderAddress'>
            <el-input v-model='form.senderAddress' type='text'></el-input>
          </el-form-item>
          <el-form-item label='寄件人姓名' prop='senderName'>
            <el-input v-model='form.senderName'></el-input>
          </el-form-item>
          <el-form-item label='操作员ID' prop='expOperatorId'>
            <el-input v-model='form.expOperatorId'></el-input>
          </el-form-item>
          <el-form-item label='操作员电话' prop='expOperatorPhone'>
            <el-input v-model='form.expOperatorPhone'></el-input>
          </el-form-item>
          <el-form-item label='快递柜ID' prop='lockerId'>
            <el-input v-model='form.lockerId'></el-input>
          </el-form-item>
          <el-form-item label='柜格类型'>
            <el-select placeholder='请选择' v-model='form.boxType'>
              <el-option key='status1' label='大格' value='1'></el-option>
              <el-option key='status2' label='中格' value='2'></el-option>
              <el-option key='status3' label='小格' value='3'></el-option>
            </el-select>
          </el-form-item>
          <!--                    <el-form-item label='状态'>-->
          <!--                        <el-select placeholder='请选择' v-model='form.status'>-->
          <!--                            <el-option key='status1' label='未入柜' value='1'></el-option>-->
          <!--                            <el-option key='status2' label='已入柜' value='2'></el-option>-->
          <!--                            <el-option key='status2' label='已取出' value='3'></el-option>-->
          <!--                        </el-select>-->
          <!--                    </el-form-item>-->
          <el-form-item>
            <el-button type="primary" @click="handleConfirm">表单提交</el-button>
            <el-button>取消</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
    <el-dialog title='确认入柜' :visible.sync='editVisible' width='30%' :close-on-click-modal='false'>
      <el-form ref='confirmForm' :model='lockerBoxInfo' label-width='100px'>
        <el-form-item>请根据以下信息将包裹放入柜格中</el-form-item>
        <el-form-item label='柜格ID'>
          <el-input v-model='lockerBoxInfo.lockerBoxId' readonly></el-input>
        </el-form-item>
        <el-form-item label='柜格列'>
          <el-input v-model='lockerBoxInfo.lockerColumn' readonly></el-input>
        </el-form-item>
        <el-form-item label='柜格行'>
          <el-input v-model='lockerBoxInfo.lockerRow' readonly></el-input>
        </el-form-item>
        <el-form-item label='柜格类型'>
          <el-input v-text='getType(false,lockerBoxInfo.boxType)' readonly></el-input>
        </el-form-item>
        <el-form-item label='操作员ID'>
          <el-input v-model='form.expOperatorId' readonly></el-input>
        </el-form-item>
        <el-form-item label='操作员电话'>
          <el-input v-model='form.expOperatorPhone' readonly></el-input>
        </el-form-item>
        <!--                <el-form-item label='状态'>-->
        <!--                    <el-select placeholder='请选择' v-model='form.status'>-->
        <!--                        <el-option key='status1' label='未入柜' value='1'></el-option>-->
        <!--                        <el-option key='status2' label='已入柜' value='2'></el-option>-->
        <!--                        <el-option key='status3' label='已取出' value='3'></el-option>-->
        <!--                    </el-select>-->
        <!--                </el-form-item>-->
      </el-form>
      <span slot='footer' class='dialog-footer'>
                <el-button @click='editVisible = false'>取 消</el-button>
                <el-button type='primary' @click="onSubmit">确 定</el-button>
            </span>
    </el-dialog>

  </div>
</template>

<script>
import {ChangeLockerBoxInfo, GetLockerBoxForPost} from '@/api/locker';
import {AddPackageInfo} from '@/api/Package';

export default {
  name: 'PackageForm',
  data() {
    return {
      form: {
        expOperatorId: '',
        expOperatorPhone: '',
        expressCompany: '',
        expressNumber: '',
        receiverAddress: '',
        receiverName: '',
        receiverPhone: '',
        senderAddress: '',
        senderName: '',
        senderPhone: '',
        status: '',
        lockerId: '',
        boxType: '',
        lockerBoxId: '',
        packageId: ''
      },
      lockerBoxInfo: {},
      editVisible: false,
      imageUrl: '',
      file: '',
      expressInfo: [],
      userInfo: [],
      companyInfo: [],
      rules: {
        expOperatorId: [
          {require: true, min: 18, message: "操作员ID格式有误！", trigger: 'blur'}
        ],
        expOperatorPhone: [
          {require: true, min: 11, message: "操作员手机号格式有误！", trigger: 'blur'}
        ],
        expressCompany: [
          {require: true, message: "快递公司不能为空！", trigger: 'blur'}
        ],
        expressNumber: [
          {require: true, message: "快递单号不能为空", trigger: 'blur'},
        ],
        receiverAddress: [
          {require: true, message: "收件地址不能为空！", trigger: 'blur'},
        ],
        receiverName: [
          {require: true, message: "收件人不能为空！", trigger: 'blur'},
        ],
        receiverPhone: [
          {require: true, min: 11, message: "收件人号码格式有误！", trigger: 'blur'}
        ],
        senderAddress: [
          {require: true, message: "寄件人地址不能为空！", trigger: 'blur'}
        ],
        senderName: [
          {require: true, message: "寄件人不能为空！", trigger: 'blur'}
        ],
        senderPhone: [
          {require: true, min: 11, message: "寄件人号码格式有误！", trigger: 'blur'}
        ],
        lockerId: [
          {require: true, min: 18, message: "快递柜ID格式有误！", trigger: 'blur'}
        ],
      }
    };
  },
  created() {
    this.getData();
  },
  methods: {


    async getLockerBoxForPost() {
      await GetLockerBoxForPost(this.form).then(res => {
            if (res.code === 200 && res.data !== null) {
              this.form.lockerBoxId = res.data.lockerBoxId;
              this.lockerBoxInfo = res.data;
              this.editVisible = true;
            } else {
              this.$message.warning(res.message);
            }

          }
      );
      return result;
    },
    getType(isColor, type) {
      let result;
      if (isColor) {
        switch (type) {
          case 1:
            result = 'danger';
            break;
          case 2:
            result = '';
            break;
          case 3:
            result = 'success';
            break;
        }
      } else {
        switch (type) {
          case 1:
            result = '大格';
            break;
          case 2:
            result = '中格';
            break;
          case 3:
            result = '小格';
            break;
        }
      }
      return result;
    },
    getData() {
      // GetExpressInfo(1).then(res => {
      //     this.expressInfo = res.data.list;
      // });
      // GetUserInfo(1).then(res => {
      //     this.userInfo = res.data.items;
      // });
      // GetCompanyInfo(1).then(res=>{
      //    this.companyInfo = res.data.items;
      // });
      this.form.expOperatorId = sessionStorage.getItem("id")
      this.form.expOperatorPhone = sessionStorage.getItem("phone")
    },
    async handleConfirm() {
      this.$message.info("正在锁定柜格");
      this.lockerBoxInfo = {};
      await this.getLockerBoxForPost();

    },
    async updateLockerBox() {
      this.lockerBoxInfo.boxIsEmpty = '2';
      this.lockerBoxInfo.doorStatus = '1';
      ChangeLockerBoxInfo(this.lockerBoxInfo).then(res => {
        if (res.code === 200) {
          this.$message.success('柜格信息提交成功！');
        } else {
          this.$message.error('柜格信息提交异常！');
        }
      })
    },
    async onSubmit() {
      this.$confirm('确定包裹已入柜？', '提示', {
        type: 'warning'
      })
          .then(() => {
            this.editVisible = false;
            this.updateLockerBox();
            this.form.status = 2;
            AddPackageInfo(this.form).then(res => {
                  if (res.code === 200) {
                    this.$message.success('包裹信息提交成功！');
                  } else {
                    this.$message.error('包裹信息提交异常！');
                  }
                }
            );

          })
          .catch(() => {
            this.editVisible = true;
          });

    }
  }
};
</script>

<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  width: 180px;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}

.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
