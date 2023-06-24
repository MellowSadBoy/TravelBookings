import React, {useContext, useState} from 'react'
import classNames from 'classnames/bind'
import styles from './PaymentOffice.module.scss'
import {EnterpriseContext} from "~/config/provider/EnterpriseProvider";
import {createHotelBooked, createTourBooked} from "~/services/workspaces.sevices";
import {Button, Dialog, DialogContent, DialogContentText} from "@material-ui/core";
import config from "~/config";
import CheckBoxIcon from "@mui/icons-material/CheckBox";
import DateUtils from "~/utils/DateUtils";

const cx = classNames.bind(styles)

function PaymentOffice(props) {
    const {
        totalPayment,
        hotel,
        methodPayment,
        name,
        customerId,
        adult,
        email,
        telephone,
        note,
        requests,
        salePrice
        , checkIn,
        checkOut,
        roomId
    } = props;
    const [checkbox, setCheckbox] = useState(false);
    const handleCheckboxChange = (event) => {
        setCheckbox(event.target.checked);
    };
    const [showSucceed, setShowSucceed] = useState(false);
    const confirm = async () => {
        if (checkbox) {
            const day = DateUtils.calculateDay(checkIn, checkOut);

            const body = {
                startAt: checkIn,
                endAt: checkOut,
                hotelId: hotel.id,
                hotelName: hotel.name,
                roomId: roomId,
                featuredImgHotel: hotel.imgUrls[0],
                note: note,
                totalPrice: {
                    currencyCode: 'VND',
                    amount: totalPayment
                },
                customerId: customerId,
                customerName: name,
                customerEmail: email,
                customerPhone: telephone,
                salePrice: {
                    amount: salePrice * day,
                    currencyCode: 'VND'
                },
                quantityPeople: adult,
                requesteds: requests,
                paymentMethod: methodPayment
            };
            console.log(body);
            // Tạo hotel đã đặt
            await createHotelBooked(body);
            setShowSucceed(true);
            setTimeout(() => {
                window.location.href = config.routes.home;
                setShowSucceed(false);
            }, 4000);
        }
    }

    const backHome = () => {
        window.location.href = config.routes.home;
    }
    return (
        <div>
            <div className={cx("box-content")}>
                <h3>Thanh toán tại Khách sạn</h3>
                <Dialog open={showSucceed}>
                    <DialogContent>
                        <DialogContentText>
                            <div style={{fontSize: "1.4rem", display: "flex"}}>
                                <CheckBoxIcon style={{color: "#22c55e"}}/>
                                <span style={{paddingTop: "4px"}}>
                                        Xác nhận thành công
                                      </span>
                            </div>
                            <a href={config.routes.home}>
                                <Button style={{
                                    width: "100%",
                                    backgroundColor: "#0064D2",
                                    color: "#FFFFFF",
                                    marginTop: "3rem",
                                }}
                                >
                                    Quay lại trang chủ
                                </Button>
                            </a>
                        </DialogContentText>
                    </DialogContent>
                </Dialog>

                <div className={cx("description")}>
                    <div className={cx("box-description")}>
                        <div className={cx("box-note-shop box-note-default")}>
                            <label>Khi bạn đến khách sạn của chúng tôi nhận phòng, sẽ tiến hành thanh toán tiền
                                phòng.</label>
                            <div className={cx("box-address")}>
                                <ul className={cx("ul-list-address")}>
                                    <li>
                                        <h3><i class="fa fa-map-marker" aria-hidden="true"/>{
                                            hotel?.address?.province}</h3>
                                        <h4><i class="fa fa-circle" aria-hidden="true"/>
                                            {hotel?.address?.address}
                                            <span>
                        <a href="https://www.google.com/maps/place/S%C3%A2n+B%C3%B3ng+L%E1%BB%A5c+H%E1%BB%93n/@21.5491251,107.3911848,13z/data=!4m15!1m8!3m7!1s0x314b2ef9e22c89c9:0x73ecf5c44ca0357f!2zTOG7pWMgSOG7k24sIELDrG5oIExpw6p1LCBRdeG6o25nIE5pbmgsIFZp4buHdCBOYW0!3b1!8m2!3d21.5538089!4d107.4203669!16s%2Fg%2F1vfn43nb!3m5!1s0x314b2ef92acb6ca7:0x7be58a0c2bd34d04!8m2!3d21.5471143!4d107.4291286!16s%2Fg%2F11dztrycj_"
                           target="_blank">
                        <img src="https://data.vietnambooking.com/common/icon/icon_map_marker.png"/></a></span></h4>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
                <div className={cx("box-content-bottom")}>
                    <div className={cx("box-response-submit-payment")}/>
                    <div
                        className={cx("box-submit-form", 'submit-payment')} noValidate="novalidate">
                        <div className={cx('type-checkbox', 'input-item', 'form-group')}>

                            <label for="chk-agree-rule-atm_domestic"><input
                                className={cx("form-check-input")}
                                id="chk-agree-rule-atm_domestic"
                                onChange={handleCheckboxChange}
                                name="chk_agree_rule" type="checkbox"
                                value="1"/> Tôi đồng ý với các điều khoản
                                đặt hàng của Mellow Booking</label>
                        </div>
                        <div className={cx("box-submit")}>
                            <button onClick={confirm} className={cx("btn-submit-payment")}>Xác nhận</button>
                        </div>

                        <div className={cx("box-security")}><i class="fa fa-lock" aria-hidden="true"/> Security
                            Payment
                        </div>
                    </div>

                    <div className={cx("clearfix")}/>
                </div>
            </div>
        </div>

    )
}

export default PaymentOffice;
