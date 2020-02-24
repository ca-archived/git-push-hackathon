<template>
  <transition>
    <div class="modal__mask" v-if="show">
      <div class="modal__window" @event="close()">
        <slot name="content" />
        <div v-if="confirm" class="modal__window__buttons">
          <Button @event="enter()">{{ enterText }}</Button>
          <Button @event="close()">戻る</Button>
        </div>
      </div>
    </div>
  </transition>
</template>

<script>
import { createComponent } from '@vue/composition-api'
import Button from '~/components/atoms/Button'

export default createComponent({
  props: {
    show: {
      type: Boolean,
      default: false
    },
    confirm: {
      type: Boolean,
      default: false
    },
    enterText: String
  },
  setup(props, context) {
    const enter = () => {
      context.emit('enter')
    }
    const close = () => {
      context.emit('close')
    }
    return {
      enter,
      close
    }
  },
  components: {
    Button
  }
})
</script>

<style lang="scss" scoped>
.modal {
  &__mask {
    position: fixed;
    z-index: 102;
    top: 0;
    left: 0;
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100vw;
    height: 100vh;
    background: $__mask;
  }
  &__window {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: space-around;
    padding: 60px 100px;
    background: $__mainColor;
  }
}
</style>