import {
  Box,
  Button,
  SimpleGrid,
  Stack,
  useDisclosure,
} from "@chakra-ui/react";
import DevelopmentTable from "views/admin/userManagement/components/DevelopmentTable";

import { columnsDataDevelopment } from "views/admin/userManagement/variables/columnsData";
import React, { useEffect, useState } from "react";
import DModal from "./components/Modal";
import httpServ from "service/httpService";
import Loading from "components/loading/Loading";

export default function Settings() {
  const { isOpen, onOpen, onClose } = useDisclosure();
  const [data, setData] = useState([]);
  const [isReset, setIsReset] = useState(true);
  const [isLoading, setIsLoading] = useState(true);
  useEffect(() => {
    if (isReset) {
      setIsLoading(false);
      httpServ.filterUsers({}).then((res) => {
        setData(res.data.searchList);
        setIsReset(false);
        setIsLoading(false);
      });
    }
  }, [isReset]);
  return (
    <Box pt={{ base: "130px", md: "80px", xl: "80px" }}>
      <SimpleGrid
        mb="20px"
        columns={{ sm: 1 }}
        spacing={{ base: "20px", xl: "20px" }}
      >
        <Stack spacing={4} direction="row" align="center" justifyContent="end">
          <Button colorScheme="teal" size="lg" onClick={onOpen}>
            Tạo quản trị viên
          </Button>
        </Stack>
        {isLoading ? (
          <Box padding="20px 0">
            <Loading />
          </Box>
        ) : (
          <DevelopmentTable
            columnsData={columnsDataDevelopment}
            tableData={data}
            setIsReset={setIsReset}
          />
        )}
        <DModal isOpen={isOpen} onClose={onClose} setIsReset={setIsReset} />
      </SimpleGrid>
    </Box>
  );
}
