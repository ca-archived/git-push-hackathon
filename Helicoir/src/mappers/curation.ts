import services from '~/services'
import { CurationVideoType } from '~/types/resource'

export const formatCurationVideos = (token: string, id: string): CurationVideoType[] => {
  let result: [] = []
  services
    .getPlaylistItems(token, id)
    .then((res): void => {
      const raw = res.data
      raw.items.forEach((el: CurationVideoType) => {
        el.isDuplicated = 
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