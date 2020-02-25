import React, { FC, useContext } from "react";
import styled from "styled-components";
import { zIndex } from "@/constants/zIndex";
import { ModalContext } from "@/utils/customHooks/useModal";

// NOTE: click background, close modal menu
export const Modal: FC = ({ children }) => {
  const { isShowingModal } = useContext(ModalContext);

  return isShowingModal ? (
    <>
      <Wrapper>{children}</Wrapper>
      <BackGround isShowing={isShowingModal} />
    </>
  ) : null;
};

const Wrapper = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: ${zIndex.modal + 1};
`;

const BackGround = styled.div<{ isShowing: boolean }>`
  position: fixed;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  cursor: pointer;
  background-color: black;
  opacity: ${({ isShowing }) => (isShowing ? 0.4 : 0)};
  transition: opacity ease 500ms;
  z-index: ${zIndex.modal};
`;
