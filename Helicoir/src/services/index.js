import axios from 'axios'

const services = {
  /* ==== GET REQUESTS ==== */
  getRelatedVideos(videoID) {
    const params = {
      part: 'snippet',
      type: 'video',
      access_token: token,
      relatedToVideoId: videoID
    }
    return axios.get('/api/search', { params })
  },
  getOwnPlaylists(token) {
    const params = {
      access_token: token,
      part: 'snippet',
      mine: true,
      maxResults: 50
    }
    return axios.get('/api/playlists', { params })
  },
  getChannelPlaylists(channelId, token) {
    const params = {
      part: 'snippet',
      access_token: token,
      channelId,
      pageToken,
      maxResults: 50
    }
    return axios.get('/api/playlists', { params })
  },
  getPlaylistItems(token, paramater) {
    const params = {
      part: 'snippet',
      access_token: token,
      playlistId: paramater.id,
      // pageToken: params.pageToken,
      maxResults: 30
    }
    console.log(params)
    return axios.get('/api/playlistItems', { params })
  }
  /* ==== POST REQUESTS ==== */
  /* ==== AUTHENTICATION ==== */
  // authMethod() {
  //   const params = {
  //     client_id:
  //       '278812716718-0c8fieggnimq47pmo1ucepfc3855apae.apps.googleusercontent.com',
  //     redirect_uri: 'http://localhost:5884',
  //     response_type: 'token',
  //     scope: 'https://www.googleapis.com/auth/youtube'
  //   }
  //   $router.push(
  //     `https://accounts.google.com/o/oauth2/auth?client_id=${client_id}&redirect_uri=${redirect_uri}&response_type=${response_type}&scope=${scope}`,
  //     '_blank'
  //   )
  // }
}

export default services
