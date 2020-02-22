<template>
  <div class="container">
    <ItemCard
      v-for="item in items"
      :key="item.id"
      :title="item.snippet.title"
      :image="item.snippet.thumbnails.high.url"
      :link="`/curation?id=${item.id}`"
    />
  </div>
</template>

<script lang="ts">
import { createComponent, ref, computed } from '@vue/composition-api'
import services from '../../services'
import ListContainer from '~/components/organisms/ListContainer/index.vue'
import ItemCard from '@/components/molecules/ItemCard/index.vue'
import Cookies from 'js-cookie'

export default createComponent({
  setup(props, context) {
    const result = []
    return {
      items: ref(result)
    }
  },
  components: {
    ItemCard,
    ListContainer
  },
  mounted() {
    console.log(Cookies.get())
    services.getOwnPlaylists(this.$accessor.token).then((res) => {
      this.items = res.data.items
    })
  }
})
</script>
