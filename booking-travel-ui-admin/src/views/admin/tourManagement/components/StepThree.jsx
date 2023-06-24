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
import React, { useState } from "react";
import { useForm } from "react-hook-form";
import { toast } from "react-toastify";
import { MoneyTypes } from "share/data/data";
import { AgeTypes } from "share/data/data";

const AddStepThree = ({
  handleNextStep,
  handleBackStep,
  defaultValues,
  loading,
  isDisabled,
  isUpdate,
}) => {
  const textColor = useColorModeValue("navy.700", "white");
  const textColorTitle = useColorModeValue("light", "brand.400");

  const [finalValues, setFinalValues] = useState(defaultValues);

  const {
    handleSubmit,
    register,
    reset,
    formState: { errors },
  } = useForm();

  function onSubmit(values) {
    if (loading) return;
    const { aboutAgeType, note, amount, currencyCode, sale } = values;
    const data = {
      aboutAgeType,
      note,
      sale,
      price: {
        amount,
        currencyCode,
      },
    };
    setFinalValues((prev) => [...prev, data]);
    reset();
  }

  const handleNext = () => {
    let countAdults = 0;
    let countChildren = 0;
    for (let value of finalValues) {
      if (value.aboutAgeType === "CHILDREN") {
        countChildren++;
      } else {
        countAdults++;
      }
    }
    if (countAdults !== 1 || countChildren !== 1) {
      toast.error(
        "Must be one pricing value of children and one pricing value of adult"
      );
      return;
    }
    handleNextStep(finalValues);
  };

  return (
    <>
      <Flex flexDirection="column">
        {finalValues.map((value, idx) => {
          const {
            aboutAgeType,
            note,
            sale,
            price: { amount, currencyCode },
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
                  Age type:
                </Text>
                {aboutAgeType}
              </Box>
              <Box display="flex" mb={3}>
                <Text color="blue.500" minWidth="120px">
                  Note:
                </Text>
                {note}
              </Box>
              <Box display="flex" mb={3}>
                <Text color="blue.500" minWidth="120px">
                  Sale:
                </Text>
                {sale}
              </Box>
              <Box display="flex" mb={3}>
                <Text color="blue.500" minWidth="120px">
                  Price amount:
                </Text>
                {amount}
              </Box>
              <Box display="flex" mb={3}>
                <Text color="blue.500" minWidth="120px">
                  Price currency:
                </Text>
                {currencyCode}
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
            ms="4px"
            fontSize="larger"
            fontWeight="500"
            color={textColorTitle}
            mb="8px"
          >
            Pricing values
          </FormLabel>
          <BasicField
            placeholder="Age type"
            title="Age type"
            name="aboutAgeType"
            type="SELECT"
            register={register("aboutAgeType")}
            errors={errors}
            options={AgeTypes}
            isDisabled={isDisabled}
          />
          <BasicField
            placeholder="Note"
            title="Note"
            name="note"
            type="INPUT"
            typeInput="text"
            register={register("note", {
              required: "This is required",
            })}
            errors={errors}
            isDisabled={isDisabled}
          />
          <FormLabel
            ms="4px"
            fontSize="sm"
            fontWeight="500"
            color={textColor}
            display="flex"
          >
            Price
          </FormLabel>
          <Flex
            display={"flex"}
            flexDirection="row"
            alignItems="center"
            mb={"24px"}
          >
            <BasicField
              placeholder="amount"
              title=""
              name="amount"
              type="INPUT"
              typeInput="number"
              register={register("amount", {
                required: "This is required",
              })}
              errors={errors}
              defaultValue={1}
              isDisabled={isDisabled}
            />
            <BasicField
              placeholder=""
              title=""
              name="currencyCode"
              type="SELECT"
              register={register("currencyCode")}
              errors={errors}
              options={MoneyTypes}
              isDisabled={isDisabled}
            />
          </Flex>
          <BasicField
            placeholder="Sale"
            title="Sale"
            name="sale"
            type="INPUT"
            typeInput="number"
            defaultValue={0}
            register={register("sale", {
              required: "This is required",
            })}
            errors={errors}
            isDisabled={isDisabled}
          />
        </Stack>
        {!isDisabled && (
          <Button type="submit" colorScheme="blue">
            Add pricing value
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
          isLoading={loading}
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
          isLoading={loading}
          onClick={() => {
            handleNext();
          }}
        >
          {isUpdate ? "Update tour" : "Create new tour"}
        </Button>
      </Flex>
    </>
  );
};

export default AddStepThree;
