import React, {useEffect} from "react";
import classNames from "classnames/bind";
import {Container} from '@mui/material'


import styles from "./Home.module.scss";
import Banner from "~/components/main-home/banner-slider";
import SearchHome from "~/components/main-home/search";
import BackTop from "~/components/BackTop";
import Section from "~/components/main-home/section";
import Content from "~/components/main-home/content";
import {Navbar} from "react-bootstrap";

const cx = classNames.bind(styles);

function Home(props) {
    return (
        <div className={cx("wrapper")}>
            <Container style={{paddingLeft:"0",paddingRight:"0"}}>
                <Navbar/>
                <Banner/>
                <SearchHome/>
                <Section/>
                <Content/>
                <BackTop/>
            </Container>
        </div>
    );
}

export default Home;
