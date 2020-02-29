import React, {useEffect} from 'react';
import {useSelector, useDispatch} from 'react-redux';
import PlaylistItems from './PlaylistItems';
import {getPlaylistItems, addPlaylistItem} from '../../../models';

const PlaylistItemsContainer = ({match}) => {
  const dispatch = useDispatch();
  const playlistId = match.params.playlistId;
  const playlistItems = useSelector(state => state.playlistItemsReducer);
  useEffect(() => {
    dispatch(getPlaylistItems(playlistId));
  }, [JSON.stringify(playlistItems)]);

  const _props = {
    ...playlistItems,
    addPlaylistItem: (videoId = '') => {
      dispatch(addPlaylistItem(playlistId, videoId));
    },
  };
  return <PlaylistItems {..._props} />;
};

export default PlaylistItemsContainer;
