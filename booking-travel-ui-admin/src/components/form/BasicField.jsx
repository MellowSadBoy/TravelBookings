import {
  FormControl,
  FormErrorMessage,
  FormLabel,
  HStack,
  Input,
  Radio,
  RadioGroup,
  Select,
  Text,
  Textarea,
  useColorModeValue,
} from "@chakra-ui/react";
import React from "react";

const BasicField = ({
  type,
  typeInput = "text",
  title = "",
  name = "",
  placeholder = "",
  register,
  errors,
  options = [],
  defaultValue = "",
  isDisabled = false,
}) => {
  const textColor = useColorModeValue("navy.700", "white");
  const brandStars = useColorModeValue("brand.500", "brand.400");
  const newRegister = register ? register : () => {};
  const isInvalid = errors[name] ? true : false;

  const renderContent = () => {
    switch (type) {
      case "INPUT":
        return (
          <Input
            isDisabled={isDisabled}
            fontSize="sm"
            ms={{ base: "0px", md: "0px" }}
            type={typeInput}
            placeholder={placeholder}
            mb="8px"
            fontWeight="500"
            size="lg"
            name={name}
            color={textColor}
            step="0.01"
            defaultValue={defaultValue}
            {...newRegister}
          />
        );
      case "TEXTAREA":
        return (
          <Textarea
            isDisabled={isDisabled}
            fontSize="sm"
            placeholder={placeholder}
            mb="8px"
            size="lg"
            name={name}
            {...newRegister}
            color={textColor}
          />
        );
      case "SELECT":
        return (
          <Select
            color={textColor}
            mb="8px"
            {...newRegister}
            isDisabled={isDisabled}
          >
            {options.map((type) => {
              return (
                <option
                  key={type.hint}
                  value={type?.hint ? type.hint : type.id}
                >
                  {type?.title ? type.title : type.name}
                </option>
              );
            })}
          </Select>
        );
      case "RADIO":
        return (
          <RadioGroup
            color={textColor}
            mb="10px"
            defaultValue={options[0].hint}
            {...newRegister}
            isDisabled={isDisabled}
          >
            <HStack spacing="24px">
              {options.map((type) => {
                return (
                  <Radio key={type.hint} value={type.hint}>
                    {type.title}
                  </Radio>
                );
              })}
            </HStack>
          </RadioGroup>
        );
      default:
        break;
    }
  };
  return (
    <FormControl isInvalid={isInvalid}>
      {title && (
        <FormLabel
          display="flex"
          ms="4px"
          fontSize="sm"
          fontWeight="500"
          color={textColor}
          mb="8px"
        >
          {title}
          <Text color={brandStars}>*</Text>
        </FormLabel>
      )}
      {renderContent()}
      {isInvalid ? (
        <FormErrorMessage mb="8px">
          {errors[name]?.message || "error"}
        </FormErrorMessage>
      ) : (
        <></>
      )}
    </FormControl>
  );
};

export default BasicField;
