import React, {useContext} from "react";

import classNames from "classnames/bind";
import styles from "./CusInfoBookedTour.module.scss";
import {Box, Grid,} from "@mui/material";
import DateUtils from "~/utils/DateUtils";
import MoneyUtils from "~/utils/MoneyUtils";
import {UserContext} from "~/config/provider/UserProvider";

const cx = classNames.bind(styles);
const CusInfoBookedTour = (props) => {
    const {tourBookedInfo} = props;
    const {customer}= useContext(UserContext);

    return (
        <div className={cx("cusInfobookedTour")}>
            <Box sx={{width: '100%',}} className={cx('box-infoBooked')}>
                <div className={cx('info-header')}>
                    <h4>Thông tin khách đặt tour</h4>
                </div>
                <div className={cx('info-content')}>
                    <Grid container spacing={2}>
                        <Grid item xs={7.5}>
                            <p>Họ Và tên: <span>{tourBookedInfo?.customerName}</span></p>
                            <p>Địa Chỉ: <span>{customer?.address?.address}</span></p>
                            <p>Số điện thoại: <span>{tourBookedInfo?.customerPhone}</span></p>
                            <p>Email: <span>{tourBookedInfo?.customerEmail}</span></p>
                            <p>Số người đi: <span>{tourBookedInfo?.quantityAdult} người lớn, {tourBookedInfo?.quantityChildren} trẻ em</span></p>
                            <p>Thời gian đặt: <span>{DateUtils.convertTime(tourBookedInfo?.createdAt)}</span></p>
                        </Grid>
                        <Grid item xs={4} className={cx('box-status-pay')}>
                            <p>Tổng tiền: <span>{MoneyUtils.getMoney(tourBookedInfo?.totalPrice?.amount)} VND</span></p>
                            <p style={{color: 'red'}}>{tourBookedInfo?.paymentMethod==='QR'?'Đã thanh toán':'Chưa thanh toán'}</p>
                        </Grid>
                    </Grid>
                </div>
            </Box>
        </div>
    );
};

export default CusInfoBookedTour;
