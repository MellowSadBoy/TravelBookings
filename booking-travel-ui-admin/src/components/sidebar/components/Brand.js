import React from "react";

// Chakra imports
import { Flex, useColorModeValue } from "@chakra-ui/react";

// Custom components
import { HSeparator } from "components/separator/Separator";
import Logo from "components/logo/Logo";

export function SidebarBrand() {
  return (
    <Flex align="center" direction="column">
      <Logo />
      <HSeparator mb="20px" />
    </Flex>
  );
}

export default SidebarBrand;
