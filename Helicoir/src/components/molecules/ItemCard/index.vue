<template>
  <nuxt-link class="item-card" :to="link" @mouseover="toggleMouseover()" @mouseleave="toggleMouseover()">
    <div class="item-card__thumbnail">
      <Thumbnail :source="image" />
    </div>
    <div class="item-card__description">
      <Caption :title="title" />
    </div>
    <a v-if="mouseover" class="item-card__link"></a>
  </nuxt-link>
</template>

<script lang="ts">
import Caption from '~/components/atoms/Caption/index.vue'
import Thumbnail from '~/components/atoms/Thumbnail/index.vue'

export default {
  props: ['title', 'description', 'channel', 'image', 'link', 'id'],
  components: {
    Caption,
    Thumbnail
  },
  data() {
    return {
      mouseover: false
    }
  },
  methods: {
    toggleMouseover() {
      this.mouseover = !this.mouseover
    },
    stay() {this.mouseover = true}
  }
}
</script>

<style lang="scss" scoped>
.item-card {
  position: relative;
  display: block;
  width: 300px;
  margin: 40px;
  cursor: pointer;
  @include bp(tablet) {
    width: 10vmin;
  }
  &__description {
    padding: 10px 0;
  }
  &__link {
    position: absolute;
    top: 0;
    right: 0;
    width: 60px;
    height: 60px;
    background: black;
  }
}
</style>
