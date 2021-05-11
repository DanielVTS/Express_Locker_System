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
                <el-input v-model="query.id" placeholder="搜索快递柜id或名字" class="handle-input mr10"></el-input>
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
                <el-table-column prop="lockerId" label="快递柜ID" width="170" align="center"></el-table-column>
                <el-table-column prop='province' label='省份'></el-table-column>
                <el-table-column prop='city' label='城市'></el-table-column>
                <el-table-column prop="lockerName" label="快递柜名"></el-table-column>
                <el-table-column prop="totalBox" label="总格数"></el-table-column>
                <el-table-column prop="usedBox" label="使用中格数"></el-table-column>
                <el-table-column prop='rowNum' label='行数'></el-table-column>
                <el-table-column prop='columnNum' label='列数'></el-table-column>
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
                    <el-input readonly v-model='form.lockerId'></el-input>
                </el-form-item>
                <el-form-item label="省份">
                    <el-input v-model="form.province"></el-input>
                </el-form-item>
                <el-form-item label="城市">
                    <el-input v-model="form.city"></el-input>
                </el-form-item>
                <el-form-item label='快递柜名'>
                    <el-input v-model='form.lockerName'></el-input>
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
import {ChangeLockerInfo, DeleteLockerInfo, GetLockerInfo} from '@/api/locker';

export default {
        name: 'LockerTable',
        data() {
            return {
                query: {
                    lockerId:'',
                    province:'',
                    city:'',
                    lockerName:'',
                    totalBox:'',
                    usedBox: '',
                    pageIndex: 1,
                    pageSize: 5,
                    rowNum:0,
                    columnNum:0
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
                GetLockerInfo(this.query).then(res => {
                    this.tableData = res.data.items;
                    this.pageTotal = res.data.total ;
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
                console.log(row.lockerId);
                this.$confirm('确定要删除吗？', '提示', {
                    type: 'warning'
                })
                    .then(() => {
                        if(row.usedBox!==0){
                            this.$message.error("该柜格仍有使用中柜格！");
                        }else{
                            DeleteLockerInfo(row.lockerId).then(res => {
                                if (res.code !== 200) {
                                    this.$message.error(res.message);
                                } else {
                                    this.$message.success('删除成功');
                                    this.tableData.splice(index, 1);
                                }

                            });
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
                this.editVisible = true;
            },
            // 保存编辑
            saveEdit() {
                this.editVisible = false;
                this.$message.success(`修改第 ${this.idx + 1} 行成功`);
                this.$set(this.tableData, this.idx, this.form);
                ChangeLockerInfo(this.form).then(res => {
                });
                console.log(this.form);
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
