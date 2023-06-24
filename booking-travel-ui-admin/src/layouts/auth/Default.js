// Chakra imports
import { Box, Flex, Icon, Text } from "@chakra-ui/react";
import PropTypes from "prop-types";
import React from "react";
import Footer from "components/footer/FooterAuth";
import FixedPlugin from "components/fixedPlugin/FixedPlugin";
// Custom components
import { NavLink } from "react-router-dom";
// Assets
import { FaChevronLeft } from "react-icons/fa";
import InfoCompany from "./InfoCompany";

function AuthIllustration(props) {
  const { children, illustrationBackground } = props;
  // Chakra color mode
  return (
    <Flex position="relative" h="max-content">
      <Flex
        h={{
          sm: "initial",
          md: "unset",
          lg: "auto",
          xl: "auto",
        }}
        w="100%"
        maxW={{ md: "66%", lg: "1313px" }}
        mx="auto"
        pt={{ sm: "50px", md: "0px" }}
        px={{ lg: "30px", xl: "0px" }}
        ps={{ xl: "70px" }}
        justifyContent="start"
        direction="column"
      >
        <NavLink
          to="/admin"
          style={() => ({
            width: "fit-content",
            marginTop: "40px",
          })}
        ></NavLink>
        {children}
        <InfoCompany />
        <Footer />
      </Flex>
      <FixedPlugin />
    </Flex>
  );
}
// PROPS

AuthIllustration.propTypes = {
  illustrationBackground: PropTypes.string,
  image: PropTypes.any,
};

export default AuthIllustration;
