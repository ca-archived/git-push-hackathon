<template>
  <div class="modal__mask">
    <div class="modal__window">
      <slot />
    </div>
  </div>
</template>

<style lang="scss" scoped>
.modal{
  &__mask{
    position: absolute;
    top: 0;
    left: 0;
    width: 120vw;
    height: 120vh;
    background: $__mask;
  }
}
</style>