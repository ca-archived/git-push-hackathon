import React, {useEffect} from 'react';
import {useSelector} from 'react-redux';

const PlaylistItems = ({playlistItems}) => {
  return (
    <div>
      {playlistItems.map(({id, snippet}) => (
        <div key={id}>
          <img src={snippet.thumbnails.default.url} />
          {snippet.title}
          {snippet.description}
        </div>
      ))}
    </div>
  );
};

export default PlaylistItems;
