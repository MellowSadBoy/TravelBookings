import React, {useEffect, useState} from 'react'
import classNames from "classnames/bind";
import styles from "./TourDetail.module.scss";
import Banner from '~/components/main-home/banner-slider/Banner';
import SearchHome from '~/components/main-home/search/SearchHome';
import {Container, Grid} from '@mui/material';
import DetailInfo from '~/components/TourDetail/TourDetailInfo/DetailInfo';
import SuportBooking from '~/components/TourDetail/SuportOnBooking/SuportBooking';
import {useParams} from "react-router-dom";
import {getTourProfile} from "~/services/workspaces.sevices";
import {Dialog, DialogContent, DialogContentText} from "@material-ui/core";
import {BiErrorCircle} from "react-icons/bi";

const cx = classNames.bind(styles);

function TourDetail() {
    const {id} = useParams();
    const [error, setError] = useState(false);
    const [tourDetail, setTourDetail] = useState({});
    useEffect(() => {
        const fetchData = async () => {
            await getTourProfile(id).then(res =>
                setTourDetail(res?.data));
        }
        fetchData();
    }, [id]);
    return (
        <div className={cx("wrapper")}>
            <Container style={{paddingLeft: "0", paddingRight: "0"}}>
                <Banner/>
                <SearchHome/>
                <Dialog open={error}>
                    <DialogContent>
                        <DialogContentText>
                            <div style={{
                                display: "flex",
                                justifyContent: "center"
                            }}>
                                <BiErrorCircle size={30} style={{color: "#FF9933"}}/>
                            </div>

                            <div style={{fontSize:"14px"}}>Vui lòng nhập số lượng khách</div>
                        </DialogContentText>
                    </DialogContent>
                </Dialog>
                <div className={cx('single-box-content')}>
                    <div className={cx('breadcrumb-custom', 'du-lich')}>
                        <div className={cx('breadcrumb')}>
            <span itemProp="itemListElement" itemScope="">
                    <a href="https://www.vietnambooking.com/du-lich" itemProp="item">
                        <span itemProp="name">Du lịch</span>
                    </a>
                </span> »
                            <span itemProp="itemListElement" itemScope="">
                      <a href="https://www.vietnambooking.com/du-lich-trong-nuoc.html" itemProp="item">
                          <span itemProp="name">Du lịch trong nước</span>
                      </a>

                 </span> »
                            <span itemProp="itemListElement" itemScope="">
                    <a href="https://www.vietnambooking.com/du-lich/tour-du-lich/du-lich-can-tho.html" itemProp="item">
                      <span itemProp="name">Du lịch {tourDetail?.tour?.location?.province}</span>
                    </a>
                </span> »
                            <span> {tourDetail?.tour?.name} giá tốt, khởi hành từ {tourDetail?.tour?.addressStart?.province} </span>
                        </div>

                    </div>

                    <div className={cx('container-content')}>
                        <Grid container spacing={2}>
                            <Grid item xs={9}>
                                <DetailInfo schedules={tourDetail?.schedules} tour={tourDetail?.tour}/>
                            </Grid>
                            <Grid item xs={3}>
                                <SuportBooking setError={setError} tour={tourDetail?.tour}/>
                            </Grid>
                        </Grid>
                    </div>

                </div>
            </Container>
        </div>
    )
}

export default TourDetail;