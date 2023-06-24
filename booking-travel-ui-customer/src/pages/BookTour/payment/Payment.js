import React, {useState} from "react";

import classNames from "classnames/bind";
import styles from "./Payment.module.scss";
import {Box, Grid} from "@mui/material";
import {Menu, MenuItem} from "react-pro-sidebar";
import PaymentQr from "./PaymentQr/PaymentQr";
import PaymentOffice from "./PaymentOffice/PaymentOffice";

const cx = classNames.bind(styles);

const Payment = (props) => {
    const {totalPayment, tour, name, customerId, children, adult, email, telephone, note, requests, salePrice} = props;
    const [methodPayment, setMethodPayment] = useState('QR');

    return (
        <Grid item xs={8}>
            <Box sx={{width: '100%'}}>

                <div className={cx('tour-box-content')}>
                    <div className={cx("box-menu-tab")}>
                        <h3>Thanh toán qua</h3>
                        <Menu>
                            <MenuItem onClick={() => setMethodPayment('QR')}
                                      key={1}
                                      style={{
                                          backgroundColor: methodPayment === 'QR' ? "#0064D2" : "",
                                          width: '103%',
                                          marginLeft: '-6px'
                                      }}
                                      className={cx('menu-item')}
                            >
                                                              <span style={{marginLeft: "2rem"}}>

                                Quét mã QR
                                                              </span>
                            </MenuItem>
                            <MenuItem key={2}
                                      onClick={() => setMethodPayment('IN_OFFICE')}
                                      style={{
                                          backgroundColor: methodPayment === 'IN_OFFICE' ? "#0064D2" : "",
                                          width: '103%',
                                          marginLeft: '-6px'
                                      }}

                                      className={cx('menu-item')}
                            >
                                                              <span style={{marginLeft: "2rem"}}>

                                Tại văn phòng
                                                              </span>
                            </MenuItem>
                        </Menu>
                    </div>
                    <div className={cx('box-content-tab')}>
                        <div className={cx("box-content-top")}>

                        </div>

                        {methodPayment === 'QR' ?
                            <PaymentQr name={name}
                                       customerId={customerId}
                                       salePrice={salePrice}
                                       email={email}
                                       telephone={telephone}
                                       requests={requests}
                                       note={note} children={children} adult={adult} methodPayment={methodPayment}
                                       tour={tour} totalPayment={totalPayment}/> :
                            <PaymentOffice name={name}
                                           customerId={customerId}
                                           salePrice={salePrice}
                                           email={email}
                                           telephone={telephone}
                                           requests={requests}
                                           note={note} children={children} adult={adult} methodPayment={methodPayment}
                                           tour={tour} totalPayment={totalPayment}/>}
                    </div>

                </div>

            </Box>
        </Grid>


    );
};

export default Payment;
