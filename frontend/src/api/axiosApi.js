import axios from 'axios'

const axiosApi = axios.create({
    //baseURL: `/api`,
    headers: { 'Content-Type': 'application/json' },
    timeout: 6000,
    baseURL: window.location.protocol + "//" + window.location.hostname + ":8098/api"
})

export default {
  isBusy: false,
  async hello() {
    return await axiosApi.get(`/hello`);
  },
  async upload(file) {
    let formData = new FormData();
    formData.append("file", file);

    return await axiosApi.post("/upload", formData, {
      headers: {
        "Content-Type": "multipart/form-data"
      },
    })
  },
  async derp(herp) {
    return await axiosApi({ url: '/derp/' + herp, method: 'GET' })
  },
  async getUpTime() {
    return await axiosApi({ url: '/uptime', method: 'GET' })
  },
  async getloewox() {
    return await axiosApi({ url: '/eFiowQzT', method: 'GET' })
  },
  async getwlkjxi(herp) {
    return await axiosApi({ url: '/LKjexW2k/' + herp, method: 'GET' })
  },
  async getTorrents() {
    return await axiosApi({ url: '/getTorrents', method: 'GET' })
  },
  async rmTorrent(herp) {
    return await axiosApi({ url: '/remove-torrent/' + herp, method: 'GET' })
  },
  async getRatio(herp) {
    return await axiosApi({ url: '/getRatio/' + herp, method: 'GET' })
  }
}