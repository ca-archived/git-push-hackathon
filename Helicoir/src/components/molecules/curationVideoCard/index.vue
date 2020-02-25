<template>
  <nuxt-link v-if="link" class="video-card" :to="link">
    <div class="video-card__thumbnail">
      <Thumbnail :source="image" />
    </div>
    <div class="video-card__mask">
      <div class="video-card__description">
        <Caption :title="title" />
        <div class="video-card__description__channel">
          <div class="videocard__description__bar" />
          <span class="videocard__description__channelName">{{ channel }}</span>
        </div>
      </div>
    </div>
  </nuxt-link>
  <a v-else class="video-card">
    <div class="video-card__thumbnail">
      <Thumbnail :source="image" />
    </div>
    <div class="video-card__mask">
      <div class="video-card__description">
        <Caption :title="title" />
        <div class="video-card__description__channel">
          <div class="videocard__description__bar" />
          <span class="videocard__description__channelName">{{ channel }}</span>
        </div>
      </div>
    </div>
  </a>
</template>

<script lang="ts">
import Caption from '~/components/atoms/Caption/index.vue'
import Thumbnail from '~/components/atoms/Thumbnail/index.vue'

export default {
  props: ['title', 'channel', 'image', 'id', 'tags'],
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
  width: calc(120px + 36vmin);
  margin: 10px 4vmin;
  cursor: pointer;
  @include bp(tablet) {
    width: 10vmin;
  }
  &__mask{
    position: absolute;
    bottom: 0px;
    left: 0;
    display: flex;
    flex-direction: column-reverse;
    width: inherit;
    height: 150px;
    background: linear-gradient(to bottom, rgba(0, 0, 0, 0) 30%, rgba(0, 0, 0, 1))
  }
  &__description {
    display: flex;
    align-items: flex-end;
    padding: 10px 0;

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
