import React, {useEffect, useState} from "react";
import classNames from "classnames/bind";
import {Container, Grid} from '@mui/material'
import styles from "./FilterTourItem.module.scss";
import {useMediaQuery} from "react-responsive";

import {Pagination, PaginationItem} from "@mui/lab";
import {ArrowLeft, ArrowRight} from "~/components/Icon";
import {filterTour} from "~/services/workspaces.sevices";
import MoneyUtils from "~/utils/MoneyUtils";
import TimeHowLongConvertor from "~/utils/convertor/TimeHowLongConvertor";
import DateUtils from "~/utils/DateUtils";
import {CiClock2} from "react-icons/ci";
import {CgCalendarDates} from "react-icons/cg";
import {TfiLocationPin} from "react-icons/tfi";

const cx = classNames.bind(styles);

function FilterTourItem(props) {
    let {id, name} = props;
    const [page, setPage] = useState(1);
    const [rowsPerPage] = useState(5);
    const startIndex = (page - 1) * rowsPerPage;
    const endIndex = startIndex + rowsPerPage;
    const [tours, setTours] = useState([]);
    const currentTours = tours.slice(startIndex, endIndex);
    const handleChangePage = (event, newPage) => {
        setPage(newPage);
    };
    useEffect(() => {
        const fetchData = async () => {

            let body = {
                search: name,
                maxResult: 200
            }
            // eslint-disable-next-line default-case
            switch (id) {
                case 'filterType':
                    body = {
                        filterType: name,
                        maxResult: 200

                    }
                    break;
                case 'type':
                    body = {
                        type: name,
                        maxResult: 200
                    }
                    break;

            }
            if (id === 'all' || name === 'all') {
                body = {
                    maxResult: 200
                }
            }
            await filterTour(body).then(result => setTours(result?.data?.searchList));

        }
        fetchData();

    }, [id, name]);
    const desktopSmall = useMediaQuery({maxWidth: 1080});

    return (
        <div className={cx("wrapper")}>
            <div className={cx("box-tours")}>
                {tours.length > 0 ? (
                    <Grid container direction="row" spacing={desktopSmall}>
                        {currentTours?.map((item, index) => {
                            return (
                                <a className={cx("hotel-item__wrapper")}
                                   style={{width: "100%", textDecoration: "none"}}
                                   href={`/tour-detail/id/${item.id}`}>
                                    <Grid
                                        className={cx("")}
                                        style={{width: "100%"}}
                                        key={item?.id}
                                        container
                                    >

                                        <Grid item md={3} xs={3}>
                                            <div className={cx("box-img")}>
                                                <span
                                                    ng-show="LoadingFinish &amp;&amp; prostr_577741!=null &amp;&amp; prostr_577741!=''&amp;&amp; prostr_577741!=undefined"
                                                    className={cx("v-ribbon")}>
                                                        <span data-toggle="tooltip" data-placement="bottom"
                                                              data-delay="{&quot;show&quot;:100, &quot;hide&quot;:50}"
                                                              className={cx("ribbon-content", "normalContent")}
                                                              data-original-title="Ưu Đãi Đặc Biệt">
                                                            <span className={cx("limit-length", "ng-binding")}>Ưu Đãi Đặc Biệt</span>
                                                    </span>

                                                </span>

                                                <img width={250} className={cx("lazy")}
                                                     alt={item.name}
                                                     src={item.featureImgUrl}/>
                                            </div>
                                        </Grid>
                                        <Grid item md={6} xs={6}>
                                            <div className={cx("box-name")}>
                                                <p className={cx("name-tour")}>{item.name}</p>
                                                <div style={{marginLeft: "10px", paddingTop: "20px"}}>

                                                    <div className={cx("box-icon", "color-text")}>
                                                        <CiClock2
                                                            className={cx("icon")}/>{TimeHowLongConvertor.convert(item?.timeHowLong)}
                                                    </div>
                                                    <div className={cx("box-icon", "box-place-tour")}>
                                                        <TfiLocationPin className={cx("icon")}
                                                                        style={{color: "#40a6f2"}}
                                                                        color={"#40a6f2"}/>
                                                        {item?.location?.address}
                                                    </div>
                                                    <div className="hightlight hidden-xs">
                                                        <div className={cx("super-tag")}>
                                                            <i className="fa fa-check"/> Gần gũi với thiên nhiên
                                                        </div>
                                                        <div className={cx("super-tag")}>
                                                            <i className="fa fa-check"/> Đông Dương
                                                        </div>
                                                    </div>
                                                    <div className={cx("box-icon", "box-start-tour", "color-text")}>
                                                        <CgCalendarDates className={cx("icon")}/>
                                                        KH: {DateUtils.formatDayWeek(item?.startDate)} hàng tuần
                                                    </div>
                                                </div>
                                            </div>
                                        </Grid>
                                        <Grid style={{backgroundColor: "#fff"}} item md={3} xs={3}>
                                            <div className={cx("box-price-promotion-tour")}>
                                                <span> {MoneyUtils.getMoney(MoneyUtils.getMoneyDiscounted(item?.priceStandard?.amount, item?.discount))}<sup>đ</sup></span>
                                                <del> {MoneyUtils.getMoney(item?.priceStandard?.amount)}<sup>đ</sup>
                                                </del>
                                                <div className={cx("box-percent-tour")}>
                                                    <div className={cx("arrow-left")}/>
                                                    -{Math.ceil(item?.discount * 100)}%
                                                </div>
                                            </div>
                                        </Grid>
                                    </Grid>
                                </a>

                            );
                        })}
                        <Grid item md={12} style={{paddingTop: "40px"}}>
                            <Pagination
                                count={Math.ceil(tours.length / rowsPerPage)}
                                page={page}
                                onChange={handleChangePage}
                                color="primary"
                                renderItem={(item) => (
                                    <PaginationItem
                                        slots={{
                                            previous: ArrowLeft,
                                            next: ArrowRight,
                                        }}
                                        {...item}
                                    />
                                )}
                            />
                        </Grid>
                    </Grid>
                ) : (
                    <Grid container>
                        <Grid style={{width: "100%"}} item md={12}>
                            <div className={cx("notify")}>
                                <div className={cx("notify-text")}>Không tìm thấy tour</div>
                            </div>
                        </Grid>
                    </Grid>
                )}
            </div>
        </div>
    );
}

export default FilterTourItem;
