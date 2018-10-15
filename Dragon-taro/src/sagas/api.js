export function Get(path) {
  const url = `https://api.github.com/${path}`;
  const accessToken = sessionStorage.getItem("access_token");
  const headers = {
    Authorization: `token ${accessToken}`
  };

  // 二箇所returnを書かないとPromiseが返らない
  return fetch(url, {
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
