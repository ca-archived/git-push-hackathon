import {
  ListedGist,
  DetailedGist,
  DetailedGistFile
} from "../../api/objects/gist";
import { GitHubClient } from "../../api/githubClient";
import { GithubClientFactory } from "../../api/clientGenerator";
import { GistFileContentHash } from "./gistFileContentHash";

export class GistCache {
  private listedGists: { [id: string]: ListedGist };
  private _apiClient: GitHubClient;
  private _gistFileContentHash: GistFileContentHash;

  constructor() {
    this.listedGists = {};
    this._apiClient = GithubClientFactory.instance();
    this._gistFileContentHash = new GistFileContentHash();
  }

  getGistFileContent(gistId: string, filename: string): string | undefined {
    return this._gistFileContentHash.getContent(gistId, filename);
  }

  async loadListedGists() {
    const listedGists = await this._apiClient.getListedGists();

    for (const listedGist of listedGists) {
      this.listedGists[listedGist.id] = listedGist;
    }
  }

  isListedGistsLoaded(): boolean {
    return Object.keys(this.listedGists).length > 0;
  }

  getListedGists() {
    return this.listedGists;
  }

  addGist(gist: DetailedGist) {
    this.listedGists[gist.id] = gist;
    this.setFilesContent(gist.id, gist.files);
  }

  private setFilesContent(gistId: string, files: DetailedGistFile[]) {
    for (const file of files) {
      this._gistFileContentHash.setContent(gistId, file.filename, file.content);
    }
  }

  async loadGistDetail(gistId: string): Promise<void> {
    const result = await this._apiClient.getSingleGist(gistId);
    this.setFilesContent(gistId, result.files);
  }

  isGistDetailLoaded(gistId: string): boolean {
    return this.listedGists[gistId] !== undefined;
  }

  getGistFiles(gistId: string): { [filename: string]: DetailedGistFile } {
    const a = this.listedGists[gistId];
    const files: { [filename: string]: DetailedGistFile } = {};

    for (const file of a.files) {
      const content = this.getGistFileContent(gistId, file.filename);
      if (content) {
        files[file.filename] = {
          ...file,
          content
        };
      }
    }

    return files;
  }

  updateGistFileContent(gistId: string, filename: string, content: string) {
    this._gistFileContentHash.setContent(gistId, filename, content);
  }
}
