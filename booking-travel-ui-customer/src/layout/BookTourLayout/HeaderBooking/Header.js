import React, {useContext} from 'react';
import classNames from "classnames/bind";
import {Box, Container, Grid} from "@mui/material";

import styles from "./Header.module.scss";
import logo from '../../../assets/logo/Mellow_reverse.png'
import {Link} from "@material-ui/core";
import config from "~/config";
import {EnterpriseContext} from "~/config/provider/EnterpriseProvider";

const cx = classNames.bind(styles);


function Header(props) {
    const {infoBook} = props;
    const {enterprise} = useContext(EnterpriseContext);

    return (
        <div className={cx('wrapper')}>
            <div className={cx('header-wrapper')}>
                <Container>
                    <Grid container spacing={2}>

                        <Grid item xs={3}>
                            <a href={config.routes.home}>
                                <div className={cx('box-logo')}>
                                    <img src={logo} alt={enterprise?.name}/>
                                </div>
                            </a>

                        </Grid>
                        <Grid item xs={9}>
                            <div className={cx('box-step')}>

                                <ul>
                                    <li className={cx(infoBook ? "passed" : "active")}>
                                        <span>{infoBook ? <i className="fa fa-check"/> : "1"}</span> Điền thông tin
                                    </li>
                                    <li className={cx(infoBook ? "active" : '')}>
                                        <div className={cx("box-bar")}>
                                            <div>
                                                <i className={cx("bar")}/>
                                            </div>
                                            <div><span>2</span> Thanh Toán</div>
                                            <div>
                                            </div>
                                        </div>
                                    </li>

                                </ul>


                            </div>
                        </Grid>
                    </Grid>
                </Container>
            </div>
        </div>
    );
}

export default Header;
