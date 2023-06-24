import React, {useContext} from "react";

import classNames from "classnames/bind";
import styles from "./BookingInfo.module.scss";
import {Box,} from "@mui/material";
import {EnterpriseContext} from "~/config/provider/EnterpriseProvider";
import DateUtils from "~/utils/DateUtils";
import MoneyUtils from "~/utils/MoneyUtils";

const cx = classNames.bind(styles);
const BookingInfo = (props) => {
    const {enterprise} = useContext(EnterpriseContext);
    const {ageValues, tour, children, adult,totalPrice} = props;

    return (
        <div className={cx("bookingTour")}>
            <Box sx={{width: '100%',}}>
                <div className={cx("box-summary")}>
                    <div className={cx("summary-main")}>
                        <div className={cx("box-img")}>
                            <img src={tour.featureImgUrl} alt={tour?.name}/>
                        </div>
                        <div className={cx("box-title")}>
                            <h3>{tour.name}</h3>

                        </div>
                    </div>
                    <div className={cx("summary-total")}>
                        <table className={cx("tlb-info")}>

                            <tbody>
                            <tr>
                                <td>
                                    <div className={cx("title")}><i class="fa fa-id-card" aria-hidden="true"/> Mã tour
                                    </div>
                                </td>
                                <td>
                                    <div className={cx("info-right")}> {tour.id} </div>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <div className={cx("title")}><i class="fa fa-suitcase" aria-hidden="true"/> Ngày
                                        khởi hành
                                    </div>
                                </td>
                                <td>
                                    <div className={cx("info-right")}>{DateUtils.formatDate(tour?.startDate)}  </div>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <div className={cx("title")}><i class="fa fa-users" aria-hidden="true"/> Người lớn
                                    </div>
                                </td>
                                <td>
                                    <div className={cx("info-right")}>{adult} khách</div>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <div className={cx("title")}><i className="fa fa-users" aria-hidden="true"/> Trẻ em
                                    </div>
                                </td>
                                <td>
                                    <div className={cx("info-right")}> {children} khách</div>
                                </td>
                            </tr>
                            {
                                ageValues.map((item) => (
                                    <tr>
                                        <td>
                                            <div className={cx("title")}><i className="fa fa-money"
                                                                            aria-hidden="true"/>
                                                Giá {item?.aboutAgeType === 'CHILDREN' ? "Trẻ em" : "Người lớn"}
                                            </div>
                                        </td>
                                        <td>
                                            <div
                                                className={cx("info-right")}> {MoneyUtils.getMoney(item?.price?.amount)} VNĐ
                                            </div>
                                        </td>
                                    </tr>
                                ))
                            }
                            </tbody>
                        </table>
                    </div>
                    <div className={cx("summary-total")}>
                        <table className={cx("tlb-info")}>
                            <tbody>
                            <tr className={cx("tr-voucher hidden")}>
                                <td>
                                    <div className={cx("title")}>Giảm giá</div>
                                </td>
                                <td>
                                    {ageValues.map((item) => (
                                        <div className={cx("info-right")}>{MoneyUtils.getMoney(item?.price?.amount -
                                            MoneyUtils.getMoneyDiscounted(item?.price?.amount, item?.sale))} VND/
                                            {item?.aboutAgeType === 'CHILDREN' ? "Trẻ em" : "Người lớn"}</div>

                                    ))
                                    }


                                </td>
                            </tr>
                            <tr className={cx("tr-total")}>
                                <td>
                                    <div className={cx("title")}>Tổng tiền</div>
                                </td>
                                <td>
                                    <div data-price-format="10,900,000 VND" className={cx("info-right")}>
                                        {MoneyUtils.getMoney(totalPrice)} VND
                                    </div>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                        <div className={cx("box-hotline")}><i class="fa fa-phone" aria-hidden="true"/> Gọi <a
                            href="tel:19003398">{enterprise?.hotLine}</a> để được hỗ trợ 24/7
                        </div>
                    </div>

                </div>


            </Box>

        </div>


    );
};

export default BookingInfo;
