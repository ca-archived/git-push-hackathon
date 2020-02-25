<template>
  <div class="caption-area">
    <h3 class="caption-area__text title" v-if="title">{{ title }}</h3>
    <p class="caption-area__text text" v-if="description">{{ text }}</p>
  </div>
</template>

<script>
import { createComponent } from '@vue/composition-api'

export default createComponent({
  props: {
    title: {
      type: String | Boolean,
      default: false
    },
    description: {
      type: String | Boolean,
      default: false
    }
  }
})
</script>

<style lang="scss" scoped>
.caption-area {
  width: 100%;

  &__text {
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    &.title {
      font-size: calc(16px + .6vmin);
    }
    &.text {
      font-size: 16px;
    }
  }
}
</style>
