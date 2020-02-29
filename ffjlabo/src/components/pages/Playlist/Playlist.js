import React, {useEffect} from 'react';
import {useSelector} from 'react-redux';
import {Link} from 'react-router-dom';

const Playlist = ({playlists}) => {
  return (
    <div>
      {playlists.map(({id, snippet}) => (
        <div key={id}>
          <Link to={`/playlist/${id}`}>
            <img src={snippet.thumbnails.default.url} />
            {snippet.title}
            {snippet.description}
          </Link>
        </div>
      ))}
    </div>
  );
};

export default Playlist;
