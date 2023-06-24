import React from "react";

import classNames from "classnames/bind";
import { Box,  } from "@mui/material";
import styles from "./Tour.module.scss";

const cx = classNames.bind(styles);
const Tour = () => {
  return (
    <div className={cx("tour")}>
        <Box className={cx('box-tour')}>
            <div className={cx("box-img")}>
            <a href="https://www.vietnambooking.com/du-lich/tour-dong-bang-song-cuu-long.html">
                <img className={cx("lazy")} alt="Tour Đồng bằng sông Cửu Long 4N3Đ: Hành trình khám phá 8 tỉnh miền Tây" src="https://www.vietnambooking.com/wp-content/uploads/2023/04/tour-dong-bang-song-cuu-long-10-300x194.jpg" />
                <div className={cx("note-special")}>Trọn gói hành trình đặc sắc nhất</div>
                <button>Xem tour</button>
            </a>
            </div>
            <div className={cx("box-country")}>
            <i class="fa fa-map-marker" aria-hidden="true"></i>
                Cần Thơ, Miền Tây                                
            </div>
            <div className={cx("box-content-promotion") }>
                <div className={cx("box-title-content")}>
                    <h3 className={cx("title-h3")}><a href="https://www.vietnambooking.com/du-lich/tour-dong-bang-song-cuu-long.html">Tour Đồng bằng sông Cửu Long 4N3Đ: Hành trình khám phá 8 tỉnh miền Tây</a></h3>
                </div>
                <div className={cx("box-summary-content-tour")}>
                    <table className={cx("table tlb-time-and-traffic-tour")}>
                        <tbody><tr>
                            <td> <i class="fa fa-clock-o" aria-hidden="true"></i> 4 ngày 3 đêm </td>
                            <td> <img className={cx("lazy")} alt="o_to" src="https://www.vietnambooking.com/wp-content/themes/vietnambooking_master/images/index/tour/icon_traffic/o_to.png" /> </td>
                        </tr>
                    </tbody></table>

                    <div classNames={cx("box-place-tour")}>
                        <i class="fa fa-map-marker-alt" aria-hidden="true"></i>
                        Hồ Chí Minh                                    
                        </div>
                    <div className={cx("box-start-tour")}>
                        <i class="fa fa-calendar-alt" aria-hidden="true"></i>
                        KH: Thứ 2, thứ 5 hàng tuần                                    
                    </div>
                </div>

                <div className={cx("box-price-promotion-tour")}>
                    <span>4,290,000 <sup>đ</sup></span><del>6,129,000 <sup>đ</sup></del>
                    <div className={cx("box-percent-tour")}><div className={cx("arrow-left")}></div>-30%</div>                                </div>


            </div>
        </Box>
   
    </div>

  );
};

export default Tour;
