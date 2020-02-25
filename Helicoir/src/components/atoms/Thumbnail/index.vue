<template>
  <img class="thumbnail" :src="source" />
</template>

<script lang="ts">
import { createComponent } from '@vue/composition-api'

export default createComponent({
  props: {
    source: String,
    size: String
  }
})
</script>

<style lang="scss" scoped>
.thumbnail{
  display: block;
  width: 100%;
  margin: 0 auto;
  background: black;
}
</style>