import {
  Button,
  Modal,
  ModalBody,
  ModalCloseButton,
  ModalContent,
  ModalHeader,
  ModalOverlay,
  Text,
  Grid,
  Icon,
} from "@chakra-ui/react";
import BasicField from "components/form/BasicField";
import React, { useState } from "react";
import { useForm } from "react-hook-form";
import { useSelector } from "react-redux";
import { toast } from "react-toastify";
import httpServ from "service/httpService";

import { MdCancel, MdDelete } from "react-icons/md";

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
      await httpServ.deleteHotel(idDelete, user.id).then((res) => {
        setIsReset(true);
        toast("Delete hotel successful");
        onClose();
      });
    } catch (error) {
      setMess({
        type: false,
        mess: "Something error",
      });
    }
  }

  return (
    <Modal isOpen={isOpen} onClose={onClose}>
      <ModalOverlay />
      <ModalContent>
        <ModalHeader>
          Are you sure you want to delete this hotel id:{idDelete}?
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

            <Grid templateColumns="repeat(2, 1fr)" gap={6} marginTop={"20px"}>
              <Button
                fontSize="sm"
                fontWeight="500"
                w="100%"
                h="50"
                mb="24px"
                colorScheme="orange"
                ml={2}
                onClick={() => onClose()}
              >
                <Icon
                  as={MdCancel}
                  width="20px"
                  height="20px"
                  color="inherit"
                />
                Cancel
              </Button>
              <Button
                fontSize="sm"
                fontWeight="500"
                w="100%"
                h="50"
                mb="24px"
                type="submit"
                isLoading={isSubmitting}
                colorScheme="red"
              >
                <Icon
                  as={MdDelete}
                  width="20px"
                  height="20px"
                  color="inherit"
                />
                Delete
              </Button>
            </Grid>
          </form>
        </ModalBody>
      </ModalContent>
    </Modal>
  );
};

export default Delete;
