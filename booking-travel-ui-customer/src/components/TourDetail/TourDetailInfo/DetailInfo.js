import React from 'react'
import classNames from 'classnames/bind'

import styles from './DetailIfo.module.scss';
import {Container} from '@mui/material';
import Banner from './banner-slider/Banner';
import TimeHowLongConvertor from "~/utils/convertor/TimeHowLongConvertor";
import DateUtils from "~/utils/DateUtils";
import MoneyUtils from "~/utils/MoneyUtils";

const cx = classNames.bind(styles);

function DetailInfo(props) {
    const {tour, schedules} = props;
    return (
        <Container>
            <div className={cx('single-box-content-inner')}>
                <h1 className={cx("title-tour")}>{tour?.name} giá tốt, khởi hành từ {tour?.addressStart?.province}</h1>
                <div className={cx('single-content')}>
                    <div className={cx('owl-carousel', 'owl-loaded', 'owl-drag')}>
                        < div className={cx('owlowl-stage-outer-carousel')}>
                            <div className={cx('owl-stage')}>
                                <div className={cx("owl-item")} style={{width: "620px"}}>
                                    <Banner imgs={tour?.imgUrls}/>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div className={cx("box-tlb-tour")}>
                        <table className={cx("tlb-info-tour")}>
                            <tbody>
                            <tr>
                                <td>
                                    <i class="fa fa-map-marker" aria-hidden="true"/>
                                    <span>{tour?.addressStart?.province}</span>
                                </td>
                                <td>
                                    <i class="fa fa-clock-o" aria-hidden="true"/>
                                    <span>{TimeHowLongConvertor.convert(tour?.timeHowLong)}</span>
                                </td>
                                <td className={cx('traffic')}>
                                    <span>Phương tiện: </span>
                                    <img className={cx("img-traffic")} title="Xe"
                                         src="https://www.vietnambooking.com/wp-content/themes/vietnambooking_master/images/index/tour/icon_traffic/o_to.png"
                                         alt="o_to"/>
                                    <img className={cx("img-traffic")} title="Tàu thủy"
                                         src="https://www.vietnambooking.com/wp-content/themes/vietnambooking_master/images/index/tour/icon_traffic/tau_thuy.png"
                                         alt="tau_thuy"/>
                                </td>
                            </tr>
                            <tr>
                                <td colSpan="3">

                                    <span className={cx("title-tour")}>Mã tour: </span>
                                    <span className={cx("id-tour")}>
                            {tour?.id}
                        </span>
                                </td>
                            </tr>
                            <tr>
                                <td colSpan="3">
                                    <i class="fa fa-calendar" aria-hidden="true"/>
                                    <span>Khởi hành:</span>
                                    <span> {DateUtils.formatDate(tour?.startDate)}, {DateUtils.formatDayWeek(tour?.startDate)} hàng tuần</span>
                                </td>
                            </tr>
                            </tbody>
                        </table>

                    </div>

                    <div className={cx("box-service-tour")}>
                        <h2 className={cx("title-service")}>Dịch vụ kèm theo</h2>
                        <ul className={cx("list-extra-services")}>
                            <li>
                                <img
                                    src="https://www.vietnambooking.com/wp-content/themes/vietnambooking_master/images/index/tour/icon_services/icon_tick.png"
                                    alt="bao hiem"/>
                                &nbsp;&nbsp;Bảo hiểm
                            </li>
                            <li>
                                <img
                                    src="https://www.vietnambooking.com/wp-content/themes/vietnambooking_master/images/index/tour/icon_services/icon_meal.png"
                                    alt="bua an"/>
                                &nbsp;&nbsp;Bữa ăn
                            </li>
                            <li>
                                <img
                                    src="https://www.vietnambooking.com/wp-content/themes/vietnambooking_master/images/index/tour/icon_services/icon_guide.png"
                                    alt="huong dan vien"/>&nbsp;&nbsp;Hướng dẫn viên
                            </li>
                            <li>
                                <img
                                    src="https://www.vietnambooking.com/wp-content/themes/vietnambooking_master/images/index/tour/icon_services/icon_ticket.png"
                                    alt="ve tham quan"/>&nbsp;&nbsp;Vé tham quan
                            </li>
                            <li>
                                <img
                                    src="https://www.vietnambooking.com/wp-content/themes/vietnambooking_master/images/index/tour/icon_services/icon_bus.png"
                                    alt="Xe đưa đón"/>&nbsp;&nbsp;Xe đưa đón
                            </li>
                        </ul>
                    </div>

                    <div className={cx("single-box-excerpt")}>
                        <div className={cx("single-box-excerpt")}>
                            <p>
                                <a href="https://www.vietnambooking.com/du-lich/du-lich-my-tho-can-tho-2-ngay-gia-tot-khoi-hanh-tu-tphcm.html">
                                    <strong>{tour?.name}</strong></a>&nbsp;sẽ dẫn dắt quý khách đến những địa
                                điểm xinh đẹp và thơ mộng.
                                Ghé đến đây, chúng ta sẽ cùng hòa nhịp với nếp sống dân dã cửa người dân địa phương,
                                thưởng thức các món đặc sản trứ danh của Nam Bộ.
                            </p>

                            <p>
                                <span style={{color: "#FF0000"}}><u><b>ĐIỂM HẤP DẪN NHẤT:</b></u></span>
                            </p>

                            <ul>
                                {tour?.description}
                            </ul>

                            <p>
                                <strong>Chương trình đặc biệt cho mùa xuân năm nay,&nbsp;nhanh tay gọi đến
                                    số<span>&nbsp;1900 3398&nbsp;</span>đặt ngay để nhận ưu đãi SHOCK!</strong>
                            </p>
                        </div>
                    </div>
                    <div className={cx('panel-group')}>
                        <div className={cx("panel", 'panel-tour-product')}>
                            <div className={cx("panel-heading")} role="tab" id="heading-program-tour-0">
                                <h4 className={cx("panel-title")}>
                                    <a role="button" id="a-title-program-tour-0" data-toggle="collapse"
                                       data-parent="#tour-product" href="#program-tour-0" aria-expanded="true"
                                       aria-controls="program-tour-0">
                                        Chương trình tour <i class="pull-right fa fa-chevron-down"></i>
                                    </a>
                                </h4>
                            </div>
                            <div id="program-tour-0" className={cx("panel-collapse", 'collapse', 'in')} role="tabpanel"
                                 aria-labelledby="heading-program-tour-0">
                                <div
                                    className={cx("panel-body", 'content-tour-item', 'content-tour-tab-program-tour-0')}>
                                    {schedules?.map(({title, description}) => (
                                        <div>
                                            <h3 style={{textAlign: "justify"}}>
                                      <span style={{color: "#B22222"}}>
                                    <u><strong>{title}</strong></u>
                                      </span>
                                            </h3>

                                            <p>
                                                {description}
                                            </p>
                                        </div>
                                    ))

                                    }


                                    <p style={{textAlign: "center"}}>
                                        <span style={{color: "#0000FF"}}><strong><em>&gt;XEM NGAY GIÁ VÀ THÔNG TIN CHI TIẾT TRONG PHẦN</em></strong><strong><em>&nbsp;</em></strong></span>
                                        <span
                                            style={{color: "#FF8C00"}}><strong><em>DỊCH VỤ BAO GỒM</em></strong></span>
                                        <span style={{color: "#0000FF"}}><strong><em>&nbsp;</em></strong><strong><em>Ở TRÊN&lt;</em></strong></span>
                                    </p>

                                    <p style={{textAlign: "center"}}>
                                    </p>
                                </div>
                            </div>
                        </div>
                        <div className={cx("panel", 'panel-tour-product')}>
                            <div className={cx("panel-heading")} role="tab" id="heading-table-price-1">
                                <h4 className={cx("panel-title")}>
                                    <a role="button" id="a-title-table-price-1" data-toggle="collapse"
                                       data-parent="#tour-product" href="#table-price-1" aria-expanded="true"
                                       aria-controls="table-price-1">
                                        Dịch vụ bao gồm <i class="pull-right fa fa-chevron-down"></i>
                                    </a>
                                </h4>
                            </div>
                            <div id="table-price-1" className={cx("panel-collapse", 'collapse', 'in')} role="tabpanel"
                                 aria-labelledby="heading-table-price-1">
                                <div
                                    className={cx("panel-body", 'content-tour-item', 'content-tour-tab-table-price-1')}>
                                    <table cellpadding="1" cellspacing="1" className={cx("banggia")}>
                                        <thead>
                                        <tr>
                                            <th scope="col">
                                                <strong>KHỞI HÀNH</strong>
                                            </th>
                                            <th colspan="3" rowspan="1" scope="col">
                                                <p>
                                                    <strong>GIÁ TOUR (VND/ KHÁCH</strong>
                                                </p>
                                            </th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr>
                                            <td colspan="1">
                                                <p>
                                                    &nbsp;
                                                </p>
                                            </td>
                                            <td style={{textAlign: "center"}}>
                                                <p style={{margin: 0, justifyContent: "center", display: "flex"}}>
                                                    NGƯỜI LỚN (10 tuổi trở lên)
                                                </p>

                                            </td>
                                            <td style={{textAlign: "center"}}>
                                                <p style={{margin: 0, justifyContent: "center", display: "flex"}}>
                                                    TRẺ EM (5 - 9 tuổi)
                                                </p>

                                            </td>
                                        </tr>
                                        <tr>
                                            <td colspan="1" style={{textAlign: "center"}}>
                                                <span style={{color: "#FF0000"}}><strong>Hàng tuần</strong></span>
                                            </td>
                                            <td style={{textAlign: "center"}}>
                                                <span
                                                    style={{color: "#FF0000"}}><strong>{MoneyUtils.getMoney(tour?.priceStandard?.amount)} VND</strong></span>
                                            </td>
                                            <td style={{textAlign: "center"}}>
                                                <span style={{color: "#FF0000"}}><strong>Liên hệ</strong></span>
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>

                                    <p>
                                        <em>* Trẻ em&nbsp;(Từ 5 - 10 tuổi): Tiêu chuẩn ghế ngồi riêng trên xe, ăn uống,
                                            vé tham quan nhưng phải&nbsp;ngủ chung với&nbsp;người lớn.<br/>
                                            **Giá có thể thay đổi tùy vào thời điểm tour khởi hành và dịch vụ theo yêu
                                            cầu - Liên hệ Tổng đài&nbsp;<span style={{color: "#FF0000"}}><strong>1900 3398</strong></span>&nbsp;để
                                            được báo giá chi tiết.&nbsp;</em>
                                    </p>

                                    <h3 style={{textAlign: "center"}}>
                                        <span style={{color: "#B22222"}}><u><b>GIÁ TOUR BAO GỒM</b></u></span>
                                    </h3>

                                    <ul>
                                        <li style={{textAlign: " justify"}}>
                                            <strong><u>Di chuyển:</u></strong> Xe vận chuyển tốt đời mới đón - tiễn và
                                            phục vụ theo chương trình. (4, 7, 16, 29, 35, 45 chỗ tùy theo số lượng khách
                                            của mỗi tour).
                                        </li>
                                        <li style={{textAlign: " justify"}}>
                                            <u><strong>Lưu trú</strong></u>:&nbsp;Ngủ 2 khách/phòng khách sạn chuẩn 3
                                            sao tiện nghi: hệ thống máy nước nóng lạnh, điện thoại, phòng tắm riêng,
                                            phòng 2-3 người.<br/>
                                            <em>Quý khách có nhu cầu ở phòng đơn vui lòng liên hệ&nbsp;Tổng đài&nbsp;
                                                <span
                                                    style={{color: "#FF0000"}}><strong>1900 3398</strong></span>&nbsp;để
                                                được tư vấn giá.&nbsp;</em>
                                        </li>
                                        <li style={{textAlign: " justify"}}>
                                            <u><strong>Bữa ăn</strong></u>:
                                            <ul>
                                                <li>
                                                    01&nbsp;bữa ăn sáng: Tô, ly.
                                                </li>
                                                <li>
                                                    03 bữa ăn chính: 02 bữa trưa + 01 bữa tối - có món canh hoặc lẩu,
                                                    món mặn, món xào, cơm trắng, tráng miệng, trà đá.
                                                </li>
                                            </ul>
                                        </li>
                                        <li style={{textAlign: " justify"}}>
                                            <strong><u>Quà tặng:</u></strong> Nón du lịch + 01 nước 0.5l/khách/ngày.
                                        </li>
                                        <li style={{textAlign: " justify"}}>
                                            Vé tham quan theo chương trình.
                                        </li>
                                        <li style={{textAlign: " justify"}}>
                                            HDV phục vụ, hướng dẫn đoàn suốt tuyến.
                                        </li>
                                        <li style={{textAlign: " justify"}}>
                                            Bảo hiểm du lịch theo quy định.
                                        </li>
                                    </ul>

                                    <h3 style={{textAlign: "center"}}>
                                        <span style={{color: "#B22222"}}><u><b>GIÁ TOUR KHÔNG BAO GỒM</b></u></span>
                                    </h3>

                                    <ol>
                                        <li style={{textAlign: " justify"}}>
                                            Vui chơi giải trí, dịch vụ cá nhân, và các chi phí ăn uống ngoài chương
                                            trình.
                                        </li>
                                        <li style={{textAlign: " justify"}}>
                                            Tiền Tip cho tài xế và hướng dẫn viên.
                                        </li>
                                        <li style={{textAlign: " justify"}}>
                                            Hóa đơn giá trị gia tăng (VAT). Nếu quý khách có nhu cầu xuất hóa đơn, công
                                            ty du lịch sẽ phụ thu thêm 10% trên giá tour.
                                        </li>
                                        <li style={{textAlign: " justify"}}>
                                            Các khoản phụ thu (nếu có).
                                        </li>
                                        <li style={{textAlign: " justify"}}>
                                            Phụ thu phòng đơn, phòng đôi, phòng 3 nếu quý khách có nhu cầu.
                                        </li>
                                    </ol>

                                    <p style={{marginLeft: " 21.85pt", textAlign: " justify"}}>
                                        <strong><em>Lưu ý: Giá trẻ em chỉ áp dụng khi số lượng trẻ em không chiếm đến
                                            10% số lượng cả đoàn khách.</em></strong>
                                    </p>

                                    <ul dir="ltr">
                                    </ul>
                                </div>
                                <div className={cx("box-readmore", 'readmore-table-price-1')}>
                                </div>
                            </div>
                        </div>
                        <div className={cx("panel", 'panel-tour-product')}>
                            <div className={cx("panel-heading")} role="tab" id="heading-tour-rule-2">
                                <h4 className={cx("panel-title")}>
                                    <a role="button" id="a-title-tour-rule-2" data-toggle="collapse"
                                       data-parent="#tour-product" href="#tour-rule-2" aria-expanded="true"
                                       aria-controls="tour-rule-2" class="collapsed">
                                        Quy định <i class="pull-right fa fa-chevron-down"></i>
                                    </a>
                                </h4>
                            </div>
                            <div id="tour-rule-2" className={cx("panel-collapse", 'collapse', 'in')} role="tabpanel"
                                 aria-labelledby="heading-tour-rule-2">
                                <div
                                    className={cx("panel-body", 'content-tour-item', 'content-tour-tab-tour-rule-2', 'active')}>
                                    <p>
                                    </p>

                                    <h3 dir="ltr" style={{textAlign: " center"}}>
                                        <span style={{color: "#B22222"}}><u><strong>QUY TRÌNH ĐĂNG KÝ TOUR</strong></u></span>
                                    </h3>

                                    <ul dir="ltr">
                                        <li role="presentation" style={{textAlign: " justify"}}>
                                            <strong>Đợt 01:</strong> Quý khách thanh toán 70% giá trị của tour ngay khi
                                            đăng ký mua tour.​​
                                        </li>
                                        <li role="presentation" style={{textAlign: " justify"}}>
                                            <strong>Đợt 02:</strong> Quý khách thanh toán 30% giá trị của tour trước
                                            lịch khởi hành 03 ngày.
                                        </li>
                                    </ul>

                                    <p dir="ltr" style={{textAlign: " justify"}}>
                                        <u><strong>*Lưu ý:</strong></u> Đối với những tour quý khách đăng ký sát lịch
                                        khởi hành từ 03 cho đến 05 ngày, quý khách vui lòng thanh toán 100% giá trị của
                                        tour.
                                    </p>

                                    <h3 dir="ltr" style={{textAlign: " center"}}>
                                        <span
                                            style={{color: "#B22222"}}><u><strong>ĐIỀU KIỆN HỦY TOUR</strong></u></span>
                                    </h3>

                                    <p dir="ltr" style={{textAlign: " justify"}}>
                                        <u><em><strong>Trường hợp hủy bỏ dịch vụ từ Vietnam Booking:</strong></em></u>
                                    </p>

                                    <p dir="ltr" style={{textAlign: " justify"}}>
                                        Nếu <strong>Vietnam Booking</strong> không thực hiện được chuyến du lịch/ dịch
                                        vụ, công ty phải báo ngay cho khách hàng biết và thanh toán lại cho khách hàng
                                        toàn bộ số tiền mà khách hàng đã đóng trong vòng 3 ngày kể từ lúc chính thức
                                        thông báo hủy chuyến đi/ dịch vụ du lịch bằng hình thức tiền mặt hoặc chuyển
                                        khoản.
                                    </p>

                                    <p dir="ltr" style={{textAlign: " justify"}}>
                                        <em><strong><u>Trường hợp hủy bỏ dịch vụ từ Quý khách hàng:</u></strong></em>
                                    </p>

                                    <p dir="ltr" style={{textAlign: " justify"}}>
                                        Trong trường hợp không thể tiếp tục sử dụng dịch vụ/ tour, Quý khách phải thông
                                        báo cho Công ty bằng văn bản hoặc email (Không giải quyết các trường hợp liên hệ
                                        chuyển/ hủy tour qua điện thoại). Đồng thời Quý khách vui lòng mang Biên bản
                                        đăng ký tour/ dịch vụ &amp; biên lai đóng tiền đến văn phòng Vietnam Booking để
                                        làm thủ tục hủy/ chuyển tour.
                                    </p>

                                    <ul dir="ltr">
                                        <li style={{textAlign: " justify"}}>
                                            Các trường hợp chuyển/ đổi dịch vụ/ tour: Cty sẽ căn cứ xem xét tình hình
                                            thực tế để tính phí và có mức hỗ trợ Quý khách hàng
                                        </li>
                                        <li style={{textAlign: " justify"}}>
                                            Trường hợp hủy dịch vụ/ tour: Quý khách phải chịu chi phí hủy tour/ dịch vụ
                                            theo quy định của Vietnam Booking và toàn bộ phí ngân hàng cho việc thanh
                                            toán trực tuyến.
                                        </li>
                                    </ul>

                                    <p style={{textAlign: " justify"}}>
                                        <strong>Phí hủy được quy định như sau:</strong>
                                    </p>

                                    <ul>
                                        <li style={{textAlign: " justify"}} value="5">
                                            Trước 28&nbsp;ngày&nbsp;khởi hành: Không tính phí.
                                        </li>
                                        <li style={{textAlign: " justify"}} value="5">
                                            Trước 18&nbsp;ngày khởi hành: 50% giá tour.
                                        </li>
                                        <li style={{textAlign: " justify"}} value="5">
                                            Trước 5&nbsp;ngày khởi hành: 100% giá tour.
                                        </li>
                                        <li style={{textAlign: " justify"}}>
                                            Trường hợp quý khách đến trễ giờ khởi hành được tính là hủy 3 ngày trước
                                            ngày khởi hành.
                                        </li>
                                        <li style={{textAlign: " justify"}}>
                                            <strong>Giai đoạn Lễ/Tết: không hoàn, không hủy, không đổi.</strong>
                                        </li>
                                    </ul>

                                    <p style={{textAlign: " justify"}}>
                                        Việc huỷ bỏ chuyến đi phải được thông báo trực tiếp với Công ty hoặc qua fax,
                                        email, tin nhắn và phải được Công ty xác nhận. Việc huỷ bỏ bằng điện thoại không
                                        được chấp nhận.<br/>
                                        Các ngày đặt cọc, thanh toán, huỷ và dời tour: không tính thứ 07, Chủ Nhật.
                                    </p>

                                    <h3 dir="ltr" style={{textAlign: " center"}}>
                                        <span style={{color: "#B22222"}}><u><strong>NHỮNG LƯU Ý KHÁC</strong></u></span>
                                    </h3>

                                    <ul dir="ltr">
                                        <li style={{textAlign: " justify"}}>
                                            Ngày khởi hành, Quý khách vui lòng tập trung tại điểm đón theo quy định.
                                        </li>
                                        <li style={{textAlign: " justify"}}>
                                            Chúng tôi không chịu trách nhiệm khi Quý khách đến trễ.
                                        </li>
                                        <li role="presentation" style={{textAlign: " justify"}}>
                                            Quý khách phải mang theo: giấy tờ tùy thân hợp pháp (CMTND hoặc Passport).
                                        </li>
                                        <li role="presentation" style={{textAlign: " justify"}}>
                                            Quý khách là người ăn chay vui lòng mang thêm đồ ăn chay theo để đảm bảo
                                            khẩu vị của mình.
                                        </li>
                                        <li role="presentation" style={{textAlign: " justify"}}>
                                            Bất cứ dịch vụ nào trong tour nếu Quý khách không sử dụng cũng không được
                                            hoàn lại.
                                        </li>
                                        <li role="presentation" style={{textAlign: " justify"}}>
                                            Hướng dẫn viên có quyền sắp xếp lại thứ tự các điểm thăm quan cho phù hợp
                                            điều kiện từng ngày khởi hành cụ thể nhưng vẫn đảm bảo tất cả các điểm thăm
                                            quan trong chương trình.
                                        </li>
                                    </ul>

                                    <p dir="ltr" role="presentation" style={{textAlign: " justify"}}>
                                        **Trong những trường hợp khách quan như: khủng bố, thiên tai…hoặc do có sự cố,
                                        có sự thay đổi lịch trình của các phương tiện vận chuyển công cộng như : máy
                                        bay, tàu hỏa…thì Vietnam Booking&nbsp;sẽ giữ quyền thay đổi lộ trình bất cứ lúc
                                        nào vì sự thuận tiện, an toàn cho khách hàng và sẽ không chịu trách nhiệm bồi
                                        thường những thiệt hại phát sinh**.<br/>
                                        ***Nếu số khách tham gia không đủ số lượng tối thiểu để khởi hành, Công ty sẽ hỗ
                                        trợ dời sang ngày khởi hành gần nhất hoặc hoàn lại phí tour như đã đặt
                                        cọc&nbsp;cho quý khách​***.
                                    </p>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </Container>

    )
}

export default DetailInfo