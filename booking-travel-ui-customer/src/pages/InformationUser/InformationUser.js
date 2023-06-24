import React, {useEffect, useState} from "react";
import classNames from "classnames/bind";
import styles from "./InformationUser.module.scss";

import YourProfile from "~/pages/InformationUser/YourProfile";
import InformationUserWrapper from "~/pages/InformationUser/wrapper";

const cx = classNames.bind(styles);

function InformationUser(props) {
    const id = localStorage.getItem("cs-id");
    const [userId, setUserId] = useState();

    useEffect(() => {
        setUserId(id);

    }, []);
    return (
        <div className={cx('wrapper')}>
            <InformationUserWrapper userId={userId} child={<YourProfile/>}/>
        </div>
    );
}

export default InformationUser;
