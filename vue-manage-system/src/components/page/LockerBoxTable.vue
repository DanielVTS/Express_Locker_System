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
                <el-input v-model="query.id" placeholder="搜索快递柜id" class="handle-input mr10"></el-input>
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
                <el-table-column prop='lockerBoxId' label='快递柜格ID' width='290' align='center'></el-table-column>
                <el-table-column prop="lockerId" label="快递柜ID" width="170" align="center"></el-table-column>
                <el-table-column prop='lockerColumn' label='柜格列'></el-table-column>
                <el-table-column prop='lockerRow' label='柜格行'></el-table-column>
<!--                <el-table-column prop="doorStatus" label="柜门状态"></el-table-column>-->
                <el-table-column label='柜门状态' align='center'>
                    <template slot-scope='scope'>
                        <el-tag :type="scope.row.doorStatus===1?'success':''">
                            {{ scope.row.doorStatus === 1 ? '关闭' : '打开' }}
                        </el-tag>
                    </template>
                </el-table-column>
                <el-table-column label='柜格是否占用' align='center'>
                    <template slot-scope='scope'>
                        <el-tag :type="scope.row.boxIsEmpty===1?'success':''">
                            {{ scope.row.boxIsEmpty === 1 ? '可用' : '已占用' }}
                        </el-tag>
                    </template>
                </el-table-column>
<!--                <el-table-column prop="boxIsEmpty" label="柜格是否占用"></el-table-column>-->
<!--                <el-table-column prop="boxType" label="柜格类型"></el-table-column>-->
                <el-table-column label='柜格类型' align='center'>
                    <template slot-scope='scope'>
                        <el-tag :type='getType(true,scope.row.boxType)'>
                            {{ getType(false, scope.row.boxType)}}
                        </el-tag>
                    </template>
                </el-table-column>
<!--                <el-table-column prop='status' label='状态'></el-table-column>-->
                <el-table-column label='状态' align='center'>
                    <template slot-scope='scope'>
                        <el-tag :type="scope.row.status===1?'success':''">
                            {{ scope.row.status === 1 ? '启用' : '禁用' }}
                        </el-tag>
                    </template>
                </el-table-column>
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
            <el-form ref="form" :model="form" label-width="100px">
                <el-form-item label='ID'>
                    <el-input readonly v-model='form.lockerBoxId'></el-input>
                </el-form-item>
                <el-form-item label="柜门状态">
                    <el-select placeholder='请选择' v-model="form.doorStatus"  >
                        <el-option key='doorStatus2' label='打开' value='2'></el-option>
                        <el-option key='doorStatus1' label='关闭' value='1'></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="柜格是否占用">
                    <el-select placeholder='请选择' v-model='form.boxIsEmpty'>
                        <el-option key='boxIsEmpty2' label='已占用' value='2'></el-option>
                        <el-option key='boxIsEmpty1' label='可用' value='1'></el-option>
                    </el-select>
<!--                    <el-input v-model="form.boxIsEmpty"></el-input>-->
                </el-form-item>
                <el-form-item label='状态'>
                    <el-select placeholder='请选择' v-model='form.status'>
                        <el-option key='status1' label='启用' value='1'></el-option>
                        <el-option key='status2' label='禁用' value='2'></el-option>
                    </el-select>
<!--                    <el-input v-model='form.status'></el-input>-->
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
import {ChangeLockerBoxInfo, DeleteLockerBoxInfo, GetLockerBoxInfo} from '@/api/locker';

export default {
        name: 'LockerBoxTable',
        data() {
            return {
                query: {
                    lockerBoxId:'',
                    lockerId:'',
                    lockerColumn:'',
                    lockerRow:'',
                    doorStatus:'',
                    boxIsEmpty:'',
                    boxType: '',
                    status:'',
                    pageIndex: 1,
                    pageSize: 5,
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
            getType(isColor,type) {
                let result;
                if(isColor){
                    switch (type) {
                        case 1: result='danger';break;
                        case 2: result='';break;
                        case 3: result='success';break;
                    }
                }else{
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

            async getData() {
                await GetLockerBoxInfo(this.query).then(res => {
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
                console.log(row.lockerId);
                this.$confirm('确定要删除吗？', '提示', {
                    type: 'warning'
                })
                    .then(() => {
                        if(row.boxIsEmpty!==1&&row.doorStatus!==1){
                            this.$message.error('柜格使用中！');
                        }else{
                            this.$message.success('删除成功');
                            this.tableData.splice(index, 1);
                            this.confirmDelete(row);
                        }
                    })
                    .catch(() => {
                    });
            },
            async confirmDelete(row){
                await DeleteLockerBoxInfo(row.lockerBoxId);
                await this.$set(this.query, 'pageIndex', this.query.pageIndex);
                await this.getData();
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
                this.form={};
                this.idx = index;
                this.form = JSON.parse(JSON.stringify(row));
                this.form.doorStatus='';
                this.form.boxIsEmpty = '';
                this.form.status = '';
                this.editVisible = true;
            },
            // 保存编辑
            saveEdit() {
                this.editVisible = false;
                this.$message.success(`修改第 ${this.idx + 1} 行成功`);
                this.$set(this.tableData, this.idx, this.form);
                ChangeLockerBoxInfo(this.form).then(res => {
                    this.getData();
                });
                console.log(this.form);
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
