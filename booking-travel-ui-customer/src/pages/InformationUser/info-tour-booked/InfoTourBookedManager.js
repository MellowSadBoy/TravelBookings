import React from 'react';


import InformationUserWrapper from "~/pages/InformationUser/wrapper";
import InfoTourBooked from "~/pages/InformationUser/info-tour-booked/InfoTourBooked";

function InfoTourBookedManager(props) {
    return (
        <div style={{
            width: "100%",
            paddingTop: "var(--padding-top)",
            backgroundColor: "#f5f5f5",
            paddingBottom: "20px  "
        }}>
            <InformationUserWrapper child={<InfoTourBooked/>}/>
        </div>
    );
}

export default InfoTourBookedManager;
