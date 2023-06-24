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
import StepTwo from "./StepTwo";
import StepSuccess from "./StepSuccess";
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
  const [loading, setLoading] = useState(!isUpdate && !isDetail ? false : true);

  const handleNextStep = useCallback(
    (values) => {
      if (step === 1) {
        !isDetail && setOneValues(values);
        setStep(2);
      }
      if (step === 2) {
        if (isDetail) {
          return;
        }

        setTwoValues(values);

        setLoading(true);

        const dataHotel = {
          ...oneValues,
          roomsInput: values,
        };

        console.log("🚀 ~ file: Modal.js:48 ~ hotelJson:", dataHotel);

        httpServ
          .createHotel(dataHotel)
          .then((res) => {
            setIsReset(true);
            setLoading(false);
            setStep(3);
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
      return <>CẬP NHẬT KHÁCH SẠN</>;
    }
    if (isDetail) {
      return <>CHI TIẾT KHÁCH</>;
    }
    return <>TẠO KHÁCH SẠN</>;
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
      case 2:
        return (
          <StepTwo
            handleNextStep={handleNextStep}
            handleBackStep={handleBackStep}
            defaultValues={twoValues}
            loading={loading}
            isDisabled={isDetail}
            isUpdate={isUpdate}
          />
        );
      case 3:
        return <StepSuccess />;
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
    if (!isOpen) {
      setStep(1);
    }
  }, [isOpen]);

  useEffect(() => {
    if (!isDetail && !isUpdate) return;
    setLoading(true);
    httpServ.getHotel(idDetail ? idDetail : idUpdate).then((res) => {
      const { hotel } = res.data;
      console.log(
        "🚀 ~ file: Modal.js:133 ~ httpServ.getHotel ~ res.data:",
        res.data
      );

      setOneValues({
        hotelInput: res.data,
      });

      console.log(
        "🚀 ~ file: Modal.js:140 ~ httpServ.getHotel ~ hotelInput:",
        hotel
      );

      setLoading(false);
    });
  }, [idDetail, isDetail, isUpdate, idUpdate]);

  return (
    <Modal isOpen={isOpen} onClose={onClose} size={"6xl"}>
      <ModalOverlay />
      <ModalContent>
        <ModalHeader>{renderHeader()}</ModalHeader>
        <ModalHeader>BƯỚC {step}</ModalHeader>
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
          {(step === 3 || isDetail || isUpdate) && (
            <Button colorScheme="blue" mt={10} onClick={() => onClose()}>
              ĐÓNG
            </Button>
          )}
        </ModalBody>
      </ModalContent>
    </Modal>
  );
};

export default DModal;
