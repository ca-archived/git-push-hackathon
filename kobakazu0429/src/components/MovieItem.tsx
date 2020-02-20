import React, { FC } from "react";
import styled from "styled-components";
import { Thumbnails } from "youtube/v3/playlistItems";

interface Props {
  title: string;
  thumbnail?: Thumbnails["default"];
}

const placeholdImage =
  "http://placehold.jp/F0F0F0/8E8E8E/345x260.png?text=No image available";

export const MovieItem: FC<Props> = ({ title, thumbnail }) => {
  return (
    <Wrapper>
      <CompactYoutubeThumbnail
        src={thumbnail?.url ?? placeholdImage}
        // TODO: support for IE, FireFox
        // @ts-ignore
        loading="lazy"
      ></CompactYoutubeThumbnail>
      <Musictitle>{title}</Musictitle>
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
`;

const CompactYoutubeThumbnail = styled.img`
  width: 100%;
  height: auto;
  /* NOTE: provide thumbnail by youtube api has letterbox the top and bottom of it. */
  clip-path: inset(13% 0 13% 0);
  margin: -12% 0;
`;

const Musictitle = styled.h3`
  font-size: 1rem;
  line-height: 1.25;
  max-height: 2.5rem;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  text-overflow: ellipsis;
  font-weight: normal;
`;
