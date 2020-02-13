import axios from 'axios'

export const getRelatedVideos = (videoID) => {
  const relatedVideoParams = {
    part: 'snippet',
    type: 'video',
    key: process.env.API_KEY,
    relatedToVideoId: videoID
  }
  return axios.get('https://www.googleapis.com/youtube/v3/search', {
    // https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=UCOefINa2_BmpuX4BbHjdk9A&key=APIキー
    params: relatedVideoParams
  })
}
