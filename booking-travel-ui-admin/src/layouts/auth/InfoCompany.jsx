import { Box, Flex, Img, Text } from "@chakra-ui/react";
import Logo from "components/logo/Logo";
import React from "react";
import { useSelector } from "react-redux";
import logo from "../../assets/logo/Mellowbookingbg.png";

const InfoCompany = () => {
  const info = useSelector((state) => state.global.infoCompany);
  const { imgLogoUrl, name, description } = info;
  return (
    <Box
      display={{ base: "none", md: "flex" }}
      h="100%"
      minH="100vh"
      w={{ lg: "50vw", "2xl": "44vw" }}
      position="absolute"
      right="0px"
      justifyContent="center"
      alignItems="center"
      flexDirection="column"
    >
      <Img src={logo}  />
      <Text fontSize="5xl" fontWeight="600" ms="2px">
        {name}
      </Text>
      <Text fontSize="4xl" fontWeight="400" ms="2px">
        {description}
      </Text>
    </Box>
  );
};

export default InfoCompany;
