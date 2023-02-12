import Vue from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify'
import IdleVue from 'idle-vue'

Vue.config.productionTip = false

const eventsHub = new Vue()
Vue.use(IdleVue, {
  eventEmitter: eventsHub,
  idleTime: 300000,
})

new Vue({
  vuetify,
  render: h => h(App),
}).$mount('#app')