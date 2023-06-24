import React, {useEffect, useRef, useState} from "react";
import classNames from "classnames/bind";
import {Grid} from "@mui/material";
import {useMediaQuery} from "react-responsive";
import {Link} from "react-router-dom";

import styles from "./Login.module.scss";
import config from "~/config";
import Background from "~/assets/logo/Mellowbookingbg.png";
import {FaFacebook} from "react-icons/fa";
import {FcGoogle} from "react-icons/fc";
import {BsApple} from "react-icons/bs";
import {activeUser, getUserToken, loginCustomer} from "~/services/workspaces.sevices";
import {RingLoader} from "react-spinners";
import {encode} from "base-64";
import {auth, ggProvider} from "~/firebase";
import {signInWithPopup} from "firebase/auth";

const cx = classNames.bind(styles);

function Login(props) {
    const isTablet = useMediaQuery({maxWidth: 875});
    const [em, setEm] = useState('');
    const [pw, setPw] = useState('');
    const [loadL, setLoadL] = useState(false);
    const [errCM, setErrCM] = useState(false);
    const [fn, setFn] = useState(null);
    const [im, setIm] = useState(null);
    const [err, setErr] = useState('');
    const [erp, setErp] = useState('');
    const [login, setLogin] = useState(false);
    const [dataGG, setDataGG] = useState();

    const validators = () => {
        setErp(!pw
        || pw.trim() === "" ? "Vui lòng nhập mật khẩu" : "");
        setErr(!em ||
        em.trim() === "" ? "Vui lòng nhập email" : "");
        setLogin(!erp && !err);
    }
    const handleLogin = async () => {
        validators();
        const type = "NORMALLY";
        if (login) {
            setLoadL(true);
            const result = await loginCustomer(em, encode(pw),
                type, fn, im
            );
            const errMsg = result?.data.status !== 200;
            setErrCM(errMsg);
            if (result?.data.status === 200) {
                const user = await getUserToken(result?.data.data, type);
                console.log(user);
                localStorage.clear();
                localStorage.setItem("cs-id", user?.data.data.id);
                window.location.href = config.routes.home;
            }
            setLoadL(false);

        }

    }

    const loginGG = () => {
        signInWithPopup(auth, ggProvider).then(async function (firebaseRes) {
            setLoadL(true);
            const authData = firebaseRes._tokenResponse;
            setDataGG(authData);
            console.log(authData);
            const type = "GOOGLE";
            const result = await loginCustomer(authData.email, encode(authData.localId),
                type, authData.fullName, authData.photoUrl
            );
            const errMsg = result?.data.status !== 200;
            setErrCM(errMsg);
            if (result?.data.status === 200) {
                if (result?.data.data.status !== "ACTIVE") {
                    await activeUser(result?.data.data.id, authData.fullName);
                }
                localStorage.clear();
                localStorage.setItem("cs-id", result?.data.data.id);
                window.location.href = config.routes.home;
            }
            setLoadL(false);

        }).catch(function (error) {
            window.location.reload();
        });

    }

    return (
        <div className={cx("wrapper")}>
            <div
                style={{
                    backgroundImage: `url(${isTablet ? "" : Background})`,
                    height: "600px",
                    backgroundSize: "cover",
                    backgroundRepeat: "no-repeat",
                }}
                className={cx("content-wrapper")}
            >
                <Grid container direction="row" justifyContent="flex-end">
                    <Grid item xs={12} md={5}>
                        <div className={cx("form-login")}>
                            <h1 className={cx("login-text")}>Đăng nhập</h1>
                            <div style={{
                                display: errCM ? 'block' : 'none'
                            }} className={cx("err-lg")}>
                                <div className={cx("ic-err-lg")}>
                                    <svg viewBox="0 0 16 16" className="fJb11i">
                                        <path fill="none" stroke="#FF424F" d="M8 15A7 7 0 108 1a7 7 0 000 14z"
                                              clipRule="evenodd"/>
                                        <rect stroke="none" width="7" height="1.5" x="6.061" y="5" fill="#FF424F"
                                              rx=".75"
                                              transform="rotate(45 6.06 5)"/>
                                        <rect stroke="none" width="7" height="1.5" fill="#FF424F" rx=".75"
                                              transform="scale(-1 1) rotate(45 -11.01 -9.51)"/>
                                    </svg>
                                </div>

                                <div className={cx("tx-err-lg")}>Đăng nhập KHÔNG thành công.
                                    Bạn vui lòng thử lại hoặc đăng nhập bằng cách khác nhé!
                                </div>
                            </div>

                            <div className={cx("container-input")}>
                                <input
                                    className={cx("input")}
                                    type="text"
                                    placeholder=" Email"
                                    value={em}
                                    onChange={e => setEm(e.target.value)}
                                />
                                <span className={cx("error")}>{err}</span>
                            </div>
                            <div className={cx("container-input")}>
                                <input
                                    type="password"
                                    className={cx("input")}
                                    placeholder=" Mật khẩu"
                                    value={pw}
                                    onChange={e => setPw(e.target.value)}
                                />
                                <span className={cx("error")}>{erp}</span>
                            </div>

                            <button onClick={handleLogin} className={cx("btn-function")}>{loadL ? (
                                <RingLoader
                                    size={20}
                                    color={"#ffffff"}
                                    loading={true}
                                />
                            ) : "Đăng nhập"}</button>
                            <div className={cx("link-function")}>
                                <a className={cx("link-item")} href="">
                                    Quên mật khẩu
                                </a>
                                <a className={cx("link-item")} href="">
                                    Đăng nhập với SMS
                                </a>
                            </div>
                            <div className={cx("separate")}>
                                <div className={cx("separate-left")}/>
                                <span className={cx("separate-text")}>Hoặc</span>
                                <div className={cx("separate-right")}/>
                            </div>
                            <div className={cx("outside")}>
                                <button href="" className={cx("btn-outside")}>
                                    <FaFacebook/> Facebook
                                </button>
                                <button onClick={loginGG} className={cx("btn-outside")}>
                                    <FcGoogle/> Google
                                </button>
                                <button href="" className={cx("btn-outside")}>
                                    <BsApple style={{color: "#333"}}/> Apple
                                </button>
                            </div>
                            <h4 className={cx("notify")}>
                                Bạn mới biết đến Mellow booking?
                                <Link to={config.routes.register}> Đăng ký</Link>
                            </h4>
                        </div>
                    </Grid>
                </Grid>
            </div>
        </div>
    );
}

export default Login;
