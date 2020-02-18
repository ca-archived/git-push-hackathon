<template>
  <a class="item-card" :href="link">
    <div class="item-card__thumbnail">
      <Thumbnail :source="image" />
    </div>
    <div class="item-card__description">
      <Caption :title="title" />
    </div>
  </a>
</template>

<script lang="ts">
import Caption from '~/components/atoms/Caption/index.vue'
import Thumbnail from '~/components/atoms/Thumbnail/index.vue'

export default {
  props: ['title', 'description', 'channel', 'image', 'link', 'id'],
  components: {
    Caption,
    Thumbnail
  }
}
</script>

<style lang="scss" scoped>
.item-card {
  display: block;
  width: 300px;
  cursor: pointer;
  // @include bp(tablet){
  //   width: 10vmin;
  // }
  &__description {
    padding: 10px 0;
  }
}
</style>
