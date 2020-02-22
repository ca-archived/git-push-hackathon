import React, { FC, useContext, useCallback, useState } from "react";
import styled from "styled-components";
import { BrandButton } from "@/components/Button";
import { TextField } from "@/components/TextField";
import RootContext from "@/contexts/RootContext";
import { ModalContext } from "@/utils/customHooks/useModal";

export const AddPlaylists: FC = () => {
  const { youtubeStore } = useContext(RootContext);
  const { closeModal } = useContext(ModalContext);
  const [playlistName, setPlaylistName] = useState("");

  const handleAddPlaylistItem = useCallback(() => {
    if (playlistName) {
      youtubeStore.playlistsApi?.insertPlaylists(playlistName);
      setPlaylistName("");
    }
    closeModal();
  }, [playlistName]);

  const handleCancel = useCallback(() => {
    setPlaylistName("");
    closeModal();
  }, []);

  return (
    <Wrapper>
      <Inline>
        <label htmlFor="insertPlaylistsUrlForm">
          プレイリストの名前を入力してください
        </label>
        <TextField
          name="insertPlaylistsUrlForm"
          placeholder="お気に入りMusicリスト"
          value={playlistName}
          onChange={setPlaylistName}
        />
      </Inline>
      <Controlls>
        <BrandButton maxWidth="50%" onClick={handleAddPlaylistItem}>
          追加する
        </BrandButton>
        <BrandButton maxWidth="50%" inverse onClick={handleCancel}>
          キャンセル
        </BrandButton>
      </Controlls>
    </Wrapper>
  );
};

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 90%;
  height: 200px;
  padding: 10px;
  background-color: #fff;
  border-radius: 10px;
`;

const Inline = styled.div`
  width: 100%;
`;

const Controlls = styled.div`
  display: flex;
  flex-direction: row-reverse;
  width: 100%;

  button:first-child {
    margin-left: 5px;
  }
  button:last-child {
    margin-right: 5px;
  }
`;
