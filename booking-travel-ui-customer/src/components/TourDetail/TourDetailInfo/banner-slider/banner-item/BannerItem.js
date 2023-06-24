import React from 'react';
import classNames from "classnames/bind";
import styles from "./BannerItem.module.scss";
import {Paper} from '@mui/material'

const cx = classNames.bind(styles);

function BannerItem(props) {

    return (
        <Paper className={cx('paper')}>
            <div className={cx("wrapper")}>
                <img className={cx("image-background")}
                     src={props.item.imgUrl}
                     alt={props.title}
                     loading="lazy">

                </img></div>

        </Paper>

    );
}

export default BannerItem;
