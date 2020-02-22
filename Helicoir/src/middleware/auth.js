export default (context) => {
  if (!context.app.$accessor.token ) {
    context.app.$accessor.toggleAuthenticated = false
  }
}
