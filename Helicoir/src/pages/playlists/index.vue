<template>
  <div class="container">
    <ItemCard
      v-for="item in items.items"
      :key="item"
      :title="item.title"
      :image="item.thumbnails.high.url"
    />
    <p>here: {{ items }}</p>
  </div>
</template>

<script lang="ts">
import { createComponent, ref, computed } from '@vue/composition-api'
import services from '../../services'
import ItemCard from '~/components/molecules/ItemCard/index.vue'
import Cookies from 'js-cookie'

export default createComponent({
  setup(props, context) {
    const result = []
    return {
      items: ref(result)
    }
  },
  components: {
    ItemCard
  },
  mounted() {
    // this.$store.actions.saveTokenInStorage()
    if (process.browser) {
      const hashes = window.location.hash
      const tokenString_end = hashes.indexOf('&')
      const token = hashes.slice(14, tokenString_end)
      if (hashes) {
        this.$accessor.commitToken(token)
      }
    }
    services.getOwnPlaylists(this.$accessor.token).then((res) => {
      this.items.push(res.data.items[0].kind)
      console.log(res)
    })
  }
})
</script>
