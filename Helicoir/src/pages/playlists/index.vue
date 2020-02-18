<template>
  <div class="container">
    <ItemCard
      v-for="item in items.item"
      :key="item"
      :title="item.title"
      :image="item.thumbnails.high.url"
    />
    <p>here: {{ items }}</p>
    <button @click="tokenclick('this!')">this!</button>
  </div>
</template>

<script lang="ts">
import { createComponent, ref } from '@vue/composition-api'
import services from '../../services'
import ItemCard from '~/components/molecules/ItemCard/index.vue'
import Cookies from 'js-cookie'

// 0auth認証の実装について
/*
最初にトップページから認証用のページに飛ばすリンクを作っておき、そこから跳ばせて認証を行う
認証前にアクセスしてしまうと何もデータが帰ってこなくなってしまうので、
その際は認証用ページへのURLを添付できるといい
*/

/*#access_token=ya29.Il-9B_NKnpJhiVtITkKTzrSF6RErcWy4NsI2vF6QJr5R22Njzm9K2tv2EEsPFwvthsseVigo-S6T7GS9lIRu50sIZQWwxWHp3PfQvcpK8ggqhD_MYEyrax5uyii7EubtKw&
token_type=Bearer&
expires_in=3599&
scope=https://www.googleapis.com/auth/youtube */

export default createComponent({
  components: {
    ItemCard
  },
  mounted() {
    // this.$store.actions.saveTokenInStorage()
    if (process.browser) {
      const hashes = window.location.hash
      const tokenString_end = hashes.indexOf('&')
      const token = hashes.slice(14, tokenString_end)
      console.log(token)
      if (hashes) {
        this.$accessor.commitToken(token)
      }
    }
  },
  methods: {
    tokenclick(e) {
      console.log(Cookies.getJSON('cookieState'))
    }
  },
  setup() {
    // const items = ref(services.getOwnPlaylists())
    return {
      items: ref(
        'a' /*services.getOwnPlaylists(localStorage.getItem('token'))*/
      )
    }
  }
})
</script>
