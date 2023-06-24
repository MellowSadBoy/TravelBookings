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
import StepThree from "./StepThree";
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
      return <>CẬP NHẬT TOUR</>;
    }
    if (isDetail) {
      return <>CHI TIẾT TOUR</>;
    }
    return <>TẠO TOUR</>;
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
            isDisabled={isDetail}
          />
        );
      case 3:
        return (
          <StepThree
            handleNextStep={handleNextStep}
            handleBackStep={handleBackStep}
            defaultValues={threeValues}
            loading={loading}
            isDisabled={isDetail}
            isUpdate={isUpdate}
          />
        );
      case 4:
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
    if (!isDetail && !isUpdate) return;
    setLoading(true);
    httpServ.getTour(idDetail ? idDetail : idUpdate).then((res) => {
      const { pricingValues, schedules, tour, vehicle } = res.data;
      if (pricingValues) {
        setThreeValues(pricingValues);
      }
      if (schedules) {
        setTwoValues(schedules);
      }
      setOneValues({
        tourInput: tour ? tour : {},
        vehicleInput: vehicle ? vehicle : {},
      });
      setLoading(false);
    });
  }, [idDetail, isDetail, isUpdate, idUpdate]);

  return (
    <Modal isOpen={isOpen} onClose={onClose}>
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
          {(step === 4 || isDetail || isUpdate) && (
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
