import React, {useEffect, useState} from "react";
import classNames from "classnames/bind";
import {Grid} from "@mui/material";
import {useMediaQuery} from "react-responsive";

import styles from "~/pages/auth/Login/Login.module.scss";
import config from "~/config";
import Background from "~/assets/logo/Mellowbookingbg.png";
import {FaFacebook} from "react-icons/fa";
import {FcGoogle} from "react-icons/fc";
import {BsApple} from "react-icons/bs";
import {filterUser, registerUser} from "~/services/workspaces.sevices";
import {encode} from "base-64";
import {RingLoader} from "react-spinners";
import {Link} from "react-router-dom";


const cx = classNames.bind(styles);

function Register(props) {
    const isTablet = useMediaQuery({maxWidth: 875});
    const [em, setEm] = useState('');
    const [pw, setPw] = useState('');
    const [err, setErr] = useState('');
    const [erp, setErp] = useState('');
    const [register, setRegister] = useState(false);
    const [loadR, setLoadR] = useState(false);

    const getDataR = () => {
        return {

            password: encode(pw),

            roleType: "CUSTOMER",
            user: {
                email: em,
                serviceType: "NORMALLY",
                userStatus: "INACTIVE",
            },
        };
    }
    const validators = () => {
        setErp(!pw
        || pw.trim() === "" ? "Vui lòng nhập mật khẩu" : "");
        setErr(!em ||
        em.trim() === "" ? "Vui lòng nhập email" : "");
        setRegister(!erp && !err);
    }

    const handleRegister = async () => {
        validators();
        const body = {
            email: em,
            serviceType: "NORMALLY",
            status: "ACTIVE"
        }
        const checkedMail = (await filterUser(body))?.data;
        const errMsg = checkedMail?.total !== 0 ? "Email của bạn đã tồn tại" : "";
        setErr(errMsg);
        let data = getDataR();

        if (checkedMail?.total === 0 && register) {
            setLoadR(true);
            const result = await registerUser(data);
            const errMsg = result?.data.status !== 200 ? result?.data.message : "";
            setLoadR(false);
            setErr(errMsg);
            if (result?.data.status === 200) {
                localStorage.setItem("vr-co", encode(result?.data.data.code));
                window.location.href = `/register-verify-code/${em}/id/${result?.data.data.userId}`;
            }
        }

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
                            <h1 className={cx("login-text")}>Đăng ký</h1>
                            <div className={cx("box-input")}>
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
                            </div>
                            <button onClick={handleRegister}
                                    onTouchStart={handleRegister}
                                    className={cx("btn-function")}>  {loadR ? (
                                <RingLoader
                                    size={20}
                                    color={"#ffffff"}
                                    loading={true}
                                />
                            ) : "Đăng ký"}</button>
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
                                <a href="" className={cx("btn-outside")}>
                                    <FaFacebook/> Facebook
                                </a>
                                <a href="" className={cx("btn-outside")}>
                                    <FcGoogle/> Google
                                </a>
                                <a href="" className={cx("btn-outside")}>
                                    <BsApple style={{color: "#333"}}/> Apple
                                </a>
                            </div>
                            <div className={cx("security")}>
                                <div className={cx("security-text")}>
                                    Bằng việc đăng kí, bạn đã đồng ý với Mellow về
                                    <div>
                                        <a href="">Đều khoản dịch vụ</a> &
                                        <a href="">Chính sách bảo mật</a>
                                    </div>
                                </div>
                            </div>

                            <h4 className={cx("notify")}>
                                Bạn đã có tài khoản?
                                <Link to={config.routes.login}> Đăng nhập </Link>
                            </h4>
                        </div>
                    </Grid>
                </Grid>
            </div>
        </div>
    );
}

export default Register;
