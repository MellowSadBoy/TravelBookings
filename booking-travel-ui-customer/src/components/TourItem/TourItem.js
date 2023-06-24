import React from "react";

import classNames from "classnames/bind";
import {Box,} from "@mui/material";
import styles from "./TourItem.module.scss";
import TimeHowLongConvertor from "~/utils/convertor/TimeHowLongConvertor";
import DateUtils from "~/utils/DateUtils";
import MoneyUtils from "~/utils/MoneyUtils";
import {CiClock2} from "react-icons/ci";
import {TfiLocationPin} from "react-icons/tfi";
import {CgCalendarDates} from "react-icons/cg";

const cx = classNames.bind(styles);
const TourItem = (props) => {
    const {
        id, name, featureImgUrl, timeHowLong,
        startDate, priceStandard, location, addressStart, discount
    } = props;
    return (
        <div className={cx("tour")}>
            <Box className={cx('box-tour')}>
                <div className={cx("box-img")}>
                    <a href={`tour-detail/id/${id}`}>
                        <img width={300} height={194} className={cx("lazy")}
                             alt={name}
                             src={featureImgUrl}/>
                        <div className={cx("note-special")}>Trọn gói hành trình đặc sắc nhất</div>
                        <button>Xem tour</button>
                    </a>
                </div>
                <div className={cx("box-country")}>
                    <i class="fa fa-map-marker" aria-hidden="true"/>
                    {location.province}
                </div>
                <div className={cx("box-content-promotion")}>
                    <div className={cx("box-title-content")}>
                        <h3 className={cx("title-h3")}><a href={`tour-detail/id/${id}`}>
                            {name}</a>
                        </h3>
                    </div>
                    <div className={cx("box-summary-content-tour")}>
                        <div className={cx("box-icon", "color-text")}>
                            <CiClock2
                                className={cx("icon")}/>{TimeHowLongConvertor.convert(timeHowLong)}</div>
                        <div className={cx("box-icon", "box-place-tour")}>
                            <TfiLocationPin className={cx("icon")}/>
                            {addressStart.province}
                        </div>
                        <div className={cx("box-icon", "box-start-tour", "color-text")}>
                            <CgCalendarDates className={cx("icon")}/>
                            KH: {DateUtils.formatDayWeek(startDate)} hàng tuần
                        </div>
                    </div>

                    <div className={cx("box-price-promotion-tour")}>
                        <span> {MoneyUtils.getMoney(MoneyUtils.getMoneyDiscounted(priceStandard.amount, discount))}<sup>đ</sup></span>
                        <del> {MoneyUtils.getMoney(priceStandard.amount)}<sup>đ</sup></del>
                        <div className={cx("box-percent-tour")}>
                            <div className={cx("arrow-left")}/>
                            -{Math.ceil(discount * 100)}%
                        </div>
                    </div>


                </div>
            </Box>

        </div>

    );
};

export default TourItem;
