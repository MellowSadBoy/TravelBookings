import React, {useContext} from "react";

import classNames from "classnames/bind";
import styles from "./CusInfoBookedHotel.module.scss";
import {Box, Grid,} from "@mui/material";
import DateUtils from "~/utils/DateUtils";
import MoneyUtils from "~/utils/MoneyUtils";
import {UserContext} from "~/config/provider/UserProvider";

const cx = classNames.bind(styles);
const CusInfoBookedHotel = (props) => {
    const {hotelBookedInfo} = props;
    const {customer}= useContext(UserContext);

    return (
        <div className={cx("cusInfobookedTour")}>
            <Box sx={{width: '100%',}} className={cx('box-infoBooked')}>
                <div className={cx('info-header')}>
                    <h4>Thông tin khách đặt phòng</h4>
                </div>
                <div className={cx('info-content')}>
                    <Grid container spacing={2}>
                        <Grid item xs={7.5}>
                            <p>Họ Và tên: <span>{hotelBookedInfo?.customerName}</span></p>
                            <p>Địa Chỉ: <span>{customer?.address?.address}</span></p>
                            <p>Số điện thoại: <span>{hotelBookedInfo?.customerPhone}</span></p>
                            <p>Email: <span>{hotelBookedInfo?.customerEmail}</span></p>
                            <p>Số người đi: <span>{hotelBookedInfo?.quantityAdult} người lớn, {hotelBookedInfo?.quantityChildren} trẻ em</span></p>
                            <p>Thời gian đặt: <span>{DateUtils.convertTime(hotelBookedInfo?.createdAt)}</span></p>
                        </Grid>
                        <Grid item xs={4} className={cx('box-status-pay')}>
                            <p>Tổng tiền: <span>{MoneyUtils.getMoney(hotelBookedInfo?.totalPrice?.amount)} VND</span></p>
                            <p style={{color: 'red'}}>{hotelBookedInfo?.paymentMethod==='QR'?'Đã thanh toán':'Chưa thanh toán'}</p>
                        </Grid>
                    </Grid>
                </div>
            </Box>
        </div>
    );
};

export default CusInfoBookedHotel;
