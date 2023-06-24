import { Box, Button, Stack } from "@chakra-ui/react";
import BasicField from "components/form/BasicField";
import Loading from "components/loading/Loading";
import Location from "components/locationOptions/Location";
import React, { useCallback, useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { toast } from "react-toastify";
import httpServ from "service/httpService";
const UpdateAddress = ({ token, userId, setIsReload, onClose }) => {
  const {
    handleSubmit,
    register,
    reset,
    formState: { errors, isSubmitting },
  } = useForm();
  const [loading, setLoading] = useState(true);
  const [location, setLocation] = useState({});
  const [dfLocation, setDfLocation] = useState({});

  async function onSubmit(values) {
    await httpServ
      .updateAddress(userId, { ...values, ...location })
      .then((res) => {
        setIsReload(true);
        toast.success("Cập nhật thành công");
        onClose();
      })
      .catch((err) => {
        setIsReload(true);
        toast.error("Có lỗi xảy ra");
        onClose();
      });
  }

  useEffect(() => {
    setLoading(true);
    httpServ.getUserInfoByToken(token).then((res) => {
      const { data } = res;
      const {
        address: {
          address,
          countryCode,
          district,
          districtCode,
          district_child_id,
          province,
          provinceCode,
          province_child_id,
          ward,
          wardCode,
        },
      } = data.data;
      reset({
        address: address,
      });
      const values = {
        district_child_id,
        countryCode,
        district,
        districtCode,
        province_child_id,
        province,
        provinceCode,
        ward,
        wardCode,
      };
      setLocation(values);
      setDfLocation(values);
      setLoading(false);
    });
  }, [reset, token]);

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

  if (loading) {
    return (
      <Box padding="10px 0">
        <Loading />
      </Box>
    );
  }

  return (
    <form onSubmit={handleSubmit(onSubmit)}>
      <Stack>
        <Location
          onChange={onChangeLocation}
          defaultValues={dfLocation}
          showDefault={true}
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
        />
      </Stack>
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
        Cập nhật
      </Button>
    </form>
  );
};

export default UpdateAddress;
