import React, {useContext, useEffect, useState} from "react";
import {Container, Grid, Popover, Typography, Box} from "@mui/material";
import classNames from "classnames/bind";
import styles from "./TopNavbar.module.scss";
import {useMediaQuery} from "react-responsive";
import {Link} from "react-router-dom";

import {FaRegHandshake} from "react-icons/fa";
import Notify from "~/assets/notify/notify-empty.png";
import {
    MdContactSupport,
    MdNotifications,
    MdOutlineLanguage,
} from "react-icons/md";
import UserDropdown from "./UserDropdown";
import config from "~/config";
import {UserContext} from "~/config/provider/UserProvider";
import {VscOutput} from "react-icons/vsc";
import {AiOutlineAppstore} from "react-icons/ai";
const cx = classNames.bind(styles);

function TopNavbar(props) {
    const maxMd = useMediaQuery({maxWidth: 900});
    const minMd = useMediaQuery({minWidth: 900});
    const [anchorEl, setAnchorEl] = React.useState(null);
    const [languageFunction, setLanguageFunction] = useState(null);
    const [login, setLogin] = useState(false);
    const {customer} = useContext(UserContext);
    const [language, setLanguage] = useState("Tiếng Việt");

    useEffect(() => {
        setLogin(customer !== undefined && Object.keys(customer).length > 0);
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [!(customer === undefined), customer]);

    const handLeLanguageFunctionClick = (event) => {
        setLanguageFunction(event.currentTarget);
    };
    const handleLanguageFunctionClose = () => {
        setLanguageFunction(null);
    };
    const handleNotifyClick = (event) => {
        setAnchorEl(event.currentTarget);
    };

    const handleNotifyClose = () => {
        setAnchorEl(null);
    };
    const changeE = () => {
        setLanguage("English");
    }
    const changeV = () => {
        setLanguage("Tiếng Việt");
    }


    const open = Boolean(anchorEl);
    const show = Boolean(languageFunction);
    const id = open ? "simple-popover" : undefined;
    return (
        <div className={cx("navbar-wrapper")}>
            <Container
                style={{paddingLeft: 0, paddingRight: 0, height: "5rem", marginLeft: "12rem", marginRight: "12rem"}}>
                <Grid className={cx("wrapper")} container={false}>
                    {minMd && (
                        <Grid
                            item
                            md={6}
                            lg={6}
                            container
                            direction="row"
                            justifyContent="flex-start"
                            alignItems="center"
                        >
                            <div className={cx("navbar-left")}>
                                <Link className={cx("navbar-right-item", "navbar-separate")} href="">
                                    <VscOutput size={15} className={cx("icon")}/>
                                    Đặt chổ của tôi
                                </Link>
                                <Link className={cx("navbar-right-item", "navbar-separate")} href="">
                                    <AiOutlineAppstore className={cx("icon")}/>
                                    App Mellow Booking
                                </Link>
                                <Link className={cx("navbar-right-item")} href="">
                                    <FaRegHandshake size={22} className={cx("icon")}/>
                                    Hợp tác với chúng tôi
                                </Link>


                            </div>
                        </Grid>
                    )}
                    <Grid
                        item
                        container
                        md={6}
                        lg={6}
                        direction="row"
                        justifyContent={maxMd ? `flex-start` : "flex-end"}
                        alignItems="center"
                    >
                        <div className={cx("navbar-right")}>
                            <Popover
                                id={id}
                                open={open}
                                anchorEl={anchorEl}
                                onClose={handleNotifyClose}
                                anchorOrigin={{
                                    vertical: "bottom",
                                    horizontal: "right",
                                }}
                                transformOrigin={{
                                    vertical: "top",
                                    horizontal: "right",
                                }}
                                PaperProps={{
                                    style: {
                                        backgroundColor: "transparent",
                                        boxShadow: "none",
                                        borderRadius: 0,
                                    },
                                }}
                            >
                                <Box
                                    sx={
                                        maxMd
                                            ? {
                                                position: "relative",
                                                mt: "10px",
                                                "&::before": {
                                                    backgroundColor: "white",
                                                    content: '""',
                                                    display: "block",
                                                    position: "absolute",
                                                    width: 12,
                                                    height: 12,
                                                    top: -6,
                                                    transform: "rotate(45deg)",
                                                    left: `calc(10% - 6px)`,
                                                },
                                            }
                                            : {
                                                position: "relative",
                                                mt: "10px",
                                                "&::before": {
                                                    backgroundColor: "white",
                                                    content: '""',
                                                    display: "block",
                                                    position: "absolute",
                                                    width: 12,
                                                    height: 12,
                                                    top: -6,
                                                    transform: "rotate(45deg)",
                                                    left: `calc(90% - 6px)`,
                                                },
                                            }
                                    }
                                />
                                <Typography
                                    sx={{
                                        p: 2,
                                        backgroundColor: "white",
                                        borderRadius: "2px",
                                        padding: 0,
                                    }}
                                >
                                    <div className={cx("box-notify")}>
                                        <div className={cx("notify")}>
                                            <img
                                                className={cx("image-notify")}
                                                src={Notify}
                                                alt="notify image"
                                            />
                                            <h4 className={cx("notify-text")}>
                                                Đăng nhập để xem thông báo
                                            </h4>
                                        </div>
                                        <div className={cx("function")}>
                                            <a href={""} className={cx("btn-function")}>
                                                Register
                                            </a>
                                            <a href={""} className={cx("btn-function")}>
                                                Login
                                            </a>
                                        </div>
                                    </div>
                                </Typography>
                            </Popover>
                            <span
                                className={cx("navbar-right-item")}
                                onClick={handleNotifyClick}
                                aria-describedby={id}
                                variant="contained">
                            <MdNotifications outline={true} size={20} className={cx("icon")}/>Thông báo
                            </span>
                            <a className={cx("navbar-right-item")} href={config.routes.chatService}>
                                <MdContactSupport size={20} className={cx("icon")}/>
                                Hỗ trợ
                            </a>
                            <Popover

                                id={id}
                                open={show}
                                anchorEl={languageFunction}
                                onClose={handleLanguageFunctionClose}
                                anchorOrigin={{
                                    vertical: "bottom",
                                    horizontal: "right",
                                }}
                                transformOrigin={{
                                    vertical: "top",
                                    horizontal: "right",
                                }}
                                PaperProps={{
                                    style: {
                                        backgroundColor: "transparent",
                                        boxShadow: "none",
                                        borderRadius: 0,
                                    },
                                }}
                            >
                                <Box
                                    sx={{
                                        position: "relative",
                                        mt: "10px",
                                        "&::before": {
                                            backgroundColor: "white",
                                            content: '""',
                                            display: "block",
                                            position: "absolute",
                                            width: 12,
                                            height: 12,
                                            top: -6,
                                            transform: "rotate(45deg)",
                                            left: "calc(90% - 6px)",
                                        },
                                    }}
                                />
                                <Typography sx={{
                                    p: 2, backgroundColor: "white",
                                    border: "1px solid gray"
                                }}>
                                    <div className={cx("box-language")}>
                                        <span onClick={changeV} className={cx("language-text")}>Tiếng Việt</span>
                                        <span onClick={changeE} className={cx("language-text")}>English</span>
                                    </div>
                                </Typography>
                            </Popover>
                            <span
                                className={cx("navbar-right-item")}
                                onClick={handLeLanguageFunctionClick}
                                aria-describedby={id}
                                variant="contained"
                            >
                <MdOutlineLanguage size={20} className={cx("icon")}/>
                                {language}
              </span>

                            {!login ? (
                                <div className={cx("login-register")}>
                                    <Link
                                        to={config.routes.register}
                                        className={cx("navbar-item", "navbar-separate")}
                                    >
                                        Đăng ký
                                    </Link>
                                    <Link
                                        to={config.routes.login}
                                        className={cx("navbar-right-item")}
                                        style={{
                                            color: "#0064D2"
                                        }}
                                    >
                                        Đăng nhập
                                    </Link>
                                </div>
                            ) : (
                                <UserDropdown
                                    imgUrl={customer?.imageUrl}
                                    id={customer?.id}
                                    userName={customer?.username}/>
                            )}
                        </div>
                    </Grid>
                </Grid>
            </Container>
        </div>
    );
}

export default TopNavbar;
