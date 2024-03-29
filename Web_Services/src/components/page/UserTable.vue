<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-cascades"></i> 基础表格
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <div class="handle-box">
        <el-input v-model="query.username" placeholder="搜索用户名(模糊查询)" class="handle-input mr10"></el-input>
        <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
      </div>
      <el-table
          :data="tableData"
          border
          class="table"
          ref="multipleTable"
          header-cell-class-name="table-header"
          @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="id" label="ID" width="180" align="center"></el-table-column>
        <el-table-column prop="username" label="用户名"></el-table-column>
        <el-table-column prop="phone" label="手机"></el-table-column>
        <el-table-column label="状态" align="center">
          <template slot-scope="scope">
            <el-tag :type="scope.row.status===1?'success':''">
              {{ scope.row.status === 1 ? '启用' : '禁用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label='权限' align='center'>
          <template slot-scope='scope'>
            <el-tag :type="scope.row.authority===1?'success':''">
              {{ scope.row.authority === "1" ? '管理员' : '普通用户' }}
            </el-tag>
          </template>
        </el-table-column>

        <el-table-column prop="createTime" label="注册时间"></el-table-column>
        <el-table-column label="操作" width="180" align="center" v-if="$store.state.power">
          <template slot-scope="scope">
            <el-button
                type="text"
                icon="el-icon-edit"
                @click="handleEdit(scope.$index, scope.row)"
            >编辑
            </el-button>
            <el-button
                type="text"
                icon="el-icon-delete"
                class="red"
                @click="handleDelete(scope.$index, scope.row)"
            >删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination
            background
            layout="total, prev, pager, next"
            :current-page="query.pageIndex"
            :page-size="query.pageSize"
            :total="pageTotal"
            @current-change="handlePageChange"
        ></el-pagination>
      </div>
    </div>

    <!-- 编辑弹出框 -->
    <el-dialog title="编辑" :visible.sync="editVisible" width="30%">
      <el-form ref="form" :model="form" label-width="70px">
        <el-form-item label='ID'>
          <el-input readonly v-model='form.id'></el-input>
        </el-form-item>
        <el-form-item label="用户名">
          <el-input v-model="form.username"></el-input>
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="form.phone"></el-input>
        </el-form-item>
        <el-form-item label="权限">
          <el-select v-model="form.authority" placeholder="请选择">
            <el-option key="authority2" label="普通用户" value="2"></el-option>
            <el-option key="authority1" label="管理员" value="1"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label='账号状态'>
          <el-select v-model='form.status' placeholder='请选择'>
            <el-option key='status1' label='启用' value='1'></el-option>
            <el-option key='status2' label='禁用' value='2'></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </span>
    </el-dialog>
  </div>
</template>

<script>
import {ChangeUserInfo, DeleteUserInfo, GetUserInfo} from '@/api/user';

export default {
  name: 'UserTable',
  data() {
    return {
      query: {
        username: '',
        password: '',
        phone: '',
        status: '',
        authority: '',
        createTime: '',
        pageIndex: 1,
        pageSize: 5
      },
      tableData: [],
      multipleSelection: [],
      delList: [],
      editVisible: false,
      pageTotal: 0,
      form: {},
      idx: -1,
      id: -1
    };
  },
  created() {
    this.getData();
  },
  methods: {
    getData() {
      GetUserInfo(this.query).then(res => {
        this.tableData = res.data.items;
        this.pageTotal = res.data.total || 50;
      });
    },
    // 触发搜索按钮
    handleSearch() {
      this.$set(this.query, 'pageIndex', 1);
      this.getData();
    },
    // 删除操作
    handleDelete(index, row) {
      // 二次确认删除
      console.log(index);
      console.log(row.id);
      this.$confirm('确定要删除吗？', '提示', {
        type: 'warning'
      })
          .then(() => {
            if (row.id === sessionStorage.getItem("id")) {
              this.$message.error('不可删除登陆中的账号！')
            } else {
              this.$message.success('删除成功');
              this.tableData.splice(index, 1);
              DeleteUserInfo(row.id);
            }

          })
          .catch(() => {
          });
    },
    // 多选操作
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },

    // 编辑操作
    handleEdit(index, row) {
      this.idx = index;
      this.form = JSON.parse(JSON.stringify(row));
      this.editVisible = true;
    },
    // 保存编辑
    saveEdit() {
      this.editVisible = false;
      ChangeUserInfo(this.form).then(res => {
        if (this.form.id == sessionStorage.getItem('id')) {
          this.$message.success(`修改成功，请重新登陆`);
          sessionStorage.removeItem('ms_username');
          sessionStorage.removeItem('ms_password');
          sessionStorage.removeItem('id')
          this.$router.push('/login');
        } else {
          this.$message.success(`修改第 ${this.idx + 1} 行成功`);
          this.getData();
        }
      });

    },
    // 分页导航
    handlePageChange(val) {
      this.$set(this.query, 'pageIndex', val);
      this.getData();
    }
  }
};
</script>

<style scoped>
.handle-box {
  margin-bottom: 20px;
}

.handle-select {
  width: 120px;
}

.handle-input {
  width: 300px;
  display: inline-block;
}

.table {
  width: 100%;
  font-size: 14px;
}

.red {
  color: #ff0000;
}

.mr10 {
  margin-right: 10px;
}

.table-td-thumb {
  display: block;
  margin: auto;
  width: 40px;
  height: 40px;
}
</style>
