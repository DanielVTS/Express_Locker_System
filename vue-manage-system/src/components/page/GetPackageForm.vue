<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-calendar"></i> 表单
                </el-breadcrumb-item>
                <el-breadcrumb-item> 取出包裹</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="form-box">
                <el-form ref="packageForm" :model="form" label-width="100px" :rules='rules'>
                    <el-form-item label='取件码' prop='receiverPhone'>
                        <el-input v-model='form.code'></el-input>
                    </el-form-item>
                    <el-form-item label='收件人电话' prop='receiverPhone'>
                        <el-input v-model='form.phone'></el-input>
                    </el-form-item>

                    <el-form-item>
                        <el-button type="primary" @click="handleConfirm">表单提交</el-button>
                        <el-button>取消</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </div>
        <el-dialog title='确认取出' :visible.sync='editVisible' width='30%' :close-on-click-modal='false'>
            <el-form ref='confirmForm' :model='lockerBoxInformation' label-width='100px' >
                <el-form-item >请根据以下信息取出包裹</el-form-item>
                <el-form-item label='快递柜ID'>
                    <el-input v-model='packageBoxInformation.lockerId' readonly></el-input>
                </el-form-item>
                <el-form-item label='柜格ID'>
                    <el-input v-model='packageBoxInformation.lockerBoxId'  readonly></el-input>
                </el-form-item>

                <el-form-item label='柜格列'>
                    <el-input v-model='lockerBoxInformation.lockerColumn' readonly></el-input>
                </el-form-item>
                <el-form-item label='柜格行'>
                    <el-input v-model='lockerBoxInformation.lockerRow'  readonly></el-input>
                </el-form-item>
            </el-form>
            <span slot='footer' class='dialog-footer'>
                <el-button @click='editVisible = false'>取 消</el-button>
                <el-button type='primary' @click="onSubmit">确 定</el-button>
            </span>
        </el-dialog>

    </div>
</template>

<script>
import {ChangeLockerBoxInfo, GetLockerBoxForGet} from '@/api/locker';
import {ChangePackageBoxInfo, ChangePackageInfo, GetPackageBoxInfoByCode} from '@/api/Package';

export default {
        name: 'PackageForm',
        data() {
            return {
                form:{
                    code:"",
                    phone:""
                },
                packageBoxInformation:{},
                packageInformation:{},
                lockerBoxInformation:{},
                editVisible : false,
                imageUrl: '',
                file: '',
                expressInfo: [],
                userInfo: [],
                companyInfo: [],
                rules: {
                    expOperatorId: [
                        { require: true,min:18, message: "操作员ID不能为空！", trigger: 'blur' }
                    ],
                    expOperatorPhone: [
                        { require: true, min: 11, message: "操作员手机号不能为空！", trigger: 'blur' }
                    ],
                }
            };
        },
        created() {

        },
        methods: {
            async getPackageBoxData(){
                await GetPackageBoxInfoByCode(this.form).then(res => {
                    if(res.code!==200){
                        this.$message.warning(res.message);
                    }else{
                        this.packageBoxInformation=res.data.packageBoxInformation;
                        this.packageInformation=res.data.packageInformation;
                    }
                    }
                );
            },
            async getLockerBoxData(){
                await GetLockerBoxForGet(this.packageBoxInformation.lockerBoxId).then(res => {
                    if (res.code !== 200) {
                        this.$message.warning(res.message);
                    } else {
                        this.lockerBoxInformation = res.data;
                    }
                })
            },
            async handleConfirm() {
                this.$message.info("正在获取柜格信息")
                await this.getPackageBoxData();
                await this.getLockerBoxData();
                this.editVisible = true;
            },
            async updateLockerBox(){
                this.lockerBoxInformation.boxIsEmpty = '1';
                this.lockerBoxInformation.doorStatus = '1';
                ChangeLockerBoxInfo(this.lockerBoxInformation).then(res => {
                    if (res.code === 200) {
                        this.$message.success('柜格信息更新成功！');
                    } else {
                        this.$message.error('柜格信息更新异常！');
                    }
                })
            },
            async onSubmit() {
                this.$confirm('确认已取出包裹？', '提示', {
                    type: 'warning'
                })
                    .then(() => {
                        this.editVisible = false;
                        this.updateLockerBox();
                        this.packageInformation.status=3;
                        this.packageBoxInformation.status=3;
                        this.changeStatus();
                    })
                    .catch(() => {
                        this.editVisible = true;
                    });

            },
            async changeStatus(){
                await ChangePackageInfo(this.packageInformation).then(res => {
                        if (res.code === 200) {
                            this.$message.success('包裹信息更新成功！');
                        } else {
                            this.$message.error('包裹信息提交异常！');
                        }
                    }
                );
                await ChangePackageBoxInfo(this.packageBoxInformation).then(res => {
                    if (res.code === 200) {
                        this.$message.success('PBI信息更新成功！');
                    } else {
                        this.$message.error('PBI信息更新异常！');
                    }

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
