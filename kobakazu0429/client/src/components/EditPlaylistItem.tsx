import React, { FC, useState, useContext, useCallback } from "react";
import styled from "styled-components";
import { FiTrash2, FiSave } from "react-icons/fi";
import RootContext from "@/contexts/RootContext";
import { BrandButton, WarningButton } from "@/components/Button";
import { TextField } from "@/components/TextField";
import { ModalContext } from "@/utils/customHooks/useModal";

interface Props {
  videoId: string;
}

export const EditPlaylistItem: FC<Props> = ({ videoId }) => {
  const { youtubeStore, timeStore } = useContext(RootContext);
  const { closeModal } = useContext(ModalContext);

  const [start, setStart] = useState("");
  const [end, setEnd] = useState("");

  const saveVideo = useCallback(() => {
    const numberStart = Number(start);
    const numberEnd = Number(end);

    if (numberStart < 0 || numberEnd < 0) return;
    if (numberStart > numberEnd) return;
    timeStore.editVideo({
      youtubeVideoId: videoId,
      start: numberStart,
      end: numberEnd
    });
    closeModal();
  }, [start, end, videoId]);

  const deleteVideo = useCallback(() => {
    youtubeStore.deletePlaylistItem(videoId);
    closeModal();
  }, [videoId, youtubeStore.playlistItems]);

  return (
    <Wrapper>
      <TimesWrapper>
        <TextField
          type="number"
          placeholder="start [s]"
          value={start}
          onChange={setStart}
        />
        <TextField
          type="number"
          placeholder="end [s]"
          value={end}
          onChange={setEnd}
        />
      </TimesWrapper>
      <Controlls>
        <BrandButton maxWidth="50%" onClick={saveVideo}>
          <SavehIcon />
          保存する
        </BrandButton>
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
  height: auto;
  padding: 10px;
  background-color: #fff;
  border-radius: 10px;
`;

const Controlls = styled.div`
  width: 100%;
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

const SavehIcon = styled(FiSave)`
  font-size: 16px;
  vertical-align: text-top;
  margin-right: 6px;
`;

const TimesWrapper = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;
