declare module "youtube/v3/playlists" {
  export interface PageInfo {
    totalResults: number;
    resultsPerPage: number;
  }

  export interface ThumbnailDetail {
    url: string;
    width: number;
    height: number;
  }

  export interface Thumbnails {
    default: ThumbnailDetail;
    medium: ThumbnailDetail;
    high: ThumbnailDetail;
    standard: ThumbnailDetail;
    maxres: ThumbnailDetail;
  }

  export interface Localized {
    title: string;
    description: string;
  }

  export interface Snippet {
    publishedAt: Date;
    channelId: string;
    title: string;
    description: string;
    thumbnails: Thumbnails;
    channelTitle: string;
    localized: Localized;
  }

  export interface Status {
    privacyStatus: string;
  }

  export interface ContentDetails {
    itemCount: number;
  }

  export interface Item {
    kind: string;
    etag: string;
    id: string;
    snippet?: Snippet;
    status?: Status;
    contentDetails?: ContentDetails;
  }

  export interface PlaylistsResponse {
    kind: string;
    etag: string;
    pageInfo: PageInfo;
    items: Item[];
  }
}
