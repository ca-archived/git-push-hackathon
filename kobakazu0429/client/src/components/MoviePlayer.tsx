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

type PlayingState =
  | "playingForStart"
  | "waitForStart"
  | "playing"
  | "end"
  | undefined;

interface PlayerState extends Pick<ReactPlayerProps, "playing" | "muted"> {
  index: number;
  isdisplay: boolean;
  playingState?: PlayingState;
}

let playingState: PlayingState = "playingForStart";

export const MoviePlayer: FC<Props> = ({ playlistId }) => {
  const { youtubeStore, timeStore } = useContext(RootContext);
  const refPlayerA = useRef<ReactPlayer>(null);
  const refPlayerB = useRef<ReactPlayer>(null);

  const [playerAState, setPlayerAState] = useState<PlayerState>({
    index: initializeIndex,
    isdisplay: true
  });
  const [playerBState, setPlayerBState] = useState<PlayerState>({
    index: initializeIndex + 1,
    isdisplay: true
  });

  const nextVideoA = useCallback(() => {
    const player = refPlayerA.current?.getInternalPlayer("player") as
      | Player
      | undefined;
    if (!player) return;
    player.playVideoAt(playerBState.index + 1);
  }, [refPlayerA.current, playerBState]);

  const nextVideoB = useCallback(() => {
    const player = refPlayerB.current?.getInternalPlayer("player") as
      | Player
      | undefined;
    if (!player) return;
    player.playVideoAt(playerAState.index + 1);
  }, [refPlayerB.current, playerAState]);

  const handleTimePlayerA = (state: OnProgress) => {
    const player = refPlayerA.current?.getInternalPlayer("player") as Player;
    const playlistIndex = player.getPlaylistIndex();
    const videoId = youtubeStore.playlistItems[playlistIndex].id;
    const targetVideo = timeStore.videos.find(
      v => v.youtubeVideoId === videoId
    );

    if (playingState === "playingForStart") {
      if (targetVideo?.start && playlistIndex === 0) {
        playingState = "playing";
        refPlayerA.current?.seekTo(targetVideo.start, "seconds");
      } else if (
        targetVideo?.start &&
        targetVideo.start <= state.playedSeconds
      ) {
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

  const handleTimePlayerB = (_state: OnProgress) => {};

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
      <PlayerB
        url={`https://youtube.com/playlist?list=${playlistId}`}
        width="100%"
        height="40vh"
        ref={refPlayerB}
        playing={playerBState.playing}
        progressInterval={progressInterval}
        isdisplay={playerBState.isdisplay.toString()}
        onProgress={handleTimePlayerB}
        muted={!playerBState.isdisplay}
      />
      <div>
        <button onClick={nextVideoA}>buttonA</button>
        <button onClick={nextVideoB}>buttonB</button>
      </div>
    </>
  );
};

const PlayerA = styled(ReactPlayer)<{ isdisplay: boolean }>`
  display: ${({ isdisplay }) => (isdisplay ? "block" : "none")};
`;

const PlayerB = styled(ReactPlayer)<{ isdisplay: boolean }>`
  display: ${({ isdisplay }) => (isdisplay ? "block" : "none")};
`;
