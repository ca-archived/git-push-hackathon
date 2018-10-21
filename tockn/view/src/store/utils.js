export const request = function (method, url, accessToken) {
  let reqObj = {
    method: method,
    url: url
  }
  if (accessToken !== '') {
    reqObj.headers = {'Authorization': `bearer ${accessToken}`}
  }
  return reqObj
}
