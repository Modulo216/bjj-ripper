<template>
  <v-alert :type="alertType" transition="fade-transition" style="margin-left:auto;margin-right:auto;width:25%;" v-show="show">
    {{ alertMsg }}
  </v-alert>
</template>
<script>
export default {
  props: {
    show: {
      type: Boolean,
      default: false
    },
    alertType: {
      type: String,
      required: false,
      default: 'error'
    },
    alertMsg: {
      type: String,
      required: false,
      default: 'Error communicating with server. Try again.'
    },
  }
}
</script>