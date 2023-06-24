import React from 'react';


import InformationUserWrapper from "~/pages/InformationUser/wrapper";
import InfoHotelBooked from "~/pages/InformationUser/info-hotel-booked/InfoHotelBooked";

function InfoHotelBookedManager(props) {
    return (
        <div style={{
            width: "100%",
            paddingTop: "var(--padding-top)",
            backgroundColor: "#f5f5f5",
            paddingBottom: "20px  "
        }}>
            <InformationUserWrapper child={<InfoHotelBooked/>}/>
        </div>
    );
}

export default InfoHotelBookedManager;
