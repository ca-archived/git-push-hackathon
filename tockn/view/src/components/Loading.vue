<template>
  <div id="loading"></div>
</template>

<style scoped>
#loading {
  position: absolute;
  width: 32px;
  height: 32px;
  border-radius: 50%;
  border: 8px solid black;
  border-right-color: transparent;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  0% {transform: rotate(0deg);}
  50% {transform: rotate(180deg);}
  100% {transform: rotate(360deg);}
}

</style>
