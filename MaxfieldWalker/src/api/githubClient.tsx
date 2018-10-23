import ApolloClient, { gql, InMemoryCache } from "apollo-boost";
import axios, { AxiosInstance } from "axios";
import {
  ListedGist,
  ListedGistResponse,
  NewGistFiles,
  DetailedGistResponse,
  DetailedGist,
  UserResponse
} from "./objects/gist";

export class GitHubClient {
  private _graphQLClient: ApolloClient<InMemoryCache>;

  private _restClient: AxiosInstance;

  constructor(accessToken: string) {
    this._graphQLClient = new ApolloClient({
      cache: new InMemoryCache(),
      uri: "https://api.github.com/graphql",
      request: async operation => {
        operation.setContext({
          headers: {
            authorization: `Bearer ${accessToken}`
          }
        });
      }
    });

    this._restClient = axios.create({
      baseURL: "https://api.github.com",
      timeout: 10000,
      headers: {
        Authorization: `token ${accessToken}`
      }
    });
  }

  async getMyRepos() {
    const query = gql`
      {
        user(login: "MaxfieldWalker") {
          repositories(last: 5) {
            nodes {
              id
              name
              url
              viewerHasStarred
              stargazers {
                totalCount
              }
              isPrivate
            }
          }
        }
      }
    `;

    const result = await this._graphQLClient.query({ query });
    return result;
  }

  async getListedGists(): Promise<ListedGist[]> {
    const result = await this._restClient.get<ListedGistResponse[]>("/gists");

    if (result.status == 200) {
      return result.data
        .filter(x => Object.keys(x.files).length > 0)
        .map(x => ({
          ...x,
          files: Object.values(x.files),
          isPrivate: !x.public
        }));
    } else {
      console.error("Failed to get gists");
      return [];
    }
  }

  async createGist(
    description: string,
    isPublic: boolean,
    files: NewGistFiles
  ): Promise<DetailedGist> {
    const result = await this._restClient.post<DetailedGistResponse>("/gists", {
      description,
      public: isPublic,
      files
    });

    const { data } = result;

    const gist = {
      ...data,
      files: Object.values(data.files),
      isPrivate: !data.public
    };

    return gist;
  }

  async getSingleGist(gistId: string): Promise<DetailedGist> {
    const result = await this._restClient.get<DetailedGistResponse>(
      `/gists/${gistId}`
    );

    const { data } = result;

    return {
      ...data,
      files: Object.values(data.files),
      isPrivate: !data.public
    };
  }

  async deleteGist(gistId: string): Promise<boolean> {
    const result = await this._restClient.delete(`/gists/${gistId}`);
    return result.status == 204;
  }

  async editGist(
    gistId: string,
    description: string,
    files: NewGistFiles
  ): Promise<DetailedGist> {
    const result = await this._restClient.patch<DetailedGistResponse>(
      `/gists/${gistId}`,
      {
        description,
        files
      }
    );

    const { data } = result;

    const gist = {
      ...data,
      files: Object.values(data.files),
      isPrivate: !data.public
    };

    return gist;
  }

  async getUserData(): Promise<UserResponse> {
    const result = await this._restClient.get<UserResponse>("/user");

    return result.data;
  }
}
