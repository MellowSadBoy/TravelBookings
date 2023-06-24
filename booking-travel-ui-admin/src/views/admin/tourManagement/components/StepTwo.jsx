import {
  Box,
  Button,
  Flex,
  FormLabel,
  Stack,
  Text,
  useColorModeValue,
} from "@chakra-ui/react";
import BasicField from "components/form/BasicField";
import Location from "components/locationOptions/Location";
import React, { useCallback, useState } from "react";
import { useForm } from "react-hook-form";
import { toast } from "react-toastify";
import { getDateTime } from "share/utils/functions";
import { getTimeConvert } from "share/utils/functions";

const AddStepTwo = ({
  handleNextStep,
  handleBackStep,
  defaultValues,
  isDisabled,
}) => {
  const textColorTitle = useColorModeValue("light", "brand.400");

  const [finalValues, setFinalValues] = useState(defaultValues);
  const [location, setLocation] = useState({});

  const {
    handleSubmit,
    register,
    reset,
    formState: { errors },
  } = useForm();

  const onChangeLocation = useCallback((province, district, ward) => {
    setLocation({
      countryCode: "VN",
      district: district.name,
      districtCode: district.code,
      province: province.name,
      provinceCode: province.code,
      ward: ward.name,
      wardCode: ward.code,
    });
  }, []);

  function onSubmit(values) {
    if (isDisabled) return;
    const data = {
      ...values,
      arrivalTime: getDateTime(values.arrivalTime),
      leaveTime: getDateTime(values.leaveTime),
      locationStop: {
        ...location,
      },
    };
    setFinalValues((prev) => [...prev, data]);
    reset();
  }

  return (
    <>
      <Flex flexDirection="column">
        {finalValues.map((value, idx) => {
          const {
            locationStop: { district, province, ward },
            arrivalTime,
            leaveTime,
            description,
            nameLocation,
            title,
          } = value;
          return (
            <Box
              key={idx}
              padding="4px"
              border="1px solid gray"
              borderRadius="4px"
              width="100%"
              mb={5}
            >
              <Box display="flex" mb={3}>
                <Text color="blue.500" minWidth="120px">
                  Arrival time:
                </Text>
                {getTimeConvert(arrivalTime)}
              </Box>
              <Box display="flex" mb={3}>
                <Text color="blue.500" minWidth="120px">
                  Leave time:
                </Text>
                {getTimeConvert(leaveTime)}
              </Box>
              <Box display="flex" mb={3}>
                <Text color="blue.500" minWidth="120px">
                  Description:
                </Text>
                {description}
              </Box>
              <Box display="flex" mb={3}>
                <Text color="blue.500" minWidth="120px">
                  Title:
                </Text>
                {title}
              </Box>
              <Box display="flex" mb={3}>
                <Text color="blue.500" minWidth="120px">
                  Name Location:
                </Text>
                {nameLocation}
              </Box>
              <Box display="flex" mb={3}>
                <Text color="blue.500" minWidth="120px">
                  Province:
                </Text>
                {province}
              </Box>
              <Box display="flex" mb={3}>
                <Text color="blue.500" minWidth="120px">
                  District:
                </Text>
                {district}
              </Box>
              <Box display="flex" mb={3}>
                <Text color="blue.500" minWidth="120px">
                  Ward:
                </Text>
                {ward}
              </Box>
              {!isDisabled && (
                <Button
                  fontSize="sm"
                  variant="red"
                  fontWeight="500"
                  color="red"
                  w="20"
                  h="10"
                  mt="4px"
                  onClick={() => {
                    const clValues = [...finalValues];
                    clValues.splice(idx, 1);
                    setFinalValues(clValues);
                  }}
                >
                  Delete
                </Button>
              )}
            </Box>
          );
        })}
      </Flex>
      <form onSubmit={handleSubmit(onSubmit)}>
        <Stack>
          <FormLabel
            display="flex"
            mb={3}
            ms="4px"
            fontSize="larger"
            fontWeight="500"
            color={textColorTitle}
          >
            Schedules
          </FormLabel>
          <BasicField
            placeholder="Arrival time"
            title="Arrival time"
            name="arrivalTime"
            type="INPUT"
            typeInput="date"
            register={register("arrivalTime", {
              required: "This is required",
            })}
            errors={errors}
            isDisabled={isDisabled}
          />
          <BasicField
            placeholder="Leave time"
            title="Leave time"
            name="leaveTime"
            type="INPUT"
            typeInput="date"
            register={register("leaveTime", {
              required: "This is required",
            })}
            errors={errors}
            isDisabled={isDisabled}
          />
          <BasicField
            placeholder="Description"
            title="Description"
            name="description"
            type="INPUT"
            typeInput="text"
            register={register("description", {
              required: "This is required",
            })}
            errors={errors}
            isDisabled={isDisabled}
          />
          <BasicField
            placeholder="Name location"
            title="Name location"
            name="nameLocation"
            type="INPUT"
            typeInput="text"
            register={register("nameLocation", {
              required: "This is required",
            })}
            errors={errors}
            isDisabled={isDisabled}
          />
          <BasicField
            placeholder="Title"
            title="Title"
            name="title"
            type="INPUT"
            typeInput="text"
            register={register("title", {
              required: "This is required",
            })}
            errors={errors}
            isDisabled={isDisabled}
          />
          <Location onChange={onChangeLocation} isDisabled={isDisabled} />
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
            isDisabled={isDisabled}
          />
        </Stack>
        {!isDisabled && (
          <Button type="submit" colorScheme="blue">
            Add schedule
          </Button>
        )}
      </form>
      <Flex mt={5} justifyContent={"space-between"} alignItems="center">
        <Button
          fontSize="sm"
          variant="gray"
          color={"red"}
          fontWeight="500"
          w="40%"
          h="50"
          onClick={() => handleBackStep()}
        >
          Back
        </Button>
        <Button
          fontSize="sm"
          variant="brand"
          fontWeight="500"
          w="40%"
          h="50"
          mt="0px"
          onClick={() => {
            if (isDisabled) {
              handleNextStep();
            } else {
              if (finalValues.length <= 0) {
                toast.error("At least one schedule");
                return;
              }
              handleNextStep(finalValues);
            }
          }}
        >
          Next
        </Button>
      </Flex>
    </>
  );
};

export default AddStepTwo;
