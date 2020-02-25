import React, {
  FC,
  useState,
  useCallback,
  useEffect,
  useContext,
  useRef
} from "react";
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

type PlayingState = "prepare" | "ready" | "playing" | "end";

interface PlayerState extends Pick<ReactPlayerProps, "playing" | "muted"> {
  index: number;
  isdisplay: boolean;
  playingState?: PlayingState;
}

let playingStateA: PlayingState = "prepare";
let playingStateB: PlayingState = "prepare";

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

  useEffect(() => {
    if (!refPlayerA.current) return;
    const player = refPlayerA.current?.getInternalPlayer("player") as Player;
    if (!player) return;
    const playlistIndex = player.getPlaylistIndex();
    const videoId = youtubeStore.playlistItems[playlistIndex].id;
    const targetVideo = timeStore.videos.find(
      v => v.youtubeVideoId === videoId
    );

    if (
      playingStateA === "prepare" &&
      targetVideo?.start &&
      playlistIndex === 0
    ) {
      refPlayerA.current?.seekTo(targetVideo.start, "seconds");
      playingStateA = "ready";
      // player.playVideo();
    }
  }, [timeStore.videos]);

  const handleTimePlayerA = (state: OnProgress) => {
    if (!refPlayerA.current) return;
    const player = refPlayerA.current.getInternalPlayer("player") as Player;
    if (!player) return;
    const playlistIndex = player.getPlaylistIndex();
    const videoId = youtubeStore.playlistItems[playlistIndex].id;
    const targetVideo = timeStore.videos.find(
      v => v.youtubeVideoId === videoId
    );

    if (playingStateA === "prepare" && playlistIndex > 0) {
      playingStateA = "ready";
      if (targetVideo?.start) {
        refPlayerA.current.seekTo(targetVideo.start, "seconds");
        player.pauseVideo();
      }
    }

    if (playingStateA === "playing") {
      if (targetVideo?.end && targetVideo.end <= state.playedSeconds) {
        playingStateA = "end";
        player.stopVideo();
        nextVideoB();
      }
    }
  };

  const handleTimePlayerB = (state: OnProgress) => {
    if (!refPlayerB.current) return;
    const player = refPlayerB.current.getInternalPlayer("player") as Player;
    if (!player) return;
    const playlistIndex = player.getPlaylistIndex();
    const videoId = youtubeStore.playlistItems[playlistIndex].id;
    const targetVideo = timeStore.videos.find(
      v => v.youtubeVideoId === videoId
    );

    if (playingStateB === "prepare" && playlistIndex > 0) {
      playingStateB = "ready";
      if (targetVideo?.start) {
        refPlayerB.current.seekTo(targetVideo.start, "seconds");
        player.pauseVideo();
      }
    }

    if (playingStateB === "playing") {
      if (targetVideo?.end && targetVideo.end <= state.playedSeconds) {
        playingStateB = "end";
        player.pauseVideo();
        nextVideoA();
      }
    }
  };

  return (
    <>
      <ReactPlayer
        url={`https://youtube.com/playlist?list=${playlistId}`}
        width="100%"
        height="40vh"
        controls
        ref={refPlayerA}
        playing={playerAState.playing}
        progressInterval={progressInterval}
        isdisplay={playerAState.isdisplay.toString()}
        onProgress={handleTimePlayerA}
        onPlay={() => (playingStateA = "playing")}
        onEnded={() => (playingStateA = "end")}
        muted={!playerAState.isdisplay}
      />
      <PlayerB
        url={`https://youtube.com/playlist?list=${playlistId}`}
        width="100%"
        height="40vh"
        controls
        ref={refPlayerB}
        playing={playerBState.playing}
        progressInterval={progressInterval}
        isdisplay={playerBState.isdisplay.toString()}
        onProgress={handleTimePlayerB}
        onPlay={() => (playingStateB = "playing")}
        onEnded={() => (playingStateB = "end")}
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
