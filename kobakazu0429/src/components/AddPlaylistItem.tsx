import React, { FC, useContext, useCallback, useState } from "react";
import styled from "styled-components";
import { BrandButton } from "@/components/Button";
import { TextField } from "@/components/TextField";
import RootContext from "@/contexts/RootContext";
import { ModalContext } from "@/utils/customHooks/useModal";

export const AddPlaylistItem: FC = () => {
  const { youtubeStore } = useContext(RootContext);
  const { closeModal } = useContext(ModalContext);
  const [url, setUrl] = useState("");

  const handleAddPlaylistItem = useCallback(() => {
    const playlistId = youtubeStore.playlistItems[0].snippet?.playlistId;
    const query = new URL(url).searchParams;
    const videoId = query.get("v");

    if (playlistId && videoId) {
      youtubeStore.playlistItemsApi?.insertPlaylistItems(playlistId, videoId);
      setUrl("");
    }
    closeModal();
  }, [url]);

  const handleCancel = useCallback(() => {
    setUrl("");
    closeModal();
  }, []);

  return (
    <Wrapper>
      <label htmlFor="insertPlaylistItemUrlForm">
        追加したい動画のURLを入力してください
      </label>
      <TextField
        name="insertPlaylistItemUrlForm"
        placeholder="https://youtube.com/watch?v=40dJS_LC6S8"
        value={url}
        onChange={setUrl}
      />
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
  width: 80%;
  height: 200px;
  padding: 10px;
  background-color: #fff;
  border-radius: 10px;
`;

const Controlls = styled.div`
  display: flex;
  flex-direction: row-reverse;

  button:first-child {
    margin-left: 5px;
  }
  button:last-child {
    margin-right: 5px;
  }
`;
