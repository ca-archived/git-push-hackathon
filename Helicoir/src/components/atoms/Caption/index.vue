<template>
  <div class="caption-area">
    <h3 v-if="title">{{ title }}</h3>
    <p v-if="description">{{ text }}</p>
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

<style lang="scss" scoped></style>
