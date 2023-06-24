import React from "react";

import classNames from "classnames/bind";
import styles from "./BookHotelInfo.module.scss";
import {Box,} from "@mui/material";
import MoneyUtils from "~/utils/MoneyUtils";
import DateUtils from "~/utils/DateUtils";

const cx = classNames.bind(styles);
const BookHotelInfo = (props) => {
    const {hotel, quantity, room, checkIn, checkOut, discount, total} = props;
    return (
        <div className={cx("bookingTour")}>
            <Box sx={{width: '100%',}}>
                <div className={cx("box-summary")}>
                    <div className={cx("summary-main")}>
                        <div className={cx("box-img")}>
                            <img src={room?.imgUrls} />
                        </div>
                        <div className={cx("box-title")}>
                            <h3>Khách sạn{' '} {hotel?.name}</h3>
                            <div className={cx("description")}> Phòng Premium hướng Biển</div>
                        </div>
                    </div>
                    <div className={cx("summary-total")}>
                        <table className={cx("tlb-info")}>

                            <tbody>
                            <tr>
                                <td>
                                    <div className={cx("title")}><i class="fa fa-sign-in" aria-hidden="true"/> Ngày nhận
                                        phòng
                                    </div>
                                </td>
                                <td>
                                    <div className={cx("info-right")}> {DateUtils.reverseDate(checkIn)} </div>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <div className={cx("title")}><i class="fa fa-sign-out" aria-hidden="true"></i> Ngày
                                        trả phòng
                                    </div>
                                </td>
                                <td>
                                    <div className={cx("info-right")}>{DateUtils.reverseDate(checkOut)} </div>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <div className={cx("title")}><i class="fa fa-users" aria-hidden="true"></i> Số khách
                                        phòng
                                    </div>
                                </td>
                                <td>
                                    <div className={cx("info-right")}> {quantity} khách, 1 phòng</div>
                                </td>
                            </tr>

                            </tbody>
                        </table>
                    </div>
                    <div className={cx("summary-total")}>
                        <table className={cx("tlb-info")}>
                            <tbody>
                            <tr>
                                <td>
                                    <div className={cx("title")}>Số khách tối đa</div>
                                </td>
                                <td>
                                    <div className={cx("info-right")}> {room?.maxPeople} </div>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <div className={cx("title")}> Giá 1 phòng x 1 đêm</div>
                                </td>
                                <td>
                                    <div className={cx("info-right")}> {MoneyUtils.getMoney(room?.price?.amount)} VND
                                    </div>
                                </td>
                            </tr>
                            <tr className={cx("tr-voucher", "hidden")}>
                                <td>
                                    <div className={cx("title")}>Giảm giá</div>
                                </td>
                                <td>
                                    <div className={cx("info-right")}>{MoneyUtils.getMoney(discount)} VND/ đêm</div>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <div className={cx("type-tax")}>Phí dịch vụ</div>
                                </td>
                                <td>
                                    <div className={cx("info-right", "type-tax")}>MIỄN PHÍ</div>
                                </td>
                            </tr>
                            <div className={cx("line")}>

                            </div>
                            <tr className={cx("tr-total")}>
                                <td>
                                    <div className={cx("title")}>Tổng tiền</div>
                                </td>
                                <td>
                                    <div className={cx("info-right")}>{MoneyUtils.getMoney(total)} VND</div>
                                </td>
                            </tr>

                            </tbody>
                        </table>
                        <div>
                            <div className="note">
                                <p>Lưu ý: Nếu số khách vượt quá số lượng cho phép, thì sẽ phụ thu 50.000VND/1 khách</p>
                            </div>

                        </div>
                        <div className={cx("box-hotline")}><i class="fa fa-phone" aria-hidden="true"/> Gọi <a
                            href="tel:19003398">{hotel?.fax}</a> để được hỗ trợ 24/7
                        </div>
                    </div>

                </div>


            </Box>

        </div>


    );
};

export default BookHotelInfo;
