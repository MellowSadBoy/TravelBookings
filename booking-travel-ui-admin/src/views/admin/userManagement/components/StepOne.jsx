import {
  Button,
  Flex,
  FormLabel,
  Image,
  Input,
  Stack,
  Text,
  useColorModeValue,
} from "@chakra-ui/react";
import BasicField from "components/form/BasicField";
import Loading from "components/loading/Loading";
import Location from "components/locationOptions/Location";
import React, { useCallback, useMemo, useState } from "react";
import { useForm } from "react-hook-form";
import { useDispatch } from "react-redux";
import { toast } from "react-toastify";
import httpServ from "service/httpService";
import { GenderTypes } from "share/data/data";
import { uploadImageFB } from "share/utils/functions";
import { getDateTime } from "share/utils/functions";
import { getTimeInputFormat } from "share/utils/functions";
import { isFileImage } from "share/utils/functions";
import { encodeBase64 } from "share/utils/functions";
import { isValidEmail } from "share/utils/functions";

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
  const textColor = useColorModeValue("secondaryGray.900", "white");
  const {
    handleSubmit,
    register,
    formState: { errors, isSubmitting, watch },
  } = useForm({
    defaultValues: useMemo(() => {
      if (!defaultValues) return;
      const {
        email,
        username,
        fullName,
        description,
        birthday,
        gender,
        telephone,
        address,
        password,
      } = defaultValues;

      return {
        email,
        username,
        fullName,
        description,
        birthday: getTimeInputFormat(birthday),
        gender,
        telephone,
        address: address?.address || {},
        password,
      };
    }, [defaultValues]),
  });
  const [location, setLocation] = useState({});
  const [mess, setMess] = useState({
    type: true,
    mess: "",
  });
  const [loadingMainImg, setLoadingMainImg] = useState(false);
  const [imgLink, setImgLink] = useState(defaultValues?.imageUrl || "");
  console.log(imgLink);

  async function onSubmit(values) {
    const {
      email,
      username,
      fullName,
      description,
      birthday,
      gender,
      telephone,
      address,
      password,
    } = values;
    if (isUpdated) {
      const baseInfo = {
        username,
        fullName,
        gender,
        telephone,
        description,
        imageUrl:imgLink,
        birthday:getDateTime(birthday),
      };
      const baseAddress={
        address,
        countryCode: "VN",
        ...location,
      }
      try {
        await httpServ
        .updateInfo(idUpdate, baseInfo)
        await httpServ
        .updateAddress(idUpdate, baseAddress);
        toast.success("Cập nhật thành công");
        setIsReset(true);
        onClose();
      } catch (error) {
        toast.success("Có lỗi xảy ra");
      }
    } else {
      const finalData = {
        password: encodeBase64(password),
        roleType: "ADMIN",
        user: {
          address: {
            address,
            countryCode: "VN",
            ...location,
          },
          birthday,
          byUser: null,
          description,
          email,
          fullName,
          gender,
          imageUrl: "string",
          serviceType: "NORMALLY",
          telephone,
          username,
        },
      };
      try {
        const { data } = await httpServ.signUp(finalData);
        const { code, userId } = data.data;
        if (code && userId) {
          toast.success("Tạo quản trị viên thành công");
          setIsReset(true);
          onClose();
        } else {
          toast.success("Có lỗi xảy ra");
        }
      } catch (error) {
        toast.success("Có lỗi xảy ra");
      }
    }
  }

  const addMainImg = async (e) => {
    setLoadingMainImg(true);
    const file = e.target.files[0];
    if (!file) return;
    const isImg = isFileImage(file);
    if (isImg) {
      const imgUrl = await uploadImageFB(file, idUpdate);
      setImgLink(imgUrl);
      e.target.value = null;
      setLoadingMainImg(false);
    } else {
      toast.error("File được chọn phải là ảnh");
      e.target.value = null;
      setLoadingMainImg(false);
    }
  };
  const onChangeLocation = useCallback((province, district, ward) => {
    setLocation({
      countryCode: "VN",
      district: district.name,
      districtCode: district.code,
      district_child_id: district.child_id,
      province: province.name,
      provinceCode: province.code,
      province_child_id: province.child_id,
      ward: ward.name,
      wardCode: ward.code,
    });
  }, []);

  return (
    <form onSubmit={handleSubmit(onSubmit)}>
      <FormLabel
        ms="4px"
        fontSize="sm"
        fontWeight="500"
        color={textColor}
        display="flex"
      >
        Ảnh chính
      </FormLabel>
      <Flex justifyContent="center">
        <Image
          loading={<Loading />}
          src={imgLink}
          width="200px"
          height="200px"
          borderRadius="20px"
        />
      </Flex>
      <Input id="main_img" hidden type="file" onChange={addMainImg} />
      {!isDetail && (
        <Flex justifyContent="center">
          <Button
            as="label"
            htmlFor={!loadingMainImg ? "main_img" : ""}
            cursor="pointer"
            isLoading={loadingMainImg}
          >
            Thêm ảnh đại diện
          </Button>
        </Flex>
      )}
      <BasicField
        placeholder="Email"
        title="Email"
        name="email"
        type="INPUT"
        register={register("email", {
          required: "This is required",
          validate: {
            isEmail: (v) => isValidEmail(v) || "This field must be an email",
          },
        })}
        errors={errors}
        isDisabled={isDisabled || isUpdated}
      />
      <BasicField
        placeholder="User name"
        title="User name"
        name="username"
        type="INPUT"
        register={register("username", {
          required: "This is required",
        })}
        errors={errors}
        isDisabled={isDisabled}
      />
      <BasicField
        placeholder="Full name"
        title="Full name"
        name="fullName"
        type="INPUT"
        register={register("fullName", {
          required: "This is required",
        })}
        errors={errors}
        isDisabled={isDisabled}
      />
      <BasicField
        placeholder="Description"
        title="Description"
        name="description"
        type="TEXTAREA"
        register={register("description", {
          required: "This is required",
        })}
        errors={errors}
        isDisabled={isDisabled}
      />
      <BasicField
        placeholder="Your birthday"
        title="Birthday"
        name="birthday"
        type="INPUT"
        typeInput="date"
        register={register("birthday", {
          required: "This is required",
        })}
        errors={errors}
        isDisabled={isDisabled}
      />
      <BasicField
        placeholder="Gender"
        title="Gender"
        name="gender"
        type="RADIO"
        register={register("gender")}
        errors={errors}
        options={GenderTypes}
        isDisabled={isDisabled}
      />
      <BasicField
        placeholder="Telephone"
        title="Telephone"
        name="telephone"
        type="INPUT"
        register={register("telephone", {
          required: "This is required",
        })}
        errors={errors}
        isDisabled={isDisabled}
      />
      <Stack>
        <Location
          onChange={onChangeLocation}
          isDisabled={isDisabled}
          defaultValues={isDetail || isUpdated ? defaultValues.address : null}
          showDefault={isDetail || isUpdated}
        />
        <BasicField
          placeholder="Address"
          title="Address"
          name="address"
          type="INPUT"
          typeInput="text"
          register={register("address", {
            required: "This is required",
          })}
          errors={errors}
          isDisabled={isDisabled}
        />
      </Stack>
      {!isDetail && !isUpdated && (
        <>
          <BasicField
            placeholder="Password"
            title="Password"
            name="password"
            type="INPUT"
            typeInput="password"
            register={register("password")}
            errors={errors}
          />
          <BasicField
            placeholder="Confirm password"
            title="Confirm password"
            name="cf_password"
            type="INPUT"
            typeInput="password"
            register={register("cf_password", {
              required: "This is required",
              validate: {
                isMatchPw: (v) => {
                  if (watch("password") !== v) {
                    return "Your passwords do no match";
                  }
                },
              },
            })}
            errors={errors}
          />
        </>
      )}
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
      {!isDetail && (
        <Button
          fontSize="sm"
          variant="brand"
          fontWeight="500"
          w="100%"
          h="50"
          mb="24px"
          type="submit"
          isLoading={isSubmitting}
        >
          {isUpdated ? "Cập nhật" : "Tạo quản trị viên"}
        </Button>
      )}
    </form>
  );
};

export default AddStepOne;
