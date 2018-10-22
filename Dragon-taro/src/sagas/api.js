const accessToken = sessionStorage.getItem("access_token");
const url = path => `https://api.github.com/${path}`;
const headers = { Authorization: `token ${accessToken}` };

export function Get(path) {
  return fetch(url(path), {
    method: "GET",
    mode: "cors",
    headers: headers
  })
    .then(res => {
      if (!res.ok) throw new Error(res.statusText);
      return res.json();
    })
    .then(resp => ({ resp }))
    .catch(error => ({ error }));
}

export function Send(path, data, method) {
  return fetch(url(path), {
    method: method,
    headers: headers,
    body: JSON.stringify(data)
  })
    .then(res => {
      if (!res.ok) throw new Error(res.statusText);

      return res.json();
    })
    .then(resp => ({ resp }))
    .catch(error => ({ error }));
}
