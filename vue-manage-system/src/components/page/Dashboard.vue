<template>
    <div>
        <el-row :gutter="20">
            <el-col :span="8">
                <el-card shadow="hover" class="mgb20" style="height:252px;">
                    <div class="user-info">
                        <img src="../../assets/img/img.jpg" class="user-avator" alt/>
                        <div class="user-info-cont">
                            <div class="user-info-name">{{name}}</div>
                            <div>{{role}}</div>
                        </div>
                    </div>
                    <div class="user-info-list">
                        上次登录时间：
                        <span v-text=statustime></span>
                    </div>
                </el-card>
            </el-col>
            <el-col :span="16">
                <el-row :gutter="20" class="mgb20">
                    <el-col :span="8">
                        <el-card shadow="hover" :body-style="{padding: '0px'}">
                            <div class="grid-content grid-con-1">
                                <i class="el-icon-lx-people grid-con-icon"></i>
                                <div class="grid-cont-right">
                                    <div class="grid-num">{{usernumber}}</div>
                                    <div>用户总数</div>
                                </div>
                            </div>
                        </el-card>
                    </el-col>
                    <el-col :span="8">
                        <el-card shadow="hover" :body-style="{padding: '0px'}">
                            <div class="grid-content grid-con-2">
                                <i class="el-icon-lx-notice grid-con-icon"></i>
                                <div class="grid-cont-right">
                                    <div class="grid-num">{{ lockerNumber }}</div>
                                    <div>快递柜总数</div>
                                </div>
                            </div>
                        </el-card>
                    </el-col>
                    <el-col :span="8">
                        <el-card shadow="hover" :body-style="{padding: '0px'}">
                            <div class="grid-content grid-con-3">
                                <i class="el-icon-lx-goods grid-con-icon"></i>
                                <div class="grid-cont-right">
                                    <div class="grid-num">{{packageNumber}}</div>
                                    <div>包裹总数</div>
                                </div>
                            </div>
                        </el-card>
                    </el-col>
                </el-row>
            </el-col>
        </el-row>
<!--        <el-row :gutter="20">-->
<!--            <el-col :span="12">-->
<!--                <el-card shadow="hover">-->
<!--                    <div class="schart-box">-->
<!--                        <div class="content-title">用户分布</div>-->
<!--                        <schart class="schart" canvasId="ring" :options="options4"></schart>-->
<!--                    </div>-->
<!--                </el-card>-->
<!--            </el-col>-->


<!--            <el-col :span="12">-->
<!--                <el-card shadow="hover">-->
<!--                    <div class="schart-box">-->
<!--                        <div class="content-title">商品分布</div>-->
<!--                        <schart class="schart" canvasId="pie" :options="options5"></schart>-->
<!--                    </div>-->
<!--                </el-card>-->
<!--            </el-col>-->
<!--        </el-row>-->
    </div>
</template>

<script>
import Schart from 'vue-schart';
import bus from '../common/bus';
import {GetPackageInfo} from '@/api/Package';
import {GetLockerInfo} from '@/api/locker';
import {GetUserInfo} from '@/api/user';


export default {
        name: 'dashboard',
        data() {
            return {
                name: sessionStorage.getItem('ms_username'),
                statustime: new Date(this.$store.state.statusTime),
                usernumber: 0,
                packageNumber: 0,
                lockerNumber: 0,
                data: [
                    {
                        name: '2018/09/04',
                        value: 1083
                    },
                    {
                        name: '2018/09/05',
                        value: 941
                    },
                    {
                        name: '2018/09/06',
                        value: 1139
                    },
                    {
                        name: '2018/09/07',
                        value: 816
                    },
                    {
                        name: '2018/09/08',
                        value: 327
                    },
                    {
                        name: '2018/09/09',
                        value: 228
                    },
                    {
                        name: '2018/09/10',
                        value: 1065
                    }
                ],
                options1: {
                    type: 'bar',
                    title: {
                        text: '最近一周各品类销售图'
                    },
                    bgColor: '#fbfbfb',
                    labels: ['周一', '周二', '周三', '周四', '周五'],
                    datasets: [
                        {
                            label: '家电',
                            fillColor: 'rgba(241, 49, 74, 0.5)',
                            data: [234, 278, 270, 190, 230]
                        },
                        {
                            label: '百货',
                            data: [164, 178, 190, 135, 160]
                        },
                        {
                            label: '食品',
                            data: [144, 198, 150, 235, 120]
                        }
                    ]
                },
                options2: {
                    type: 'line',
                    title: {
                        text: '最近几个月各品类销售趋势图'
                    },
                    bgColor: '#fbfbfb',
                    labels: ['6月', '7月', '8月', '9月', '10月'],
                    datasets: [
                        {
                            label: '家电',
                            data: [234, 278, 270, 190, 230]
                        },
                        {
                            label: '百货',
                            data: [164, 178, 150, 135, 160]
                        },
                        {
                            label: '食品',
                            data: [114, 138, 200, 235, 190]
                        }
                    ]
                },
                options3: {
                    type: 'pie',
                    title: {
                        text: '用户分布'
                    },
                    legend: {
                        position: 'left'
                    },
                    bgColor: '#fbfbfb',
                    labels: ['管理员', '普通用户'],
                    datasets: [
                        {
                            data: []
                        }
                    ]
                },
                options4: {
                    type: 'ring',
                    title: {
                        text: '用户分布'
                    },
                    showValue: false,
                    legend: {
                        position: 'bottom',
                        bottom: 40
                    },
                    bgColor: '#fbfbfb',
                    labels: ['管理员', '普通用户'],
                    datasets: [
                        {
                            data: []
                        }
                    ]
                },
                options5: {
                    type: 'pie',
                    title: {
                        text: '快递商品分布'
                    },
                    legend: {
                        position: 'left'
                    },
                    bgColor: '#fbfbfb',
                    labels: ['T恤', '牛仔裤', '连衣裙', '毛衣', '七分裤', '短裙', '羽绒服'],
                    datasets: [
                        {
                            data: [334, 278, 190, 235, 260, 200, 141]
                        }
                    ]
                },
            };

        },
        components: {
            Schart
        },
        computed: {
            role() {
                return this.$store.state.power == 1 ? '管理员' : '普通用户';
            }
        },
        created() {
            this.changeDate();
            this.getData();
        },
        methods: {
            changeDate() {
                const now = new Date().getTime();
                this.data.forEach((item, index) => {
                    const date = new Date(now - (6 - index) * 86400000);
                    item.name = `${date.getFullYear()}/${date.getMonth() + 1}/${date.getDate()}`;
                });
            },
            getData() {
                GetPackageInfo(1).then(res => {
                    this.packageNumber = res.data.total;
                });
                GetLockerInfo(1).then(res => {
                    this.lockerNumber = res.data.total;
                    let temp1 = []
                    let temp2 = []
                    res.data.list.forEach(function(item) {
                        temp1.push(item.name)
                        temp2.push(1)
                    });
                    this.options5.labels = temp1
                    this.options5.datasets[0].data = temp2
                    console.log(this.options5.labels);
                    console.log(this.options5.datasets[0].data);
                });
                GetUserInfo(1).then(res => {
                    this.usernumber = res.data.total;
                    let user1 = 0;
                    let user2 = 0;
                    res.data.items.forEach(function(item) {
                        if (item.power === 1) {
                            user1 = user1 + 1;
                        } else {
                            user2 = user2 + 1;
                        }
                    });
                    this.options3.datasets[0].data.push(user1);
                    this.options3.datasets[0].data.push(user2);
                    this.options4.datasets[0].data.push(user1);
                    this.options4.datasets[0].data.push(user2);
                    console.log(this.options4.datasets[0].data);
                });
            },
            handleListener() {
                bus.$on('collapse', this.handleBus);
                // 调用renderChart方法对图表进行重新渲染
                window.addEventListener('resize', this.renderChart);
            },
            handleBus(msg) {
                setTimeout(() => {
                    this.renderChart();
                }, 200);
            },
            renderChart() {
                this.$refs.bar.renderChart();
                this.$refs.line.renderChart();
            }
        }
    };
</script>


<style scoped>
    .el-row {
        margin-bottom: 20px;
    }

    .grid-content {
        display: flex;
        align-items: center;
        height: 100px;
    }

    .grid-cont-right {
        flex: 1;
        text-align: center;
        font-size: 14px;
        color: #999;
    }

    .grid-num {
        font-size: 30px;
        font-weight: bold;
    }

    .grid-con-icon {
        font-size: 50px;
        width: 100px;
        height: 100px;
        text-align: center;
        line-height: 100px;
        color: #fff;
    }

    .grid-con-1 .grid-con-icon {
        background: rgb(45, 140, 240);
    }

    .grid-con-1 .grid-num {
        color: rgb(45, 140, 240);
    }

    .grid-con-2 .grid-con-icon {
        background: rgb(100, 213, 114);
    }

    .grid-con-2 .grid-num {
        color: rgb(45, 140, 240);
    }

    .grid-con-3 .grid-con-icon {
        background: rgb(242, 94, 67);
    }

    .grid-con-3 .grid-num {
        color: rgb(242, 94, 67);
    }

    .user-info {
        display: flex;
        align-items: center;
        padding-bottom: 20px;
        border-bottom: 2px solid #ccc;
        margin-bottom: 20px;
    }

    .user-avator {
        width: 120px;
        height: 120px;
        border-radius: 50%;
    }

    .user-info-cont {
        padding-left: 50px;
        flex: 1;
        font-size: 14px;
        color: #999;
    }

    .user-info-cont div:first-child {
        font-size: 30px;
        color: #222;
    }

    .user-info-list {
        font-size: 14px;
        color: #999;
        line-height: 25px;
    }

    .user-info-list span {
        margin-left: 70px;
    }

    .mgb20 {
        margin-bottom: 20px;
    }

    .todo-item {
        font-size: 14px;
    }

    .todo-item-del {
        text-decoration: line-through;
        color: #999;
    }

    .schart {
        width: 100%;
        height: 300px;
    }
</style>
