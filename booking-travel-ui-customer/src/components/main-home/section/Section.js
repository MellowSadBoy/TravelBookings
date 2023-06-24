import React, {useEffect, useState} from 'react';
import classNames from "classnames/bind";

import styles from './Section.module.scss';
import {Avatar, Grid, Link} from "@mui/material";

const cx = classNames.bind(styles);

function Section(props) {
    useEffect(() => {
        async function fetchData() {
        }

        fetchData();

    }, []);

    const sectionData = [{
        icon: "https://www.vietnambooking.com/wp-content/uploads/2023/04/tour-le-30-4.png",
        name: "Tour lễ 30/4",
        id: "filterType",
        filter: "TOUR_LAST_HOUR",
    }, {
        icon: "https://www.vietnambooking.com/wp-content/uploads/2023/04/tour-du-lich-he.png",
        name: "Tour hè",
        id: "filterType",
        filter: "TOUR_SUMMER",
    }, {
        icon: "https://www.vietnambooking.com/wp-content/uploads/2023/04/tour-du-lich-nuoc-ngoai.png",
        name: "Tour quốc tế",
        id: "type",
        filter: "ABROAD",
    }, {
        icon: "https://www.vietnambooking.com/wp-content/uploads/2023/04/tour-du-lich-trong-nuoc.png",
        name: "Tour trong nước",
        id: "type",
        filter: "DOMESTIC",
    }]


    return (
        <div className={cx("container")}>
            <Grid container className={cx("main-section")} style={{width: "1140px", marginLeft: "12rem"}}>{
                sectionData.map(({name, icon,id ,filter}) => (
                    <Grid style={{borderRight: "1px solid #eee"}} className={cx("gird-ten")} item container xs={3}>
                        <Grid className={cx("gird-ten")} item xs={12}>
                            <a   href={`/filter-tour/${id}/${filter}`} style={{textDecoration: "none"}}>
                                <Avatar sx={{width: 64, height: 64}} src={icon}/>
                            </a>
                        </Grid>
                        <Grid style={{paddingTop: "3rem"}} className={cx("gird-ten")} item xs={12}>
                            <div className={cx("title-main")}>
                                <a className={cx("title-main")} href={`/filter-tour/${id}/${filter}`} style={{textDecoration: "none"}}>
                                    {name}
                                </a>
                            </div>
                        </Grid>
                    </Grid>))
            }
            </Grid>
        </div>

    );
}

export default Section;
