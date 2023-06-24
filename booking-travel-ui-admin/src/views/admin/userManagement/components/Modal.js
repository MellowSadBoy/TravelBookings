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
  Spinner,
  WrapItem,
} from "@chakra-ui/react";
import React, { useCallback, useEffect, useState } from "react";
import StepOne from "./StepOne";
import httpServ from "service/httpService";
import { toast } from "react-toastify";

const DModal = ({
  isOpen,
  onClose,
  isUpdate = false,
  isDetail = false,
  idUpdate,
  idDetail,
  setIsReset,
}) => {
  const [step, setStep] = useState(1);
  const [oneValues, setOneValues] = useState(null);
  const [twoValues, setTwoValues] = useState([]);
  const [threeValues, setThreeValues] = useState([]);
  const [loading, setLoading] = useState(!isUpdate && !isDetail ? false : true);

  const handleNextStep = useCallback(
    (values) => {
      if (step === 1) {
        !isDetail && setOneValues(values);
        setStep(2);
      }
      if (step === 2) {
        !isDetail && setTwoValues(values);
        setStep(3);
      }
      if (step === 3) {
        if (isDetail) {
          return;
        }
        setLoading(true);
        httpServ
          .createTour({
            ...oneValues,
            scheduleInput: twoValues,
            pricingValues: values,
          })
          .then((res) => {
            setIsReset(true);
            setLoading(false);
            setStep(4);
          })
          .catch((err) => {
            toast.error("Có lỗi xảy ra");
            setLoading(false);
          });
      }
    },
    [step, oneValues, twoValues, isDetail, setIsReset]
  );

  const handleBackStep = useCallback(() => {
    if (step > 1) {
      setStep(step - 1);
    }
  }, [step]);

  const renderHeader = () => {
    if (isUpdate) {
      return <>CẬP NHẬT</>;
    }
    if (isDetail) {
      return <>CHI TIẾT</>;
    }
    return <>TẠO QUẢN TRỊ VIÊN</>;
  };

  const renderBody = () => {
    switch (step) {
      case 1:
        return (
          <StepOne
            handleNextStep={handleNextStep}
            defaultValues={oneValues}
            isDisabled={isDetail}
            isDetail={isDetail}
            isUpdated={isUpdate}
            idUpdate={idUpdate}
            setIsReset={setIsReset}
            onClose={onClose}
          />
        );
      default:
        return <></>;
    }
  };

  useEffect(() => {
    return () => {
      setStep(1);
    };
  }, []);

  useEffect(() => {
    if (!isDetail && !isUpdate) return;
    setLoading(true);
    httpServ.getUserInfo(idDetail ? idDetail : idUpdate).then((res) => {
      setOneValues(res.data)
      setLoading(false);
    });
  }, [idDetail, isDetail, isUpdate, idUpdate]);

  return (
    <Modal isOpen={isOpen} onClose={onClose}>
      <ModalOverlay />
      <ModalContent>
        <ModalHeader>{renderHeader()}</ModalHeader>
        <ModalCloseButton />
        <ModalBody>
          {loading ? (
            <Box padding={10}>
              <Flex justifyContent="center">
                <WrapItem>
                  <Spinner />
                </WrapItem>
              </Flex>
            </Box>
          ) : (
            renderBody()
          )}
          <Button colorScheme="blue" onClick={() => onClose()}>
              ĐÓNG
          </Button>
        </ModalBody>
      </ModalContent>
    </Modal>
  );
};

export default DModal;
