package hcmuaf.edu.vn.bookingtravel.api.model.input.hotelBooked;

import com.fasterxml.jackson.annotation.JsonFormat;
import hcmuaf.edu.vn.bookingtravel.api.base.model.MoneyV2;
import hcmuaf.edu.vn.bookingtravel.api.model.hotelBooked.enums.PaymentMethod;
import lombok.Data;

import java.util.Date;
import java.util.List;


@Data
public class HotelBookedInput {
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date startAt;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date endAt;
    private String hotelId;
    private String hotelName;
    private String roomId;
    private String featuredImgHotel;
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
