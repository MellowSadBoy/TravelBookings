import React, {useContext, useEffect, useState} from 'react';

import classNames from "classnames/bind";
import styles from "./InfoHotelBooked.module.scss";


import {Box, Container, createTheme, Tab, Tabs, ThemeProvider} from "@mui/material";

import TabPanel from "@mui/lab/TabPanel";

import {TabContext} from "@mui/lab";
import {themeCustomer} from "~/components/CustomerMaterial";
import {filterHotelBooked} from "~/services/workspaces.sevices";
import InfoHotelBookedItem from './info-hotel-booked-item/InfoHotelBookedItem';

const cx = classNames.bind(styles);

function InfoHotelBooked(props) {
    const theme = createTheme(themeCustomer);
    const [valueTab, setValueTab] = useState('1');
    const [hotelBookedAll, setHotelBookedAll] = useState([]);
    const [hotelBookedReady, setHotelBookedReady] = useState([]);
    const [hotelBookedCancelled, setHotelBookedCancelled] = useState([]);
    const id = localStorage.getItem("cs-id");
    useEffect(() => {
        let HotelBookedFilter = {
            customerId: id,
        }

        async function loadData() {
            const allData = await filterHotelBooked(HotelBookedFilter);
            const hotelBookedALls = [];
            const hotelBookedReadys = [];
            const hotelBookedCancelleds = [];
            for (const item of allData?.data?.searchList) {
                hotelBookedALls.push(item);
                if (item.status === "READY") {
                    hotelBookedReadys.push(item);
                }
                if (item.status === "CANCELLED") {
                    hotelBookedCancelleds.push(item);
                }


            }
            setHotelBookedAll(hotelBookedALls);
            setHotelBookedReady(hotelBookedReadys);
            setHotelBookedCancelled(hotelBookedCancelleds);


        }

        loadData();

    }, []);

    const handleChange = (event, newValue) => {
        setValueTab(newValue);
    };

    return (
        <div className={cx('wrapper')}>
            <Container>
                <div className={cx('purchase')}>
                    <TabContext value={valueTab}>
                        <Box sx={{borderBottom: 1, borderColor: 'divider'}}>
                            <ThemeProvider theme={theme}>
                                <Tabs onChange={handleChange}
                                      value={valueTab}
                                      textColor="primary"
                                      indicatorColor="primary"
                                >
                                    <Tab
                                        label={"Tất cả"}
                                        value='1'
                                        sx={{fontSize: "1.6rem", textTransform: 'none'}}
                                    />
                                    <Tab
                                        label={"Đã đặt"}
                                        value='2'
                                        sx={{fontSize: "1.6rem", textTransform: 'none'}}
                                    />

                                    <Tab
                                        label={"Đã huỷ"}
                                        value='3'
                                        sx={{fontSize: "1.6rem", textTransform: 'none'}}
                                    />


                                </Tabs>
                            </ThemeProvider>
                        </Box>
                        <TabPanel value="1">
                            <div className={cx('items')}>
                                {hotelBookedAll.map((tourBookedItem) => (
                                    <InfoHotelBookedItem tourBookedItem={tourBookedItem}/>
                                ))
                                }
                            </div>
                        </TabPanel>
                        <TabPanel value="2">
                            <div className={cx('items')}>
                                {hotelBookedReady.map((tourBookedItem) => (
                                    <InfoHotelBookedItem tourBookedItem={tourBookedItem}/>
                                ))
                                }
                            </div>
                        </TabPanel>
                        <TabPanel value="3">
                            <div className={cx('items')}>
                                {hotelBookedCancelled.map((tourBookedItem) => (
                                    <InfoHotelBookedItem tourBookedItem={tourBookedItem}/>
                                ))
                                }
                            </div>
                        </TabPanel>
                    </TabContext>

                </div>
            </Container>
        </div>
    );
}

export default InfoHotelBooked;
