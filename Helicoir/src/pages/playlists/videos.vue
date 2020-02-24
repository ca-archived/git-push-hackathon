<template>
  <div class="container">
    <ListContainer>
      <div slot="heading">
        <h3>プレイリストから選ぶ</h3>
      </div>
      <ItemCard
        v-for="item in items"
        :key="item.id"
        :title="item.snippet.title"
        :image="item.snippet.thumbnails.high.url"
        @action="() => item.isSelected = !item.isSelected"
      >
        <Modal
          slot="modal"
          :show="item.isSelected"
          :confirm="true"
          @enter="startCuration({
            id: item.snippet.resourceId.videoId,
            title: item.snippet.title,
            thumbnail: item.snippet.thumbnails.high.url
          })"
          :enterText="`開始する`"
          @close="() => item.isSelected = false"
        >
          <ItemListCard
            slot="content"
            :title="item.snippet.title"
            :channel="item.snippet"
            :image="item.snippet.thumbnails.high.url"
          />
        </Modal>
      </ItemCard>
    </ListContainer>
  </div>
</template>

<script lang="ts">
import { createComponent, ref } from '@vue/composition-api'
import services from '../../services'
import { formatVideos } from '../../mappers'
import ListContainer from '~/components/organisms/ListContainer/index.vue'
import ItemCard from '@/components/molecules/ItemCard/index.vue'
import ItemListCard from '~/components/molecules/ItemListCard/index.vue'
import Modal from '~/components/independents/Modal/index.vue'
import Cookies from 'js-cookie'

export default createComponent({
  setup(props, context) {
    const result = []
    const startCuration = (params) => {
      context.root.$accessor.commitSnapshot({
        search: {
          id: params.id,
          title: params.title,
          thumbnail: params.thumbnail
        }
      })
      console.log(context.root.$accessor.commitSnapshot)
      context.root.$router.push(`/curation`)
    }
    return {
      items: ref(result),
      startCuration
    }
  },
  components: {
    ItemCard,
    ListContainer,
    Modal,
    ItemListCard
  },
  mounted() {
    this.items = formatVideos(this.$accessor.token, this.$route.query.id)
    console.log(this.items)
  },
  methods: {}
})
</script>
