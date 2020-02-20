import React, { FC, useEffect, useContext } from "react";
import styled from "styled-components";
import { Redirect } from "react-router-dom";
import { observer } from "mobx-react-lite";
import RootContext from "@/contexts/RootContext";
import { MovieItem } from "@/components/MovieItem";
import { MoviePlayer } from "@/components/MoviePlayer";
import { useQuery } from "@/utils/customHooks/useQuery";

export const PlayerPage: FC = observer(() => {
  const { youtubeStore } = useContext(RootContext);

  const query = useQuery();
  const playlistId = query.get("playlistId");
  if (!playlistId) return <Redirect to="/" />;

  useEffect(() => {
    youtubeStore.fetchPlaylistItems(playlistId);
  }, []);

  return (
    <>
      <MoviePlayer playlistId={playlistId} />
      <Wrapper>
        {youtubeStore.playlistItems.map(({ id, snippet }) => (
          <MovieItem
            title={snippet?.title ?? ""}
            thumbnail={snippet?.thumbnails.standard}
            key={id}
          />
        ))}
      </Wrapper>
    </>
  );
});

const Wrapper = styled.div`
  padding: 15px;
`;
