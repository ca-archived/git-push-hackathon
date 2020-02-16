<template>
  <div class="item-card">
    <div class="item-card__thumbnail">
      <Thumbnail :source="image" />
    </div>
    <div class="item-card__description">
      <Caption :text="title" />
    </div>
  </div>
</template>

<script lang="ts">
import Caption from '~/components/atoms/Caption'
import Thumbnail from '~/components/atoms/Thumbnail'

export default {
  props: ['title', 'description', 'channel', 'image', 'link', 'id'],
  components: {
    Caption,
    Thumbnail
  }
}
</script>
