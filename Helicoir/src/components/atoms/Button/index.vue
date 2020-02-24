<template>
  <nuxt-link v-if="link" class="button" @click="emitEvent">
    <slot />
  </nuxt-link>
    <a v-else class="button" @click="emitEvent">
    <slot />
  </a>
</template>

<script>
export default ({
  props: ['link'],
  methods: {
    emitEvent() {
      this.$emit('event')
    }
  }
})
</script>

<style lang="scss" scoped>
.button{
  display: flex;
  justify-content: center;
  min-width: 100px;
  padding: 20px 20px;
  color: $__mainColor;
  font-weight: bold;
  background: white;
}
</style>