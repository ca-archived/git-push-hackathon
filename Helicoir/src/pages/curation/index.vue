<template>
  <div class="container">
    <div slot="heading">
    </div>
    <CurationListContainer>
      <CurationVideoCard
        v-for="item in result"
        :key="item.id"
        :title="item.snippet.title"
        :image="item.snippet.thumbnails.high.url"
      />
    </CurationListContainer>
  </div>
</template>

<script lang="ts">
import { createComponent, ref, onMounted } from '@vue/composition-api'
import services from '../../services'
import { formatCurationMixin } from '../../mappers'
import CurationListContainer from '~/components/organisms/CurationListContainer/index.vue'
import CurationVideoCard from '@/components/molecules/CurationVideoCard/index.vue'
import Cookies from 'js-cookie'

export default createComponent({
  setup(props, context) {
    let result: [] = []
    const getItem = (token: string, id: string): CurationVideoType[] => {
      services
        .getRelatedVideos(token, id)
        .then((res): VideoType[] => {
          console.log('achieved!')
          const raw = res.data
          console.log(raw)
          raw.items.forEach((el: CurationVideoType) => {
            if (context.root.$accessor.curationItems.items) {
              el.isDuplicated = context.root.$accessor.curationItems.items.find(
                (item) => item.snippet.id === el.id
              )
            } else {
              el.isDuplicated = false
            }
            console.log(el)
            result.push(el)
          })
          console.log(result)
        })
        .catch((error) => {
          console.log(error)
        })
    }
    onMounted(() => {
      getItem(
        context.root.$accessor.token,
        context.root.$accessor.curationItems.search.id
      )
      console.log(result)
    })
    return {
      result
    }
  },
  components: {
    CurationVideoCard,
    CurationListContainer
  },
  mounted() {
    console.log(Cookies.get())
    services.getOwnPlaylists(this.$accessor.token).then((res) => {
      this.items = res.data.items
    })
  }
})
</script>
