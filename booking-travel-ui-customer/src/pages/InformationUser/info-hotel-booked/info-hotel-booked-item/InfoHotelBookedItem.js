import React, {useContext} from 'react';
import classNames from "classnames/bind";
import styles from "./InfoHotelBookedItem.module.scss";
import {Avatar, Button, createTheme, Grid, ThemeProvider} from "@mui/material";
import {Link} from 'react-router-dom';
import MoneyUtils from "~/utils/MoneyUtils";
import DateUtils from "~/utils/DateUtils";


const cx = classNames.bind(styles);

function InfoHotelBookedItem(props) {
    const {hotelBookedItem} = props;
    const theme = createTheme({
        palette: {
            primary: {
                // Purple and green play nicely together.
                main: '#ff5588 ',
            },
            secondary: {
                // This is green.A700 as hex.
                main: '#e3f2fd',
            },
        },
    });
    return (

        <Link to={`/booked-hotel-detail/${hotelBookedItem.id}`}>
            <div className={cx('orders')}>
                <div className={cx('header')}>

                    <Grid container rowSpacing={1} columnSpacing={{xs: 1, sm: 2, md: 3}}>
                        <Grid item xs={10}>
                            <div className={cx('header-left')}>
                                <h4>{hotelBookedItem.type}</h4>
                            </div>
                        </Grid>
                        <Grid item xs={2}>
                            <div className={cx('header-right')}>

                                <p className={cx("status-order")}>{hotelBookedItem.status === 'CANCELLED' ?
                                    'ĐÃ HUỶ' : 'ĐÃ ĐẶT'}</p>


                            </div>
                        </Grid>

                    </Grid>
                </div>
                <div className={cx('order-item')}>
                    <div className={cx('itemOrder')}>
                        <Grid container spacing={10}>
                            <Grid item xs={1.5}>
                                <div className={cx('itemOrder_avatar')}>
                                    <div className={cx('img-product-order')}>
                                        <Avatar className={cx('avatar-product')}
                                                src={hotelBookedItem?.featuredImgHotel}
                                                variant="square"/>

                                    </div>
                                </div>
                            </Grid>
                            <Grid item xs={10}>
                                <h1>{hotelBookedItem?.hotelName}</h1>

                                <p className={cx('order_purchase_label_variation')}>Mã
                                    Khách sạn: <span>{hotelBookedItem?.hotelId}</span>
                                    <p className={cx('order_purchase_label_variation')}>Ngày đặt:{" "}
                                        <span>{DateUtils.formatDate(hotelBookedItem?.createdAt)}</span>
                                    </p>
                                    <p className={cx('order_purchase_label_quality')}>Số
                                        khách: <span>{hotelBookedItem?.quantityPeople} người</span></p>

                                </p>

                            </Grid>
                        </Grid>
                    </div>

                </div>

                <div className={cx('label-order-bottom')}>
                    <div className={cx('label-order-bottom-price')}>
                        <Grid container spacing={0} columnSpacing={{xs: 1, sm: 2, md: 3}}>
                            <Grid item xs={11}>
                                <p>Tổng Tiền:<span style={{color: '#0064D2', marginLeft: '1rem', fontSize: 18}}>
                               {MoneyUtils.getMoney(hotelBookedItem?.totalPrice?.amount)} VND
                            </span>
                                </p>

                            </Grid>
                        </Grid>

                    </div>

                </div>
            </div>
        </Link>

    );
}

export default InfoHotelBookedItem;
