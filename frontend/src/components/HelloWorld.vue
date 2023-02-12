<template>
  <v-container>
    <Overlay :show="showOverlay" />
    <v-row >
      <v-treeview
        return-object 
        v-model="tree"
        :items="items"
        item-key="id"
        open-on-click>
        <template v-slot:prepend="{ item, open }">
          <v-icon v-if="!item.size">
            {{ open ? 'mdi-folder-open' : 'mdi-folder' }}
          </v-icon>
          <v-icon v-else>
            {{ getFontAwesomeIconFromMIME(item.type) }}
          </v-icon>
        </template>
      </v-treeview>
    </v-row>
    <AlertComponent :show="showAlert" />
  </v-container>
</template>

<script>
import Overlay from './Overlay.vue'
import AlertComponent from './AlertComponent.vue'
import api from '../api/axiosApi';
export default {
  name: 'HelloWorld',
  components: {
    Overlay,
    AlertComponent
  },
  data: () => ({
    tree: [],
    items: [],
    showAlert: false,
    showOverlay: false
  }),
  mounted() {
    this.showOverlay = true
    api.hello().then(resp => {
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
    getFontAwesomeIconFromMIME(mimeType) {
      let icon_classes = [
        { "key": "image", "val": "mdi-file-image" },
        { "key": "audio", "val": "mdi-file-audio" },
        { "key": "video", "val": "mdi-file-video" },
        { "key": "application/pdf", "val": "mdi-file-pdf-box" },
        { "key": "application/msword", "val": "mdi-file-word" },
        { "key": "application/vnd.ms-word", "val": "mdi-file-word" },
        { "key": "application/vnd.oasis.opendocument.text", "val": "mdi-file-word" },
        { "key": "application/vnd.openxmlformats-officedocument.wordprocessingml", "val": "mdi-file-word" },
        { "key": "application/vnd.ms-excel", "val": "mdi-file-excel" },
        { "key": "application/vnd.openxmlformats-officedocument.spreadsheetml", "val": "mdi-file-excel" },
        { "key": "application/vnd.oasis.opendocument.spreadsheet", "val": "mdi-file-excel" },
        { "key": "application/vnd.ms-powerpoint", "val": "mdi-file-powerpoint" },
        { "key": "application/vnd.openxmlformats-officedocument.presentationml", "val": "mdi-file-powerpoint" },
        { "key": "application/vnd.oasis.opendocument.presentation", "val": "mdi-file-powerpoint" },
        { "key": "text/plain", "val": "mdi-file-text" },
        { "key": "text/html", "val": "mdi-file-code" },
        { "key": "application/json", "val": "mdi-file-code" },
        { "key": "application/gzip", "val": "mdi-zip-box-outline" },
        { "key": "application/zip", "val": "mdi-zip-box-outline" }
      ];

      let ico = icon_classes.find(i => mimeType !== null && mimeType.search(i.key) === 0)
      return ico ? ico.val : "mdi-file"

    }
  }
}
</script>
