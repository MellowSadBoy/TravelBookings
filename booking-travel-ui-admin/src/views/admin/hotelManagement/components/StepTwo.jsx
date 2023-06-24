import {
  Box,
  Button,
  Flex,
  FormLabel,
  Stack,
  Text,
  Grid,
  useColorModeValue,
  Table,
  Thead,
  Tbody,
  Tr,
  Th,
  Td,
  Icon,
  Image,
  Input,
  Wrap,
  WrapItem,
} from "@chakra-ui/react";
import BasicField from "components/form/BasicField";
import React, { useState, useMemo } from "react";
import { useForm } from "react-hook-form";
import { toast } from "react-toastify";

import { isFileImage } from "share/utils/functions";

import { MoneyTypes } from "share/data/data";
import { RoomType } from "share/data/data";

import { uploadImageFB } from "share/utils/functions";

import { guidGenerator } from "share/utils/functions";
import { Vacant } from "share/data/data";

import { MdDelete, MdArrowBack, MdDone } from "react-icons/md";

const AddStepTwo = ({
  handleNextStep,
  handleBackStep,
  defaultValues,
  isDisabled,
  isDetail,
}) => {
  const textColorTitle = useColorModeValue("light", "brand.400");
  const textColor = useColorModeValue("secondaryGray.900", "white");
  const brandStars = useColorModeValue("brand.500", "brand.400");

  const [finalValues, setFinalValues] = useState(defaultValues);

  const [loadingSubImg, setLoadingSubImg] = useState(false);
  const [imagesRoom, setImagesRoom] = useState([]);
  const {
    handleSubmit,
    register,
    reset,
    formState: { errors },
  } = useForm();

  function onSubmit(values) {
    console.log("values neeee ", values);
    if (isDisabled) return;
    const data = {
      discount: values.discount_room,
      maxPeople: values.max_people_room,
      numberRoom: values.name_room,
      price: {
        amount: values.price_amount,
        currencyCode: values.currency_code,
      },
      type: values.type_room,
      vacant: values.vacant,
      imgUrls: imagesRoom,
    };

    console.log("finalValues: ", finalValues);

    setFinalValues((prev) => [...prev, data]);

    console.log("finalValues: ", finalValues);
    reset();
  }

  const handleAddMutilImg = async (e) => {
    setLoadingSubImg(true);
    const file = e.target.files[0];
    if (!file) return;
    const isImg = isFileImage(file);
    if (isImg) {
      const imgUrl = await uploadImageFB(file, guidGenerator());
      setImagesRoom((prev) => [...prev, imgUrl]);
      e.target.value = null;
      setLoadingSubImg(false);
    } else {
      toast.error("File được chọn phải là ảnh");
      e.target.value = null;
      setLoadingSubImg(false);
    }
  };

  const isImgsError = useMemo(() => {
    const isError = imagesRoom.length <= 3;
    return isError;
  }, [imagesRoom]);

  return (
    <>
      <form onSubmit={handleSubmit(onSubmit)}>
        <Stack borderRadius="25px" padding="5" marginBottom={"50px"}>
          <FormLabel
            display="flex"
            mb={3}
            ms="4px"
            fontSize="larger"
            fontWeight="500"
            color={textColorTitle}
          >
            Thêm thông tin phòng
          </FormLabel>
          <Grid templateColumns="repeat(2, 1fr)" gap={6}>
            <BasicField
              placeholder="Số phòng"
              title="Số phòng"
              name="name_room"
              type="INPUT"
              typeInput="text"
              register={register("name_room", {
                required: "Trường này là bắt buộc",
              })}
              errors={errors}
            />

            <BasicField
              placeholder="Loại phòng"
              title="Loại phòng"
              name="type_room"
              type="SELECT"
              register={register("type_room")}
              errors={errors}
              options={RoomType}
            />

            <BasicField
              placeholder="Số người tối đa"
              title="Số người tối đa"
              name="max_people_room"
              type="INPUT"
              typeInput="text"
              register={register("max_people_room", {
                required: "Trường này là bắt buộc",
              })}
              errors={errors}
            />

            <BasicField
              placeholder="Trạng thái phòng"
              title="Trạng thái phòng"
              name="vacant"
              type="SELECT"
              register={register("vacant")}
              errors={errors}
              options={Vacant}
            />

            <BasicField
              placeholder="Tỉ lệ giảm giá"
              title="Tỉ lệ giảm giá"
              name="discount_room"
              type="INPUT"
              typeInput="number"
              register={register("discount_room", {
                required: "Trường này là bắt buộc",
              })}
              errors={errors}
            />

            <BasicField
              placeholder="Giá"
              title="Giá"
              name="price_amount"
              type="INPUT"
              typeInput="number"
              register={register("price_amount", {
                required: "Trường này là bắt buộc",
              })}
              errors={errors}
            />

            <BasicField
              placeholder="Đơn vị tiền tệ"
              title="Đơn vị tiền tệ"
              name="currency_code"
              type="SELECT"
              register={register("currency_code")}
              errors={errors}
              options={MoneyTypes}
            />
          </Grid>

          <FormLabel
            ms="4px"
            fontSize="sm"
            fontWeight="500"
            color={textColor}
            display="flex"
          >
            Ảnh mô tả<Text color={brandStars}>*</Text> ({imagesRoom.length}/4)
          </FormLabel>
          {isImgsError && !isDetail ? (
            <Text color="red" mb="8px">
              Cần ít nhất 4 ảnh
            </Text>
          ) : (
            <></>
          )}
          <Wrap mb="10px">
            {imagesRoom.map((img, idx) => {
              return (
                <WrapItem key={idx} border="1px solid gray">
                  <Stack display="flex" alignItems="center">
                    <Box boxSize="sm" width="100px" height="100px">
                      <Image
                        src={img}
                        alt=""
                        width={"200px"}
                        height={"100px"}
                        objectFit={"cover"}
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
                          const imgs = [...imagesRoom];
                          imgs.splice(idx, 1);
                          setImagesRoom(imgs);
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
                id="sub_img_room"
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
                htmlFor={loadingSubImg ? "" : "sub_img_room"}
                fontSize="sm"
                isLoading={loadingSubImg}
                fontWeight="500"
                cursor={"pointer"}
                w="40%"
                h="50"
                colorScheme="blue"
              >
                Thêm ảnh khách sạn
              </Button>
            </Stack>
          )}

          {!isDisabled && (
            <Button type="submit" colorScheme="blue" marginBottom={"70px"}>
              Thêm phòng
            </Button>
          )}
        </Stack>
      </form>

      <Stack
        border={"1px solid #b4b9bf"}
        borderRadius="25px"
        padding="5"
        marginBottom={"50px"}
      >
        <FormLabel
          display="flex"
          mb={3}
          ms="4px"
          fontSize="larger"
          fontWeight="500"
          color={textColorTitle}
        >
          Danh sách phòng
        </FormLabel>
        <Table>
          <Thead>
            <Tr>
              <Th padding="5px" fontSize="11px">
                Số phòng
              </Th>
              <Th padding="5px" fontSize="11px">
                Ảnh phòng
              </Th>
              <Th padding="5px" fontSize="11px">
                Loại phòng
              </Th>

              <Th padding="5px" fontSize="11px">
                Tỉ lệ giảm giá
              </Th>
              <Th padding="5px" fontSize="11px">
                Giá
              </Th>
              <Th padding="5px" fontSize="11px">
                Đơn vị tiền tệ
              </Th>
              <Th padding="5px" fontSize="11px">
                Trạng thái
              </Th>
              <Th padding="5px" fontSize="11px">
                Chức năng
              </Th>
            </Tr>
          </Thead>
          <Tbody>
            {finalValues.map((row, idx) => {
              return (
                <Tr fontSize="15px" key={idx}>
                  <Td padding="5px">{row.numberRoom}</Td>
                  <Td padding="5px">
                    <Image
                      src={row.imgUrls[0]}
                      alt=""
                      width={"200px"}
                      height={"100px"}
                      objectFit={"cover"}
                    />
                  </Td>
                  <Td padding="5px">{row.type}</Td>
                  <Td padding="5px">{row.discount}</Td>
                  <Td padding="5px">{row.price.amount}</Td>
                  <Td padding="5px">{row.price.currencyCode}</Td>
                  <Td padding="5px">{row.vacant}</Td>
                  <Td padding="5px">
                    <Flex align="start" flexDirection="column">
                      <Flex>
                        {!isDisabled && (
                          <Button
                            fontSize="sm"
                            variant="red"
                            fontWeight="500"
                            color="red"
                            w="20"
                            h="10"
                            mt="4px"
                            onClick={() => {
                              const clValues = [...finalValues];
                              clValues.splice(idx, 1);
                              setFinalValues(clValues);
                            }}
                          >
                            Delete
                            <Icon as={MdDelete} width="15px" height="15px" />
                          </Button>
                        )}
                      </Flex>
                    </Flex>
                  </Td>
                </Tr>
              );
            })}
          </Tbody>
        </Table>
      </Stack>

      <Grid templateColumns="repeat(2, 1fr)" gap={6} marginTop={"20px"}>
        <Button
          fontWeight="500"
          w="100%"
          h="50"
          colorScheme="orange"
          onClick={() => handleBackStep()}
        >
          <Icon as={MdArrowBack} width="20px" height="20px" color="inherit" />
          Trở lại
        </Button>
        <Button
          fontWeight="500"
          mb="24px"
          type="submit"
          w="100%"
          h="50"
          variant="brand"
          onClick={() => {
            if (isDisabled) {
              handleNextStep();
            } else {
              if (finalValues.length <= 0) {
                toast.error("At least one room");
                return;
              }

              console.log("finalValues 407", finalValues);
              handleNextStep(finalValues);
            }
          }}
        >
          <Icon as={MdDone} width="20px" height="20px" color="inherit" />
          Hoàn thành
        </Button>
      </Grid>
    </>
  );
};

export default AddStepTwo;
