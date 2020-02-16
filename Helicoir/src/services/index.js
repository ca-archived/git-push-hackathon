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
  getOwnPlaylists(pageToken) {
    const params = {
      part: 'snippet',
      key: process.env.API_KEY,
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
  }
  /* ==== POST REQUESTS ==== */
}
export default services
