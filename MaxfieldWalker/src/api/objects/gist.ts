interface GistCommonAttributes {
  public: boolean;
  url: string;
  id: string;
  description: string;
}

export interface ListedGistResponse extends GistCommonAttributes {
  files: { [key: string]: ListedGistFile };
}

export interface DetailedGistResponse extends GistCommonAttributes {
  files: { [key: string]: DetailedGistFile };
}

export interface UserResponse {
  avatar_url: string;
}

/**
 * リストで取得したGist
 * 各ファイルのcontentは含まれていない
 */
export interface ListedGist extends GistCommonAttributes {
  files: ListedGistFile[];
  isPrivate: boolean;
}

export interface DetailedGist extends GistCommonAttributes {
  files: DetailedGistFile[];
  isPrivate: boolean;
}

export interface ListedGistFile {
  filename: string;
  language: string;
  raw_url: string;
}

export interface DetailedGistFile extends ListedGistFile {
  content: string;
}

export interface NewGistFiles {
  [filename: string]: NewGist | null;
}

export interface NewGist {
  filename: string;
  content: string;
}
