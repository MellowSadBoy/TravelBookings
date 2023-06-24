import React, {useEffect, useState} from 'react';
import classNames from "classnames/bind";

import styles from './ContentItem.module.scss';
import {Avatar, Grid, Link} from "@mui/material";
import TourItem from "~/components/TourItem";

const cx = classNames.bind(styles);

function ContentItem(props) {
    const {title,id,name, tourList} = props;
    useEffect(() => {
        console.log(tourList);
    }, []);


    return (
        <div className={cx("container")}>
            <div className={cx("main-section")}>
                <div className={cx("box-title")}>
                    <table className={cx("table")}>
                        <tbody>
                        <tr>
                            <td style={{width: "250px"}}><h2 className={cx("text-title")}>{title}</h2></td>
                            <td>
                                <div className={cx("box-link-title")}>
                                    <a className={cx("link")}
                                       href={`/filter-tour/${id}/${name}`}>Xem tất cả </a>
                                </div>
                            </td>
                        </tr>
                        </tbody>
                    </table>

                </div>
            </div>
            <Grid container className={cx("main-section")}>
                {tourList?.map(({
                                    id, name, featureImgUrl, timeHowLong,
                                    startDate, priceStandard, location, addressStart, discount
                                }) => (
                    <Grid style={{flexBasis: "23%", maxWidth: "23%"}} xs={3}>
                        <TourItem
                            id={id} name={name} featureImgUrl={featureImgUrl} timeHowLong={timeHowLong}
                            startDate={startDate} priceStandard={priceStandard} location={location}
                            addressStart={addressStart} discount={discount}/>
                    </Grid>
                ))}

            </Grid>
        </div>

    );
}

export default ContentItem;
