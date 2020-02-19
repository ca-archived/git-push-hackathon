declare module "youtube/v3/playlistItems" {
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

  export interface ResourceId {
    kind: string;
    videoId: string;
  }

  export interface Snippet {
    publishedAt: Date;
    channelId: string;
    title: string;
    description: string;
    thumbnails: Thumbnails;
    channelTitle: string;
    playlistId: string;
    position: number;
    resourceId: ResourceId;
  }

  export interface ContentDetails {
    videoId: string;
    videoPublishedAt: Date;
  }

  export interface Status {
    privacyStatus: string;
  }

  export interface Item {
    kind: string;
    etag: string;
    id?: string;
    snippet?: Snippet;
    contentDetails?: ContentDetails;
    status?: Status;
  }

  export interface PlaylistItemsResponse {
    kind: string;
    etag: string;
    pageInfo: PageInfo;
    items: Item[];
  }
}
