import services from '~/services'
import { VideoType, PlaylistItemType, PlaylistType } from '~/types/resource'

export const formatPlaylists = (token: string): PlaylistType[] => {
  let result: [] = []
  const items = services.getOwnPlaylists(token)
  services.getOwnPlaylists(token).then((res): void => {
    const items = res.data.items
    result = items
  })
  return result
}
