import {GET_PLAYLISTS, GET_PLAYLIST_ITEMS} from '../actions/constants';
import {client} from './utils';

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
