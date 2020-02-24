type Snippet = {
  publishedAt?: string
  channelId?: string
  title?: string
  description?: string
  thumbnails?: {
    [s: string]: {
      url?: string
      width?: string
      height?: string
    }
  }
}

type ResourceElement = {
  id?:
    | string
    | {
        kind?: string
        videoId?: string
        channelId?: string
        playlistId?: string
      }
  nextPageToken?: string
  prevPageToken?: string
}

export interface SearchResult extends ResourceElement {
  snippet?: Snippet & {
    channelTitle?: string
  }
}

export interface PlaylistType extends ResourceElement {
  snippet?: Snippet & {
    channelTitle?: string
    tags?: [string]
  }
}

export interface PlaylistItemType extends ResourceElement {
  snippet: Snippet & {
    channelTitle?: string
    playlistId?: string
    resourceId?: string
  }
}

export interface VideoType extends ResourceElement {
  snippet: Snippet & {
    channelTitle?: string
    tags?: [string]
    categoryId?: string
  }
  isSelected?: boolean
}

export type CurationVideoType = VideoType & {
  isDuplicated?: boolean
}