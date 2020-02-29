import Cookies from 'js-cookie';
import {GET_PLAYLISTS, GET_PLAYLIST_ITEMS} from '../actions/constants';

const accessToken = Cookies.get('access_token');
const axios = require('axios');

const client = axios.create({
  baseURL: 'https://www.googleapis.com/youtube/v3',
  timeout: 1000,
  headers: {Authorization: `Bearer ${accessToken}`},
});

export const getPlaylists = () => {
  return async dispatch => {
    try {
      const response = await client.get('/playlists', {
        params: {
          part: 'snippet',
          mine: 'true',
        },
      });
      const {items} = response.data;
      dispatch({
        type: GET_PLAYLISTS,
        payload: {
          playlists: items,
        },
      });

      return items;
    } catch (err) {
      console.log(err);
    }
  };
};

export const getPlaylistItems = (playlistId = '') => {
  return async dispatch => {
    try {
      const response = await client.get('/playlistItems', {
        params: {
          part: 'snippet',
          playlistId: playlistId,
        },
      });
      const {items} = response.data;
      dispatch({
        type: GET_PLAYLIST_ITEMS,
        paload: {
          videos: items,
        },
      });

      return items;
    } catch (err) {
      console.log(err);
    }
  };
};
