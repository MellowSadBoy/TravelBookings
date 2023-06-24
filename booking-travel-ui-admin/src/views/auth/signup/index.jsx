import React, { useCallback, useState } from "react";
import { NavLink } from "react-router-dom";
import {
  Box,
  Button,
  Flex,
  Heading,
  Stack,
  Text,
  useColorModeValue,
} from "@chakra-ui/react";
import DefaultAuth from "layouts/auth/Default";
import illustration from "assets/img/auth/auth.png";
import { useForm } from "react-hook-form";
import BasicField from "components/form/BasicField";
import { GenderTypes } from "share/data/data";
import Location from "components/locationOptions/Location";
import { isValidEmail } from "share/utils/functions";
import httpServ from "service/httpService";
import { encodeBase64 } from "share/utils/functions";
import { Link } from "react-router-dom/cjs/react-router-dom";
import { useDispatch } from "react-redux";
import { addActiveAccounts } from "store/AuthSlice/AuthSlice";

function SignUp() {
  const dispatch = useDispatch();
  const textColor = useColorModeValue("navy.700", "white");
  const textColorSecondary = "gray.400";
  const textColorDetails = useColorModeValue("navy.700", "secondaryGray.600");
  const textColorBrand = useColorModeValue("brand.500", "white");
  const [showActive, setShowActive] = useState({
    isShow: false,
    id: null,
  });
  const [location, setLocation] = useState({});
  const [mess, setMess] = useState({
    type: true,
    mess: "",
  });

  const {
    handleSubmit,
    register,
    reset,
    formState: { errors, isSubmitting },
    watch,
  } = useForm();

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
      const { data, message } = await httpServ.signUp(finalData);
      const { code, userId } = data.data;
      if (code && userId) {
        dispatch(
          addActiveAccounts({
            code,
            userId,
          })
        );
        setMess({
          mess: data.message,
          type: true,
        });
        setShowActive({
          isShow: true,
          id: userId,
        });
        reset();
      } else {
        setMess({
          mess: message || "Something error",
          type: false,
        });
      }
    } catch (error) {
      setMess({
        mess: error?.errorMessage || "Something error",
        type: false,
      });
    }
  }
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
            Sign Up
          </Heading>
          <Text
            mb="36px"
            ms="4px"
            color={textColorSecondary}
            fontWeight="400"
            fontSize="md"
          >
            Create your admin account
          </Text>
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
              placeholder="Email"
              title="Email"
              name="email"
              type="INPUT"
              register={register("email", {
                required: "This is required",
                validate: {
                  isEmail: (v) =>
                    isValidEmail(v) || "This field must be an email",
                },
              })}
              errors={errors}
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
            <Stack>
              <Location onChange={onChangeLocation} defaultValues={null} />
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
            {showActive.isShow ? (
              <Link to={`/auth/active/${showActive.id}`}>
                <Text color="chocolate" mb="8px" variant="blue">
                  Click here to active
                </Text>
              </Link>
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
              Sign Up
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

export default SignUp;
