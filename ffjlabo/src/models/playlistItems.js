import {GET_PLAYLIST_ITEMS, ADD_PLAYLIST_ITEM} from '../actions/constants';
import {client} from './utils';

export const getPlaylistItems = (playlistId = '') => {
  return async dispatch => {
    try {
      const response = await client.get('/playlistItems', {
        params: {playlistId},
      });
      const {items} = response.data;
      dispatch({
        type: GET_PLAYLIST_ITEMS,
        payload: {
          playlistItems: items,
        },
      });

      return items;
    } catch (err) {
      console.log(err);
    }
  };
};

export const addPlaylistItem = (playlistId = '', videoId = '') => {
  return async dispatch => {
    try {
      const response = await client.post('/playlistItems', {
        snippet: {playlistId, resourceId: {kind: 'youtube#video', videoId}},
      });
      const insertedPlaylistItem = response.data;
      dispatch({
        type: ADD_PLAYLIST_ITEM,
        payload: {
          insertedPlaylistItem,
        },
      });

      return insertedPlaylistItem;
    } catch (err) {
      console.log(err);
    }
  };
};
