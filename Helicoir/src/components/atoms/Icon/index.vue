<template>
  <div class="icon__wrap">
    <img class="icon" :src="thumbnail" />
  </div>
</template>

<script lang="ts">
import { createComponent } from '@vue/composition-api'

export default createComponent({
  props: {
    thumbnail: String,
    id: String,
    title: String,
    channel: String
  },
  setup(props) {
    const style = {
      backgroundImage: props.thumbnail
    }
    return {
      style
    }
  }
})
</script>

<style lang="scss" scoped>
.icon {
  width: 120%;
  height: 120%;

  &__wrap {
    display: flex;
    align-items: center;
    width: 60px;
    height: 60px;
    border: 1px solid white;
    border-radius: 100px;
    overflow: hidden;
  }
}
</style>