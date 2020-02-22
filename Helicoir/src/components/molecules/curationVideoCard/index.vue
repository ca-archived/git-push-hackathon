<template>
  <nuxt-link class="item-card" :to="link">
    <div class="video-card__thumbnail">
      <Thumbnail :source="image" />
    </div>
    <div class="video-card__description">
      <Caption :title="title" />
      <div class="video-card__description__channel">
        <div class="videocard__description__bar" />
        <span class="videocard__description__channelName">{{ channel }}</span>
      </div>
    </div>
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
  methods: {}
}
</script>

<style lang="scss" scoped>
.video-card {
  position: relative;
  display: block;
  width: 300px;
  margin: 40px;
  cursor: pointer;
  @include bp(tablet) {
    width: 10vmin;
  }
  &__description {
    position: absolute;
    top: 0;
    left: 0;
    display: flex;
    align-items: flex-end;
    padding: 10px 0;
    background: $__mask;

    &__channel {
      display: flex;
      align-items: center;
    }
    &__bar {
      height: 1px;
      background: $__subGrayColor;
    }
    &__channelName {
      color: $__subGrayColor;
    }
  }
}
</style>
