const accessToken = sessionStorage.getItem("access_token");
const url = path => `https://api.github.com/${path}`;
const headers = {
  Authorization: `token ${accessToken}`
};

export function Get(path) {
  // 二箇所returnを書かないとPromiseが返らない
  return fetch(url(path), {
    method: "GET",
    mode: "cors",
    headers: headers
  })
    .then(res => res.json())
    .then(payload => {
      return { resp: payload };
    })
    .catch(error => {
      return { error: error };
    });
}

export function Post(path, data) {
  return fetch(url(path), {
    method: "POST",
    headers: headers,
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
