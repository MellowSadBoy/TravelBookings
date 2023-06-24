import {
  Avatar,
  Box,
  Button,
  Input,
  Text,
  useColorModeValue,
} from "@chakra-ui/react";
import Card from "components/card/Card.js";
import React, { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { isFileImage } from "share/utils/functions";
import { toast } from "react-toastify";
import { uploadImageFB } from "share/utils/functions";
import httpServ from "service/httpService";
import { setUser } from "store/AuthSlice/AuthSlice";

export default function Banner(props) {
  const dispatch = useDispatch();
  const user = useSelector((state) => state.auth.user);
  const { banner, setIsReload } = props;
  const textColorPrimary = useColorModeValue("secondaryGray.900", "white");
  const textColorSecondary = "gray.400";
  const borderColor = useColorModeValue(
    "white !important",
    "#111C44 !important"
  );
  const [loading, setLoading] = useState(false);
  const { imageUrl, fullName, email, id } = user;
  const handleUpdateImg = async (e) => {
    if (loading) return;
    setLoading(true);
    const file = e.target.files[0];
    if (!file) return;
    const isImg = isFileImage(file);
    if (isImg) {
      const imgUrl = await uploadImageFB(file, id);
      if (imgUrl) {
        await httpServ.updateAvatar(id, imgUrl);
        setIsReload(true);
        toast.success("Cập nhật ảnh đại diện thành công");
        e.target.value = null;
        setLoading(false);
      } else {
        toast.error("Có lỗi xảy ra, vui lòng thử lại");
        e.target.value = null;
        setLoading(false);
      }
      e.target.value = null;
    } else {
      toast.error("File được chọn phải là ảnh");
      e.target.value = null;
      setLoading(false);
    }
  };

  return (
    <Card mb={{ base: "0px", lg: "20px" }} align="center">
      <Box
        bg={`url(${banner})`}
        bgSize="cover"
        borderRadius="16px"
        h="131px"
        w="100%"
      />
      <Avatar
        mx="auto"
        src={imageUrl}
        h="87px"
        w="87px"
        mt="-43px"
        border="4px solid"
        borderColor={borderColor}
      />
      <Box>
        <Button
          as="label"
          cursor="pointer"
          htmlFor={`${loading ? "" : "avatar_update"}`}
          bg={"blue.500"}
          mt={5}
          mb={5}
          isLoading={loading}
        >
          Cập nhật ảnh đại diện
        </Button>
        <Input
          type="file"
          hidden
          id="avatar_update"
          onChange={handleUpdateImg}
        />
      </Box>
      <Text color={textColorPrimary} fontWeight="bold" fontSize="xl" mt="10px">
        {fullName}
      </Text>
      <Text color={textColorSecondary} fontSize="sm">
        {email}
      </Text>
    </Card>
  );
}
