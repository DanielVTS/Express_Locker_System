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
        <el-input v-model="query.lockerBoxId" placeholder="搜索ID、包裹ID、取件号(精确查询)" class="handle-input mr10"></el-input>
        <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
      </div>
      <el-table
          :data="tableData"
          border
          class="table"
          ref="multipleTable"
          header-cell-class-name="table-header"
          @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <!--                <el-table-column prop="packageBoxId" width='165' label="包裹柜格ID" align="center"></el-table-column>-->
        <el-table-column prop="packageId" width='165' label="包裹ID" align="center"></el-table-column>
        <el-table-column prop='lockerId' width='165' label='快递柜ID' align='center'></el-table-column>
        <el-table-column prop='lockerBoxId' width='290' label='快递柜格ID' align='center'></el-table-column>
        <el-table-column prop='packageCode' width='115' label='取件码' align='center'></el-table-column>
        <el-table-column prop='operatorId' label='操作员ID' width='165' align='center'></el-table-column>
        <el-table-column label='状态' align='center'>
          <template slot-scope='scope'>
            <el-tag :type='getType(true,scope.row.status)'>
              {{ getType(false, scope.row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180" align="center" v-if="$store.state.power">
          <template slot-scope="scope">
            <!--                        <el-button-->
            <!--                                type="text"-->
            <!--                                icon="el-icon-edit"-->
            <!--                                @click="handleEdit(scope.$index, scope.row)"-->
            <!--                        >编辑-->
            <!--                        </el-button>-->
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
    <el-dialog title="编辑" :visible.sync="editVisible" width="30%" :close-on-click-modal='false'>
      <el-form ref="form" :model="form" label-width="100px">
        <el-form-item label='包裹ID'>
          <el-input v-model='form.packageId' type='number' readonly></el-input>
        </el-form-item>

        <el-form-item label='操作员ID'>
          <el-input v-model='form.expOperatorId'></el-input>
        </el-form-item>
        <el-form-item label='操作员电话'>
          <el-input v-model='form.expOperatorPhone'></el-input>
        </el-form-item>
        <el-form-item label='状态'>
          <el-select placeholder='请选择' v-model='form.status'>
            <el-option key='status1' label='未入柜' value='1'></el-option>
            <el-option key='status2' label='已入柜' value='2'></el-option>
            <el-option key='status2' label='已取出' value='3'></el-option>
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
import {ChangePackageInfo, DeletePackageInfo, GetPackageBoxInfo} from '@/api/Package';


export default {
  name: 'SendTable',
  data() {
    return {
      query: {
        pbiId: '',
        operatorId: '',
        expressCompany: '',
        expressNumber: '',
        packageId: '',
        packageCode: '',
        lockerId: '',
        lockerBoxId: '',
        status: '',
        pageIndex: 1,
        pageSize: 5,
        date1: '',
        date2: ''
      },
      tableData: [],
      multipleSelection: [],
      delList: [],
      editVisible: false,
      pageTotal: 0,
      form: {},
      idx: -1,
      id: -1,
      expressInfo: [],
      userInfo: [],
      companyInfo: []
    };
  },
  created() {
    this.getData();
  },
  methods: {
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
            result = '未入柜';
            break;
          case 2:
            result = '已入柜';
            break;
          case 3:
            result = '已取出';
            break;
        }
      }
      return result;
    },
    getData() {
      GetPackageBoxInfo(this.query).then(res => {
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
            if (row.status !== 3) {
              this.$message.error('包裹仍未取出！');
            } else {
              this.$message.success('删除成功');
              this.tableData.splice(index, 1);
              DeletePackageInfo(row.packageId);
            }
          })
          .catch(() => {
          });
    },
    // 多选操作
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    delAllSelection() {
      const length = this.multipleSelection.length;
      let str = '';
      this.delList = this.delList.concat(this.multipleSelection);
      for (let i = 0; i < length; i++) {
        str += this.multipleSelection[i].username + ' ';
      }
      this.$message.error(`删除了${str}`);
      this.multipleSelection = [];
    },
    // 编辑操作
    handleEdit(index, row) {
      this.idx = index;
      this.form = JSON.parse(JSON.stringify(row));
      this.form.expOperatorId = sessionStorage.getItem('id');
      this.form.expOperatorPhone = sessionStorage.getItem('phone');
      this.form.status = "";
      this.editVisible = true;
    },
    // 保存编辑
    saveEdit() {
      this.editVisible = false;
      this.$set(this.tableData, this.idx, this.form);
      ChangePackageInfo(this.form).then(res => {
        if (res.code === 200) {
          this.$message.success(`修改第 ${this.idx + 1} 行成功`);
          this.getData();
        } else {
          this.$message.error(res.msg)
        }
      });
      this.$set(this.query, 'pageIndex', this.query.pageIndex);
      this.getData();
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

</style>
