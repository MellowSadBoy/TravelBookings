import React, {useEffect, useState} from 'react'
import classNames from "classnames/bind";
import styles from "./HotelDetail.module.scss";
import Banner from '~/components/TourDetail/TourDetailInfo/banner-slider/Banner';
import SearchHome from '~/components/main-home/search/SearchHome';
import {Container, Grid} from '@mui/material';

import {useParams} from "react-router-dom";
import {getRoomDetail} from "~/services/workspaces.sevices";
import {Dialog, DialogContent, DialogContentText} from "@material-ui/core";
import {BiErrorCircle} from "react-icons/bi";
import SuportBookingHotel from "~/components/HotelDetail/SuportOnBookingHotel";
import MoneyUtils from "~/utils/MoneyUtils";
import {getStarIcon} from "~/utils/getStarArr";

const cx = classNames.bind(styles);

function HotelDetail() {
    const {id} = useParams();
    const [error, setError] = useState(false);
    const [hotelDetail, setHotelDetail] = useState({});
    useEffect(() => {
        const fetchData = async () => {
            await getRoomDetail(id).then(res =>
                setHotelDetail(res?.data));
        }
        fetchData();
    }, [id]);
    return (
        <div className={cx("wrapper")}>
            <Container style={{paddingLeft: "0", paddingRight: "0"}}>
                <Dialog open={error}>
                    <DialogContent>
                        <DialogContentText>
                            <div style={{
                                display: "flex",
                                justifyContent: "center"
                            }}>
                                <BiErrorCircle size={30} style={{color: "#FF9933"}}/>
                            </div>

                            <div style={{fontSize: "14px"}}>Vui lòng nhập số lượng khách</div>
                        </DialogContentText>
                    </DialogContent>
                </Dialog>
                <div className={cx('single-box-content')}>
                    <div className={cx('breadcrumb-custom', 'du-lich')}>
                        <div className={cx('breadcrumb')}>
            <span itemProp="itemListElement" itemScope="">
                    <a href="https://www.vietnambooking.com/du-lich" itemProp="item">
                        <span itemProp="name">Trang chủ</span>
                    </a>
                </span> »
                            <span itemProp="itemListElement" itemScope="">
                      <a href="https://www.vietnambooking.com/du-lich-trong-nuoc.html" itemProp="item">
                          <span itemProp="name">Khách sạn</span>
                      </a>

                 </span> »
                            <span> {hotelDetail?.hotel?.name} giá tốt nhất tại {hotelDetail?.hotel?.address?.province} </span>
                        </div>

                    </div>
                    {/*nội dung*/}
                    <div className={cx('container-content')}>
                        <Grid container spacing={2}>
                            {/*  left*/}
                            <Grid item xs={9}>
                                <Container>
                                    <div className={cx('single-box-content-inner')}>
                                        <h1 className={cx("title-tour")}>{hotelDetail?.hotel?.name}</h1>
                                        <div className={cx('single-content')}>
                                            {/*hình*/}
                                            <div className={cx('owl-carousel', 'owl-loaded', 'owl-drag')}>
                                                < div className={cx('owlowl-stage-outer-carousel')}>
                                                    <div className={cx('owl-stage')}>
                                                        <div className={cx("owl-item")} style={{width: "620px"}}>
                                                            <Banner imgs={hotelDetail?.hotel?.imgUrls}/>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                        </div>
                                    </div>


                                </Container>
                            </Grid>
                            {/*  right*/}
                            <Grid item xs={3} sx={{marginLeft: '-14rem', marginTop: '-1rem'}}>
                                <SuportBookingHotel setError={setError} hotel={hotelDetail?.hotel} room={hotelDetail?.room}/>

                            </Grid>
                        </Grid>
                        <Container>
                            <div className={cx("box-info-summary")}>
                                <div className={cx("container")}>
                                    <div className={cx("row")}>
                                        <div>
                                            <div className={cx("single-box-content", " single-hotel")}>
                                                <div className={cx("box-info-summary-inner")}>
                                                    <div className={cx("box-content-top")}>
                                                        <table className={cx("table")}>
                                                            <tbody>
                                                            <tr>
                                                                <td className={cx("td-col-left")}>
                                                                    <div className={cx("box-info")}>
                                                                        <span className={cx("box-title", 'hotel-type')}>Khách sạn</span>
                                                                        <span className={cx("box-star", 'hotel-star')}>
                                                                        {getStarIcon(hotelDetail?.hotel?.type)}
                                                                    </span>
                                                                    </div>
                                                                    <div className={cx("hotel-title", 'hotel-title')}>
                                                                        <h1>Khách sạn {hotelDetail?.hotel?.name} </h1>
                                                                    </div>
                                                                    <div className={cx("box-address", "hotel-address")}>
                                                                        <h3>{hotelDetail?.hotel?.address?.address}</h3>
                                                                    </div>
                                                                </td>
                                                                <td className={cx("td-col-right", "type-price")}>
                                                                    <div className={cx("price-room-from")}>Giá phòng
                                                                        từ
                                                                    </div>
                                                                    <div className={cx("price-old")}>
                                                                        <div className={cx("old")}>
                                                                            <div
                                                                                className={cx("old")}>{MoneyUtils.getMoney(hotelDetail?.room?.price?.amount)} VND
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div
                                                                        className={cx("price-medium")}>{MoneyUtils.getMoney(MoneyUtils.getMoneyDiscounted(hotelDetail?.room?.price?.amount, hotelDetail?.room?.discount))}
                                                                        VND<span>/ đêm</span></div>

                                                                </td>
                                                            </tr>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                    <div className={cx("box-content-bottom")}>
                                                        <div className={cx("box-facilities")}>
                                                            <div className={cx("box-title")}>
                                                                <h2>Tiện nghi khách sạn</h2>
                                                            </div>
                                                            <div className={cx("content-facilities")}>
                                                                <div><i className="fa-solid fa-person-swimming"/>
                                                                    Hồ bơi ngoài trời
                                                                </div>
                                                                <div><i className="fa-solid fa-dumbbell"/> Trung tâm
                                                                    thể dục
                                                                </div>
                                                                <div><i className="fa-solid fa-spa"/> Khu vực thư
                                                                    giãn
                                                                </div>
                                                                <div><i className="fa-solid fa-utensils"/> Nhà hàng
                                                                </div>
                                                                <div><i className="fa-solid fa-martini-glass"/> Quầy
                                                                    bar
                                                                </div>
                                                                <div><i className="fa-solid fa-wifi"/> Wifi</div>
                                                                <div><i
    className="fa-solid fa-temperature-arrow-up"/> Máy
                                                                    lạnh
                                                                </div>
                                                                <div><i className="fa-solid fa-ban-smoking"/> Phòng
                                                                    không hút
                                                                    thuốc
                                                                </div>
                                                            </div>

                                                        </div>
                                                    </div>
                                                </div>

                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div className={cx("box-map")}>
                                <div className={cx("container")}>
                                    <div className={cx("row")}>
                                        <div className={cx("col-lg-12 col-md-12")}>
                                            <div className={cx("box-map-inner")}>
                                                <div className={cx("box-content")}>
                                                    <h3 className={cx("title")}>Khám phá địa điểm lân cận</h3>
                                                    <div className={cx("content-item")}>
                                                        <ul>
                                                            <li>Công viên
                                                                nước {hotelDetail?.hotel?.address?.province}</li>
                                                            <li>Cổng Hoa Giấy
                                                                Checkin {hotelDetail?.hotel?.address?.province}</li>
                                                            <li>Căn Nhà Hoang Bãi Sau</li>
                                                            <li>Sat</li>
                                                            <li>Monument for War Heroes</li>
                                                            <li>DU LỊCH BẢO
                                                                HÂN {hotelDetail?.hotel?.address?.province}</li>
                                                            <li>Nhà SEO FI / Selfie House</li>
                                                            <li>Chùa Phước Hải Tự</li>
                                                            <li>Linh Sơn Cổ Tự</li>
                                                        </ul>
                                                        <div>

                                                        </div>
                                                    </div>
                                                </div>
                                                <div className={cx("box-button-map")}>
                                                    <a title="">

                                                        <img
                                                            src="https://data.vietnambooking.com/business/hotel/svg/common/icon_map.svg"
                                                            alt="map google"/>
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </Container>
                    </div>

                </div>
            </Container>
        </div>
    )
}

export default HotelDetail;