import React, { FC, useState, useCallback, createContext } from "react";
import { Modal } from "@/components/Modal";

interface IModalContext {
  isShowingModal: boolean;
  openModal: () => void;
  closeModal: () => void;
  toggleModal: () => void;
}

export const ModalContext = createContext({} as IModalContext);

export const ModalProvider: FC = ({ children }) => {
  const modal = useModal();

  return (
    <ModalContext.Provider value={modal}>
      {children}
      <Modal />
    </ModalContext.Provider>
  );
};

const useModal = () => {
  const [isShowingModal, setIsShowingModal] = useState(false);

  const openModal = useCallback(() => {
    // scroll disabled
    document.body.style.overflow = "hidden";
    setIsShowingModal(true);
  }, []);

  const closeModal = useCallback(() => {
    // scroll enabled
    document.body.style.overflow = "unset";
    setIsShowingModal(false);
  }, []);

  const toggleModal = useCallback(() => {
    setIsShowingModal(!isShowingModal);
  }, []);

  return {
    isShowingModal,
    openModal,
    closeModal,
    toggleModal
  };
};
