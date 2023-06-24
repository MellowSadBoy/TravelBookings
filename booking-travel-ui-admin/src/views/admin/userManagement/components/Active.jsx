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
  
  const Active = ({ isOpen, setIsReset, onClose, id,isActive }) => {
    const user = useSelector((state) => state.auth.user);
  
    const [loading,setLoading]=useState(false);
  
    async function onSubmit() {
      if (!user.id) return;
      setLoading(true);
      try {
        if(isActive){
            await httpServ.activeUser(id,user.id);
            onClose();
            setIsReset(true);
            toast.success("Kích hoạt thành công");
            setLoading(false);
        }else{
            await httpServ.inActiveUser(id,user.id);
            onClose();
            setIsReset(true);
            toast.success("Hủy trạng thái thành công");
            setLoading(false);
        }
      } catch (error) {
        onClose();
        setIsReset(true);
        toast.error("Có lỗi xảy ra");
        setLoading(false)
      }
      
    }
  
    return (
      <Modal isOpen={isOpen} onClose={onClose}>
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>
            {isActive ?'Bạn có chắc muốn kích hoạt trạng thái hoạt động cho tài khoản id: ':'Bạn có chắc muốn hủy kích hoạt trạng thái hoạt động cho tài khoản id: '}{id}?
          </ModalHeader>
          <ModalCloseButton />
          <ModalBody>
            <Button
                fontSize="sm"
                color={isActive?"blue.500":"red.500"}
                fontWeight="500"
                w="100%"
                h="50"
                mb="24px"
                type="submit"
                isLoading={loading}
                onClick={()=>onSubmit()}
              >
                {isActive ?'Kích hoạt trạng thái':'Hủy trạng thái'}
              </Button>
            <Button onClick={() => onClose()}>Đóng</Button>
          </ModalBody>
        </ModalContent>
      </Modal>
    );
  };
  
  export default Active;
  