import React, {useContext, useEffect, useState} from 'react';

import classNames from "classnames/bind";
import styles from "./InfoTourBooked.module.scss";


import {Box, Container, createTheme, Tab, Tabs, ThemeProvider} from "@mui/material";

import TabPanel from "@mui/lab/TabPanel";

import {TabContext} from "@mui/lab";
import {themeCustomer} from "~/components/CustomerMaterial";
import {filterTourBooked} from "~/services/workspaces.sevices";
import InfoTourBookedItem from './info-tour-booked-item/InfoTourBookedItem';

const cx = classNames.bind(styles);

function InfoTourBooked(props) {
    const theme = createTheme(themeCustomer);
    const [valueTab, setValueTab] = useState('1');
    const [tourBookedAll, setTourBookedAll] = useState([]);
    const [tourBookedReady, setTourBookedReady] = useState([]);
    const [tourBookedCancelled, setTourBookedCancelled] = useState([]);
    const id = localStorage.getItem("cs-id");
    useEffect(() => {
        let TourBookedFilter = {
            customerId: id,
        }

        async function loadData() {
            const allData = await filterTourBooked(TourBookedFilter);
            const tourBookedALls = [];
            const tourBookedReadys = [];
            const tourBookedCancelleds = [];
            for (const item of allData?.data?.searchList) {
                tourBookedALls.push(item);
                if (item.status === "READY") {
                    tourBookedReadys.push(item);
                }
                if (item.status === "CANCELLED") {
                    tourBookedCancelleds.push(item);
                }


            }
            setTourBookedAll(tourBookedALls);
            setTourBookedReady(tourBookedReadys);
            setTourBookedCancelled(tourBookedCancelleds);


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
                                {tourBookedAll.map((tourBookedItem) => (
                                    <InfoTourBookedItem tourBookedItem={tourBookedItem}/>
                                ))
                                }
                            </div>
                        </TabPanel>
                        <TabPanel value="2">
                            <div className={cx('items')}>
                                {tourBookedReady.map((tourBookedItem) => (
                                    <InfoTourBookedItem tourBookedItem={tourBookedItem}/>
                                ))
                                }
                            </div>
                        </TabPanel>
                        <TabPanel value="3">
                            <div className={cx('items')}>
                                {tourBookedCancelled.map((tourBookedItem) => (
                                    <InfoTourBookedItem tourBookedItem={tourBookedItem}/>
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

export default InfoTourBooked;
