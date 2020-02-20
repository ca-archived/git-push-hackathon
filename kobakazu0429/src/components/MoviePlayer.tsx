import React, { FC } from "react";
import ReactPlayer from "react-player";

interface Props {
  playlistId: string;
}

export const MoviePlayer: FC<Props> = ({ playlistId }) => {
  return (
    <ReactPlayer
      url={`https://youtube.com/playlist?list=${playlistId}`}
      width="100%"
      height="40vh"
    />
  );
};
