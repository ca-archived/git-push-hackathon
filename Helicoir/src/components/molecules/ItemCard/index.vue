<template>
  <nuxt-link v-if="link" class="itemlist-card" :to="link">
    <slot name="modal" />
    <div class="item-card__thumbnail">
      <Thumbnail :source="image" />
    </div>
    <div class="item-card__description">
      <Caption :title="title" />
    </div>
  </nuxt-link>

  <div v-else class="item-card" @click="emitEvent">
    <slot name="modal" />
    <div class="item-card__thumbnail">
      <Thumbnail :source="image" />
    </div>
    <div class="item-card__description">
      <Caption :title="title" />
    </div>
  </div>
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
  methods: {
    emitEvent() {
      this.$emit('action')
    }
  }
}
</script>

<style lang="scss" scoped>
.item-card {
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
}
</style>
