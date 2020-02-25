<template>
  <div class="curated-items" v-if="items.id !== 'no-items' | undefined">
    <p class="curated-items__text">追加した動画</p>
    <div class="curated-items__box">
      <div class="curated-items__iconWrap" v-for="item in items" :key="item.id">
        <Icon
          :id="item.id"
          :thumbnail="item.thumbnail"
          :title="item.title"
        />
      </div>
    </div>
  </div>
</template>

<script>
import Icon from '~/components/atoms/Icon'

export default {
  props: ['items'],
  components: {
    Icon
  }
}
</script>

<style lang="scss" scoped>
.curated-items{
  display: flex;
  align-items: center;
  &__box{
    display: flex;
    width: 100%;
    max-width: inherit;
    margin: 0 10%;
    padding: 0 5%;
    overflow: scroll;
  }
  &__iconWrap{
    margin: 0 4px;
  }
}
</style>