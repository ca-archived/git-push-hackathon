<template>
  <img class="thumbnail" :src="source" />
</template>

<script lang="ts">
import { createComponent } from '@vue/composition-api'

type Props = {
  source: string
}

export default createComponent({
  props: {
    source: String,
    size: String
  },
  setup(props: Props) {}
})
</script>
