<template>
  <div class="container">
    <ItemCard
      v-for="item in items.item"
      :key="item"
      :title="item.title"
      :image="item.thumbnails.high.url"
    />
    <p>here: {{ items }}</p>
  </div>
</template>

<script lang="ts">
import { createComponent, ref } from '@vue/composition-api'
import services from '../../services'
import ItemCard from '~/components/molecules/ItemCard/index.vue'

// 0auth認証の実装について
/*
最初にトップページから認証用のページに飛ばすリンクを作っておき、そこから跳ばせて認証を行う
認証前にアクセスしてしまうと何もデータが帰ってこなくなってしまうので、
その際は認証用ページへのURLを添付できるといい
*/

export default createComponent({
  components: {
    ItemCard
  },
  setup() {
    // const items = ref(services.getOwnPlaylists())
    return {
      items: ref(services.getOwnPlaylists())
    }
  }
})
</script>
