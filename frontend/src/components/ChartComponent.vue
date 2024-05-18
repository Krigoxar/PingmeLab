<script setup>
import VueApexCharts from "vue-apexcharts";
import axios from "axios";
import Constants from ".//Constants.vue";
</script>

<template>
  <div class="flex-nowrap" style="display: flex;">
    <apexchart id="chart" height="150" ref="realtimeChart" type="rangeBar" :options="chartOptions" :series="series"
      class="flex-fill"></apexchart>
    <b-button size="sm" variant="primary" 
      style="z-index: 10; height: 35px !important; margin-bottom: 21px !important;"
      class="anim m-3 align-self-center float-right h-25" @click="closeTab()">
      <strong>Del</strong>
    </b-button>
  </div>
</template>

<script>
export default {
  name: "Chart",
  props: {
    allUrls: Object,
    url: Object,
  },
  data() {
    return {
      series: [
        {
          name: "Online",
          data: [
            {
              x: 'Activity',
              y: [
                new Date().getTime(),
                new Date().getTime() + 1000 * 60 * 60 * 24
              ]
            }
          ]
        },
        {
          name: "Offline",
          data: [
            {
              x: 'Activity',
              y: [
                new Date().getTime(),
                new Date().getTime() + 1000 * 60 * 60 * 24
              ]
            }
          ]
        },
      ],
      chartOptions: {
        chart: {
          height: '200px',
          // offsetX: -10,
          // offsetY: -40,
          toolbar: {
            show: true,
            offsetY: 47,
            tools: {
              download: false,
              zoom: false
            },
            autoSelected: 'pan'
          },
          type: 'rangeBar',
        },
        title: {
          align: 'left',
          margin: 10,
          offsetX: 20,
          offsetY: 45,
          floating: false,
          text: this.url.url
        },
        colors: ['#007bff', '#dc3545'],
        plotOptions: {
          bar: {
            horizontal: true,
            barHeight: '100%'
          }
        },
        xaxis: {
          type: 'datetime',
        },
        yaxis: {
          show: false
        },
        legend: {
          position: 'top',
          offsetY: 9,
        }
      },
    }
  },
  components: {
    apexchart: VueApexCharts,
  },
  methods: {
    toMill(inDate) {
      return (Math.round(new Date(inDate).getTime() / 1000) * 1000)
    },
    closeTab() {
      this.allUrls.urls = this.allUrls.urls.filter(obj => { return obj.id != this.url.id })

      axios.delete(Constants.LINK + '/api/categorys/' + this.allUrls.id + '/url?urlId=' + this.url.id)
        .then(response => {
          return response.data
        }).catch(err => console.log(err))
    },
    async getObservations() {
      await axios.get(Constants.LINK + '/api/observation?url=' + this.url.url)
        .then(response => {
          if (Array.isArray(response.data)) {
            this.transform(response.data);
          }
        }).catch(err => console.log(err))
    },
    transform(value) {
      let toMill = function (inDate) {
        return (Math.round(new Date(inDate).getTime() / 1000) * 1000)
      };
      let deleteExcess = function () {
        let i = 1;
        while (i < (value.length - 1)) {
          if ((value[i - 1].responding == value[i].responding) == (value[i].responding == value[i + 1].responding)){
            console.log(i);
            value.splice(i,1);
          }
          else {
            i++;
          }
        }
      };
      value.push({
        id: 0,
        observationDate: this.url.date,
        responding: false
      });

      value.sort(function (a, b) {
        return (toMill(a.observationDate) - toMill(b.observationDate));
      })

      deleteExcess()

      let Online = [];
      let Offline = [];
      let min, max;
      min = toMill(value[0].observationDate);
      max = toMill(value[value.length - 1].observationDate);
      for (let i = 1; i < value.length; i++) {
        let obj = {
          x: 'Activity',
          y: [
            toMill(value[i - 1].observationDate),
            toMill(value[i].observationDate),
          ]
        }

        if (value[i].responding && value[i - 1].responding) {
          Online.push(obj)
        }
        else {
          Offline.push(obj)
        }

      }
      this.$refs.realtimeChart.updateSeries([{
        name: "Online",
        data: Online
      },
      {
        name: "Offline",
        data: Offline
      }], true);

      this.$refs.realtimeChart.updateOptions({
        xaxis: {
          min: min - (1000 * 60 * 30),
          max: max + (1000 * 60 * 30)
        }
      });
    },
    setUpdate() {
      this.polling = setInterval(() => {
        this.getObservations()
      }, 1000 * 60 * 5)
    }
  },
  mounted() {
    this.getObservations()
    this.setUpdate()
  },
};
</script>

<style>
#chart {
  margin-top: -30px;
}
</style>