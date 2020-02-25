import camelCase from "lodash.camelcase";
import mapKeys from "lodash.mapkeys";
import mapValues from "lodash.mapvalues";
import isObject from "lodash.isobject";
import isArray from "lodash.isarray";

type Callback<T> = (...args: any) => T;
type ArrayOrObject = {} | [];

const mapKeysDeep = (
  data: ArrayOrObject,
  callback: Callback<string>
): ArrayOrObject => {
  if (isArray(data)) {
    return data.map(innerData => mapKeysDeep(innerData, callback));
  } else if (isObject(data)) {
    return mapValues(mapKeys(data, callback), val =>
      mapKeysDeep(val, callback)
    );
  } else {
    return data;
  }
};

export const mapKeysCamelCase = (data: ArrayOrObject) =>
  mapKeysDeep(data, (_value: any, key: string) => camelCase(key));
