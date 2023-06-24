package hcmuaf.edu.vn.bookingtravel.api.model.tourBooked;

import com.fasterxml.jackson.annotation.JsonFormat;
import hcmuaf.edu.vn.bookingtravel.api.base.model.BaseModel;
import hcmuaf.edu.vn.bookingtravel.api.base.model.MoneyV2;
import hcmuaf.edu.vn.bookingtravel.api.model.tour.enums.TimeHowLong;
import hcmuaf.edu.vn.bookingtravel.api.model.tour.enums.TourType;
import hcmuaf.edu.vn.bookingtravel.api.model.tourBooked.enums.PaymentMethod;
import hcmuaf.edu.vn.bookingtravel.api.model.tourBooked.enums.TourBookedStatus;
import lombok.Data;
import org.springframework.data.mongodb.core.index.TextIndexed;

import java.util.Date;
import java.util.List;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 09/05/2023, Thứ Ba
 **/
@Data
public class TourBooked extends BaseModel {
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date cancelledAt;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date startAt;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date endAt;
    private String cancelName;
    private String tourId;
    @TextIndexed
    private String tourName;
    private String featuredImgTour;
    private TimeHowLong timeHowLong;
    private TourType tourType;
    private TourBookedStatus status;
    private String note;
    private MoneyV2 tax;
    private MoneyV2 totalPrice;
    private String customerId;
    @TextIndexed
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private MoneyV2 salePrice;
    private long quantityChildren;
    private long quantityAdult;
    private List<String>  requesteds;
    private PaymentMethod paymentMethod;
}
