import axios, { AxiosInstance, AxiosRequestConfig } from "axios";

import { mapKeysCamelCase } from "@/utils/convertObjectKeys2Camelcase";
import { isDevelopment } from "@/utils/environment";

interface Options {
  baseUrl: string;
  bearerToekn?: string;
}

interface MyAxiosConfig extends AxiosRequestConfig {
  headers: {
    "Content-Type": string;
    authorization?: string;
  };
}

export class RestClient {
  constructor({ baseUrl, bearerToekn }: Options) {
    const axiosConfig: MyAxiosConfig = {
      baseURL: baseUrl,
      timeout: 5000,
      headers: {
        "Content-Type": "application/json"
      }
    };

    if (bearerToekn) {
      axiosConfig.headers.authorization = `Bearer ${bearerToekn}`;
    }

    this.axios = axios.create(axiosConfig);

    this.axios.interceptors.response.use(
      response => {
        const { config, data, status } = response;
        const { method, params, url } = config;
        const convertedData = mapKeysCamelCase(data);
        if (isDevelopment()) {
          console.group(
            `${
              method ? method.toUpperCase() : "undefined method"
            }:${status} - ${url}`
          );
          if (params) console.table(params);
          console.log(convertedData);
          console.groupEnd();
        }
        return { ...response, data: convertedData };
      },
      error => {
        console.log(error);
        return Promise.reject(error);
      }
    );
  }

  private axios: AxiosInstance;

  public async get<T>(
    path: string,
    params?: object,
    successed?: (res: object) => void,
    errored?: (res: object) => void,
    always: () => any = () => {}
  ) {
    try {
      const response = await this.axios.get<T>(path, { params });
      if (successed) successed(response);
      return response;
    } catch (error) {
      if (errored) errored(error);
      throw error;
    } finally {
      always();
    }
  }

  public async post<T>(
    path: string,
    params?: object,
    successed?: (res: object) => void,
    errored?: (res: object) => void,
    always: () => any = () => {}
  ) {
    try {
      const response = await this.axios.post<T>(path, params);
      if (successed) successed(response);
      return response;
    } catch (error) {
      if (errored) errored(error);
      throw error;
    } finally {
      always();
    }
  }

  public async delete<T>(
    path: string,
    params?: object,
    successed?: (res: object) => void,
    errored?: (res: object) => void,
    always: () => any = () => {}
  ) {
    try {
      const response = await this.axios.delete<T>(path, { data: { params } });
      if (successed) successed(response);
      return response;
    } catch (error) {
      if (errored) errored(error);
      throw error;
    } finally {
      always();
    }
  }
}
