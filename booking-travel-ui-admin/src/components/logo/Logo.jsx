import { Img } from "@chakra-ui/react";
import React from "react";
import { useSelector } from "react-redux";
import logo from "../../assets/logo/Mellow_reverse.png";
const Logo = () => {
  const info = useSelector((state) => state.global.infoCompany);
  return <Img src={logo} />;
};

export default Logo;
