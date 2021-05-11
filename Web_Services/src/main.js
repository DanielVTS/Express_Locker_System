import 'babel-polyfill';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css'; // 默认主题
import Vue from 'vue';
import VueI18n from 'vue-i18n';
import App from './App.vue';
import './assets/css/icon.css';
import './components/common/directives';
import {messages} from './components/common/i18n';
import router from './router';
import store from './store';

Vue.config.productionTip = false;
Vue.use(VueI18n);
Vue.use(ElementUI, {
    size: 'small'
});
const i18n = new VueI18n({
    locale: 'zh',
    messages
});

//使用钩子函数对路由进行权限跳转
router.beforeEach((to, from, next) => {
    document.title = `${to.meta.title} | vue-manage-system`;
    const username = sessionStorage.getItem('ms_username');
    const password = sessionStorage.getItem('ms_password');

    if (!username && !password && to.path !== '/login') {
        next('/login');
    } else {
        next();
    }
});

new Vue({
    router,
    i18n,
    store,
    render: h => h(App)
}).$mount('#app');
