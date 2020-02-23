import React, { FC, useEffect, useContext, useCallback } from "react";
import styled from "styled-components";
import { Redirect } from "react-router-dom";
import { observer } from "mobx-react-lite";
import RootContext from "@/contexts/RootContext";
import { Video } from "@/components/Video";
import { MoviePlayer } from "@/components/MoviePlayer";
import { BrandButton } from "@/components/Button";
import { AddPlaylistItem } from "@/components/AddPlaylistItem";
import { useQuery } from "@/utils/customHooks/useQuery";
import { ModalContext } from "@/utils/customHooks/useModal";

export const PlayerPage: FC = observer(() => {
  const { youtubeStore } = useContext(RootContext);
  const { openModal, setContent } = useContext(ModalContext);
  const query = useQuery();
  const playlistId = query.get("playlistId");
  if (!playlistId) return <Redirect to="/" />;

  useEffect(() => {
    youtubeStore.fetchPlaylistItems(playlistId);
  }, [playlistId, youtubeStore.playlistItems]);

  const insertAddPlaylistItem2Modal = useCallback(() => {
    setContent(<AddPlaylistItem />);
    openModal();
  }, []);

  return (
    <>
      <MoviePlayer playlistId={playlistId} />
      <Wrapper>
        {youtubeStore.playlistItems.map(({ id, snippet }) => (
          <Video
            title={snippet?.title ?? ""}
            thumbnail={snippet?.thumbnails.standard}
            videoId={id}
            key={id}
          />
        ))}
      </Wrapper>
      <ButtonWrapper>
        <BrandButton
          inverse
          maxWidth="80%"
          onClick={insertAddPlaylistItem2Modal}
        >
          動画をプレイリストに追加する
        </BrandButton>
      </ButtonWrapper>
    </>
  );
});

const Wrapper = styled.div`
  padding: 15px;
`;

const ButtonWrapper = styled.div`
  button {
    margin: 0 auto;
  }
`;
