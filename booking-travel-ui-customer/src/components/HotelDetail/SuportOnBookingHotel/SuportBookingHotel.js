import React, {useContext, useEffect, useState} from 'react'
import classNames from 'classnames/bind';

import styles from './SuportBooking.module.scss'
import DateUtils from "~/utils/DateUtils";
import MoneyUtils from "~/utils/MoneyUtils";
import {UserContext} from "~/config/provider/UserProvider";
import config from "~/config";

const cx = classNames.bind(styles);

function SuportBookingHotel(props) {
    const {setError, hotel, room} = props;
    const [adult, setAdult] = useState();
    const [dateIn, setDateIn] = useState(new Date());
    const [dateOut, setDateOut] = useState(new Date());

    const [login, setLogin] = useState(false);
    const {customer} = useContext(UserContext);
    useEffect(() => {
        setLogin(customer !== undefined && Object.keys(customer).length > 0);
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [!(customer === undefined), customer]);

    const booking = () => {
        if (!login) {
            window.location.href = config.routes.login;
        } else {
            if ((!adult) || (adult === 0)) {
                setError(true);
                setTimeout(() => {
                    setError(false);
                }, 2000);
            } else
                window.location.href = `/book-hotel/id/${room?.id}/adult/${adult === undefined ? 0 : adult}/check-in/${DateUtils.formatDateRes(dateIn)}/check-out/${DateUtils.formatDateRes(dateOut)}`;
        }
    }

    function handleCodeKeyPress(event) {
        const keyCode = event.keyCode || event.which;
        const keyValue = String.fromCharCode(keyCode);
        // Chỉ cho phép nhập số từ 0-9 và phím điều khiển
        if (!/^[0-9\b]+$/.test(keyValue)) {
            event.preventDefault();
        }
    }


    return (
        <div className={cx('suport')}>
            <div className={cx('sidebar-box-tour')}>
                <div className={cx("box-form-price-tour", 'vertical')}>
                    <table className={cx("tlb-box-price-tour")}>
                        <tbody>
                        <tr>
                        </tr>
                        <tr>
                            <td><label>Ngày nhận phòng</label></td>
                            <td>
                                <input type={"date"} onChange={event =>
                                    setDateIn(DateUtils.convertDate(event.target.value))}
                                       value={DateUtils.formatDateRes(dateIn)} style={{width: "100%"}}/>

                            </td>
                        </tr>
                        <tr>
                            <td><label>Ngày trả phòng</label></td>
                            <td>
                                <input type={"date"}
                                       onChange={event =>
                                           setDateOut(DateUtils.convertDate(event.target.value))}
                                       value={DateUtils.formatDateRes(dateOut)} style={{width: "100%"}}/>

                            </td>
                        </tr>
                        <tr>
                            <td><label>Số khách</label></td>
                            <td>
                                <input onKeyPress={handleCodeKeyPress}
                                       type={"tel"} value={adult} onChange={(event) =>
                                    setAdult(event.target.value)} placeholder='Nhập số lượng người'/>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <button onClick={booking}
                                        className={cx("btn-submit-set-tour")}>Đặt phòng khách sạn
                                </button>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <div className={cx("sidebar-box-item")}>
                    <h3>TỔNG ĐÀI TƯ VẤN</h3>
                    <div className={cx("sidebar-box-content", 'sidebar-hotline')}>
                        <label>Mọi thắc mắc của Quý khách</label>
                        vui lòng gọi:<a href="tel:19003398">1900 3398</a>
                        <span className={cx("span-note")}>Chúng tôi hỗ trợ 24/7</span>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default SuportBookingHotel
