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
} from "@chakra-ui/react";
import BasicField from "components/form/BasicField";
import Loading from "components/loading/Loading";
import Location from "components/locationOptions/Location";
import React, {
  useCallback,
  useEffect,
  useMemo,
  useRef,
  useState,
} from "react";
import { useForm } from "react-hook-form";
import { toast } from "react-toastify";
import httpServ from "service/httpService";
import { MoneyTypes } from "share/data/data";
import { TourTypes } from "share/data/data";
import { TimeTourData } from "share/data/data";
import { VehicleTypes } from "share/data/data";
import { guidGenerator } from "share/utils/functions";
import { getTimeInputFormat } from "share/utils/functions";
import { isFileImage } from "share/utils/functions";
import { uploadImageFB } from "share/utils/functions";
import { getDateTime } from "share/utils/functions";
import { getSeats } from "share/utils/functions";

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
        tourInput: {
          addressStart,
          description,
          discount,
          featureImgUrl,
          hotelId,
          imgUrls,
          location,
          name,
          note,
          priceStandard,
          quantityVisit,
          startDate,
          tax,
          timeHowLong,
          type,
        },
        vehicleInput: { automaker, code, name: vehicleName, vehicleType },
      } = defaultValues;

      return {
        auto_maker: automaker,
        currencyCode_priceStandard: priceStandard?.currencyCode,
        currencyCode_tax: tax?.currencyCode,
        description,
        detail_begin_address: addressStart?.address,
        detail_end_address: location?.address,
        discount,
        featureImgUrl,
        hotelId,
        name,
        note,
        priceStandard_amount: priceStandard?.amount,
        quantityVisit,
        startDate: getTimeInputFormat(startDate),
        tax_amount: tax?.amount,
        timeHowLong,
        type,
        vehicle_code: code,
        vehicle_name: vehicleName,
        vehicle_type: vehicleType,
      };
    }, [defaultValues]),
  });

  const [images, setImages] = useState(defaultValues?.tourInput.imgUrls || []);
  const [imgLink, setImgLink] = useState(defaultValues?.tourInput.featureImgUrl);
  const [begin, setBegin] = useState({});
  const [end, setEnd] = useState({});
  const [hotels, setHotels] = useState([]);
  const [loading, setLoading] = useState(true);
  const [loadingMainImg, setLoadingMainImg] = useState(false);
  const [loadingSubImg, setLoadingSubImg] = useState(false);

  async function onSubmit(values) {
    if (!isImgsError) {
      const {
        auto_maker,
        currencyCode_priceStandard,
        currencyCode_tax,
        description,
        detail_begin_address,
        detail_end_address,
        discount,
        hotelId,
        name,
        note,
        priceStandard_amount,
        quantityVisit,
        startDate,
        tax_amount,
        timeHowLong,
        type,
        vehicle_code,
        vehicle_name,
        vehicle_type,
      } = values;
      const tourInput = {
        addressStart: { ...begin, address: detail_begin_address },
        description: description,
        discount: discount,
        featureImgUrl: imgLink,
        hotelId: hotelId,
        imgUrls: images,
        location: {
          ...end,
          address: detail_end_address,
        },
        name: name,
        note: note,
        priceStandard: {
          amount: priceStandard_amount,
          currencyCode: currencyCode_priceStandard,
        },
        quantityVisit: quantityVisit,
        startDate: getDateTime(startDate),
        tax: {
          amount: tax_amount,
          currencyCode: currencyCode_tax,
        },
        timeHowLong: timeHowLong,
        type: type,
      };
      if (isUpdated) {
        await httpServ.updateTour(idUpdate, tourInput).then((res) => {
          toast.success("Update tour successful");
          onClose();
          setIsReset(true);
        });
      } else {
        const data = {
          tourInput,
          vehicleInput: {
            automaker: auto_maker,
            code: vehicle_code,
            name: vehicle_name,
            numberOfSeats: getSeats(vehicle_type),
            vehicleType: vehicle_type,
          },
        };
        handleNextStep(data);
      }
    }
  }

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
    });
  }, []);

  const onChangeEndLocation = useCallback((province, district, ward) => {
    setEnd({
      countryCode: "VN",
      district: district.name,
      districtCode: district.code,
      province: province.name,
      provinceCode: province.code,
      ward: ward.name,
      wardCode: ward.code,
      province_child_id: province.child_id,
      district_child_id: district.child_id,
    });
  }, []);

  const addMainImg = async (e) => {
    setLoadingMainImg(true);
    const file = e.target.files[0];
    if (!file) return;
    const isImg = isFileImage(file);
    if (isImg) {
      const imgUrl = await uploadImageFB(file, guidGenerator());
      setImgLink(imgUrl);
      e.target.value = null;
      setLoadingMainImg(false);
    } else {
      toast.error("File được chọn phải là ảnh");
      e.target.value = null;
      setLoadingMainImg(false);
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
    const isError = images.length <= 3 || !imgLink;
    return isError;
  }, [images, imgLink]);

  useEffect(() => {
    setLoading(true);
    httpServ
      .getHotels()
      .then((res) => {
        const data = res.data || [];
        setHotels(data);
        setLoading(false);
      })
      .catch((err) => {
        setHotels([]);
        setLoading(false);
      });
  }, []);
  if (loading) {
    return <Loading />;
  }
  return (
    <form onSubmit={handleSubmit(onSubmit)}>
      <Stack>
        <FormLabel
          display="flex"
          ms="4px"
          fontSize="larger"
          fontWeight="500"
          color={textColorTitle}
          mb="8px"
        >
          Thông tin tour
        </FormLabel>
        <BasicField
          placeholder="Khách sạn"
          title="Khách sạn"
          name="hotelId"
          type="SELECT"
          register={register("hotelId")}
          errors={errors}
          options={hotels}
          isDisabled={isDisabled}
        />
        <BasicField
          placeholder="Tên"
          title="Tên"
          name="name"
          type="INPUT"
          typeInput="text"
          register={register("name", {
            required: "This is required",
          })}
          errors={errors}
          isDisabled={isDisabled}
        />
        <BasicField
          placeholder="Ghi chú"
          title="Ghi chú"
          name="note"
          type="INPUT"
          typeInput="text"
          register={register("note", {
            required: "This is required",
          })}
          errors={errors}
          isDisabled={isDisabled}
        />
        <BasicField
          placeholder="Mô tả"
          title="Mô tả"
          name="description"
          type="TEXTAREA"
          register={register("description", {
            required: "This is required",
          })}
          errors={errors}
          isDisabled={isDisabled}
        />
        <BasicField
          placeholder="Thời gian chuyến đi"
          title="Thời gian chuyến đi"
          name="timeHowLong"
          type="SELECT"
          register={register("timeHowLong")}
          errors={errors}
          options={TimeTourData}
          isDisabled={isDisabled}
        />
        <BasicField
          placeholder="Giờ khởi hành"
          title="Giờ khởi hành"
          name="startDate"
          type="INPUT"
          typeInput="date"
          register={register("startDate", {
            required: "This is required",
          })}
          errors={errors}
          isDisabled={isDisabled}
        />
        <BasicField
          placeholder="Loại tour"
          title="Loại tour"
          name="type"
          type="SELECT"
          register={register("type")}
          errors={errors}
          options={TourTypes}
          isDisabled={isDisabled}
        />
        <FormLabel
          ms="4px"
          fontSize="sm"
          fontWeight="500"
          color={textColor}
          display="flex"
        >
          Giá tiêu chuẩn
        </FormLabel>
        <Stack
          display={"flex"}
          flexDirection="row"
          alignItems="center"
          mb={"24px"}
        >
          <BasicField
            placeholder="Giá"
            title=""
            name="priceStandard_amount"
            type="INPUT"
            typeInput="number"
            register={register("priceStandard_amount", {
              required: "This is required",
            })}
            errors={errors}
            defaultValue={1}
            isDisabled={isDisabled}
          />
          <BasicField
            placeholder=""
            title=""
            name="currencyCode_priceStandard"
            type="SELECT"
            register={register("currencyCode_priceStandard")}
            errors={errors}
            options={MoneyTypes}
            isDisabled={isDisabled}
          />
        </Stack>
        <FormLabel
          ms="4px"
          fontSize="sm"
          fontWeight="500"
          color={textColor}
          display="flex"
        >
          Phí tax
        </FormLabel>
        <Stack
          display={"flex"}
          flexDirection="row"
          alignItems="center"
          mb={"24px"}
        >
          <BasicField
            placeholder="Phí tax"
            title=""
            name="tax_amount"
            type="INPUT"
            typeInput="number"
            register={register("tax_amount", {
              required: "This is required",
            })}
            errors={errors}
            defaultValue={1}
            isDisabled={isDisabled}
          />
          <BasicField
            placeholder=""
            title=""
            name="currencyCode_tax"
            type="SELECT"
            register={register("currencyCode_tax")}
            errors={errors}
            options={MoneyTypes}
            isDisabled={isDisabled}
          />
        </Stack>

        <BasicField
          placeholder="Giảm giá"
          title="Giảm giá"
          name="discount"
          type="INPUT"
          typeInput="number"
          register={register("discount", {
            required: "This is required",
          })}
          errors={errors}
          defaultValue={0}
          isDisabled={isDisabled}
        />


        <BasicField
          placeholder="Số lượng hành khách"
          title="Số lượng hành khách"
          name="quantityVisit"
          type="INPUT"
          typeInput="number"
          register={register("quantityVisit", {
            required: "This is required",
          })}
          errors={errors}
          defaultValue={1}
          isDisabled={isDisabled}
        />


        <FormLabel
          ms="4px"
          fontSize="sm"
          fontWeight="500"
          color={textColor}
          display="flex"
        >
          Ảnh chính
        </FormLabel>


        {isImgsError && !isDetail ? (
          <Text color="red" mb="8px">
            Cần 1 ảnh chính
          </Text>
        ) : (
          <></>
        )}


        <Image
          loading={<Loading />}
          src={isDetail ? defaultValues?.tourInput?.featureImgUrl : imgLink}
        />


        
        <Input id="main_img" hidden type="file" onChange={addMainImg} />
        {!isDetail && (
          <Button
            as="label"
            htmlFor={!loadingMainImg ? "main_img" : ""}
            cursor="pointer"
            isLoading={loadingMainImg}
          >
            Thêm ảnh chính
          </Button>
        )}




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
              <WrapItem key={idx} border="1px solid gray">
                <Stack display="flex" alignItems="center">
                  <Box boxSize="sm" width="100px" height="100px">
                    <Image src={img} alt="" />
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
              bg="blue.300"
              fontWeight="500"
              cursor={"pointer"}
              w="100%"
              h="50"
            >
              Thêm ảnh phụ
            </Button>
          </Stack>
        )}
      </Stack>
      <Stack>
        <FormLabel
          display="flex"
          ms="4px"
          fontSize="larger"
          fontWeight="500"
          color={textColorTitle}
          mb="8px"
        >
          Địa điểm khởi hành
        </FormLabel>
        <Location
          onChange={onChangeBeginLocation}
          defaultValues={defaultValues?.tourInput.addressStart || null}
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
            required: "This is required",
          })}
          errors={errors}
          isDisabled={isDisabled}
        />
      </Stack>
      <Stack>
        <FormLabel
          display="flex"
          ms="4px"
          fontSize="larger"
          fontWeight="500"
          color={textColorTitle}
          mb="8px"
        >
          Địa điểm kết thúc
        </FormLabel>
        <Location
          onChange={onChangeEndLocation}
          defaultValues={defaultValues?.tourInput.location || null}
          isDisabled={isDisabled}
          showDefault={isUpdated || isDetail}
        />
        <BasicField
          placeholder="Chi tiết"
          title="Chi tiết"
          name="detail_end_address"
          type="INPUT"
          typeInput="text"
          register={register("detail_end_address", {
            required: "This is required",
          })}
          errors={errors}
          isDisabled={isDisabled}
        />
      </Stack>
      {!isUpdated && (
        <Stack>
          <FormLabel
            display="flex"
            ms="4px"
            fontSize="larger"
            fontWeight="500"
            color={textColorTitle}
            mb="8px"
          >
            Phương tiện
          </FormLabel>
          <BasicField
            placeholder="Thương hiệu"
            title="Thương hiệu"
            name="auto_maker"
            type="INPUT"
            typeInput="text"
            register={register("auto_maker", {
              required: "This is required",
            })}
            errors={errors}
            isDisabled={isDisabled}
          />
          <BasicField
            placeholder="Mã xe"
            title="Mã xe"
            name="vehicle_code"
            type="INPUT"
            typeInput="text"
            register={register("vehicle_code", {
              required: "This is required",
            })}
            errors={errors}
            isDisabled={isDisabled}
          />
          <BasicField
            placeholder="Tên xe"
            title="Tên xe"
            name="vehicle_name"
            type="INPUT"
            typeInput="text"
            register={register("vehicle_name", {
              required: "This is required",
            })}
            errors={errors}
            isDisabled={isDisabled}
          />
          <BasicField
            placeholder="Loại xe"
            title="Loại xe"
            name="vehicle_type"
            type="SELECT"
            register={register("vehicle_type")}
            errors={errors}
            options={VehicleTypes}
            isDisabled={isDisabled}
          />
        </Stack>
      )}
      <Flex mt={5} justifyContent={"space-between"} alignItems="center">
        {isUpdated ? (
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
        ) : (
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
          >
            Tiếp theo
          </Button>
        )}
      </Flex>
    </form>
  );
};

export default AddStepOne;
