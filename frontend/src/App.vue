<template>
  <v-app style="background-color:#424242">
    <Overlay :show="showOverlay" />
    <v-container v-if="derping === true">
      <v-app-bar app style="background-color: #141E30;background: linear-gradient(to right, #24243e, #141E30, #0f0c29); }">
        <v-btn @click="derping = false" target="_blank" color="success">
          <span>LOGOUT</span>
          <v-icon right dark>mdi-exit-run</v-icon>
        </v-btn>
        <v-spacer></v-spacer>
        <span id="theHeader">BJJ RIPPER</span>
        <v-spacer></v-spacer>
        <span id="uptime" class="caption" style="color:#3F7FBF;text-shadow: 0 0 3px #FF0000">{{ hours + ' hours ' + minutes + ' mins ' + seconds + ' secs' }}</span>
      </v-app-bar>

      <v-main>
        <UploadFiles v-if="derping === true" />
        <v-divider color="#feac5e" class="mb-1"/>
        <TorrentsComponent v-if="derping === true" />
      </v-main>
    </v-container>

    <v-container fill-height fluid v-if="derping === false">
      <v-row align="center" justify="center" class="px-7">
        <div style="width:50%">
          <v-text-field
            v-on:keyup.enter="derpIt"
            v-model="herptext"
            append-outer-icon="mdi-send"
            filled
            clearable
            @click:append-outer="derpIt"
          ></v-text-field>
        </div>
      </v-row>
    </v-container>
    <AlertComponent  :alertMsg="alertMsg" :alertType="alertType" :show="showAlert" />
  </v-app>
</template>

<script>
import UploadFiles from './components/UploadFiles.vue'
import TorrentsComponent from './components/TorrentsComponent.vue'
import Overlay from './components/Overlay.vue'
import AlertComponent from './components/AlertComponent.vue'
import api from '../src/api/axiosApi'

export default {
  name: 'App',
  components: {
    UploadFiles,
    Overlay,
    AlertComponent,
    TorrentsComponent
  },
  data: () => ({
    showUpload: true,
    derping: false,
    herptext: '',
    showAlert: false,
    alertTimer: null,
    showOverlay: false,
    hours: 0,
    minutes: 0,
    seconds: 0,
    alertMsg: "alert text",
    alertType: "success",
  }),
  onIdle() {
    if(this.derping) {
      this.derping = false
      this.alertMsg = 'You have been logged out.'
      this.alertType = 'success'
      this.showAlert = true
      this.alertTimer = setInterval(() => { this.showAlert = false }, 5000)
    }
  },
  methods: {
    derpIt() {
      if(this.herptext !== null && this.herptext.length) {
        this.showOverlay = true
        this.showAlert = false
        clearInterval(this.alertTimer)
        api.derp(this.herptext).then(resp => {
          this.derping = resp.data === 1
          this.showOverlay = false
          if(this.derping) {
            this.herptext = ''
            api.getUpTime().then(resp => {
              if(resp.status === 200) {
                let arr = resp.data.split(',')
                this.hours = arr[0]
                this.minutes = arr[1]
                this.seconds = arr[2]
                setInterval(() => { this.calcUpTime() }, 1000)
              }
            })
          }
        }).catch(err => {
          console.log("ERROR", err.response, err.request)
          this.showAlert = true
          this.alertType = 'error'
          this.alertTimer = setInterval(() => { this.showAlert = false }, 5000)
          this.showOverlay = false
        })
      }
    },
    calcUpTime() {
      if(this.seconds === "59" && this.minutes === "59") {
        this.minutes = "00"
        this.seconds = "00"
        this.hours = (parseInt(this.hours) + 1).toString()
      }
      if(this.seconds === "59") {
        this.seconds = "00"
        this.minutes = (parseInt(this.minutes) + 1).toString()
      } else {
        this.seconds = (parseInt(this.seconds) + 1).toString()
      }
    }
  }
}
</script>
<style lang="scss" scoped>
@import url('https://fonts.googleapis.com/css?family=Bungee+Shade');
$size: 6px;

#theHeader {
	font-family: 'Bungee Shade';
	font-size: 39px;
  letter-spacing: 1px;
	background: linear-gradient(
		-45deg, 
		#141E30 25%, 
		#feac5e 25%, 
		#feac5e 50%, 
		#141E30 50%, 
		#141E30 75%, 
		#feac5e 75%, 
		#feac5e);
	-webkit-background-clip: text;
  background-clip:text;
	-webkit-text-fill-color: transparent;
	background-size: $size $size;
	animation: stripes 1s linear infinite;
}	

@keyframes stripes {
	100% { 
		background-position: $size 0, $size 0, $size 0;
	}
}
@media screen and (max-width: 600px) {
  #uptime {
    display:none
  }
}
</style>