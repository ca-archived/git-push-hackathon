<template>
 <div class="card">
   <div class="title">
     {{ title }}
   </div>
   <div class="content">
     {{ content }}
   </div>
 </div>
</template>

<script>

export default {
  props: {
    title: String,
    content: String
  }
}

</script>

<style>

</style>
