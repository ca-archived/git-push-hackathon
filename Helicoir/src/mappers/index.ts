import services from '~/services'
import {
  VideoType,
  PlaylistItemType,
  PlaylistType,
  CurationVideoType
} from '~/types/resource'
import { createComponent } from '@vue/composition-api'

export const formatPlaylists = (token: string): PlaylistType[] => {
  let result: [] = []
  services.getOwnPlaylists(token).then((res): void => {
    const items = res.data.items
    result = items
  })
  return result
}

export const formatVideos = (token: string, id: string): VideoType[] => {
  let result: [] = []
  services
    .getPlaylistItems(token, id)
    .then((res): void => {
      const raw = res.data
      raw.items.forEach((el: VideoType) => {
        el.isSelected = false
        result.push(el)
      })
    })
    .catch(() => {
      window.alert(
        '時間経過によりログイン状態は解除されました。Googleアカウントを選択後、プレイリスト選択からやり直してください。'
      )
      const send: any = window.open(
        `https://accounts.google.com/o/oauth2/auth?client_id=278812716718-0c8fieggnimq47pmo1ucepfc3855apae.apps.googleusercontent.com&redirect_uri=http://localhost:5884/playlists&response_type=token&scope=https://www.googleapis.com/auth/youtube`
      )
      setTimeout(send, 3000)
    })
  return result
}

export const formatCurationVideos = (
  token: string,
  id: string
): CurationVideoType[] => {
  let result: [] = []
  services
    .getPlaylistItems(token, id)
    .then((res): void => {
      const raw = res.data
      raw.items.forEach((el: CurationVideoType) => {
        el.isDuplicated = this.curationItems
        result.push(el)
      })
    })
    .catch(() => {
      window.alert(
        '時間経過によりログイン状態は解除されました。Googleアカウントを選択後、プレイリスト選択からやり直してください。'
      )
      const send: any = window.open(
        `https://accounts.google.com/o/oauth2/auth?client_id=278812716718-0c8fieggnimq47pmo1ucepfc3855apae.apps.googleusercontent.com&redirect_uri=http://localhost:5884/playlists&response_type=token&scope=https://www.googleapis.com/auth/youtube`
      )
      setTimeout(send, 3000)
    })
  return result
}

export const formatCurationMixin = createComponent({
  setup(props, context) {
    const getItem = (token: string, id: string): CurationVideoType[] => {
      console.log(token, id)
      services
        .getRelatedVideos(token, id)
        .then((res): VideoType[] => {
          console.log('achieved!')
          const raw = res.data
          const result: [] = []
          console.log(context)
          raw.items.forEach((el: CurationVideoType) => {
            console.log(context.root.$accessor.curationItems)
            el.isDuplicated = context.root.$accessor.curationItems.items.find(
              (item) => item.snippet.id === el.id
            )
            console.log(el.isDuplicated)
            result.push(el)
          })
          return result
        })
        .catch((error) => {
          console.log(error)
        })
    }
    return {
      getItem
    }
  }
})
