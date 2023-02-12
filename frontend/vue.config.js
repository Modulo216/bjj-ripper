module.exports = {
  transpileDependencies: [
    'vuetify'
  ],
  devServer: {
    compress: true,
    disableHostCheck: true,
    port:65216,
    proxy: {
      '/api': {
        target: 'http://localhost:8098', // this configuration needs to correspond to the Spring Boot backends' application.properties server.port
        ws: true,
        changeOrigin: true
      }
    }
  },
  productionSourceMap: false
}
