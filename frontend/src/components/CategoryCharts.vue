<script setup>
import Chart from ".//ChartComponent.vue";
import axios from "axios";
</script>

<template>
  <div>
    <b-list-group class="mb-3">
      <b-list-group-item v-for="i in this.mycat.urls" :key="i.id">
        <Chart :url="i" :all-urls="mycat"></Chart>
      </b-list-group-item>
    </b-list-group>
    <div>
      <b-container class="justify-content-center bg-primary rounded" style="height: 56px; width: 272px; display: flex;">
        <b-row align-v="center" class="flex-nowrap">
          <b-col cols="10" class="pl-1">
            <b-form-input v-model="textUrl" placeholder="URL"></b-form-input>
          </b-col>
          <b-col cols="2" class="d-flex justify-content-center pl-2 pr-1">
            <b-button class="anim" :style="{ opacity: textUrl == '' ? 0.5 : 1 }" id="inverse" size="sm"
              variant="outline-primary" href="#" @click.prevent="newPing">
              <strong>+</strong>
            </b-button>
          </b-col>
        </b-row>
      </b-container>
    </div>
  </div>
</template>

<script>
export default {
  name: 'CategoryCharts',
  props: {
    mycat: Object,
  },
  data() {
    return {
      textUrl: '',
    }
  },
  beforeMount() {
    this.mycat.urls.sort(function (a, b) {
      return ('' + a.url).localeCompare(b.url);;
    })
  },
  methods: {
    async newPing() {
      if (this.textUrl == '') {
        return
      }

      let isAlredyInCat = false;
      this.mycat.urls.forEach((val) => {
        if (val.url == this.textUrl) {
          isAlredyInCat = true;
        }
      })

      if (isAlredyInCat) {
        return
      }

      let isPresent = await axios.get('http://localhost:8080/api/pings?url=' + this.textUrl)
        .then(response => {
          return response
        }).catch(err => console.log(err))

      let idForAdd;
      if (isPresent.status == 204) {
        let body = { "url": this.textUrl }
        let data = await axios.post('http://localhost:8080/api/pings', body)
          .then(response => {
            return response.data
          }).catch(err => console.log(err))
        idForAdd = data.id
      }
      else {
        idForAdd = isPresent.data[0].id;
      }

      let newCat = await axios.post('http://localhost:8080/api/categorys/' + this.mycat.id + '/url?urlId=' + idForAdd)
        .then(response => {
          return response.data
        }).catch(err => console.log(err))

      this.mycat.urls = newCat.urls;
      console.log(newCat);

      this.mycat.urls.sort(function (a, b) {
        return ('' + a.url).localeCompare(b.url);;
      })
    },
  }
}
</script>

<style>
#inverse {
  background-color: #fff;
}

#inverse:hover {
  background-color: #007bff;
}

#inverse:focus {
  box-shadow: 0 0 0 0.2rem rgba(64, 146, 234, 0.5);
  background-color: #007bff;
  border-color: #007bff;
  color: #fff;
}
</style>