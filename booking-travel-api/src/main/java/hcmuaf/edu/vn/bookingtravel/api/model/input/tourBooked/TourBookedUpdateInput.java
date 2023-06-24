package hcmuaf.edu.vn.bookingtravel.api.model.input.tourBooked;

import com.fasterxml.jackson.annotation.JsonFormat;
import hcmuaf.edu.vn.bookingtravel.api.base.model.MoneyV2;
import hcmuaf.edu.vn.bookingtravel.api.model.tour.enums.TimeHowLong;
import hcmuaf.edu.vn.bookingtravel.api.model.tourBooked.enums.PaymentMethod;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 18/05/2023, Thứ Năm
 **/
@Data
public class TourBookedUpdateInput {
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date startAt;
    private String tourId;
    private String note;
    private MoneyV2 tax;
    private MoneyV2 totalPrice;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private long quantityChildren;
    private MoneyV2 salePrice;
    private long quantityAdult;
    private List<String> requesteds;
    private PaymentMethod paymentMethod;
}
