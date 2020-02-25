<template>
  <div class="container">
    <div slot="heading"></div>
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
import { VideoType, CurationVideoType } from '../../types/resource'
import CurationListContainer from '~/components/organisms/CurationListContainer/index.vue'
import CurationVideoCard from '@/components/molecules/CurationVideoCard/index.vue'
import Cookies from 'js-cookie'

export default createComponent({
  setup(props, context) {
    let result: [] | CurationVideoType[] = []
    console.log(context.root.$accessor.token)
    const getItem = (token: string, id: string): void => {
      services
        .getRelatedVideos(token, id)
        .then((res): void => {
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
        .catch(() => {
          window.alert(
            '時間経過によりログイン状態は解除されました。Googleアカウントを選択してください。'
          )
          const send: any = window.open(
            `https://accounts.google.com/o/oauth2/auth?client_id=278812716718-0c8fieggnimq47pmo1ucepfc3855apae.apps.googleusercontent.com&redirect_uri=http://localhost:5884/curation&response_type=token&scope=https://www.googleapis.com/auth/youtube`
          )
          setTimeout(send, 3000)
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
  }
})
</script>
