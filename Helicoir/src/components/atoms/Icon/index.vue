<template>
  <div class="icon" style="`{ backgroundImage: thumbnail }`" />
</template>

<script lang="ts">
import { createComponent } from '@vue/composition-api'

export default createComponent({
  props: {
    thumbnail: String,
    id: String,
    title: String,
    channel: String
  }
})
</script>

<style lang="scss" scoped>
.icon{
  width: 100px;
  height: 100px;
  border: 1px solid white;
  border-radius: 100px;
  background: no-repeat;
  background-size: contain;
}
</style>