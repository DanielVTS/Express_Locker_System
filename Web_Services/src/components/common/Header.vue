<template>
  <div class="header">
    <!-- 折叠按钮 -->
    <div class="collapse-btn" @click="collapseChage">
      <i v-if="!collapse" class="el-icon-s-fold"></i>
      <i v-else class="el-icon-s-unfold"></i>
    </div>
    <div class="logo">快递柜后台管理系统</div>
    <div class="header-right">
      <div class="header-user-con">
        <!-- 全屏显示 -->
        <div class="btn-fullscreen" @click="handleFullScreen">
          <el-tooltip effect="dark" :content="fullscreen?`取消全屏`:`全屏`" placement="bottom">
            <i class="el-icon-rank"></i>
          </el-tooltip>
        </div>
        <!-- 用户头像 -->
        <div class="user-avator">
          <img src="../../assets/img/img.jpg"/>
        </div>
        <!-- 用户名下拉菜单 -->
        <el-dropdown class="user-name" trigger="click" @command="handleCommand">
                    <span class="el-dropdown-link">
                        {{ username }}
                        <i class="el-icon-caret-bottom"></i>
                    </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item divided command='editPassword'>修改密码</el-dropdown-item>
            <el-dropdown-item divided command="loginout">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </div>
    <!-- 编辑弹出框 -->
    <el-dialog title='编辑' :visible.sync='editVisible' width='30%'>
      <el-form ref='form' :model='form' label-width='70px'>
        <el-form-item label='id'>
          <el-input readonly v-model='form.id'></el-input>
        </el-form-item>
        <el-form-item label='新密码'>
          <el-input type='password' v-model='form.password1'></el-input>
        </el-form-item>
        <el-form-item label='再次输入新密码'>
          <el-input type='password' v-model='form.password2'></el-input>
        </el-form-item>
      </el-form>
      <span slot='footer' class='dialog-footer'>
                <el-button @click='editVisible = false'>取 消</el-button>
                <el-button type='primary' @click='saveEdit'>确 定</el-button>
            </span>
    </el-dialog>
  </div>
</template>
<script>
import bus from '../common/bus';
import {ChangeUserPassword} from '@/api/user';

export default {
  data() {
    return {
      collapse: false,
      fullscreen: false,
      name: 'linxin',
      message: 2,
      editVisible: false,
      form: {
        id: sessionStorage.getItem('id'),
        password1: "",
        password2: "",
      },
    };
  },
  computed: {
    username() {
      let username = sessionStorage.getItem('ms_username');
      return username ? username : this.name;
    }
  },
  created() {

  },
  methods: {
    saveEdit() {
      this.editVisible = false;

      if (this.form.password1 !== this.form.password2) {
        this.$message.error('两次输入密码不一致！');
      } else {
        this.form.password1 = sessionStorage.getItem("ms_password");
        ChangeUserPassword(this.form).then(res => {
          this.$message.success(`修改密码成功，请重新登陆`);
          this.form.password1 = "";
          this.form.password2 = "";
          sessionStorage.removeItem('ms_username');
          sessionStorage.removeItem('ms_password');
          sessionStorage.removeItem('id')
          this.$router.push('/login');
        });
      }
    },
    // 用户名下拉菜单选择事件
    handleCommand(command) {
      if (command === 'loginout') {
        sessionStorage.removeItem('ms_username');
        sessionStorage.removeItem('ms_password');
        sessionStorage.removeItem('id');
        this.$router.push('/login');
      } else {
        this.editVisible = true;
      }
    },
    // 侧边栏折叠
    collapseChage() {
      this.collapse = !this.collapse;
      bus.$emit('collapse', this.collapse);
    },
    // 全屏事件
    handleFullScreen() {
      let element = document.documentElement;
      if (this.fullscreen) {
        if (document.exitFullscreen) {
          document.exitFullscreen();
        } else if (document.webkitCancelFullScreen) {
          document.webkitCancelFullScreen();
        } else if (document.mozCancelFullScreen) {
          document.mozCancelFullScreen();
        } else if (document.msExitFullscreen) {
          document.msExitFullscreen();
        }
      } else {
        if (element.requestFullscreen) {
          element.requestFullscreen();
        } else if (element.webkitRequestFullScreen) {
          element.webkitRequestFullScreen();
        } else if (element.mozRequestFullScreen) {
          element.mozRequestFullScreen();
        } else if (element.msRequestFullscreen) {
          // IE11
          element.msRequestFullscreen();
        }
      }
      this.fullscreen = !this.fullscreen;
    }
  },
  mounted() {
    if (document.body.clientWidth < 1500) {
      this.collapseChage();
    }
  }
};
</script>
<style scoped>
.header {
  position: relative;
  box-sizing: border-box;
  width: 100%;
  height: 70px;
  font-size: 22px;
  color: #fff;
}

.collapse-btn {
  float: left;
  padding: 0 21px;
  cursor: pointer;
  line-height: 70px;
}

.header .logo {
  float: left;
  width: 250px;
  line-height: 70px;
}

.header-right {
  float: right;
  padding-right: 50px;
}

.header-user-con {
  display: flex;
  height: 70px;
  align-items: center;
}

.btn-fullscreen {
  transform: rotate(45deg);
  margin-right: 5px;
  font-size: 24px;
}

.btn-bell,
.btn-fullscreen {
  position: relative;
  width: 30px;
  height: 30px;
  text-align: center;
  border-radius: 15px;
  cursor: pointer;
}

.btn-bell-badge {
  position: absolute;
  right: 0;
  top: -2px;
  width: 8px;
  height: 8px;
  border-radius: 4px;
  background: #f56c6c;
  color: #fff;
}

.btn-bell .el-icon-bell {
  color: #fff;
}

.user-name {
  margin-left: 10px;
}

.user-avator {
  margin-left: 20px;
}

.user-avator img {
  display: block;
  width: 40px;
  height: 40px;
  border-radius: 50%;
}

.el-dropdown-link {
  color: #fff;
  cursor: pointer;
}

.el-dropdown-menu__item {
  text-align: center;
}
</style>
