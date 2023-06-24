import React, {useEffect, useState} from 'react';
import classNames from "classnames/bind";
import Carousel from 'react-material-ui-carousel';
import styles from "./Banner.module.scss";
import BannerItem from "~/components/main-home/banner-slider/banner-item";

const cx = classNames.bind(styles);

function Banner(props) {
    const {imgs} = props;
    const [items, setItems] = useState([]);
    useEffect(() => {
        let ms = [];
        for (let i = 0; i < imgs?.length; i++) {
            const img = {
                id: i,
                title: "detail-" + i,
                imgUrl: imgs[i],

            }
            ms.push(img);
        }
        setItems(ms);
    })
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
