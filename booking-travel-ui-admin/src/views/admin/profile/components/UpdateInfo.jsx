import { Box, Button, Text } from "@chakra-ui/react";
import BasicField from "components/form/BasicField";
import Loading from "components/loading/Loading";
import React, { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { useSelector } from "react-redux";
import { toast } from "react-toastify";
import httpServ from "service/httpService";
import { GenderTypes } from "share/data/data";
import { getTimeInputFormat } from "share/utils/functions";

const UpdateInfo = ({ token, userId, setIsReload, onClose }) => {
  const {
    handleSubmit,
    register,
    reset,
    formState: { errors, isSubmitting },
    watch,
  } = useForm();
  const [loading, setLoading] = useState(true);
  const [mess, setMess] = useState({
    type: true,
    mess: "",
  });

  async function onSubmit(values) {
    await httpServ
      .updateInfo(userId, values)
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
      const { fullName, gender, telephone, username, description, birthday } =
        data.data;
      reset({
        username,
        fullName,
        gender,
        telephone,
        description,
        birthday: getTimeInputFormat(birthday),
      });
      setLoading(false);
    });
  }, [reset, token]);

  if (loading) {
    return (
      <Box padding="10px 0">
        <Loading />
      </Box>
    );
  }

  return (
    <form onSubmit={handleSubmit(onSubmit)}>
      <BasicField
        placeholder="User name"
        title="User name"
        name="username"
        type="INPUT"
        register={register("username", {
          required: "This is required",
        })}
        errors={errors}
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
      />
      <BasicField
        placeholder="Gender"
        title="Gender"
        name="gender"
        type="RADIO"
        register={register("gender")}
        errors={errors}
        options={GenderTypes}
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
      />
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

export default UpdateInfo;
