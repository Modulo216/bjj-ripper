<template>
  <v-container>
    <Overlay :show="showOverlay" />
    <v-row >
      <v-radio-group v-model="radioGroup">
        <v-radio class="pb-1" v-for="(n, index) in items" :key="n.id" :value="n.id">
          <template v-slot:label>
            <div><strong class="success--text pr-1">{{index+1}}.</strong>{{n.name}}</div>
          </template>
        </v-radio>
      </v-radio-group>
      <v-btn block :disabled="radioGroup === -1" @click="remove" color="primary">
        REMOVE
      </v-btn>
    </v-row>
    <AlertComponent :show="showAlert" />
  </v-container>
</template>

<script>
import Overlay from './Overlay.vue'
import AlertComponent from './AlertComponent.vue'
import api from '../api/axiosApi';
export default {
  name: 'Manage',
  components: {
    Overlay,
    AlertComponent
  },
  data: () => ({
    items: [],
    radioGroup: -1,
    showAlert: false,
    showOverlay: false
  }),
  mounted() {
    this.showOverlay = true
    api.getloewox().then(resp => {
      this.showOverlay = false
      this.items = resp.data
    }).catch(err => {
      console.log("ERROR", err.response, err.request)
      this.showAlert = true
      setInterval(() => { this.showAlert = false }, 5000)
      this.showOverlay = false
    })
  },
  methods: {
    remove() {
      this.showOverlay = true
      api.getwlkjxi(this.radioGroup).then(resp => {
        if(resp.data === 0) {
          this.showOverlay = false
          this.$delete(this.items, this.items.indexOf(this.items.find(i => i.id === this.radioGroup)))
          this.radioGroup = -1
        }
      }).catch(err => {
        console.log("ERROR", err.response, err.request)
        this.showAlert = true
        setInterval(() => { this.showAlert = false }, 5000)
        this.showOverlay = false
      })
    }
  }
}
</script>
