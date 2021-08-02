import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

const getStore = (key) => {
    const val = sessionStorage.getItem(key);
    if (val === null) return;
    return val;
};

const state = {
    userName: getStore('userName'),
    deptNo: getStore('deptNo'),
    roleNo: getStore('roleNo')
};

const mutations = {
    setUserName(state, n) {
        state.userName = n;
    },
    setDeptNo(state, n) {
        state.deptNo = n;
    },
    setRoleNo(state, n) {
        state.roleNo = n;
    }
};

let store;
const initStore = () => {
    return store || (store = new Vuex.Store({
        state,
        mutations
    }));
};

export default initStore;