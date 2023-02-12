<template>
  <v-container>
    <v-data-table
      dark
      dense
      :footer-props="{
        'items-per-page-options': [10, 20, 30]
      }"
      :items-per-page="20"
      v-model="selected"
      :headers="headers"
      :items="items"
      single-select
      item-key="id"
      show-select
      :loading="isLoading"
      style="border-bottom-left-radius: 0; border-bottom-right-radius: 0;"
      @click:row="handleClick"
      class="elevation-1 row-pointer">
    </v-data-table>
    <v-card dark style="border-top-left-radius: 0; border-top-right-radius: 0; box-shadow: 0px 5px 1px -2px rgba(0, 0, 0, 0.2), 0px 5px 2px 0px rgba(0, 0, 0, 0.14), 0px 5px 5px 0px rgba(0, 0, 0, 0.12);">
      <v-card-actions style="border-top-left-radius: 0; border-top-right-radius: 0">
        <v-spacer />
        <v-btn block :disabled="selected.length === 0" @click="remove" color="success">
          REMOVE<v-icon right dark>mdi-delete</v-icon>
        </v-btn>
      </v-card-actions> 
    </v-card>
    <Overlay :show="showOverlay" />
    <v-dialog v-model="dialog" width="75%">
      <v-card style="background-color:#444444">
        <v-card-title>
          Seeding - {{ dialogHeader }}
        </v-card-title>
        <v-card-text>
          <line-chart :chart-data="downloads" :chart-labels="labels" />
        </v-card-text>
        <v-divider></v-divider>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="success" @click="dialog = false">
            Close
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <AlertComponent :alertMsg="alertMsg" :alertType="alertType" :show="showAlert" />
  </v-container>
</template>

<script>
import Overlay from './Overlay.vue'
import AlertComponent from './AlertComponent.vue'
import api from '../api/axiosApi'
import LineChart from './LineChart.vue'
export default {
  name: 'Torrents',
  components: {
    AlertComponent,
    LineChart,
    Overlay
  },
  data: () => ({
    timer: null,
    isLoading: true,
    items: [],
    selected: [],
    headers: [
      { text: 'Name', align: 'start', sortable: true, value: 'name' },
      { text: 'Ratio', sortable: true, value: 'uploadRatio' },
      { text: 'Size', sortable: false, value: 'totalSize' },
      { text: 'Done', sortable: false, value: 'percentDone' },
      { text: 'Added', sortable: false, value: 'addedDate' },
      { text: 'Last Active', sortable: false, value: 'activityDate' },
      { text: 'Up / Down Speed', sortable: false, value: 'speed' }
    ],
    showAlert: false,
    alertMsg: "alert text",
    alertType: "success",
    downloads: [],
    labels: [],
    dialog: false,
    dialogHeader: '',
    showOverlay: false
  }),
  mounted() {
    this.loadTorrents()
    this.timer = setInterval(this.loadTorrents, 10000)
  },
  beforeDestroy() {
    clearInterval(this.timer)
  },
  methods: {
    formatBytes(bytes, decimals = 2) {
      if (bytes === 0) return '0'
      const dm = decimals < 0 ? 0 : decimals
      const sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB']
      const i = Math.floor(Math.log(bytes) / Math.log(1024))
      return parseFloat((bytes / Math.pow(1024, i)).toFixed(dm)) + ' ' + sizes[i]
    },
    loadTorrents() {
      this.isLoading = true
      api.getTorrents().then(resp => {
        this.items.splice(0);
        for (const tor of resp.data.arguments.torrents) {
          if(tor.downloadDir === '/media/usb/bjj') {
            const torrent = { id: tor.id, name: tor.name, uploadRatio: Math.round((tor.uploadRatio + Number.EPSILON) * 100) / 100 }
            torrent.totalSize = this.formatBytes(tor.totalSize)
            torrent.percentDone = tor.percentDone * 100 + "%"

            let addedDate = new Date(tor.addedDate * 1000)
            torrent.addedDate = (addedDate.getMonth() + 1) + '/' + addedDate.getDate()

            let timeDiff = new Date() - new Date(tor.activityDate * 1000).getTime()
            let daysDiff = Math.trunc(timeDiff / (1000 * 3600 * 24))

            torrent.activityDate = daysDiff === 0 ? "Today" : daysDiff + " days"
            torrent.speed = this.formatBytes(tor.rateUpload) + ' / ' + this.formatBytes(tor.rateDownload)

            this.items.push(torrent)
          }
        }
        this.isLoading = false
      }).catch(err => {
        console.log("ERROR", err.response, err.request)
        this.alertType = 'error'
        this.showAlert = true
        setInterval(() => { this.showAlert = false }, 5000)
        clearInterval(this.timer)
        this.isLoading = false
      })
    },
    remove() {
      this.$delete(this.items, this.items.indexOf(this.items.find(i => i.id === this.selected[0].id)))
      let id = this.selected[0].id
      this.selected = []
      api.rmTorrent(id).then(resp => {
        if(resp === 0) {
          this.alertMsg = 'File successfully removed.'
          this.alertType = 'success'
          this.showAlert = true
        }
      }).catch(err => {
        console.log("ERROR", err.response, err.request)
        this.showAlert = true
        this.alertType = 'error'
        setInterval(() => { this.showAlert = false }, 5000)
      })
    },
    handleClick(row) {
      this.showOverlay = true
      this.downloads.splice(0)
      this.labels.splice(0)
      this.dialog = true

      api.getRatio(row.id).then(resp => {
        this.dialogHeader = row.name
        for (let i = (resp.data.length - 1); i >= 0; i--) {
          let ratio = resp.data[i]
          this.downloads.push(ratio.ratio)

          let ratioDate = new Date(ratio.dateTime)
          let dateStr = (ratioDate.getMonth() + 1) + '/' + ratioDate.getDate() + ' '+ ratioDate.toLocaleTimeString([], {hour: 'numeric', minute:'numeric'})

          this.labels.push(dateStr)
        }
        this.showOverlay = false
      }).catch(err => {
        this.showOverlay = false
        console.log("ERROR", err)
      })
    }
  }
}
</script>
<style lang="css">
tbody tr:hover {
  cursor: pointer
}
</style>
