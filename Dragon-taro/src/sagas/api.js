const accessToken = sessionStorage.getItem("access_token");
const url = path => `https://api.github.com/${path}`;
const headers = { Authorization: `token ${accessToken}` };
const isRequireBody = method => method == "POST" || method == "PATCH";

function options(method, data) {
  return isRequireBody(method)
    ? {
        method: method,
        headers: headers,
        body: JSON.stringify(data)
      }
    : {
        method: method,
        headers: headers
      };
}

function api(path, method = "GET", data = "null") {
  return fetch(url(path), options(method, data))
    .then(res => {
      if (!res.ok) throw new Error(res.statusText);

      return res.json();
    })
    .then(resp => ({ resp }))
    .catch(error => ({ error }));
}

export default api;
