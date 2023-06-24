import React from 'react';
import classNames from "classnames/bind";
import Carousel from 'react-material-ui-carousel';
import styles from "./Banner.module.scss";
import BannerItem from "~/components/main-home/banner-slider/banner-item";

const cx = classNames.bind(styles);

function Banner(props) {
    var items = [
        {
            id: "banner-left-001",
            title: "deal-hot-trend",
            imgUrl: "https://www.vietnambooking.com/wp-content/uploads/2022/12/tour-mien-tay-mot-ngay-20122022.jpg"
        },
        {
            id: "banner-left-002",
            title: "Sự kiện 2",
            imgUrl: "https://www.vietnambooking.com/wp-content/uploads/2023/02/Tour-du-l%E1%BB%8Bch-h%C3%A8-2023-1.jpg"
        },
        {
            id: "banner-left-003",
            title: "Sự kiện 3",
            imgUrl: "https://www.vietnambooking.com/wp-content/uploads/2023/01/tour-binh-ba-18012023.jpg"
        },
        {
            id: "banner-left-004",
            title: "Sự kiện 4",
            imgUrl: "https://www.vietnambooking.com/wp-content/uploads/2023/01/tour-phu-quy-19012023.png"
        },
        {
            id: "banner-left-005",
            title: "Sự kiện 5",
            imgUrl: "https://www.vietnambooking.com/wp-content/uploads/2022/12/dat-tour-phu-quoc-14122022.jpg"
        },
        {
            id: "banner-left-006",
            title: "Sự kiện 6",
            imgUrl: "https://www.vietnambooking.com/wp-content/uploads/2023/01/tour-philippines-4n3dvietnam-booking.jpg"
        },

    ]
    return (
        <div className={cx('wrapper')}>
            <Carousel className={cx('carousel')}
                      autoPlay
                      infiniteLoop
                      showStatus={false}
                      showIndicators={false}
                      showThumbs={false}
                      interval={2000}>{
                items.map((item, i) =>
                    <BannerItem key={i} item={item}/>)
            }
            </Carousel>
        </div>
    );
}

export default Banner;
