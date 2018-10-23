<template>
  <transition name="gist" tag="div">
  <div>
    <router-link class="a" v-bind:to="`/gists/${gist.id}`" >
      <div class="card">

        <div class="title">
          <h2>{{ cutDesc(gist.description) }}</h2>
        </div>

        <div class="content">
          <p v-for="(name, index) in fileNames(gist.files)" :key="index">{{ name }}</p>
        </div>

        <div class="wrap">
          <div class="user">
            <router-link :to="`/users/${gist.owner.login}`" class="link">
              <div class="avatar">
                <img class="avatar" :src="gist.owner.avatar_url" alt="">
              </div>
              <div class="username">
                {{ gist.owner.login }}
              </div>
            </router-link>
          </div>

          <div class="comments">
            <comments :num="gist.comments" />
          </div>
        </div>

      </div>
    </router-link>
  </div>
  </transition>
</template>

<script>
import Comments from './Comments'

export default {
  props: {
    gist: Object
  },
  methods: {
    cutDesc (text) {
      if (text === '' || text === null) {
        return '-No Description-'
      }
      if (text.length > 100) {
        return text.slice(0, 100) + '...'
      }
      return text
    },
    fileNames (files) {
      let names = Object.keys(files)
      if (names.length > 10) {
        names = names.slice(0, 10)
        names.push('...and more!')
      }
      return names
    }
  },
  components: {
    'comments': Comments
  }
}

</script>

<style scoped>

h2 {
  margin: 0;
}

p {
  margin: 8px auto;
}

.a {
  text-decoration: none;
  color: black;
}

.wrap {
  display: table;
  width: 100%;
  margin: 16px 0 0 0;
}

.user {
  display: table-cell;
}

.comments {
  display: table-cell;
  text-align: right;
}

.link {
  color: black;
  border-radius: 8px;
  padding: 4px 8px;
  border: solid 1px #000;
}

.avatar {
  width: 24px;
  height: 24px;
  display: inline-block;
  vertical-align: middle;
}

.username {
  margin: 0 4px 0 4px;
  display: inline-block;
  vertical-align: middle;
}

.gist-enter-active{
  opacity: 0;
  transform: translateX(50px);
  transition-property: transform, opacity;
  transition-duration       : 0.3s;
  transition-timing-function: cubic-bezier(0.77, 0, 0.175, 1);
  transition-delay         : 0s;
}
.gist-enter-to{
  opacity: 1;
  transform: translateX(0);
}
</style>
