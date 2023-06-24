import React, {useContext, useEffect, useState} from "react";

import classNames from "classnames/bind";
import styles from "./InfoBookedTour.module.scss";
import {Avatar, Box, Grid,} from "@mui/material";
import {EnterpriseContext} from "~/config/provider/EnterpriseProvider";
import TimeHowLongConvertor from "~/utils/convertor/TimeHowLongConvertor";
import DateUtils from "~/utils/DateUtils";
import MoneyUtils from "~/utils/MoneyUtils";
import {getTourProfile, getUser} from "~/services/workspaces.sevices";

const cx = classNames.bind(styles);
const InfoBookedTour = (props) => {
    const {tourId, tourBookedInfo} = props;
    const [ageValues, setAgeValues] = useState([]);
    useEffect(() => {
        const fetchData = async () => {
            await getTourProfile(tourId).then(res => {
                setAgeValues(res?.data?.pricingValues);

            })
        }
        fetchData();
    }, [tourId]);
    return (
        <div className={cx("cusInfobookedTour")}>
            <Box sx={{width: '100%',}} className={cx('box-infoBooked')}>
                <div className={cx('info-header')}>
                    <h4>Thông tin tour đã đặt</h4>
                </div>
                <div className={cx('info-content')}>

                    <Grid container spacing={2} sx={{marginBottom: "3rem"}}>

                        <Grid item xs={3}>
                            <Avatar style={{
                                width: "85px",
                                height: "70px",
                                marginTop: "1rem"
                            }} className={cx('avatar-product')}
                                    src={tourBookedInfo?.featureImgUrl}
                                    variant="square"/>
                        </Grid>
                        <Grid item xs={9}>
                            <h5>{tourBookedInfo?.name}</h5>
                        </Grid>
                    </Grid>
                    <p>Mã tour: <span>#{tourBookedInfo?.id}</span></p>
                    <p>Thời gian đi tour: <span>{TimeHowLongConvertor.convert(tourBookedInfo?.timeHowLong)}</span></p>
                    <p>Ngày khởi hành: <span>{DateUtils.formatDate(tourBookedInfo?.startDate)}</span></p>
                    <p>Điểm đến: <span>{tourBookedInfo?.location?.province}</span></p>
                    {
                        ageValues.map((item) => (
                            <div>
                                <p className={cx("title")}><i className="fa fa-money"
                                                              aria-hidden="true"/>
                                    Giá {item?.aboutAgeType === 'CHILDREN' ? "Trẻ em" : "Người lớn"}: {" "}
                                    <span>{MoneyUtils.getMoney(item?.price?.amount)} VNĐ</span>
                                </p>
                                <p>
                                    (Giảm -{Math.ceil(item?.sale * 100)}% còn{" "}
                                    {MoneyUtils.getMoney(MoneyUtils.getMoneyDiscounted(item?.price?.amount, item?.sale))} VNĐ)
                                </p>
                            </div>


                        ))
                    }


                </div>

            </Box>

        </div>


    );
};

export default InfoBookedTour;
