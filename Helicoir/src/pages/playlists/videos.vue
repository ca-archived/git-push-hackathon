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
    if (!this.$route.params.token) {
      //      window.alert(
      //        'アクセス権が存在しません。数秒後にGoogleアカウント認証画面に進みます。'
      //      )
      //      const send: any = window.open(
      //        `https://accounts.google.com/o/oauth2/auth?client_id=278812716718-0c8fieggnimq47pmo1ucepfc3855apae.apps.googleusercontent.com&redirect_uri=http://localhost:5884${this.$route.path}&response_type=token&scope=https://www.googleapis.com/auth/youtube`
      //      )
      //      setTimeout(send, 3000)
    }
    console.log(this.$route.query.id)
    services
      .getPlaylistItems(this.$accessor.token, { id: this.$route.query.id })
      .then((res) => {
        this.items = res.data
        console.log(this.items)
      })
  }
})
</script>
