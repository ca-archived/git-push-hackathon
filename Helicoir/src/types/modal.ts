export type Modal = {
  confirm?: {
    show: boolean
    meta: {
      title: string
      thumbnail: string
      channel: string
    }
  }
  text?: {
    show: boolean
    text: string
  }
}
