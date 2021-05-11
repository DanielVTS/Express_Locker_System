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
          <el-form-item label="用户名">
            <el-input v-model="form.username"></el-input>
          </el-form-item>
          <el-form-item label="密码">
            <el-input v-model="form.password" type="password"></el-input>
          </el-form-item>
          <el-form-item label="手机">
            <el-input type="textarea" v-model="form.phone"></el-input>
          </el-form-item>
          <el-form-item label="权限">
            <el-select v-model="form.authority" placeholder="请选择">
              <el-option key="power0" label="普通用户" value=2></el-option>
              <el-option key="power1" label="管理员" value=1></el-option>
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
import {AddUserInfo} from '@/api/user';

export default {
  name: 'UserForm',
  data() {
    return {
      form: {
        username: '',
        password: '',
        authority: '',
        phone: ''
      },
      file: '',
    };
  },
  methods: {
    onSubmit() {
      AddUserInfo(this.form).then(res => {
            this.$message.success('提交成功！');
          }
      );
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      const reader = new FileReader()
      reader.readAsDataURL(file)
      reader.onload = () => {
        const _base64 = reader.result
        this.imageUrl = _base64 //将_base64赋值给图片的src，实现图片预览
      }
      this.file = file
      // return isJPG && isLt2M;
      return false //终止上传，在用户注册后再上传
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
