export default {
  mode: 'universal',
  srcDir: 'src/',
  head: {
    title: process.env.npm_package_name || '',
    meta: [
      { charset: 'utf-8' },
      { name: 'viewport', content: 'width=device-width, initial-scale=1' },
      {
        hid: 'description',
        name: 'description',
        content: process.env.npm_package_description || ''
      }
    ],
    link: [{ rel: 'icon', type: 'image/x-icon', href: '/favicon.ico' }]
  },
  loading: { color: '#fff' },
  css: [
    { src: '~/assets/css/html5-reset.css', lang: 'css' },
    { src: '~/assets/scss/common.scss', lang: 'scss' }
  ],
  plugins: [],
  /*
   ** Nuxt.js dev-modules
   */
  buildModules: [
    '@nuxtjs/eslint-module'
  ],
  modules: [
    '@nuxtjs/axios',
    '@nuxtjs/dotenv'
  ],
  axios: {
    proxy: true,
    prefix: 'https://www.googleapis.com/youtube/v3/'
  },
  proxy: {
    '/api/': 'https://www.googleapis.com/youtube/v3/'
  },
  env: {
    API_KEY: process.env.API_KEY
  },
  build: {
    /*
     ** You can extend webpack config here
     */
    extend(config, ctx) {}
  }
}
