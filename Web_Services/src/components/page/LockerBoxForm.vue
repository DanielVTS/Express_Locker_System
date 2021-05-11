<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-calendar"></i> 表单
        </el-breadcrumb-item>
        <el-breadcrumb-item> 新增用户信息</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <div class="form-box">
        <el-form ref="form" :model="form" label-width="80px">
          <el-form-item label='快递柜ID'>
            <el-input v-model='form.lockerId'></el-input>
          </el-form-item>
          <el-form-item label="所处列">
            <el-input v-model="form.lockerColumn"></el-input>
          </el-form-item>
          <el-form-item label="所处行">
            <el-input v-model="form.lockerRow"></el-input>
          </el-form-item>
          <el-form-item label="柜格类型">
            <el-select v-model="form.boxType" placeholder="请选择">
              <el-option key='power1' label='大' value='1'></el-option>
              <el-option key="power2" label="中" value=2></el-option>
              <el-option key="power3" label="小" value=3></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label='状态'>
            <el-select v-model='form.status' placeholder='请选择'>
              <el-option key='power2' label='禁用' value='2'></el-option>
              <el-option key='power1' label='启用' value='1'></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit">表单提交</el-button>
            <el-button>取消</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import {AddSingleLockerBoxInfo} from '@/api/locker';

export default {
  name: 'LockerBoxForm',
  data() {
    return {
      form: {
        lockerId: '',
        lockerColumn: '',
        lockerRow: '',
        boxType: '',
        status: ''
      },
    };
  },
  methods: {
    onSubmit() {
      AddSingleLockerBoxInfo(this.form).then(res => {
            this.$message.success('提交成功！');
          }
      );
    },

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
