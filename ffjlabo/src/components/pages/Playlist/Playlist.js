import React, {useState, useEffect} from 'react';
import {useSelector} from 'react-redux';
import {Link} from 'react-router-dom';

const AddPlaylistForm = ({addPlaylist}) => {
  const [isForm, setIsForm] = useState(false);
  const [text, setText] = useState('');

  const handleSubmit = e => {
    e.preventDefault();
    addPlaylist(text);
  };

  return isForm ? (
    <form onSubmit={handleSubmit}>
      <input
        type="text"
        onChange={e => {
          setText(e.target.value);
        }}
      />
      <button type="submit">追加</button>
      <button
        onClick={e => {
          e.preventDefault();
          setIsForm(false);
        }}
      >
        キャンセル
      </button>
    </form>
  ) : (
    <div
      onClick={() => {
        setIsForm(true);
      }}
    >
      ＋プレイリストを追加する
    </div>
  );
};

const Playlist = ({playlists, addPlaylist}) => {
  return (
    <div>
      <AddPlaylistForm {...{addPlaylist}} />
      {playlists.map(({id, snippet}) => (
        <div key={id}>
          <Link to={`/playlist/${id}`}>
            <img src={snippet.thumbnails.default.url} />
            {snippet.title}
          </Link>
        </div>
      ))}
    </div>
  );
};

export default Playlist;
