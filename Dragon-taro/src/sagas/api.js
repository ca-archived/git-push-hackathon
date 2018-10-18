const sessionAccessToken = sessionStorage.getItem("access_token");
const url = path => `https://api.github.com/${path}`;
const headers = accessToken => {
  return { Authorization: `token ${accessToken || sessionAccessToken}` };
};

export function Get(path, accessToken = null) {
  // 二箇所returnを書かないとPromiseが返らない
  return fetch(url(path), {
    method: "GET",
    mode: "cors",
    headers: headers(accessToken)
  })
    .then(res => res.json())
    .then(payload => {
      return { resp: payload };
    })
    .catch(error => {
      return { error: error };
    });
}

export function Send(path, data, method) {
  return fetch(url(path), {
    method: method,
    headers: headers(),
    body: JSON.stringify(data)
  })
    .then(res => res.json())
    .then(payload => {
      return { resp: payload };
    })
    .catch(error => {
      return { error: error };
    });
}
