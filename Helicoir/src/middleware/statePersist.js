import Cookies from 'js-cookie'

export default (context) => {
  const store = context.app.$accessor
  console.log(Cookies.get())
}