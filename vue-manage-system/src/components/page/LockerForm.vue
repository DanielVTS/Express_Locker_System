<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-calendar"></i> 表单
                </el-breadcrumb-item>
                <el-breadcrumb-item> 新增快递柜信息</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="form-box">
                <el-form ref="form" :model="form" :rules='rules' label-position='left' label-width="110px">
                    <el-form-item>每列高度需高于8，大格高度为4，中格为2，小格为1</el-form-item>
                    <el-form-item label="省份" prop='province'>
                        <el-input v-model="form.province" ></el-input>
                    </el-form-item>
                    <el-form-item label="城市" prop='city'>
                        <el-input v-model="form.city" rows='2'></el-input>
                    </el-form-item>
                    <el-form-item label="快递柜名" prop='lockerName'>
                        <el-input type='textarea' rows='2' v-model="form.lockerName"></el-input>
                    </el-form-item>
                    <el-form-item label='每列大格格数' prop='big'>
                        <el-input v-model.number='form.big' type='number'></el-input>
                    </el-form-item>
                    <el-form-item label='每列中格格数' prop='normal'>
                        <el-input v-model.number='form.normal' type='number'></el-input>
                    </el-form-item>
                    <el-form-item label='每列小格格数' prop='small'>
                        <el-input v-model.number='form.small' type='number'></el-input>
                    </el-form-item>
                    <el-form-item label='总列数' prop='column'>
                        <el-input v-model.number='form.column' type='number'></el-input>
                    </el-form-item>
                    <el-form-item label='终端位置（列）' prop='terminalPosition'>
                        <el-input v-model.number='form.terminalPosition' type='number'></el-input>
                    </el-form-item>
                    <el-form-item label='自动创建柜格信息'>
                        <el-checkbox v-model='autoCreate' ></el-checkbox>
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
import {AddLockerBoxInfo, AddLockerInfo} from '@/api/locker';

export default {
        name: 'LockerForm',
        data() {
            return {
                form: {
                    id:'',
                    province: '',
                    city: '',
                    lockerName: '',
                    big:0,
                    normal:0,
                    small:0,
                    totalBox:0,
                    row:0,
                    column:0,
                    terminalPosition:0
                },
                imageUrl: '',
                file:'',
                autoCreate:false,
                rules: {
                    province: [
                        { require:true,message:"省份不能为空！" ,trigger: 'blur' }
                    ],
                    city: [
                        { require: true, message: "城市不能为空！", trigger: 'blur' }
                    ],
                    lockerName: [
                        { require: true, message: "快递柜名不能为空！", trigger: 'blur' }
                    ],
                    big: [
                        { require: true,type: "number", message: "格数不能小于0！", min: 0, trigger: 'blur' },
                    ],
                    normal: [
                        { require: true, type: "number", message: "格数不能小于0！", min: 0, trigger: 'blur' },
                    ],
                    small: [
                        { require: true, type: "number", message: "格数不能小于0！", min: 0, trigger: 'blur' },
                    ],
                    column: [
                        { require: true, type: "number", message: "列数不能小于1！", min: 1, trigger: 'blur' }
                    ],
                    terminalPosition: [
                        { require: true, type: "number", message: "终端位置不能小于1或大于列数！", min: 1, trigger: 'blur' }
                    ],
                }
            };
        },
        methods: {

            onSubmit() {
                let height = this.form.big * 4 + this.form.normal * 2 + this.form.small;
                this.form.row = this.form.big + this.form.normal + this.form.small;
                if (height < 8) {
                    this.$message.error('每列柜格高度低于标准！');
                } else {
                    if (this.form.terminalPosition > this.form.column) {
                        this.$message.error('每列柜格高度低于标准！');
                    } else {
                        this.form.totalBox = (this.form.big + this.form.normal + this.form.small) * (this.form.column - 1) + (height - 8);
                        if (this.autoCreate === true) {
                            this.addLockerBoxInfo();
                        } else {
                            this.addLockerInfo();
                        }
                    }
                }
            },
            async addLockerInfo() {
                let result = false;
                await AddLockerInfo(this.form).then(res => {
                        if (res.code != 200) {
                            this.$message.error('快递柜信息提交异常!'+res.message);
                        } else {
                            this.$message.success('快递柜信息提交成功！');
                            this.form.id = res.data.lockerId;
                            result = true;
                        }
                    }
                );
                return result;
            },
            async addLockerBoxInfo() {
                let result = await this.addLockerInfo();
                if (result == true) {
                    AddLockerBoxInfo(this.form).then(res => {
                        if (res.code != 200) {
                            this.$message.error('快递柜格信息提交异常!' + res.message);
                        }else this.$message.success('快递柜格信息创建成功！');
                        }
                    );
                }
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
