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
        hotelBooked: {
          id,
          hotelId,
          hotelName,
          customerName,
          customerEmail,
          customerPhone,
          featuredImgHotel,
          quantityPeople,
          room,
        },
      } = defaultValues;

      return {
        id,
        hotelId,
        hotelName,
        customerName,
        customerEmail,
        customerPhone,
        featuredImgHotel,
        quantityPeople,
        roomId: room?.id
      };
    }, [defaultValues]),
  });

  const [hotel, setHotel] = useState([]);

  const [loading, setLoading] = useState(true);

  useEffect(() => {
    setLoading(true);
    httpServ
      .getHotels()
      .then((res) => {
        const data = res.data || [];
        setHotel(data);
        setLoading(false);
      })
      .catch((err) => {
        setHotel([]);
        setLoading(false);
      });
  }, []);

  const [imgLink, setImgLink] = useState(
    defaultValues?.hotelBooked.featuredImgHotel
  );

  async function onSubmit(values) {
    console.log("Values ne ", values);

    const {
      customerEmail,
      customerName,
      customerPhone,
      note,
      quantityPeople,
      roomId,
      salePrice_amount,
      currencyCode_priceStandard,
    } = values;

    const hotelBooked = {
      customerEmail: customerEmail,
      customerName: customerName,
      customerPhone: customerPhone,
      note: note,
      quantityPeople: quantityPeople,
      roomId: roomId,
      salePrice: {
        amount: salePrice_amount,
        currencyCode: currencyCode_priceStandard,
      },
    };

    console.log(
      "🚀 ~ file: StepOne.jsx:89 ~ onSubmit ~ hotelInput:",
      hotelBooked
    );

    if (isUpdated) {
      console.log("idUpdate ", idUpdate);
      console.log("hotelBooked update ", hotelBooked);

      await httpServ.updateHotelbooked(idUpdate, hotelBooked).then((res) => {
        toast.success("Cập nhật khách sạn đã đặt thành công");
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

  const renderHotelSelect = () => {
    if (isUpdated) {
      return (
        <BasicField
          placeholder="Chọn khách sạn mới"
          title="Chọn khách sạn mới"
          name="hotelId"
          type="SELECT"
          register={register("hotelId")}
          errors={errors}
          options={hotel}
          isDisabled={isDisabled}
        />
      );
    } else if (isDetail) {
      return (
        <BasicField
          placeholder="Tên khách sạn đã đặt"
          title="Tên khách sạn đã đặt"
          name="hotelName"
          type="INPUT"
          typeInput="text"
          register={register("hotelName", {
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
            Thông tin khách sạn đã đặt
          </FormLabel>

          {renderHotelSelect()}

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
            Thông tin khách hàng đặt khách sạn
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
            placeholder="Tên khách hàng"
            title="Tên khách hàng"
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
            placeholder="Só lượng người"
            title="Só lượng người"
            name="quantityPeople"
            type="INPUT"
            typeInput="text"
            register={register("quantityPeople", {
              required: "Trường này là bắt buộc",
            })}
            errors={errors}
            isDisabled={isDisabled}
          />

          <BasicField
            placeholder="Mã phòng"
            title="Mã phòng"
            name="roomId"
            type="INPUT"
            register={register("roomId", {
              required: "This is required",
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
