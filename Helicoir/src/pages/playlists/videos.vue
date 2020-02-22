<template>
  <div class="container">
    <ListContainer>
      <div slot="heading">
        <h3>プレイリストから選ぶ</h3>
      </div>
      <ItemCard
        v-for="item in items.items"
        :key="item.id"
        :title="item.snippet.title"
        :image="item.snippet.thumbnails.high.url"
        :link="`/playlists/videos?id=${item.id}`"
      />
    </ListContainer>
  </div>
</template>

<script lang="ts">
import { createComponent, ref } from '@vue/composition-api'
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
    services
      .getPlaylistItems(this.$accessor.token, this.$route.query.id)
      .then((res) => {
        this.items = res.data
      })
  }
})
</script>
