/* eslint-disable */
import {
  Button,
  Flex,
  Icon,
  Image,
  Table,
  Tbody,
  Td,
  Text,
  Th,
  Thead,
  Tr,
  Grid,
  Stack,
  useColorModeValue,
  useDisclosure,
  Input,
  InputGroup,
  InputRightElement,
} from "@chakra-ui/react";
// Custom components
import Card from "components/card/Card";
import React, { useEffect, useMemo, useState } from "react";

import { getTypeOfTourbooked } from "share/utils/functions";

import "bootstrap/dist/css/bootstrap.css";
import {
  useGlobalFilter,
  usePagination,
  useSortBy,
  useTable,
} from "react-table";
import DModal from "./Modal";
import Cancel from "./Cancel";
import { MdCancel, MdEditDocument } from "react-icons/md";

// import { getStart } from "share/utils/functions";

export default function DevelopmentTable(props) {
  const { columnsData, tableData, setIsReset } = props;
  const [data, setData] = useState([]);
  const [idDetail, setIdDetail] = useState(null);
  const [idUpdate, setIdUpdate] = useState(null);
  const [idCancel, setIdCancel] = useState(null);
  const [currentPage, setCurrentPage] = useState(1);

  const handlePageClick = (pageNumber) => {
    setCurrentPage(pageNumber);
    setPage(pageNumber);
  };

  const renderPaginationButtons = () => {
    const buttons = [];
    const totalPages = 10; // Total number of pagination buttons

    for (let i = 1; i <= totalPages; i++) {
      buttons.push(
        <Button
          variant="outline"
          key={i}
          style={{
            margin: "0 5px",
            padding: "5px 10px",
            border: "1px solid #ccc",
            backgroundColor: currentPage === i ? "#007bff" : "#fff",
            color: currentPage === i ? "#fff" : "#333",
            cursor: "pointer",
          }}
          onClick={() => handlePageClick(i)}
        >
          {" "}
          {i}
        </Button>
      );
    }

    return buttons;
  };

  const {
    isOpen: isOpenDetail,
    onOpen: onOpenDetail,
    onClose: onCloseDetail,
  } = useDisclosure();

  const {
    isOpen: isOpenUpdate,
    onOpen: onOpenUpdate,
    onClose: onCloseUpdate,
  } = useDisclosure();

  const {
    isOpen: isOpenCancel,
    onOpen: onOpenCancel,
    onClose: onCloseCancel,
  } = useDisclosure();

  const columns = useMemo(() => columnsData, [columnsData]);

  const tableInstance = useTable(
    {
      columns,
      data,
    },
    useGlobalFilter,
    useSortBy,
    usePagination
  );

  const {
    getTableProps,
    getTableBodyProps,
    headerGroups,
    page,
    prepareRow,
    initialState,
  } = tableInstance;

  initialState.pageSize = 5;

  const setPage = (pageNumber) => {
    const { state, gotoPage } = tableInstance;
    const { pageSize } = state;

    const pageIndex = pageNumber - 1;
    const offset = pageIndex * pageSize;

    gotoPage(pageIndex);
    setCurrentPage(pageNumber);

    const newData = tableData.slice(offset, offset + pageSize);
    setData(newData);
  };

  const textColor = useColorModeValue("secondaryGray.900", "white");
  const iconColor = useColorModeValue("secondaryGray.500", "white");
  const borderColor = useColorModeValue("gray.200", "whiteAlpha.100");

  const handleDetail = (id) => {
    setIdDetail(id);
    onOpenDetail();
  };

  const handleUpdate = (id) => {
    setIdUpdate(id);
    onOpenUpdate();
  };

  const handleCancel = (id) => {
    setIdCancel(id);
    onOpenCancel();
  };

  useEffect(() => {
    setData(tableData);
  }, [tableData]);

  useEffect(() => {
    if (!isOpenDetail) setIdDetail(null);
  }, [isOpenDetail]);

  useEffect(() => {
    if (!isOpenUpdate) setIdUpdate(null);
  }, [isOpenUpdate]);

  return (
    <>
      <Card
        direction="column"
        w="100%"
        px="0px"
        overflowX={{ sm: "scroll", lg: "hidden" }}
      >
        <Table {...getTableProps()} variant="simple" color="gray.500" mb="24px">
          <Thead>
            {headerGroups.map((headerGroup, index) => (
              <Tr {...headerGroup.getHeaderGroupProps()} key={index}>
                {headerGroup.headers.map((column, index) => (
                  <Th
                    {...column.getHeaderProps(column.getSortByToggleProps())}
                    pe="10px"
                    key={index}
                    borderColor={borderColor}
                  >
                    <Flex
                      justify="space-between"
                      align="center"
                      fontSize={{ sm: "10px", lg: "12px" }}
                      color="gray.400"
                    >
                      {column.render("hint")}
                    </Flex>
                  </Th>
                ))}
              </Tr>
            ))}
          </Thead>
          <Tbody {...getTableBodyProps()}>
            {page.map((row, index) => {
              prepareRow(row);
              return (
                <Tr {...row.getRowProps()} key={index}>
                  {row.cells.map((cell, index) => {
                    let data = "";
                    if (cell.column.Header === "NAME") {
                      data = (
                        <Text color={textColor} fontSize="sm" fontWeight="700">
                          {cell.value}
                        </Text>
                      );
                    } else if (cell.column.Header === "MAINIMG") {
                      data = (
                        <Flex align="center" borderRadius={"10px"}>
                          <Image
                            src={cell.value}
                            alt=""
                            width={"200px"}
                            height={"100px"}
                            objectFit={"cover"}
                            borderRadius={"10px"}
                          />
                        </Flex>
                      );
                    } else if (cell.column.Header === "ID") {
                      data = (
                        <Text
                          _hover={{ color: "blue.600" }}
                          color={textColor}
                          fontSize="sm"
                          fontWeight="700"
                          cursor="pointer"
                          onClick={() => {
                            handleDetail(cell.row.original.id);
                          }}
                        >
                          {cell.value}
                        </Text>
                      );
                    } else if (cell.column.Header === "STATUS") {
                      data = (
                        <Text
                          color={textColor}
                          fontSize="sm"
                          fontWeight="700"
                          overflow={"hidden"}
                        >
                          {getTypeOfTourbooked(cell.value)}
                        </Text>
                      );
                    } else if (cell.column.Header === "ACTIONS") {
                      data = (
                        <Flex align="center" flexDirection="column">
                          <Flex>
                            <Button
                              colorScheme="orange"
                              ml={2}
                              onClick={() => {
                                handleUpdate(cell.row.original.id);
                              }}
                            >
                              <Icon
                                as={MdEditDocument}
                                width="20px"
                                height="20px"
                                color="inherit"
                              />
                            </Button>
                            <Button colorScheme="red" ml={2}>
                              <Icon
                                as={MdCancel}
                                width="20px"
                                height="20px"
                                color="inherit"
                                onClick={() => {
                                  handleCancel(cell.row.original.id);
                                }}
                              />
                            </Button>
                          </Flex>
                        </Flex>
                      );
                    } else {
                      data = (
                        <Text color={textColor} fontSize="sm" fontWeight="700">
                          {cell.value?.length > 200
                            ? `${cell.value.substring(0, 200)}...`
                            : cell.value}
                        </Text>
                      );
                    }
                    return (
                      <Td
                        {...cell.getCellProps()}
                        key={index}
                        fontSize={{ sm: "14px" }}
                        minW={{ sm: "40px", md: "80px", lg: "100px" }}
                        borderColor="transparent"
                      >
                        {data}
                      </Td>
                    );
                  })}
                </Tr>
              );
            })}
          </Tbody>
        </Table>

        <Grid
          templateColumns="repeat(3, 1fr)"
          gap={6}
          width={"100%"}
          marginTop={"20px"}
          padding={"20px"}
        >
          <Stack direction="row" spacing={4}>
            {renderPaginationButtons()}
          </Stack>

          {/* <Stack>
            <p>Current Page: {currentPage}</p>
          </Stack> */}
        </Grid>
      </Card>
      {idDetail && (
        <DModal
          isOpen={isOpenDetail}
          onClose={onCloseDetail}
          isDetail={true}
          idDetail={idDetail}
          setIdDetail={setIdDetail}
          setIsReset={setIsReset}
        />
      )}
      {idUpdate && (
        <DModal
          isOpen={isOpenUpdate}
          onClose={onCloseUpdate}
          isUpdate={true}
          idUpdate={idUpdate}
          setIsReset={setIsReset}
        />
      )}
      <Cancel
        isOpen={isOpenCancel}
        onOpen={onOpenCancel}
        onClose={onCloseCancel}
        idCancel={idCancel}
        setIsReset={setIsReset}
      />
    </>
  );
}
