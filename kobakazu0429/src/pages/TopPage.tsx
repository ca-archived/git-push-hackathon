import React, { FC, useEffect, useContext } from "react";
import styled from "styled-components";
import { Link } from "react-router-dom";
import { observer } from "mobx-react-lite";
import RootContext from "@/contexts/RootContext";
import { MovieItem } from "@/components/MovieItem";

export const TopPage: FC = observer(() => {
  const { youtubeStore } = useContext(RootContext);

  useEffect(() => {
    youtubeStore.fetchPlaylists();
  }, []);

  useEffect(() => {
    youtubeStore.fetchPlaylistItems(youtubeStore?.playlists[1]?.id ?? "");
  }, [youtubeStore.playlists]);

  return (
    <Wrapper>
      {youtubeStore.playlists.map(({ id, snippet }) => (
        <Link to={`/player?playlistId=${id}`} key={id}>
          <MovieItem
            title={snippet?.title ?? ""}
            thumbnail={snippet?.thumbnails.standard}
          />
        </Link>
      ))}
    </Wrapper>
  );
});

const Wrapper = styled.div`
  padding: 15px;
`;
