import {GET_PLAYLIST_ITEMS} from '../actions/constants';
import {client} from './utils';

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
