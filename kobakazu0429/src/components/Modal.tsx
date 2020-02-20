import React, { FC, useContext } from "react";
import styled from "styled-components";
import { zIndex } from "@/constants/zIndex";
import { ModalContext } from "@/utils/customHooks/useModal";

export const Modal: FC = ({ children }) => {
  const { isShowingModal, closeModal } = useContext(ModalContext);

  return isShowingModal ? (
    <>
      <Wrapper>{children}</Wrapper>
      <BackGround isShowing={isShowingModal} onClick={closeModal} />
    </>
  ) : null;
};

const Wrapper = styled.div`
  width: calc(100% - 50px);
  height: calc(100% - 50px);
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: white;
  transition: opacity ease 500ms;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: ${zIndex.modal};
`;

const BackGround = styled.div<{ isShowing: boolean }>`
  width: 100vw;
  height: 100vh;
  position: absolute;
  top: 0;
  left: 0;
  background-color: black;
  opacity: ${({ isShowing }) => (isShowing ? 0.4 : 0)};
  transition: opacity ease 500ms;
`;
