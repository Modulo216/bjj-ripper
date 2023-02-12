<template>
  <v-container>
    <v-card elevation="2" dark>
      <v-row justify="center" align="center" class="px-4 mb-1">
          <v-file-input truncate-length="40" accept=".torrent" @click:clear="clear" ref="fileupload" label="File input" @change="selectFile" />
          <v-btn class="ml-5 mr-2" color="success" small @click="upload" :disabled="currentFile === null || isBusy === true">
            Upload
            <v-icon right dark>mdi-cloud-upload</v-icon>
          </v-btn>
      </v-row>
    </v-card>
    <AlertComponent :alertMsg="alertMsg" :alertType="alertType" :show="showAlert" />
  </v-container>
</template>

<script>
import AlertComponent from './AlertComponent.vue'
import api from "../api/axiosApi"

export default {
  name: "upload-files",
  components: {
    AlertComponent
  },
  data: () => ({
    isBusy: false,
    currentFile: null,
    showAlert: false,
    alertTimer: null,
    alertMsg: "alert text",
    alertType: "success",
  }),
  methods: {
    selectFile(file) {
      if (file) {
        this.currentFile = file;
      }
    },
    upload() {
      this.isBusy = true
      if (this.currentFile) {
        this.showAlert = false
        clearInterval(this.alertTimer)
        api.upload(this.currentFile).then(() => {
          this.clear()
          this.showTheAlert("File successfully added.", "success")
          this.isBusy = false
        }).catch(err => {
          console.log("ERROR", err.response, err.request)
          this.showTheAlert("Error communicating with server. Try again.", "error")
          this.isBusy = false
        })
      }
    },
    clear() {
      this.$refs.fileupload.reset()
      this.currentFile = null
    },
    showTheAlert(msg, type) {
      this.alertMsg = msg
      this.alertType = type
      this.showAlert = true
      this.alertTimer = setInterval(() => { this.showAlert = false }, 5000)
    }
  },
};
</script>