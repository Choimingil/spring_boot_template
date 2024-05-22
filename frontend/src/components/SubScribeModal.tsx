import React, { useEffect } from "react";
import { Overlay } from "./OrderModal";
import styled, { keyframes } from "styled-components";
import Character from "../images/character.png";

interface ChildProps {
  modalOpen: React.Dispatch<React.SetStateAction<boolean>>;
  modalStatus: boolean;
}

function SubScribeModal({ modalOpen, modalStatus }: ChildProps) {
  useEffect(() => {
    if (modalStatus) {
      const timer = setTimeout(() => {
        modalOpen(false);
      }, 2000);
      return () => clearTimeout(timer);
    }
  }, [modalStatus]);
  return (
    <Overlay>
      <Modal>
        <img src={Character} alt="character" />
        <p className="hana-regular">구독이 완료되었습니다.</p>
      </Modal>
    </Overlay>
  );
}

export default SubScribeModal;

const Modal = styled.div`
  width: 376px;
  height: 400px;
  border-radius: 10px;
  background-color: white;
  z-index: 3;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  font-size: 22px;
  animation: fadeIn 0.5s forwards;
`;
