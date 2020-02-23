import React, { FC, useCallback, useEffect, useContext } from "react";
import styled from "styled-components";
import { Link } from "react-router-dom";
import { observer } from "mobx-react-lite";
import RootContext from "@/contexts/RootContext";
import { Playlist } from "@/components/Playlist";
import { BrandButton } from "@/components/Button";
import { ModalContext } from "@/utils/customHooks/useModal";
import { AddPlaylists } from "@/components/AddPlaylists";

export const TopPage: FC = observer(() => {
  const { youtubeStore } = useContext(RootContext);
  const { openModal, setContent } = useContext(ModalContext);

  useEffect(() => {
    youtubeStore.fetchPlaylists();
  }, []);

  const insertAddPlaylists2Modal = useCallback(() => {
    setContent(<AddPlaylists />);
    openModal();
  }, []);

  return (
    <>
      <Wrapper>
        {youtubeStore.playlists.map(({ id, snippet }) => (
          <Link to={`/player?playlistId=${id}`} key={id}>
            <Playlist
              title={snippet?.title ?? ""}
              thumbnail={snippet?.thumbnails.standard}
              playlistId={id}
            />
          </Link>
        ))}
      </Wrapper>
      <ButtonWrapper>
        <BrandButton inverse maxWidth="80%" onClick={insertAddPlaylists2Modal}>
          プレイリストを追加する
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
