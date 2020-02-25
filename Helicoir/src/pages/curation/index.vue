<template>
  <div class="container">
    <div slot="heading"></div>
    <CurationListContainer>
      <transition-group
        name="list"
        tag="div"
        style="display: flex;
              flex-wrap: wrap;
              justify-content: space-around;"
      >
        <CurationVideoCard
          v-for="item in result"
          :key="item.id.videoId"
          :id="item.id.videoId"
          :title="item.snippet.title"
          :image="item.snippet.thumbnails.high.url"
          :channel="item.snippet.channelTitle"
          :tags="item.snippet.tags"
          :isSelected="item.isSelected"
          @showModal="
            () => {
              item.isSelected = true
            }
          "
          @addItem="addItem"
          @next="next"
        >
          <Modal
            :show="item.isSelected"
            @close="
              () => {
                item.isSelected = false
              }
            "
          >
            <ItemListCard
              slot="content"
              :title="item.snippet.title"
              :channel="item.snippet.channelTitle"
              :image="item.snippet.thumbnails.high.url"
            />
            <p slot="text" style="line-height: 1.4;">
              {{ item.snippet.description }}
            </p>
          </Modal>
        </CurationVideoCard>
      </transition-group>
    </CurationListContainer>
  </div>
</template>

<script lang="ts">
import {
  createComponent,
  onMounted,
  reactive,
  computed
} from '@vue/composition-api'
import services from '../../services'
import { formatCurationMixin } from '../../mappers'
import { VideoType, CurationVideoType } from '../../types/resource'
import CurationListContainer from '~/components/organisms/CurationListContainer/index.vue'
import Modal from '~/components/independents/Modal/index.vue'
import CurationVideoCard from '@/components/molecules/CurationVideoCard/index.vue'
import ItemListCard from '~/components/molecules/ItemListCard/index.vue'
import Cookies from 'js-cookie'

export default createComponent({
  setup(props, context) {
    let render = true
    let result: [] | CurationVideoType[] = []
    const alt = computed(() => result)
    const getItem = (token: string, id: string): void => {
      services
        .getRelatedVideos(token, id)
        .then((res): void => {
          const raw = res.data
          const sts: any = []
          raw.items.forEach((el: CurationVideoType) => {
            // el.isDuplicated = context.root.$accessor.curationItems.items.find(
            //   (item) => item.snippet.id === el.id
            // )
            el.isSelected = false
            sts.push(el)
          })
          result.splice(0, result.length, ...sts)
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
    const addItem = (params) => {
      context.root.$accessor.commitItem(params)
      console.log(context.root.$accessor.curationItems.items)
    }
    const next = (params) => {
      console.log(params)
      context.root.$accessor.commitSearch(params)
      getItem(
        context.root.$accessor.token,
        context.root.$accessor.curationItems.search.id
      )
      console.log(result)
    }
    onMounted(() => {
      getItem(
        context.root.$accessor.token,
        context.root.$accessor.curationItems.search.id
      ),
        console.log(alt.value)
    })
    return {
      render,
      result: alt.value,
      addItem,
      next
    }
  },
  components: {
    CurationVideoCard,
    CurationListContainer,
    ItemListCard,
    Modal
  }
})
</script>

<style lang="scss" scoped>
.list {
  &-enter-active,
  &-leave-active {
    transition: all 0.3s;
  }
  &-enter,
  &-leave-to {
    opacity: 0.2;
    transform: translateY(10px);
  }
}
</style>
