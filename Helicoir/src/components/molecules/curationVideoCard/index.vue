<template>
  <a class="video-card">
    <slot />
    <div class="video-card__thumbnail">
      <Thumbnail :source="image" />
    </div>
    <div class="video-card__mask">
      <div class="video-card__description">
        <div class="video-card__description__top">
          <a>
            <font-awesome-icon
              icon="arrow-right"
              class="video-card__description__icons"
              @click="event__next"
          /></a>
          <a>
            <font-awesome-icon
              icon="sign-in-alt"
              class="video-card__description__icons"
              @click="event__add"
          /></a>
          <a :href="`https://www.youtube.com/watch?v=${id}`" target="_blank">
            <font-awesome-icon
              icon="play-circle"
              class="video-card__description__icons"
          /></a>
          <a>
            <font-awesome-icon
              icon="info"
              class="video-card__description__icons"
              @click="event__showInfo"
          /></a>
        </div>
        <div class="video-card__description__bottom">
          <Caption :title="title" />
          <div class="video-card__description__channel">
            <div class="video-card__description__bar" />
            <span class="video-card__description__channelName">{{
              channel
            }}</span>
          </div>
        </div>
      </div>
    </div>
  </a>
</template>

<script lang="ts">
import Caption from '~/components/atoms/Caption/index.vue'
import Thumbnail from '~/components/atoms/Thumbnail/index.vue'

export default {
  props: ['title', 'channel', 'image', 'id', 'tags', 'isSelected'],
  components: {
    Caption,
    Thumbnail
  },
  methods: {
    event__showInfo() {
      this.$emit('showModal')
    },
    event__add() {
      this.$emit('addItem', {
        id: this.id,
        thumbnail: this.image,
        title: this.title
      })
    },
    event__next() {}
  }
}
</script>

<style lang="scss" scoped>
.video-card {
  position: relative;
  display: block;
  width: calc(120px + 36vmin);
  margin: 10px 4vmin;
  padding-top: 5px;
  background: black;
  cursor: default;
  @include bp(tablet) {
    width: 10vmin;
  }
  &__mask {
    position: absolute;
    bottom: 0px;
    left: 0;
    display: flex;
    flex-direction: column-reverse;
    width: inherit;
    height: 100%;
    background: linear-gradient(
      to bottom,
      rgba(0, 0, 0, 0) 60%,
      rgba(0, 0, 0, 1)
    );
  }
  &__description {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    height: 100%;
    padding: 10px 0;
    &__top {
      display: flex;
      flex-direction: row-reverse;
      width: 100%;
      padding: 0 8px 0 0;
    }
    &__icons {
      margin: 0 8px 0;
      font-size: calc(8px + 1.6vmin);
      color: white;
      opacity: 0.7;
      transition: 0.15s;
      cursor: pointer;
      &:hover {
        opacity: 1;
      }
    }
    &__bottom {
      width: 100%;
    }
    &__channel {
      display: flex;
      align-items: center;
      width: 100%;
      margin: 8px 0 0;
    }
    &__bar {
      width: 12%;
      height: 1px;
      margin: 0 4% 0 0;
      background: $__subGrayColor;
    }
    &__channelName {
      color: $__subGrayColor;
    }
  }
}
</style>
