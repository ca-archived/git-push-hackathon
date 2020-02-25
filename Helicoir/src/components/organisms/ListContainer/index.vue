<template>
<section class="item-list">
  <slot name="heading" />
  <div class="item-list__container">
    <slot />
  </div>
</section>
</template>

<style lang="scss" scoped>
.item-list{
  margin: 50px 10%;
  @include bp(mobile){
    margin: 0 5%;
  }
  &__container{
    display: flex;
    flex-wrap: wrap;
    justify-content: space-around;
    margin: 40px 0;
    padding: 50px 0;
    border-top: 1px solid $__gray;
    border-bottom: 1px solid $__gray;
  }
}
</style>