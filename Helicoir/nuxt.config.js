export default {
  mode: 'universal',
  srcDir: 'src/',
  head: {
    title: process.env.npm_package_name || 'Curio!',
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
    // { src: '~/assets/scss/constants.scss', lang: 'scss' },
    // { src: '~/assets/scss/includes.scss', lang: 'scss' },
    // { src: '~/assets/scss/common.scss', lang: 'scss' }
  ],
  plugins: [
    '~/plugins/composition-api',
    '~/plugins/persistedStorage.js'
  ],
  /*
   ** Nuxt.js dev-modules
   */
  buildModules: [
    '@nuxtjs/eslint-module',
    ['@nuxt/typescript-build', {
      typeCheck: true
    }],
    'nuxt-typed-vuex'
  ],
  modules: [
    '@nuxtjs/axios',
    '@nuxtjs/dotenv',
    '@nuxtjs/style-resources',
    'nuxt-client-init-module'
  ],
  axios: {
    proxy: true,
    prefix: '/api'
  },
  proxy: {
    '/api': {
      target: 'https://www.googleapis.com/youtube/v3/',
      pathRewrite: {
        '^/api': '/'
      }
    }
  },
  styleResources: {
    scss: [
      '~/assets/scss/mixins.scss',
      '~/assets/scss/constants.scss',
      '~/assets/scss/includes.scss',
      '~/assets/scss/common.scss'
    ]
  },
  env: {
    API_KEY: process.env.API_KEY
  },
  build: {
    transpile: [
      /typed-vuex/,
    ],
    /*
     ** You can extend webpack config here
     */
    extend(config, ctx) {}
  }
}
