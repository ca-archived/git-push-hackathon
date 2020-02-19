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
        :link="`/playlists/videos?id=${item.id}`"
      />
    </ListContainer>
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
    if (process.browser) {
      const hashes = window.location.hash
      const tokenString_end = hashes.indexOf('&')
      const token = hashes.slice(14, tokenString_end)
      if (hashes) {
        this.$accessor.commitToken(token)
        console.log(this.$accessor.token)
      }
    }
    services.getOwnPlaylists(this.$accessor.token).then((res) => {
      this.items = res.data.items
    }).catch((e) => {if(process.browser){
      window.alert('アクセス権が存在しません。数秒後にGoogleアカウント認証画面に進みます。')
      const send: any = window.open(`https://accounts.google.com/o/oauth2/auth?client_id=278812716718-0c8fieggnimq47pmo1ucepfc3855apae.apps.googleusercontent.com&redirect_uri=http://localhost:5884/playlists&response_type=token&scope=https://www.googleapis.com/auth/youtube`)
      setTimeout(send, 3000)
    }})
  }
})
</script>
