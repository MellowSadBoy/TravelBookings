import React, {useEffect, useState} from 'react';
import classNames from "classnames/bind";

import styles from './Content.module.scss';
import ContentItem from "~/components/main-home/content-item";
import {filterTour} from "~/services/workspaces.sevices";


const cx = classNames.bind(styles);

function Content(props) {
    const [tourLastHour, setTourLastHour] = useState([]);
    const [tourSummer, setTourSummer] = useState([]);
    const [tourDomestic, setTourDomestic] = useState([]);
    const [tourAbroad, setTourAbroad] = useState([]);
    useEffect(() => {
        async function fetchData() {
            const filterLastHour = {
                filterType: "TOUR_LAST_HOUR",
                maxResult: 4
            }
            await filterTour(filterLastHour).then(result => setTourLastHour(result.data.searchList));
            const filterSummer = {
                filterType: "TOUR_SUMMER",
                maxResult: 4

            }
            await filterTour(filterSummer).then(result => setTourSummer(result.data.searchList));
            const filterDomestic = {
                type: 'DOMESTIC',
                maxResult: 4

            }
            await filterTour(filterDomestic).then(result => setTourDomestic(result.data.searchList));
            const filterAbroad = {
                type: "ABROAD",
                maxResult: 4

            }
            await filterTour(filterAbroad).then(result => setTourAbroad(result.data.searchList));
        }

        fetchData();

    }, []);


    return (
        <div className={cx("container")}>
            <ContentItem id={"filterType"} name={"TOUR_LAST_HOUR"} title={"TOUR GIỜ CHÓT"} tourList={tourLastHour}/>
            <ContentItem id={"filterType"} name={"TOUR_SUMMER"} title={"TOUR HÈ"} tourList={tourSummer}/>
            <ContentItem id={"type"} name={"DOMESTIC"} title={"TOUR TRONG NƯỚC"} tourList={tourDomestic}/>
            <ContentItem id={"type"} name={"ABROAD"} title={"TOUR QUỐC TẾ"} tourList={tourAbroad}/>


        </div>

    );
}

export default Content;
