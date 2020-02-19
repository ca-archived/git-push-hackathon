import services from '~/services'

export const formatPlaylists = (token: string): PlaylistItems => {
  let result = []
  services.getOwnPlaylists(token).then((res) => {
    const items = res.data.items
    items.forEach(item => {
      item.apiPath = `https://www.googleapis.com/youtube/v3/playlistItems`
    })
  })
  return result
}
