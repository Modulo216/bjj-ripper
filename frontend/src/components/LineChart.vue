<script>
  import { Line } from 'vue-chartjs'
  export default {
    extends: Line,
    props: {
      chartData: {
        type: Array | Object,
        required: false
      },
      chartLabels: {
        type: Array,
        required: true
      }
    },
    data: () => ({
      options: {
        scales: {
          yAxes: [{
            ticks: { fontColor: 'white', beginAtZero: true }
          }],
          xAxes: [{
            ticks: { fontColor: 'white' },
            gridLines: { display: false }
          }]
        },
        legend: { display: false },
        responsive: true,
        maintainAspectRatio: false
      }
    }),
    methods: {
      render() {
        this.renderChart({
          labels: this.chartLabels,
          datasets: [{
            borderColor: '#249EBF',
            pointBackgroundColor: 'white',
            borderWidth: 2,
            pointBorderColor: '#249EBF',
            backgroundColor: 'rgba(36, 36, 62, 0.5)',
            data: this.chartData
          }]
        }, this.options)
      }
    },
    watch: {
      chartData: function() {
        this.render()
      }
    }
  }
</script>