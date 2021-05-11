import Vue from 'vue'
import Vuex from 'vuex'

// import createPersistedState from "vuex-persistedstate"

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        power:0,
        statusTime: new Date().toLocaleTimeString()
    },
    mutations: {
    },
    actions: {
    },
    modules: {
    },
    // plugins: [createPersistedState()]
})
