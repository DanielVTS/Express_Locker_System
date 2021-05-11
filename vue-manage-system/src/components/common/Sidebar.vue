<template>
    <div class="sidebar">
        <el-menu
                class="sidebar-el-menu"
                :default-active="onRoutes"
                :collapse="collapse"
                background-color="#324157"
                text-color="#bfcbd9"
                active-text-color="#20a0ff"
                unique-opened
                router
        >
            <template v-for="item in items">
                <template v-if="item.subs">
                    <el-submenu :index="item.index" :key="item.index">
                        <template slot="title">
                            <i :class="item.icon"></i>
                            <span slot="title">{{ item.title }}</span>
                        </template>
                        <template v-for="subItem in item.subs">
                            <el-submenu
                                    v-if="subItem.subs"
                                    :index="subItem.index"
                                    :key="subItem.index"
                            >
                                <template slot="title">{{ subItem.title }}</template>
                                <el-menu-item
                                        v-for="(threeItem,i) in subItem.subs"
                                        :key="i"
                                        :index="threeItem.index"
                                >{{ threeItem.title }}
                                </el-menu-item>
                            </el-submenu>
                            <el-menu-item
                                    v-else
                                    :index="subItem.index"
                                    :key="subItem.index"
                            >{{ subItem.title }}
                            </el-menu-item>
                        </template>
                    </el-submenu>
                </template>
                <template v-else>
                    <el-menu-item :index="item.index" :key="item.index">
                        <i :class="item.icon"></i>
                        <span slot="title">{{ item.title }}</span>
                    </el-menu-item>
                </template>
            </template>
        </el-menu>
    </div>
</template>

<script>
import bus from '../common/bus';

export default {
        data() {
            return {
                collapse: false,
                items: [],
                items1: [
                    {
                        icon: 'el-icon-lx-home',
                        index: 'dashboard',
                        title: '系统首页'
                    },
                    {
                        icon: 'el-icon-lx-cascades',
                        index: 'usertable',
                        title: '用户信息'
                    },

                    {
                        icon: 'el-icon-lx-cascades',
                        index: 'lockertable',
                        title: '快递柜信息'
                    },
                    {
                        icon: 'el-icon-lx-cascades',
                        index: 'lockerBoxTable',
                        title: '快递柜柜格信息'
                    },
                    {
                        icon: 'el-icon-lx-cascades',
                        index: 'packageTable',
                        title: '快递包裹信息'
                    },
                    {
                        icon: 'el-icon-lx-cascades',
                        index: 'packageBoxTable',
                        title: '包裹柜格信息'
                    },
                    // {
                    //     icon: 'el-icon-lx-cascades',
                    //     index: 'companytable',
                    //     title: '公司信息表格'
                    // },
                    {
                        icon: 'el-icon-lx-calendar',
                        index: 'userform',
                        title: '新增用户信息'
                    },

                    {
                        icon: 'el-icon-lx-calendar',
                        index: 'lockerform',
                        title: '新增快递柜信息'
                    },
                    {
                        icon: 'el-icon-lx-calendar',
                        index: 'lockerBoxForm',
                        title: '新增快递柜格信息'
                    },
                    {
                        icon: 'el-icon-lx-calendar',
                        index: 'packageForm',
                        title: '新增快递信息'
                    },
                    {
                        icon: 'el-icon-lx-calendar',
                        index: 'getPackageForm',
                        title: '取件'
                    }
                    // {
                    //     icon: 'el-icon-lx-calendar',
                    //     index: 'companyform',
                    //     title: '新增公司信息'
                    // }
                ],
                items2: [
                    {
                        icon: 'el-icon-lx-home',
                        index: 'dashboard',
                        title: '系统首页'
                    },
                    {
                        icon: 'el-icon-lx-cascades',
                        index: 'lockertable',
                        title: '快递柜信息'
                    },
                    {
                        icon: 'el-icon-lx-cascades',
                        index: 'lockerBoxTable',
                        title: '快递柜柜格信息'
                    },
                    {
                        icon: 'el-icon-lx-cascades',
                        index: 'packageTable',
                        title: '快递包裹信息'
                    },
                    {
                        icon: 'el-icon-lx-cascades',
                        index: 'packageBoxTable',
                        title: '包裹柜格信息'
                    },
                    {
                        icon: 'el-icon-lx-calendar',
                        index: 'packageForm',
                        title: '新增快递信息'
                    },
                ]
            };
        },
        computed: {
            onRoutes() {
                return this.$route.path.replace('/', '');
            }
        },
        created() {
            // 通过this.$store.state.power控制不同用户看到的侧栏展示的内容
            this.items = (this.$store.state.power === "1") ? this.items1 : this.items2
            // 通过 Event Bus 进行组件间通信，来折叠侧边栏
            bus.$on('collapse', msg => {
                this.collapse = msg;
                bus.$emit('collapse-content', msg);
            });
        }
    };
</script>

<style scoped>
    .sidebar {
        display: block;
        position: absolute;
        left: 0;
        top: 70px;
        bottom: 0;
        overflow-y: scroll;
    }

    .sidebar::-webkit-scrollbar {
        width: 0;
    }

    .sidebar-el-menu:not(.el-menu--collapse) {
        width: 250px;
    }

    .sidebar > ul {
        height: 100%;
    }
</style>
