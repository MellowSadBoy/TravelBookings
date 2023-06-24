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

  const [mess, setMess] = useState({
    type: true,
    mess: "",
  });
  const {
    handleSubmit,
    register,
    formState: { errors, isSubmitting },
  } = useForm();

  async function onSubmit(values) {
    if (!user.id) return;
    try {
      await httpServ.deleteTour(idDelete, user.id, values.note).then((res) => {
        setIsReset(true);
        toast("Delete tour successful");
        onClose();
      });
    } catch (error) {
      setMess({
        type: false,
        mess: "Something error",
      });
    }
  }

  const handleDelete = () => {
    if (!user.id) return;
    httpServ.deleteTour(idDelete, user.id, "2123").then((res) => {});
  };

  return (
    <Modal isOpen={isOpen} onClose={onClose}>
      <ModalOverlay />
      <ModalContent>
        <ModalHeader>
          Are you sure you want to delete this tour id:{idDelete}?
        </ModalHeader>
        <ModalCloseButton />
        <ModalBody>
          <form onSubmit={handleSubmit(onSubmit)}>
            <BasicField
              placeholder="Note"
              title="Note"
              name="note"
              type="INPUT"
              typeInput="text"
              register={register("note", {
                required: "This is required",
              })}
              errors={errors}
            />
            {!mess.type && mess.mess ? (
              <Text color="red.500" mb="8px">
                {mess.mess}
              </Text>
            ) : (
              <></>
            )}
            {mess.type && mess.mess ? (
              <Text color="blue.500" mb="8px">
                {mess.mess}
              </Text>
            ) : (
              <></>
            )}
            <Button
              fontSize="sm"
              color="red.500"
              fontWeight="500"
              w="100%"
              h="50"
              mb="24px"
              type="submit"
              isLoading={isSubmitting}
            >
              Delete
            </Button>
          </form>
          <Button onClick={() => onClose()}>cancer</Button>
        </ModalBody>
      </ModalContent>
    </Modal>
  );
};

export default Delete;
