import {
  Box,
  Button,
  Text,
  useColorModeValue,
  useDisclosure,
} from "@chakra-ui/react";
import Card from "components/card/Card.js";
import ModalWrap from "components/modal/ModalWrap";
import React, { useCallback, useMemo, useState } from "react";
import { useSelector } from "react-redux";
import { getTimeConvert } from "share/utils/functions";
import UpdateInfo from "./UpdateInfo";
import UpdateAddress from "./UpdateAddress";

const RowRender = ({ title, value }) => {
  const textColorPrimary = useColorModeValue("secondaryGray.900", "white");
  const textColorSecondary = "gray.400";
  return (
    <Box display="flex" alignItems="center" margin="10px 0">
      <Text
        fontWeight="800"
        color={textColorSecondary}
        fontSize="md"
        mr={3}
        minWidth="200px"
      >
        {title}
      </Text>
      <Text color={textColorPrimary} fontWeight="500" fontSize="md">
        {value}
      </Text>
    </Box>
  );
};

export default function Information(props) {
  const user = useSelector((state) => state.auth.user);
  const { title, value, setIsReload, token, ...rest } = props;
  const { isOpen, onOpen, onClose } = useDisclosure();
  const bg = useColorModeValue("white", "navy.700");

  const [modal, setModal] = useState();

  const renderModalBody = useCallback(() => {
    switch (modal) {
      case "INFO":
        return (
          <UpdateInfo
            token={token}
            userId={user.id}
            setIsReload={setIsReload}
            onClose={onClose}
          />
        );
      case "ADDRESS":
        return (
          <UpdateAddress
            token={token}
            userId={user.id}
            setIsReload={setIsReload}
            onClose={onClose}
          />
        );
      default:
        break;
    }
  }, [modal, user, setIsReload, onClose, token]);

  const renderModalHead = useCallback(() => {
    switch (modal) {
      case "INFO":
        return <>Cập nhật thông tin</>;
      case "ADDRESS":
        return <>Cập nhật địa chỉ</>;
      default:
        break;
    }
  }, [modal]);

  const {
    username,
    fullName,
    email,
    telephone,
    birthday,
    address: { address, ward, district, province },
    gender,
    description,
    status,
  } = user;
  return (
    <>
      <Card bg={bg} {...rest}>
        <Box display="flex" justifyContent="end">
          <Button
            bg={"blue.400"}
            onClick={() => {
              onOpen();
              setModal("INFO");
            }}
          >
            Cập nhật thông tin
          </Button>
          <Button
            bg={"green.400"}
            onClick={() => {
              onOpen();
              setModal("ADDRESS");
            }}
            ml={5}
          >
            Cập nhật địa chỉ
          </Button>
        </Box>
        <RowRender title={"Email"} value={email} />
        <RowRender title={"Tên"} value={fullName} />
        <RowRender
          title={"Tình trạng tài khoản"}
          value={status === "ACTIVE" ? "Đã kích hoạt" : "Chưa kích hoạt"}
        />
        <RowRender title={"Tên tài khoản"} value={username} />
        <RowRender title={"Mô tả"} value={description} />
        <RowRender title={"Số điện thoại"} value={telephone} />
        <RowRender title={"Ngày sinh"} value={getTimeConvert(birthday)} />
        <RowRender
          title={"Giới tính"}
          value={gender === "MAN" ? "Nam" : "Nữ"}
        />
        <RowRender title={"Địa chỉ"} value={address} />
        <RowRender title={"Tỉnh"} value={province} />
        <RowRender title={"Huyện"} value={district} />
        <RowRender title={"Phường"} value={ward} />
      </Card>
      <ModalWrap
        onClose={onClose}
        isOpen={isOpen}
        onOpen={onOpen}
        header={renderModalHead()}
      >
        {renderModalBody()}
      </ModalWrap>
    </>
  );
}
