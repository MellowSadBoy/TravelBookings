package hcmuaf.edu.vn.bookingtravel.api.model.hotelBooked;

import com.fasterxml.jackson.annotation.JsonFormat;
import hcmuaf.edu.vn.bookingtravel.api.base.model.BaseModel;
import hcmuaf.edu.vn.bookingtravel.api.base.model.MoneyV2;
import hcmuaf.edu.vn.bookingtravel.api.model.hotel.Room;
import hcmuaf.edu.vn.bookingtravel.api.model.hotelBooked.enums.HotelBookedStatus;
import hcmuaf.edu.vn.bookingtravel.api.model.hotelBooked.enums.PaymentMethod;
import hcmuaf.edu.vn.bookingtravel.api.model.user.enums.RoleType;
import lombok.Data;

import java.util.Date;
import java.util.List;


@Data
public class HotelBooked extends BaseModel {
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date cancelledAt;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date startAt;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date endAt;
    private String cancelName;
    private String hotelId;
    private String hotelName;
    private String featuredImgHotel;
    private Room room;
    private HotelBookedStatus status;
    private String note;
    private MoneyV2 tax;
    private MoneyV2 totalPrice;
    private String customerId;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private MoneyV2 salePrice;
    private long quantityPeople;
    private List<String> requesteds;
    private PaymentMethod paymentMethod;
}
