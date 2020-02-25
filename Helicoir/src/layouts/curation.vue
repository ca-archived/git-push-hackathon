<template>
  <div>
    <Header>
      <HeaderCuratedList
        :items="curatedItems"
      />
    </Header>
    <main>
      <nuxt />
    </main>
  </div>
</template>

<script lang="ts">
import { createComponent } from '@vue/composition-api'
import Header from '~/components/independents/Header/index.vue'
import HeaderCuratedList from '~/components/molecules/HeaderCuratedList/index.vue'

export default createComponent({
  setup(props, context){
    console.log()
    return {
      curatedItems: context.root.$accessor.curationItems.items
    }
  },
  components: {
    Header,
    HeaderCuratedList
  }
})
</script>

<style lang="scss">
html {
  font-family: 'Avenir Next', 'Helvetica', '游ゴシック体', 'Yu Gothic', Arial,
    sans-serif;
  font-size: 16px;
  word-spacing: 1px;
  background: linear-gradient(-10deg, #052B16 45%, #171717) no-repeat;
  -ms-text-size-adjust: 100%;
  -webkit-text-size-adjust: 100%;
  -moz-osx-font-smoothing: grayscale;
  -webkit-font-smoothing: antialiased;
  box-sizing: border-box;
}

main {
  padding-top: 100px;
  min-height: 100vh;
}
</style>
