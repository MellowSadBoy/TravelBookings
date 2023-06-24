import React, {useContext, useEffect, useState} from 'react'
import classNames from 'classnames/bind';

import styles from './BookTour.module.scss'
import {Box, Grid} from '@mui/material';
import BookingInfo from "~/components/BookTour/BookingInfo";
import {getTourProfile, getUser} from "~/services/workspaces.sevices";
import {useParams} from "react-router-dom";
import {UserContext} from "~/config/provider/UserProvider";
import MoneyUtils from "~/utils/MoneyUtils";
import Payment from "~/pages/BookTour/payment";

const cx = classNames.bind(styles);

export default function BookTour({infoBook, setInfoBook}) {
    const {id, children, adult} = useParams();
    const [tour, setTour] = useState({});
    const [ageValues, setAgeValues] = useState([]);
    const [name, setName] = useState();
    const [errN, setErrN] = useState(false);
    const [errE, setErrE] = useState(false);
    const [errP, setErrP] = useState(false);
    const [email, setEmail] = useState();
    const [telephone, setTelephone] = useState();
    const [note, setNote] = useState();
    const user_id = localStorage.getItem("cs-id");
    const [requestOfs, setRequestOfs] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            console.log(salePrice())
            await getUser(user_id).then(user => {
                setName(user?.data?.fullName);
                setEmail(user?.data?.email);
                setTelephone(user?.data?.telephone);
                setErrP(!user?.data?.telephone);
                setErrE(!user?.data?.email);
                setErrN(!user?.data?.fullName);
            });
            await getTourProfile(id).then(res => {
                setTour(res?.data?.tour);
                setAgeValues(res?.data?.pricingValues);

            })
        }
        fetchData();
    }, [id]);
    const nextPayment = () => {
        if (!errN && !errE && !errP) {
            setInfoBook(true);
        }
    }

    const totalPrice = () => {
        let total = 0;
        for (const ageValue of ageValues) {
            if (ageValue?.aboutAgeType === 'CHILDREN') {
                total += children * MoneyUtils.getMoneyDiscounted(ageValue?.price?.amount, ageValue?.sale);
            } else {
                total += adult * MoneyUtils.getMoneyDiscounted(ageValue?.price?.amount, ageValue?.sale);
            }
        }
        return total;
    }
    const salePrice = () => {
        let total = 0;
        for (const ageValue of ageValues) {
            if (ageValue?.aboutAgeType === 'CHILDREN') {
                total += children * ageValue?.price?.amount;
            } else {
                total += adult * ageValue?.price?.amount;
            }
        }
        return total - totalPrice();
    }
    const handleCheckboxChange = (event) => {
        const {value} = event.target;
        let temp = [...requestOfs];

        if (event.target.checked) {
            temp.push(value);
        } else {
            temp = temp.filter((item) => item !== value);
        }

        setRequestOfs(temp);
    };
    const handleNameChange = (event) => {
        setName(event.target.value);
        setErrN(!event.target.value || event.target.value === '');
    };
    const handleEmailChange = (event) => {
        setEmail(event.target.value);
        setErrE(!event.target.value || event.target.value === '');
    };
    const handleTelePhoneChange = (event) => {
        setTelephone(event.target.value);
        setErrP(!event.target.value || event.target.value === '');
    };
    return (
        <div className={cx("bookTour")}>
            <Grid container spacing={5}>
                {infoBook ? <Payment name={name}
                                     customerId={user_id}
                                     email={email}
                                     salePrice={salePrice}
                                     telephone={telephone}
                                     requests={requestOfs}
                                     note={note} children={children} adult={adult}
                                     tour={tour} totalPayment={totalPrice()}/> : (
                    <Grid item xs={8}>
                        <h3 className={cx('title')}>THÔNG TIN LIÊN HỆ </h3>
                        <Box sx={{width: '100%',}}>
                            <div className={cx('box')}>
                                <div className={cx('info-customers')}>
                                    <div className={cx('info-customer')}>
                                        <Grid container spacing={1}>
                                            <Grid item xs={12}>
                                                <div className={cx('name')}>
                                                    <label for="txt_fullname">Họ và tên <span
                                                        className={cx("required")}>*</span></label>
                                                    <input value={name} onChange={(event) =>
                                                        handleNameChange(event)}
                                                           id="txt_fullname" type="text"
                                                           className={cx("form-control ")} name="txt_fullname"/>
                                                    <span style={{
                                                        display: errN ? 'block' : 'none'
                                                    }} className={cx("help-block")}>Vui lòng nhập họ và tên</span>
                                                </div>
                                            </Grid>
                                            <Grid item xs={7}>
                                                <div className={cx('email')}>
                                                    <label for="txt_email">Email <span
                                                        className={cx("required")}>*</span></label>
                                                    <input id="txt_email"
                                                           value={email} type="email"
                                                           onChange={(event) => handleEmailChange(event)}
                                                           className={cx("form-control ")} name="txt_email"/>
                                                    <span style={{
                                                        display: errE ? 'block' : 'none'
                                                    }} className={cx("help-block")}>Vui lòng nhập Email</span>
                                                </div>
                                            </Grid>
                                            <Grid item xs={5}>
                                                <div className={cx('phoneNumber')}>
                                                    <label for="txt_phoneNumber">Số điện thoại <span
                                                        className={cx("required")}>*</span></label>
                                                    <input id="txt_phoneNumber"
                                                           value={telephone} onChange={(event) =>
                                                        handleTelePhoneChange(event)}
                                                           type="text" className={cx("form-control ")}
                                                           name="txt_phoneNumber"/>
                                                    <span
                                                        style={{
                                                            display: errP ? 'block' : 'none'
                                                        }}
                                                        className={cx("help-block")}>Vui lòng nhập số điện thoại</span>
                                                </div>
                                            </Grid>

                                        </Grid>
                                    </div>
                                    <div className={cx('services-extra')}>
                                        <label className={cx('title')} for="info_services_extra">Hãy cho chúng tôi biết
                                            Quý khách cần gì? </label>
                                        <Grid container rowSpacing={1} columnSpacing={{xs: 1, sm: 2, md: 3}}
                                              sx={{margin: "1em"}}>
                                            <Grid item xs={6}>
                                                <div className={cx('box-item')}>
                                                    <label for="chk-item-1">
                                                        <input id="chk-item-1"
                                                               onChange={handleCheckboxChange}
                                                               name="info_services_extra][smoke]"
                                                               type="checkbox" value="Hút
                                                        thuốc"/> Hút
                                                        thuốc </label>
                                                </div>
                                            </Grid>
                                            <Grid item xs={6}>
                                                <div className={cx('box-item')}>
                                                    <label for="chk-item-1"> <input id="chk-item-1"
                                                                                    onChange={handleCheckboxChange}
                                                                                    name="info_services_extra][smoke]"
                                                                                    type="checkbox" value="Phòng
                                                        tầng cao"/> Phòng
                                                        tầng cao </label>
                                                </div>
                                            </Grid>
                                            <Grid item xs={6}>
                                                <div className={cx('box-item')}>
                                                    <label for="chk-item-1"> <input id="chk-item-1"
                                                                                    onChange={handleCheckboxChange}
                                                                                    name="info_services_extra][smoke]"
                                                                                    type="checkbox" value="Trẻ em
                                                        hiếu động"/> Trẻ em
                                                        hiếu động </label>
                                                </div>
                                            </Grid>
                                            <Grid item xs={6}>
                                                <div className={cx('box-item')}>
                                                    <label for="chk-item-1"> <input id="chk-item-1"
                                                                                    onChange={handleCheckboxChange}
                                                                                    name="info_services_extra][smoke]"
                                                                                    type="checkbox" value="Ăn chay"/> Ăn
                                                        chay
                                                    </label>
                                                </div>
                                            </Grid>
                                            <Grid item xs={6}>
                                                <div className={cx('box-item')}>
                                                    <label for="chk-item-1"> <input id="chk-item-1"
                                                                                    onChange={handleCheckboxChange}
                                                                                    name="info_services_extra][smoke]"
                                                                                    type="checkbox" value="Có người
                                                        khuyết tật"/> Có người
                                                        khuyết tật </label>
                                                </div>
                                            </Grid>
                                            <Grid item xs={6}>
                                                <div className={cx('box-item')}>
                                                    <label for="chk-item-1"> <input id="chk-item-1"
                                                                                    onChange={handleCheckboxChange}
                                                                                    name="info_services_extra][smoke]"
                                                                                    type="checkbox" value="Phụ nữ
                                                        có thai"/> Phụ nữ
                                                        có thai </label>
                                                </div>
                                            </Grid>
                                        </Grid>
                                    </div>
                                    <div className={cx('next')}>
                                        <div className={cx("box-title")}>Ghi Chú</div>
                                        <textarea value={note} onChange={
                                            (event) => setNote(event.target.value)
                                        } id="txt_note" autoComplete="off" maxLength="200"
                                                  className={cx("form-control")} name="info_services_extra][note]"/>
                                        <div className={cx("box-submit")}>
                                            <button onClick={nextPayment}
                                                    className={cx("btn-submit-contact-booking")}>Tiếp
                                                tục
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </Box>
                    </Grid>)}
                <Grid item xs={4}>
                    <h3 className={cx('title')}>THÔNG TIN ĐẶT TOUR</h3>
                    <BookingInfo totalPrice={totalPrice()} ageValues={ageValues} children={children} adult={adult}
                                 tour={tour}/>
                </Grid>
            </Grid>

        </div>
    )
}

