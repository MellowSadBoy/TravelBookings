import React, {useEffect, useState} from "react";
import classNames from "classnames/bind";
import styles from "./FilterTour.module.scss";
import {Container, Grid} from "@mui/material";
import Banner from "~/components/main-home/banner-slider";
import SearchHome from "~/components/main-home/search";
import BackTop from "~/components/BackTop";
import FilterTourItem from "~/pages/filterTour/filterItem";
import {useParams} from "react-router-dom";


const cx = classNames.bind(styles);

function FilterTour(props) {
    let {id, name} = useParams();

    return (
        <div className={cx("wrapper")}>
            <Container style={{paddingLeft: "0", paddingRight: "0"}}>
                <Banner/>
                <SearchHome/>
                <FilterTourItem id={id} name={name}/>
                <BackTop/>
            </Container>

        </div>
    );
}

export default FilterTour;
