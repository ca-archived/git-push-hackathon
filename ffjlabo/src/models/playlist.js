import {GET_PLAYLISTS, ADD_PLAYLIST} from '../actions/constants';
import {client} from './utils';

export const getPlaylists = () => {
  return async dispatch => {
    try {
      const response = await client.get('/playlists', {
        params: {
          mine: 'true',
          maxResults: 50,
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

export const addPlaylist = (title = '') => {
  return async dispatch => {
    try {
      const response = await client.post('/playlists', {
        snippet: {title},
      });
      const insertedPlaylist = response.data;
      dispatch({
        type: ADD_PLAYLIST,
        payload: {
          insertedPlaylist,
        },
      });

      return insertedPlaylist;
    } catch (err) {
      console.log(err);
    }
  };
};
