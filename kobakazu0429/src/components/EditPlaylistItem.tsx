import React, { FC, useContext, useCallback } from "react";
import styled from "styled-components";
import { FiTrash2 } from "react-icons/fi";
import { BrandButton, WarningButton } from "@/components/Button";

import RootContext from "@/contexts/RootContext";
import { ModalContext } from "@/utils/customHooks/useModal";

interface Props {
  videoId: string;
}

export const EditPlaylistItem: FC<Props> = ({ videoId }) => {
  const { youtubeStore } = useContext(RootContext);
  const { closeModal } = useContext(ModalContext);

  const deleteVideo = useCallback(() => {
    youtubeStore.deletePlaylistItems(videoId);
    closeModal();
  }, [videoId, youtubeStore.playlistItems]);

  return (
    <Wrapper>
      <Controlls>
        <WarningButton maxWidth="50%" inverse onClick={deleteVideo}>
          <TrashIcon />
          削除する
        </WarningButton>
        <BrandButton maxWidth="50%" inverse onClick={closeModal}>
          キャンセル
        </BrandButton>
      </Controlls>
    </Wrapper>
  );
};

const Wrapper = styled.div`
  width: 90%;
  height: 200px;
  padding: 10px;
  background-color: #fff;
  border-radius: 10px;
`;

const Controlls = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  button {
    margin: 5px 0;
  }
`;

const TrashIcon = styled(FiTrash2)`
  font-size: 16px;
  vertical-align: text-top;
  margin-right: 6px;
`;
