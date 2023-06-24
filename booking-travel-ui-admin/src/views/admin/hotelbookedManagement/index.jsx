import { Box, Input, SimpleGrid, Stack, useDisclosure } from "@chakra-ui/react";
import DevelopmentTable from "views/admin/hotelbookedManagement/components/DevelopmentTable";

import { columnsDataDevelopment } from "views/admin/hotelbookedManagement/variables/columnsData";
import React, { useEffect, useState } from "react";
import DModal from "./components/Modal";
import httpServ from "service/httpService";
import Loading from "components/loading/Loading";
import { SearchIcon } from "@chakra-ui/icons";

export default function Settings() {
  const { isOpen, onOpen, onClose } = useDisclosure();
  const [data, setData] = useState([]);
  const [isReset, setIsReset] = useState(true);
  const [isLoading, setIsLoading] = useState(true);
  const [allData, setAllData] = useState([]);

  useEffect(() => {
    if (isReset) {
      setIsLoading(false);

      const searchData = {};

      httpServ.filterHotelbooked(searchData).then((res) => {
        console.log("res.data.searchList ", res.data.searchList);
        setData(res.data.searchList);
        setAllData(res.data.searchList);

        setIsReset(false);
        setIsLoading(false);
      });
    }
  }, [isReset]);

  const handleSearch = (vl) => {
    console.log("vl", vl);
    const dataFilter = allData.filter((hotel) => {
      return hotel.hotelName.toLowerCase().includes(vl.toLowerCase());
    });
    setData(dataFilter);
  };

  return (
    <Box pt={{ base: "130px", md: "80px", xl: "80px" }} marginTop={"70px"}>
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
          <Box
            display="flex"
            alignItems="center"
            bg="gray.200"
            padding="5px 15px"
            borderRadius="10px"
          >
            <Input
              fontSize="sm"
              ms={{ base: "0px", md: "0px" }}
              type="text"
              placeholder={"Tìm kiếm"}
              fontWeight="500"
              size="lg"
              step="0.01"
              outline="none"
              onChange={(e) => handleSearch(e.target.value)}
            />
            <SearchIcon />
          </Box>
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
