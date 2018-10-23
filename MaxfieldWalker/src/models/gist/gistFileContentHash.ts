type Hash = {
  [gistId: string]: {
    [filename: string]: string;
  };
};

export class GistFileContentHash {
  private _hash: Hash;

  constructor() {
    this._hash = {};
  }

  public getContent(gistId: string, filename: string): string | undefined {
    const gist = this._hash[gistId];
    if (gist) {
      const content = gist[filename];
      return content;
    }
  }

  public setContent(gistId: string, filename: string, content: string) {
    const gist = this._hash[gistId];
    if (gist) {
      gist[filename] = content;
    } else {
      const obj: any = {};
      obj[filename] = content;
      this._hash[gistId] = obj;
    }
  }
}
