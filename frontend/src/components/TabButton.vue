<template>
  <b-container class="p-0" @mouseover="mouseOver()" @mouseout="mouseNotOver()">
    <b-row align-v="center" class="tab-ell">
      <b-col cols="9" class="">
        <strong>{{ this.catName }}</strong>
      </b-col>
      <b-col cols="3" class="d-flex justify-content-center">
        <transition name="fade" mode="out-in">
          <b-button size="sm" variant="primary" :style="{ opacity: isHovered ? 0 : 1 }" class="anim float-right h-25"
            @click="closeTab()">
            <strong>Del</strong>
          </b-button>
        </transition>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import axios from "axios";
export default {
  name: 'TabButton',
  props: {
    mytab: Object,
    tabs: Array,
    catName: String,
  },
  data() {
    return {
      isHovered: true,
    }
  },
  methods: {
    async closeTab() {
      // delete from server
      let res = await axios.delete('http://localhost:8080/api/categorys/ ' + this.mytab.id)
        .then(response => {
          return response
        }).catch(err => console.log(err))

      if (res.status == 200) {
        for (let num = 0; num < this.tabs.length; num++) {
          if (this.tabs[num] === this.mytab) {
            this.tabs.splice(num, 1)
          }
        }
      }
    },
    mouseOver() {
      this.isHovered = false;
    },

    mouseNotOver() {
      this.isHovered = true;
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.tab-ell {
  height: 32px;
}
</style>
