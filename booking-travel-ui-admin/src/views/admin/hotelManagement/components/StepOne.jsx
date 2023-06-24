import {
  Box,
  Button,
  Flex,
  FormLabel,
  Image,
  Input,
  Stack,
  Text,
  Wrap,
  WrapItem,
  useColorModeValue,
  Icon,
} from "@chakra-ui/react";
import BasicField from "components/form/BasicField";
import Location from "components/locationOptions/Location";

import { MdSkipNext } from "react-icons/md";

import React, { useCallback, useMemo, useState } from "react";
import { useForm } from "react-hook-form";
import { toast } from "react-toastify";
import httpServ from "service/httpService";
import { guidGenerator } from "share/utils/functions";
import { isFileImage } from "share/utils/functions";
import { uploadImageFB } from "share/utils/functions";
import { RankHotelType } from "share/data/data";

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
  const textColor = useColorModeValue("secondaryGray.900", "white");
  const brandStars = useColorModeValue("brand.500", "brand.400");

  const {
    handleSubmit,
    register,
    formState: { errors, isSubmitting },
  } = useForm({
    defaultValues: useMemo(() => {
      if (!defaultValues) return;
      const {
        hotelInput: { address, imgUrls, name, type, fax },
      } = defaultValues;

      return {
        detail_begin_address: address?.address,
        imgUrls,
        name,
        type,
        fax,
      };
    }, [defaultValues]),
  });

  const [images, setImages] = useState(defaultValues?.hotelInput.imgUrls || []);
  const [begin, setBegin] = useState({});
  const [loadingSubImg, setLoadingSubImg] = useState(false);

  async function onSubmit(values) {
    console.log("Values ne ", values);
    console.log(
      "🚀 ~ file: StepOne.jsx:79 ~ onSubmit ~ isImgsError:",
      isImgsError
    );
    if (!isImgsError) {
      const { detail_begin_address, name, type, fax } = values;
      const hotelInput = {
        address: { ...begin, address: detail_begin_address },
        fax: fax,
        imgUrls: images,
        name: name,
        type: type,
      };
      console.log(
        "🚀 ~ file: StepOne.jsx:89 ~ onSubmit ~ hotelInput:",
        hotelInput
      );

      if (isUpdated) {
        console.log("idUpdate ", idUpdate);

        console.log("hotelInput ", hotelInput);

        await httpServ.updateHotel(idUpdate, hotelInput).then((res) => {
          toast.success("Cập nhật thành công khách sạn");
          onClose();
          setIsReset(true);
        });
      } else {
        const data = {
          hotelInput,
        };
        handleNextStep(data);
      }
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

  const handleAddMutilImg = async (e) => {
    setLoadingSubImg(true);
    const file = e.target.files[0];
    if (!file) return;
    const isImg = isFileImage(file);
    if (isImg) {
      const imgUrl = await uploadImageFB(file, guidGenerator());
      setImages((prev) => [...prev, imgUrl]);
      e.target.value = null;
      setLoadingSubImg(false);
    } else {
      toast.error("File được chọn phải là ảnh");
      e.target.value = null;
      setLoadingSubImg(false);
    }
  };

  const isImgsError = useMemo(() => {
    const isError = images.length <= 3;
    return isError;
  }, [images]);

  const onChangeBeginLocation = useCallback((province, district, ward) => {
    setBegin({
      countryCode: "VN",
      district: district.name,
      districtCode: district.code,
      province: province.name,
      provinceCode: province.code,
      ward: ward.name,
      wardCode: ward.code,
      province_child_id: province.child_id,
      district_child_id: district.child_id,
      zip: "0000",
      city: "City",
    });
  }, []);

  return (
    <form onSubmit={handleSubmit(onSubmit)}>
      <Stack borderRadius="25px" padding="5" marginBottom={"50px"}>
        <Stack>
          <FormLabel
            display="flex"
            ms="4px"
            fontSize="larger"
            fontWeight="500"
            color={textColorTitle}
            mb="8px"
          >
            Thông tin khách sạn
          </FormLabel>
          <BasicField
            placeholder="Tên khách sạn"
            title="Tên khách sạn"
            name="name"
            type="INPUT"
            typeInput="text"
            register={register("name", {
              required: "Trường này là bắt buộc",
            })}
            errors={errors}
            isDisabled={isDisabled}
          />
          <BasicField
            placeholder="Số fax"
            title="Số fax"
            name="fax"
            type="INPUT"
            typeInput="text"
            register={register("fax", {
              required: "Trường này là bắt buộc",
            })}
            errors={errors}
            isDisabled={isDisabled}
          />

          <BasicField
            placeholder="Hạng sao"
            title="Hạng sao"
            name="type"
            type="SELECT"
            register={register("type")}
            errors={errors}
            options={RankHotelType}
            isDisabled={isDisabled}
          />

          <Stack
            borderRadius={"8px"}
            border={"1px solid #E2E8F0"}
            padding={"20px"}
          >
            <FormLabel
              ms="4px"
              fontSize="sm"
              fontWeight="500"
              color={textColor}
              display="flex"
            >
              Ảnh mô tả<Text color={brandStars}>*</Text> ({images.length}/4)
            </FormLabel>
            {isImgsError && !isDetail ? (
              <Text color="red" mb="8px">
                Cần ít nhất 4 ảnh
              </Text>
            ) : (
              <></>
            )}
            <Wrap mb="10px">
              {images.map((img, idx) => {
                return (
                  <WrapItem key={idx} borderRadius={"10px"}>
                    <Stack display="flex" alignItems="center">
                      <Box boxSize="sm" maxWidth="200px" height="100px">
                        <Image
                          src={img}
                          alt=""
                          width={"200px"}
                          height={"100px"}
                          objectFit={"cover"}
                          borderRadius={"10px"}
                        />
                      </Box>
                      {!isDisabled && (
                        <Button
                          fontSize="sm"
                          variant="red.700"
                          fontWeight="500"
                          w="5"
                          h="5"
                          background="red.500"
                          onClick={() => {
                            const imgs = [...images];
                            imgs.splice(idx, 1);
                            setImages(imgs);
                          }}
                        >
                          -
                        </Button>
                      )}
                    </Stack>
                  </WrapItem>
                );
              })}
            </Wrap>
            {!isDisabled && (
              <Stack display="flex" flexDirection="row" alignItems="center">
                <Input
                  id="sub_img"
                  hidden
                  fontSize="sm"
                  ms={{ base: "0px", md: "0px" }}
                  type="file"
                  placeholder="Image link"
                  fontWeight="500"
                  size="lg"
                  flex={1}
                  onChange={(e) => handleAddMutilImg(e)}
                />
                <Button
                  as="label"
                  htmlFor={loadingSubImg ? "" : "sub_img"}
                  fontSize="sm"
                  isLoading={loadingSubImg}
                  fontWeight="500"
                  cursor={"pointer"}
                  w="20%"
                  h="50"
                  colorScheme="blue"
                >
                  Thêm ảnh khách sạn
                </Button>
              </Stack>
            )}
          </Stack>
        </Stack>

        <Stack>
          <FormLabel
            display="flex"
            ms="4px"
            fontSize="larger"
            fontWeight="500"
            color={textColorTitle}
            mb="8px"
            mt="80px"
          >
            Địa chỉ khách sạn
          </FormLabel>
          <Location
            onChange={onChangeBeginLocation}
            defaultValues={defaultValues?.hotelInput.address || null}
            isDisabled={isDisabled}
            showDefault={isUpdated || isDetail}
          />
          <BasicField
            placeholder="Chi tiết"
            title="Chi tiết"
            name="detail_begin_address"
            type="INPUT"
            typeInput="text"
            register={register("detail_begin_address", {
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
