import {
  Box,
  Button,
  Input,
  SimpleGrid,
  Stack,
  useDisclosure,
} from "@chakra-ui/react";
import DevelopmentTable from "views/admin/tourManagement/components/DevelopmentTable";

import { columnsDataDevelopment } from "views/admin/tourManagement/variables/columnsData";
import React, { useEffect, useState } from "react";
import DModal from "./components/Modal";
import httpServ from "service/httpService";
import Loading from "components/loading/Loading";
import { SearchBar } from "components/navbar/searchBar/SearchBar";
import { SearchIcon } from "@chakra-ui/icons";

export default function Settings() {
  const { isOpen, onOpen, onClose } = useDisclosure();
  const [data, setData] = useState([]);
  const [allData,setAllData]=useState([]);
  const [isReset, setIsReset] = useState(true);
  const [isLoading, setIsLoading] = useState(true);
  useEffect(() => {
    if (isReset) {
      setIsLoading(false);
      httpServ.getTours().then((res) => {
        setData(res.data);
        setAllData(res.data);
        setIsReset(false);
        setIsLoading(false);
      });
    }
  }, [isReset]);

  const handleSearch=(vl)=>{
    const dataFilter = allData.filter((tour) => {
      return tour.name.toLowerCase().includes(vl.toLowerCase());
    });
    setData(dataFilter);
  }

  return (
    <Box pt={{ base: "130px", md: "80px", xl: "80px" }}>
      <SimpleGrid
        mb="20px"
        columns={{ sm: 1 }}
        spacing={{ base: "20px", xl: "20px" }}
      >
        <Stack
          spacing={4}
          direction="row"
          align="center"
          justifyContent="space-between"
        >
          <Box display="flex" alignItems="center" bg="gray.200" padding="5px 15px" borderRadius="10px">
            <Input
              fontSize="sm"
              ms={{ base: "0px", md: "0px" }}
              type="text"
              placeholder={"Tìm kiếm"}
              fontWeight="500"
              size="lg"
              step="0.01"
              outline="none"
              onChange={(e)=>handleSearch(e.target.value)}
            />
            <SearchIcon />
          </Box>
          <Button colorScheme="teal" size="lg" onClick={onOpen}>
            Thêm tour mới
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
