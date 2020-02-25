import axios from 'axios'

const services = {
  /* ==== GET REQUESTS ==== */
  getOneVideo(token: string, videoID: string) {
    const params = {
      part: 'snippet',
      type: 'video',
      access_token: token,
      id: videoID
    }
    return axios.get('/api/search', { params })
  },
  getRelatedVideos(token: string, videoID: string) {
    const params = {
      part: 'snippet',
      type: 'video',
      access_token: token,
      relatedToVideoId: videoID
    }
    return axios.get('/api/search', { params })
  },
  getOwnPlaylists(token: string) {
    const params = {
      access_token: token,
      part: 'snippet',
      mine: true,
      maxResults: 50
    }
    return axios.get('/api/playlists', { params })
  },
  getChannelPlaylists(token: string, channelId: string) {
    const params = {
      part: 'snippet',
      access_token: token,
      channelId,
      maxResults: 50
    }
    return axios.get('/api/playlists', { params })
  },
  getPlaylistItems(token: string, playlistId: string) {
    const params = {
      part: 'snippet',
      access_token: token,
      playlistId,
      // pageToken: params.pageToken,
      maxResults: 30
    }
    console.log(params)
    return axios.get('/api/playlistItems', { params })
  },
  /* ==== POST REQUESTS ==== */
  postPlaylist(token: string, params: any) {
    const paramaters = {
      access_token: token,
      part: 'snippet',
      snippet: {
        title: params.title,
        description: params.description
      },
      status: {
        privacyStatus: 'private'
      }
    }
    return axios.post('/api/playlists', { paramaters })
  }
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
