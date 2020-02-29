import React, {useEffect} from 'react';
import {useSelector, useDispatch} from 'react-redux';
import Playlist from './Playlist';
import {getPlaylists} from '../../../models';

const PlaylistContainer = () => {
  const dispatch = useDispatch();
  const playlists = useSelector(state => state.playlistReducer);
  useEffect(() => {
    dispatch(getPlaylists());
  }, [JSON.stringify(playlists)]);

  return <Playlist {...playlists} />;
};

export default PlaylistContainer;
