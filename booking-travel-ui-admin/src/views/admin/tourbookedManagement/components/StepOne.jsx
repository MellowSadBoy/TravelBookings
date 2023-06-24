import {
  Stack,
  Button,
  Flex,
  FormLabel,
  useColorModeValue,
  Icon,
  Box,
  Image,
} from "@chakra-ui/react";
import BasicField from "components/form/BasicField";

import { MdSkipNext } from "react-icons/md";

import React, { useMemo, useState, useEffect } from "react";
import { useForm } from "react-hook-form";
import { toast } from "react-toastify";
import httpServ from "service/httpService";

const AddStepOne = ({
  handleNextStep,
  defaultValues,
  isDisabled,
  isUpdated = false,
  isDetail,
  idUpdate,
  onClose,
  setIsReset,
}) => {
  const textColorTitle = useColorModeValue("light", "brand.400");

  const {
    handleSubmit,
    register,
    formState: { errors, isSubmitting },
  } = useForm({
    defaultValues: useMemo(() => {
      if (!defaultValues) return;
      const {
        tourBooked: {
          id,
          tourId,
          tourName,
          customerName,
          customerEmail,
          customerPhone,
          featuredImgTour,
          quantityAdult,
          quantityChildren,
        },
      } = defaultValues;

      return {
        id,
        tourId,
        tourName,
        customerName,
        customerEmail,
        customerPhone,
        featuredImgTour,
        quantityAdult,
        quantityChildren,
      };
    }, [defaultValues]),
  });

  const [tour, setTour] = useState([]);

  const [loading, setLoading] = useState(true);

  useEffect(() => {
    setLoading(true);
    httpServ
      .getTours()
      .then((res) => {
        const data = res.data || [];
        setTour(data);
        setLoading(false);
      })
      .catch((err) => {
        setTour([]);
        setLoading(false);
      });
  }, []);

  const [imgLink, setImgLink] = useState(
    defaultValues?.tourBooked.featuredImgTour
  );

  async function onSubmit(values) {
    console.log("Values ne ", values);

    const {
      id,
      tourId,
      tourName,
      customerName,
      customerEmail,
      customerPhone,
      featuredImgTour,
      quantityAdult,
      quantityChildren,
    } = values;
    const tourBooked = {
      id,
      tourId,
      tourName,
      customerName,
      customerEmail,
      customerPhone,
      featuredImgTour,
      quantityAdult,
      quantityChildren,
    };
    console.log(
      "🚀 ~ file: StepOne.jsx:89 ~ onSubmit ~ hotelInput:",
      tourBooked
    );

    if (isUpdated) {
      console.log("idUpdate ", idUpdate);

      console.log("tourBooked update ", tourBooked);

      await httpServ.updateTourbooked(idUpdate, tourBooked).then((res) => {
        toast.success("Cập nhật TOURBOOKED thành công");
        onClose();
        setIsReset(true);
      });
    }
  }

  const renderButton = () => {
    if (isUpdated) {
      return (
        <Button
          fontSize="sm"
          variant="brand"
          fontWeight="500"
          width="100%"
          h="50"
          type="submit"
          mt="0px"
          isLoading={isSubmitting}
        >
          Cập nhật
        </Button>
      );
    } else if (isDetail) {
      return <></>;
    } else {
      return (
        <Button
          fontSize="sm"
          variant="brand"
          fontWeight="500"
          width="100%"
          h="50"
          type="submit"
          onClick={() => {
            if (isDisabled) handleNextStep();
          }}
          mt="0px"
          mb="20px"
        >
          Tiếp theo
          <Icon as={MdSkipNext} width="20px" height="20px" color="inherit" />
        </Button>
      );
    }
  };

  const renderTourSelect = () => {
    if (isUpdated) {
      return (
        <BasicField
          placeholder="Chọn tour mới"
          title="Chọn tour mới"
          name="tourId"
          type="SELECT"
          register={register("tourId")}
          errors={errors}
          options={tour}
          isDisabled={isDisabled}
        />
      );
    } else if (isDetail) {
      return (
        <BasicField
          placeholder="Tên tour đã đặt"
          title="Tên tour đã đặt"
          name="tourName"
          type="INPUT"
          typeInput="text"
          register={register("tourName", {
            required: "Trường này là bắt buộc",
          })}
          errors={errors}
          isDisabled={isDisabled}
          marginBottom={"50px"}
        />
      );
    } else {
      return <></>;
    }
  };

  return (
    <form onSubmit={handleSubmit(onSubmit)}>
      <Stack borderRadius="25px" padding="5" marginBottom={"50px"}>
        <Stack marginBottom={"80px"}>
          <FormLabel
            display="flex"
            ms="4px"
            fontSize="larger"
            fontWeight="500"
            color={textColorTitle}
            mb="8px"
          >
            Thông tin tour đã đặt
          </FormLabel>

          {renderTourSelect()}

          <Box boxSize="sm" width="100%" height="auto">
            <Image
              src={imgLink}
              alt=""
              width={"40%"}
              height={"auto"}
              objectFit={"cover"}
              borderRadius={"10px"}
            />
          </Box>
        </Stack>

        <Stack marginTop={"50px"}>
          <FormLabel
            display="flex"
            ms="4px"
            fontSize="larger"
            fontWeight="500"
            color={textColorTitle}
            mb="8px"
          >
            Thông tin khách hàng đặt tour
          </FormLabel>
          <BasicField
            placeholder="Tên"
            title="Tên"
            name="customerName"
            type="INPUT"
            typeInput="text"
            register={register("customerName", {
              required: "Trường này là bắt buộc",
            })}
            errors={errors}
            isDisabled={isDisabled}
          />

          <BasicField
            placeholder="Email"
            title="Email"
            name="customerEmail"
            type="INPUT"
            typeInput="text"
            register={register("customerEmail", {
              required: "Trường này là bắt buộc",
            })}
            errors={errors}
            isDisabled={isDisabled}
          />

          <BasicField
            placeholder="Số điện thoại khách hàng"
            title="Số điện thoại khách hàng"
            name="customerPhone"
            type="INPUT"
            typeInput="text"
            register={register("customerPhone", {
              required: "Trường này là bắt buộc",
            })}
            errors={errors}
            isDisabled={isDisabled}
          />

          <BasicField
            placeholder="Só lượng người lớn"
            title="Só lượng người lớn"
            name="quantityAdult"
            type="INPUT"
            typeInput="text"
            register={register("quantityAdult", {
              required: "Trường này là bắt buộc",
            })}
            errors={errors}
            isDisabled={isDisabled}
          />

          <BasicField
            placeholder="Só lượng trẻ em"
            title="Só lượng trẻ em"
            name="quantityChildren"
            type="INPUT"
            typeInput="text"
            register={register("quantityChildren", {
              required: "Trường này là bắt buộc",
            })}
            errors={errors}
            isDisabled={isDisabled}
          />
        </Stack>
      </Stack>

      <Flex mt={5} justifyContent={"space-between"} alignItems="center">
        {renderButton()}
      </Flex>
    </form>
  );
};

export default AddStepOne;
