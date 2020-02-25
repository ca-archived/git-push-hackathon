<template>
<header class="header">
  <div class="header__wrap">
    <h1 class="header__logo">CURIO!</h1>
    <slot />
    <Menu />
  </div>
</header>
</template>

<script>
import Menu from '~/components/independents/Header/Menu'
import { createComponent } from '@vue/composition-api'

export default createComponent({
  components: {
    Menu
  }
})
</script>

<style lang="scss" scoped>
.header {
  position: fixed;
  z-index: 101;
  width: 100%;
  height: 100px;
  background: $__mainColor;
  color: white;

  &__wrap{
    display: flex;
    height: inherit;
    align-items: center;
    justify-content: space-between;
    padding: 0 50px;
  }

  &__logo{
    font-size: 42px;
  }
}
</style>