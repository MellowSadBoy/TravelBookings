import React, {useEffect} from "react";
import classNames from "classnames/bind";
import {Container} from '@mui/material'

import styles from "./SearchHotel.module.scss";
import Banner from "~/components/main-home/banner-slider";
import BackTop from "~/components/BackTop";
import Section from "~/components/main-home/section";
import Content from "~/components/main-home/content";
import SearchHotelBar from "~/components/SearchHotel/SearchHotelBar";
import Header from "~/components/Header";
import {useParams} from "react-router-dom";
// import {getAllHotels} from "~/services/workspaces.sevices";
const cx = classNames.bind(styles);

function SearchHotel() {
    let { searchedHotels } = useParams();
    const handleSearch = async (searchParams) => {

    };
    return (
        <div className={cx("wrapper")}>
            <Header/>
            <Container style={{paddingLeft:"0",paddingRight:"0"}}>
                <Banner/>
                <SearchHotelBar onSearch={handleSearch}/>
                <BackTop/>
            </Container>
        </div>
    );
}

export default SearchHotel;
