import React, {useEffect} from 'react';
import {useSelector} from 'react-redux';

const Playlist = ({playlists}) => {
  return (
    <div>
      {playlists.map(({id, snippet}) => (
        <div key={id}>
          <img src={snippet.thumbnails.default.url} />
          {snippet.title}
          {snippet.description}
        </div>
      ))}
    </div>
  );
};

export default Playlist;
