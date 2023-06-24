import React, {useState} from 'react'
import classNames from 'classnames/bind'
import styles from './PaymentQr.module.scss'
import {LogoVietQR} from "~/components/Icon";
import {createTourBooked, getQRImage} from "~/services/workspaces.sevices";
import config from "~/config";
import {Button, Dialog, DialogActions, DialogContent, DialogContentText} from "@material-ui/core";
import CheckBoxIcon from "@mui/icons-material/CheckBox";

const cx = classNames.bind(styles)

function PaymentQr(props) {
    const {
        totalPayment,
        tour,
        methodPayment,
        name,
        customerId,
        children,
        adult,
        email,
        telephone,
        note,
        requests, salePrice
    } = props;
    const [checkbox, setCheckbox] = useState(false);
    const [showQR, setShowQR] = useState(false);
    const [showSucceed, setShowSucceed] = useState(false);
    const [imgQR, setImgQR] = useState();
    const handleCheckboxChange = (event) => {
        setCheckbox(event.target.checked);
    };

    const confirm = async () => {
        console.log(tour);
        const body = {
            startAt: tour.startDate,
            tourId: tour.id,
            tourName: tour.name,
            featuredImgTour: tour.featureImgUrl,
            note: note,
            timeHowLong: tour.timeHowLong,
            tax: {},
            totalPrice: {
                currencyCode: 'VND',
                amount: totalPayment
            },
            customerId: customerId,
            customerName: name,
            customerEmail: email,
            customerPhone: telephone,
            quantityChildren: children,
            quantityAdult: adult,
            requesteds: requests,
            salePrice:{
                amount:salePrice,
                currencyCode: 'VND',

            },
            paymentMethod: methodPayment
        };
        console.log(body);
        if (checkbox) {
            const qr = await getQRImage(totalPayment);
            if (qr) {
                setImgQR(qr.data);
                setShowQR(true);
                const tourBooked = await createTourBooked(body);
                setTimeout(() => {
                    setShowQR(false);
                    setShowSucceed(true);
                    setTimeout(async () => {
                        window.location.href = config.routes.home;
                        setShowSucceed(false);
                    }, 4000);
                }, 10000);
            }
        }
    }
    return (
        <div>
            <div className={cx("box-content")}>
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

                <Dialog open={showQR}>
                    <DialogContent>
                        <DialogContentText>
                            <img src={imgQR} alt={"Viet QR"}/>
                        </DialogContentText>
                    </DialogContent>
                </Dialog>
                <div className={cx("box-content-top")}>
                    <h3>Thanh toán bằng Quét mã QR
                        <LogoVietQR width={80} height={35} className={cx("pull-right")}/>
                    </h3>
                    <div className={cx("description")}>
                        <div className={cx("box-description")}>
                            <div className={cx("box-note-shop", 'box-note-default')}>

                            </div>
                        </div>
                    </div>
                </div>
                <div className={cx("box-content-bottom")}>
                    <div className={cx("box-response-submit-payment")}/>
                    <div
                        className={cx("box-submit-form", 'submit-payment')}>
                        <div className={cx('type-checkbox', 'input-item', 'form-group')}>
                            <input onChange={handleCheckboxChange} className={cx("form-check-input")}
                                   id="chk-agree-rule-atm_domestic"
                                   name="chk_agree_rule" type="checkbox" value="1"/>
                            <label for="chk-agree-rule-atm_domestic"> Tôi đồng ý với các điều khoản đặt hàng của Mellow
                                Booking</label>
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

export default PaymentQr
