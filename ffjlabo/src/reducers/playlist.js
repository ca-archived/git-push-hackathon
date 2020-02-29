const initialState = {
  playlists: [],
};

const playlistReducer = (state = initialState, action) => {
  switch (action.type) {
    case 'GET_PLAYLISTS':
      return {...state, playlists: action.payload.playlists};
    default:
      return {...state};
  }
};

export default playlistReducer;
