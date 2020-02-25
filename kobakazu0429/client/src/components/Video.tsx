import React, { FC, useCallback, useContext } from "react";
import styled from "styled-components";
import { FiMoreVertical } from "react-icons/fi";
import { ModalContext } from "@/utils/customHooks/useModal";
import { Thumbnails } from "youtube/v3/playlistItems";
import { zIndex } from "@/constants/zIndex";
import { EditPlaylistItem } from "@/components/EditPlaylistItem";

interface Props {
  title: string;
  thumbnail?: Thumbnails["default"];
  videoId: string;
}

const placeholdImage =
  "http://placehold.jp/F0F0F0/8E8E8E/345x260.png?text=No image available";

export const Video: FC<Props> = ({ title, thumbnail, videoId }) => {
  const { openModal, setContent } = useContext(ModalContext);

  const insertEditPlaylistItem2Modal = useCallback(
    (e: React.MouseEvent<SVGElement, MouseEvent>) => {
      e.preventDefault();
      e.stopPropagation();
      setContent(<EditPlaylistItem videoId={videoId} />);
      openModal();
    },
    []
  );

  return (
    <Wrapper>
      <CompactYoutubeThumbnail
        src={thumbnail?.url ?? placeholdImage}
        // TODO: support for IE, FireFox
        // @ts-ignore
        loading="lazy"
      ></CompactYoutubeThumbnail>
      <Inline>
        <Musictitle>{title}</Musictitle>
        <MoreButton onClick={insertEditPlaylistItem2Modal} />
      </Inline>
    </Wrapper>
  );
};

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  height: auto;
  border: 0 solid ${({ theme }) => theme.color.divider};
  border-bottom-width: 1px;
  border-bottom-color: ${({ theme }) => theme.color.divider};
  margin: 20px 0;
  user-select: none;
  z-index: ${zIndex.item};
`;

const CompactYoutubeThumbnail = styled.img`
  width: 100%;
  height: auto;
  /* NOTE: provide thumbnail by youtube api has letterbox the top and bottom of it. */
  clip-path: inset(13% 0 13% 0);
  margin: -12% 0;
  z-index: ${zIndex.item + 1};
  pointer-events: none;
`;

const Musictitle = styled.h3`
  font-size: 1rem;
  line-height: 1.25;
  max-height: 2.5rem;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  text-overflow: ellipsis;
  font-weight: normal;
  user-select: text;
`;

const MoreButton = styled(FiMoreVertical)`
  margin-left: auto;
  margin-right: calc(5px - 16px);
  font-size: 20px;
  padding: 16px;
  line-height: 100%;
  height: 52px;
  width: 52px;
`;

const Inline = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  z-index: ${zIndex.item + 2};
`;
