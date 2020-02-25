module.exports = {
  root: true,
  env: {
    browser: true,
    node: true
  },
  parserOptions: {
    parser: 'babel-eslint'
  },
  extends: [
    '@nuxtjs',
    'prettier',
    'prettier/vue',
    'plugin:prettier/recommended',
    'plugin:nuxt/recommended',
    '@nuxtjs/eslint-config-typescript'
  ],
  plugins: [
    'prettier'
  ],
  // add your custom rules here
  rules: {
    'prettier/prettier': ['error', {
      'singleQuote': true
    }],
    'indent': ['error', 2],
    'no-unused-vars': 1,
    '@typescript-eslint/no-unused-vars': 1
    'space-before-function-paren': 1
  }
}

