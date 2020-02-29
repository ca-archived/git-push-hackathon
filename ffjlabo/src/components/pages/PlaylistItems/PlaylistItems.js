import React, {useState, useEffect} from 'react';
import {useSelector} from 'react-redux';

const AddPlaylistItemForm = ({addPlaylistItem}) => {
  const [isForm, setIsForm] = useState(false);
  const [videoId, setVideoId] = useState('');

  const handleSubmit = e => {
    e.preventDefault();
    addPlaylistItem(videoId);
  };

  return isForm ? (
    <form onSubmit={handleSubmit}>
      <label>https://www.youtube.com/watch?v=</label>
      <input
        type="text"
        placeholder=""
        onChange={e => {
          setVideoId(e.target.value);
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
      ＋動画を追加する
    </div>
  );
};

const PlaylistItems = ({playlistItems, addPlaylistItem}) => {
  return (
    <div>
      <AddPlaylistItemForm {...{addPlaylistItem}} />
      {playlistItems.map(({id, snippet}) => (
        <div key={id}>
          <img src={snippet.thumbnails.default.url} />
          {snippet.title}
        </div>
      ))}
    </div>
  );
};

export default PlaylistItems;
