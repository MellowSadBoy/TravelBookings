import React, {useContext, useEffect, useState} from "react";
import classNames from "classnames/bind";
import {
    Container,
    Grid,
    RadioGroup,
    Radio,
    FormControlLabel,
} from "@mui/material";

import styles from "./YourProfile.module.scss";
import UpLoadFileImage from "~/components/UploadFileImage";
import {useMediaQuery} from "react-responsive";
import {getDownloadURL, ref, uploadBytes} from "firebase/storage";
import {storage} from "~/firebase";
import {nanoid} from "nanoid";
import {getUser, updateInfoUser} from "~/services/workspaces.sevices";
import {format} from "date-fns";
import {UserContext} from "~/config/provider/UserProvider";


const cx = classNames.bind(styles);

function YourProfile(props) {
    const [gender, setGender] = useState("");
    const [images, setImages] = useState([]);
    const [open, setOpen] = useState(true);
    const [fullName, setFullName] = useState();
    const [email, setEmail] = useState();
    const [telephone, setTelephone] = useState();
    const [birthday, setBirthday] = useState();
    const [showButton, setShowButton] = useState(true);
    const [rsUser, setRSUser] = useState();
    const maxNumber = 1;
    const id = localStorage.getItem("cs-id");
    const {setShouldUpdate} = useContext(UserContext);
    useEffect(() => {
        async function loadData() {
            await getUser(id).then(data => {
                setRSUser(data?.data);
                setEmail(data?.data?.email);
                setTelephone(data?.data?.telephone);
                setFullName(data?.data?.fullName);
                setGender(data?.data?.gender);
                setBirthday(format(data?.data?.birthday ? data?.data?.birthday : new Date(), 'yyyy-MM-dd'))
            });

        }

        loadData();
        console.log(rsUser);
    }, []);
    const handleGender = (event) => {
        console.log(gender);
        setGender(event.target.value);
    };
    const handleFullName = (event) => {
        setFullName(event.target.value);
    }
    const handleEmail = (event) => {
        setEmail(event.target.value);
    }
    const handlePhone = (event) => {
        setTelephone(event.target.value);
    }
    const handleBirthDay = (event) => {
        setBirthday(event.target.value);
    }

    const onChange = (imageList, addUpdateIndex) => {
        setImages(imageList);
        setShowButton(false)
        setOpen(false);
    };
    const uploadImgFirebase = async () => {
        const imgRef = ref(storage, `images/${id + '_' + nanoid()}`);
        if (images && images.length !== 0) {
            uploadBytes(imgRef, images[0].file).then(response => {
                getDownloadURL(response.ref).then(async (url) => {
                    const body = {
                        gender: gender,
                        telephone: telephone,
                        email: email,
                        fullName: fullName,
                        birthday: birthday,
                        imageUrl: url

                    }
                    const userData = await updateInfoUser(id, body);
                    setShouldUpdate(prev => !prev);

                })
            });
        }
        const body = {
            gender: gender,
            telephone: telephone,
            email: email,
            fullName: fullName,
            birthday: birthday,
        }
        console.log(gender);
        const userData = await updateInfoUser(id, body);
        setShouldUpdate(prev => !prev);
    }
    const isTablet = useMediaQuery({minWidth: 768});
    return (
        <div className={cx("wrapper")}>
            <Container>
                <div className={cx("content-wrapper")}>
                    <Grid container>
                        <Grid item md={12} container>
                            <div className={cx("header")}>
                                <h3 className={cx("header-name")}>Hồ sơ của tôi</h3>
                                <p>Quản lý thông tin hồ sơ để bảo mật tài khoản</p>
                            </div>

                            <div className={cx("info-user")}>
                                <div className={cx("info-item")}>
                                    <Grid container>
                                        {isTablet && (
                                            <Grid container item md={2}>
                                                <h3 className={cx("label")}>Chọn ảnh</h3>
                                            </Grid>
                                        )}
                                        <Grid container item md={10}>
                                            <UpLoadFileImage
                                                imageProduct={rsUser?.imageUrl}
                                                showButton={showButton}
                                                open={open}
                                                images={images}
                                                name={rsUser?.username}
                                                maxNumber={maxNumber}
                                                onChange={onChange}
                                            />
                                        </Grid>
                                    </Grid>
                                </div>
                                <div className={cx("info-item")}>
                                    <Grid container>
                                        <Grid container item md={2}>
                                            <h3 className={cx("label")}>Tên đăng nhập</h3>
                                        </Grid>
                                        <Grid container item md={10}>
                                            <span className={cx("username")}>{rsUser?.username}</span>
                                        </Grid>
                                    </Grid>
                                </div>
                                <div className={cx("info-item")}>
                                    <Grid container>
                                        <Grid container item md={2}>
                                            <h3 className={cx("label")}>Tên</h3>
                                        </Grid>
                                        <Grid container item md={10}>
                                            <input
                                                onChange={handleFullName}
                                                value={rsUser?.fullName !== "" ? rsUser?.fullName : "Chưa có thông tin"}
                                                className={cx("input-item")}/>
                                        </Grid>
                                    </Grid>
                                </div>
                                <div className={cx("info-item")}>
                                    <Grid container>
                                        <Grid container item md={2}>
                                            <h3 className={cx("label")}>Email</h3>
                                        </Grid>
                                        <Grid container item md={10}>
                                            <input onChange={handleEmail}
                                                   value={rsUser?.email !== "" ? rsUser?.email : "Chưa có thông tin"}
                                                   className={cx("input-item")}/>
                                        </Grid>
                                    </Grid>
                                </div>
                                <div className={cx("info-item")}>
                                    <Grid container>
                                        <Grid container item md={2}>
                                            <h3 className={cx("label")}>Số điện thoại</h3>
                                        </Grid>
                                        <Grid container item md={10}>
                                            <input
                                                onChange={handlePhone}
                                                value={rsUser?.telephone !== "" ? rsUser?.telephone : "Chưa có thông tin"}
                                                className={cx("input-item")}/>
                                        </Grid>
                                    </Grid>
                                </div>
                                <div className={cx("info-item")}>
                                    <Grid container alignItems="center">
                                        <Grid container item md={2}>
                                            <h3 className={cx("label")}>Giới tính</h3>
                                        </Grid>
                                        <Grid container item md={10}>
                                            <RadioGroup
                                                value={gender}
                                                onChange={handleGender}
                                                sx={{display: "flex", flexDirection: "row"}}
                                            >
                                                <FormControlLabel
                                                    value="MAN"
                                                    control={<Radio/>}
                                                    label="Nam"
                                                />
                                                <FormControlLabel
                                                    value="WOMEN"
                                                    control={<Radio/>}
                                                    label="Nữ"
                                                />
                                                <FormControlLabel
                                                    value="OTHER"
                                                    control={<Radio/>}
                                                    label="Khác"
                                                />
                                            </RadioGroup>
                                        </Grid>
                                    </Grid>
                                </div>
                                <div className={cx("info-item")}>
                                    <Grid container>
                                        <Grid container item md={2}>
                                            <h3 className={cx("label")}>Ngày sinh</h3>
                                        </Grid>
                                        <Grid container item md={10}>
                                            <input
                                                type="date"
                                                onChange={handleBirthDay}
                                                value={birthday}
                                                className={cx("input-item")}/>
                                        </Grid>
                                    </Grid>
                                </div>
                                <div className={cx("info-item")}>
                                    <Grid container>
                                        <Grid container item md={2}></Grid>
                                        <Grid container item md={10}>
                                            <button
                                                onClick={uploadImgFirebase}
                                                style={{
                                                    padding: "8px 34px",
                                                }} className={cx("btn-save")}>Lưu
                                            </button>
                                        </Grid>
                                    </Grid>
                                </div>
                            </div>
                        </Grid>
                    </Grid>
                </div>
            </Container>
        </div>
    );
}

export default YourProfile;
