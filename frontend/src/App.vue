<script setup>
import TabButton from './components/TabButton.vue';
import CategoryCharts from "./components/CategoryCharts.vue";
import axios from "axios";
</script>

<template>
  <div>
    <b-card no-body>
      <b-tabs card pills vertical class="flex-nowrap">
        <!-- Render Tabs, supply a unique `key` to each tab -->
        <b-tab v-for="i in this.CategorysData" :key="'dyn-tab-' + i.id">
          <template #title>
            <div>
            <TabButton :tabs="CategorysData" :mytab="i" :catName="i.name" />
            </div>
          </template>
          <CategoryCharts :mycat="i"/>
        </b-tab>

        <!-- New Tab Button (Using tabs-end slot) -->
        <template #tabs-end>
          <b-container class="nav-link mt-3" style="height: 48px; display: flex;">
            <b-row align-v="center flex-nowrap">
              <b-col cols="9" class="">
                <b-form-input v-model="text" placeholder="Category name"></b-form-input>
              </b-col>
              <b-col cols="3" class="d-flex justify-content-center">
                <b-button class="anim" :style="{ opacity: text == '' ? 0.5 : 1}" size="sm" role="presentation" variant="primary" href="#" @click.prevent="newTab">
                  <strong>+</strong>
                </b-button>
              </b-col>
            </b-row>
          </b-container>
        </template>

        <!-- Render this if no tabs -->
        <template #empty>
          <div class="text-center text-muted">
            There are no open tabs<br>
            Open a new tab using the <b>+</b> button above.
          </div>
        </template>
      </b-tabs>
    </b-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      tabs: [],
      tabCounter: 0,
      CategorysData: [],
      text: '',
    }
  },
  methods: {
    async newTab() {
      if(this.text == '')
      {
        return
      }
      let body = {"name":this.text}
      let res = await axios.post('http://localhost:8080/api/categorys', body)
        .then(response => {
          return response.data
        }).catch(err => console.log(err))
      this.CategorysData.push(res)
    },
    async getCategorys() {
      let res = await axios.get('http://localhost:8080/api/categorys')
        .then(response => {
          return response.data
        }).catch(err => console.log(err))
      if(Array.isArray(res))
      {
        this.CategorysData = res;
      }
    }
  },
  mounted() {
    this.getCategorys()
  }
}
</script>

<style>
.tab-content {
  min-height: calc(100vh - (5px)) !important;
  width: calc(100vw - (5px)) !important
}

.tab-content>.active {
  min-height: 100%;
  width: 100%
}

.anim{
  transition: opacity 0.15s ease-in-out, color .15s ease-in-out,background-color .15s ease-in-out,border-color .15s ease-in-out,box-shadow .15s ease-in-out !important;
}

.nav{
  padding-top: 20px !important;
}
</style>