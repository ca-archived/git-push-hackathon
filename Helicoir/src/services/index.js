import axios from 'axios'

const services = {
  /* ==== GET REQUESTS ==== */
  getRelatedVideos(videoID) {
    const params = {
      part: 'snippet',
      type: 'video',
      key: process.env.API_KEY,
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
  getChannelPlaylists(channelId, pageToken) {
    const params = {
      part: 'snippet',
      key: process.env.API_KEY,
      channelId,
      pageToken,
      maxResults: 50
    }
    return axios.get('/api/playlists', { params })
  },
  getPlaylistItems(playlistId, pageToken) {
    const params = {
      part: 'snippet',
      key: process.env.API_KEY,
      playlistId,
      pageToken,
      maxResults: 30
    }
    return axios.get('/api/playlistItems', { params })
  },
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
