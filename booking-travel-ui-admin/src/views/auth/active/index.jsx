import React, { useState } from "react";
import { useForm } from "react-hook-form";
import { NavLink, useHistory } from "react-router-dom";
// Chakra imports
import {
  Box,
  Button,
  Checkbox,
  Flex,
  FormControl,
  FormErrorMessage,
  FormLabel,
  Heading,
  Icon,
  Input,
  InputGroup,
  InputRightElement,
  Text,
  useColorModeValue,
} from "@chakra-ui/react";
// Custom components
import DefaultAuth from "layouts/auth/Default";
// Assets
import illustration from "assets/img/auth/auth.png";

import httpServ from "service/httpService";
import { useParams } from "react-router-dom/cjs/react-router-dom";
import BasicField from "components/form/BasicField";
import { useSelector } from "react-redux";

function Active() {
  const { id } = useParams();
  const activeAccounts = useSelector((state) => state.auth.activeAccounts);
  console.log(activeAccounts);
  const textColor = useColorModeValue("navy.700", "white");
  const textColorDetails = useColorModeValue("navy.700", "secondaryGray.600");
  const textColorBrand = useColorModeValue("brand.500", "white");

  const [mess, setMess] = useState({
    type: true,
    mess: "",
  });

  const {
    handleSubmit,
    register,
    formState: { errors, isSubmitting },
  } = useForm();

  async function onSubmit(values) {
    return new Promise((resolve, reject) => {
      setTimeout(() => {
        const idxChecked = activeAccounts.findIndex((vl) => vl.userId === id);
        if (idxChecked !== -1) {
          if (activeAccounts[idxChecked].code === values.code) {
            httpServ
              .activeAccount(id, values.code)
              .then((res) => {
                setMess({
                  type: true,
                  mess: "Active account successful, you can sign now",
                });
                resolve();
              })
              .catch(() => {
                setMess({
                  type: false,
                  mess: "Incorrect code",
                });
                resolve();
              });
          } else {
            setMess({
              type: false,
              mess: "Incorrect code",
            });
            resolve();
          }
        } else {
          setMess({
            type: false,
            mess: "Incorrect code",
          });
          resolve();
        }
      }, 1000);
    });
  }

  return (
    <DefaultAuth illustrationBackground={illustration} image={illustration}>
      <Flex
        maxW={{ base: "100%", md: "max-content" }}
        w="100%"
        mx={{ base: "auto", lg: "0px" }}
        me="auto"
        h="100%"
        alignItems="start"
        justifyContent="center"
        mb={{ base: "30px", md: "60px" }}
        px={{ base: "25px", md: "0px" }}
        mt={{ base: "40px", md: "14vh" }}
        flexDirection="column"
      >
        <Box me="auto">
          <Heading color={textColor} fontSize="36px" mb="10px">
            Active your account
          </Heading>
        </Box>
        <Flex
          zIndex="2"
          direction="column"
          w={{ base: "100%", md: "420px" }}
          maxW="100%"
          background="transparent"
          borderRadius="15px"
          mx={{ base: "auto", lg: "unset" }}
          me="auto"
          mb={{ base: "20px", md: "auto" }}
        >
          <form onSubmit={handleSubmit(onSubmit)}>
            <BasicField
              placeholder="Code"
              title="Code"
              name="code"
              type="INPUT"
              typeInput="text"
              register={register("code", {
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
              Active
            </Button>
          </form>
          <Flex
            flexDirection="column"
            justifyContent="center"
            alignItems="start"
            maxW="100%"
            mt="0px"
          >
            <Text color={textColorDetails} fontWeight="400" fontSize="14px">
              Already have an account?
              <NavLink to="/auth/sign-in">
                <Text
                  color={textColorBrand}
                  as="span"
                  ms="5px"
                  fontWeight="500"
                >
                  Sign in now
                </Text>
              </NavLink>
            </Text>
          </Flex>
        </Flex>
      </Flex>
    </DefaultAuth>
  );
}

export default Active;
