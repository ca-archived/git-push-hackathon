import React, { FC, useState, useCallback, useContext, useRef } from "react";
import ReactPlayer, { ReactPlayerProps } from "react-player";
import styled from "styled-components";
import RootContext from "@/contexts/RootContext";

interface Props {
  playlistId: string;
}

interface Player {
  playVideo: () => void;
  pauseVideo: () => void;
  nextVideo: () => void;
  previousVideo: () => void;
  playVideoAt: (index: number) => void;
  getPlaylistIndex: () => number;
}

// type PlayerType = "A" | "B";
// const playerCount = 2;
const initializeIndex = 0;
const progressInterval = 5; // unit: [ms]

interface OnProgress {
  played: number;
  playedSeconds: number;
  loaded: number;
  loadedSeconds: number;
}

type PlayingState = "playingForStart" | "waitForStart" | "playing" | "end";

interface PlayerState extends Pick<ReactPlayerProps, "playing" | "muted"> {
  index: number;
  isdisplay: boolean;
  playingState?: PlayingState;
}

let playingState: PlayingState = "playingForStart";

export const MoviePlayer: FC<Props> = ({ playlistId }) => {
  const { youtubeStore, timeStore } = useContext(RootContext);

  const [playerAState, setPlayerAState] = useState<PlayerState>({
    index: initializeIndex,
    isdisplay: true
  });

  const refPlayerA = useRef<ReactPlayer>(null);

  const handleTimePlayerA = (state: OnProgress) => {
    const player = refPlayerA.current?.getInternalPlayer("player") as Player;
    const playlistIndex = player.getPlaylistIndex();
    const videoId = youtubeStore.playlistItems[playlistIndex].id;
    const targetVideo = timeStore.videos.find(
      v => v.youtubeVideoId === videoId
    );

    if (playingState === "playingForStart") {
      if (targetVideo?.start && targetVideo.start <= state.playedSeconds) {
        playingState = "playing";
        player.pauseVideo();
      }
    } else if (playingState === "playing") {
      if (targetVideo?.end && targetVideo.end <= state.playedSeconds) {
        playingState = undefined;
        player.pauseVideo();
      }
    }
  };

  return (
    <>
      <PlayerA
        url={`https://youtube.com/playlist?list=${playlistId}`}
        width="100%"
        height="40vh"
        ref={refPlayerA}
        playing={playerAState.playing}
        progressInterval={progressInterval}
        isdisplay={playerAState.isdisplay.toString()}
        onProgress={handleTimePlayerA}
        muted={!playerAState.isdisplay}
      />
    </>
  );
};

const PlayerA = styled(ReactPlayer)``;
