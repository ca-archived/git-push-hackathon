import React, {useEffect} from 'react';
import {useSelector, useDispatch} from 'react-redux';
import Playlist from './Playlist';
import {getPlaylists, addPlaylist} from '../../../models';

const PlaylistContainer = () => {
  const dispatch = useDispatch();
  const playlists = useSelector(state => state.playlistReducer);
  useEffect(() => {
    dispatch(getPlaylists());
  }, [JSON.stringify(playlists)]);

  const _props = {
    ...playlists,
    addPlaylist: (title = '') => {
      dispatch(addPlaylist(title));
    },
  };

  return <Playlist {..._props} />;
};

export default PlaylistContainer;
