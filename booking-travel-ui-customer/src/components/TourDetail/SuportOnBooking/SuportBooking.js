import React, {useContext, useEffect, useState} from 'react'
import classNames from 'classnames/bind';

import styles from './SuportBooking.module.scss'
import DateUtils from "~/utils/DateUtils";
import MoneyUtils from "~/utils/MoneyUtils";
import {UserContext} from "~/config/provider/UserProvider";
import config from "~/config";

const cx = classNames.bind(styles);

function SuportBooking(props) {
    const {setError, tour} = props;
    const [children, setChildren] = useState();
    const [adult, setAdult] = useState();
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
            if ((!children && !adult) || (children === 0 && adult === 0)) {
                setError(true);
                setTimeout(() => {
                    setError(false);
                }, 2000);
            } else
                window.location.href = `/booked-tour/id/${tour?.id}/children/${children === undefined ? 0 : children}/adult/${adult === undefined ? 0 : adult}`;
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
                            <td colspan="2">
                                <span className={cx("price-tour")}>
                                    <div className={cx("title-price-old")}>
                                      <del>{MoneyUtils.getMoney(tour?.priceStandard?.amount)} VND</del>
                                      </div>
                                    {MoneyUtils.getMoney(MoneyUtils.getMoneyDiscounted(tour?.priceStandard?.amount,
                                        tour?.discount))} <span>VND/người</span>
                                    </span>
                            </td>
                        </tr>
                        <tr>
                            <td><label>Khởi hành</label></td>
                            <td>
                                <input required="required" name="id" value="70767" type="hidden"/>
                                <input readOnly data-role="none" value={DateUtils.formatDate(tour?.startDate)}
                                       name="date_start"
                                       className={cx("form-control", 'txt-date-start', 'hasDatepicker')}
                                       required="required" id="dp1682580715255"/>

                            </td>
                        </tr>
                        <tr>
                            <td><label>Số khách</label></td>
                            <td>
                                <input onKeyPress={handleCodeKeyPress}
                                       type={"tel"} value={adult} onChange={(event) =>
                                    setAdult(event.target.value)} placeholder='Người lớn'/>
                                <input
                                    onKeyPress={handleCodeKeyPress}
                                    type={"tel"} value={children}
                                    onChange={(event) => setChildren(event.target.value)}
                                    placeholder='Trẻ em'/>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <button onClick={booking}
                                        className={cx("btn-submit-set-tour")}>Đặt Tour
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
                <div
                    className={cx("box-signature", 'type-sidebar', 'sidebar-tour-item', 'sidebar-box-item', 'sidebar-box-item-default')}>
                    <div style={{marginTop: "20px"}} className={cx("box-title")}>
                        <h3>Liên hệ tư vấn viên</h3>
                    </div>
                    <ul className={cx("list-item")}>
                        <li>
                            <div className={cx("box-icon")}>
                                <i class="fa fa-volume-control-phone" aria-hidden="true"></i>
                            </div>
                            <div className={cx("box-content")}>
                                <div className={cx("box-name")}>Ms. Nghiệp (Tour Đoàn)</div>
                                <div className={cx("box-phone")}><a href="tel:0935334174">0935 334 174</a></div>
                            </div>
                        </li>
                        <li>
                            <div className={cx("box-icon")}>
                                <i class="fa fa-volume-control-phone" aria-hidden="true"></i>

                            </div>
                            <div className={cx("box-content")}>
                                <div className={cx("box-name")}>Ms. Linh Đan</div>
                                <div className={cx("box-phone")}><a href="tel:0901196164">0901 196 164</a></div>
                            </div>
                        </li>
                    </ul>
                </div>

            </div>
        </div>
    )
}

export default SuportBooking
