import React, {useEffect} from 'react';
import {useSelector, useDispatch} from 'react-redux';
import PlaylistItems from './PlaylistItems';
import {getPlaylistItems} from '../../../models';

const PlaylistItemsContainer = ({match}) => {
  const dispatch = useDispatch();
  const playlistId = match.params.playlistId;
  const playlistItems = useSelector(state => state.playlistItemsReducer);
  useEffect(() => {
    dispatch(getPlaylistItems(playlistId));
  }, [JSON.stringify(playlistItems)]);
  return <PlaylistItems {...playlistItems} />;
};

export default PlaylistItemsContainer;
