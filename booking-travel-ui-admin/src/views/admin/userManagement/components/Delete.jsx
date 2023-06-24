import {
  Box,
  Button,
  Flex,
  Modal,
  ModalBody,
  ModalCloseButton,
  ModalContent,
  ModalHeader,
  ModalOverlay,
  Text,
} from "@chakra-ui/react";
import BasicField from "components/form/BasicField";
import React, { useState } from "react";
import { useForm } from "react-hook-form";
import { useSelector } from "react-redux";
import { toast } from "react-toastify";
import httpServ from "service/httpService";

const Delete = ({ isOpen, setIsReset, onClose, idDelete }) => {
  const user = useSelector((state) => state.auth.user);

  const [loading,setLoading]=useState(false);

  async function onSubmit() {
    if (!user.id) return;
    setLoading(true);
    try {
      await httpServ.deleteUser(idDelete, user.id).then((res) => {
        setIsReset(true);
        toast.success("Xóa tài khoản thành công");
        onClose();
      });
    } catch (error) {
      toast.error("Có lỗi xảy ra");
      setIsReset(true);
      onClose();
    }
  }

  return (
    <Modal isOpen={isOpen} onClose={onClose}>
      <ModalOverlay />
      <ModalContent>
        <ModalHeader>
          Bạn có chắc muốn xóa tài khoản id:{idDelete}?
        </ModalHeader>
        <ModalCloseButton />
        <ModalBody>
          <Button
              fontSize="sm"
              color="red.500"
              fontWeight="500"
              w="100%"
              h="50"
              mb="24px"
              type="submit"
              isLoading={loading}
              onClick={()=>onSubmit()}
            >
              Xóa
            </Button>
          <Button onClick={() => onClose()}>Đóng</Button>
        </ModalBody>
      </ModalContent>
    </Modal>
  );
};

export default Delete;
