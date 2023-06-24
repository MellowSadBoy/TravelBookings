package hcmuaf.edu.vn.bookingtravel.api.model.input.tour;

import hcmuaf.edu.vn.bookingtravel.api.base.model.MoneyV2;
import hcmuaf.edu.vn.bookingtravel.api.model.geo.Address;
import hcmuaf.edu.vn.bookingtravel.api.model.tour.enums.TimeHowLong;
import hcmuaf.edu.vn.bookingtravel.api.model.tour.enums.TourStatus;
import hcmuaf.edu.vn.bookingtravel.api.model.tour.enums.TourType;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 14/04/2023, Thứ Sáu
 **/
@Data
public class TourInput {
    private String name;
    private MoneyV2 priceStandard;
    private double discount;
    private MoneyV2 tax;
    private String note;
    private String description;
    private Date startDate;
    private Address addressStart;
    private Address location;
    private TimeHowLong timeHowLong;
    private TourType type;
    private long quantityVisit;
    private String hotelId;
    private String featureImgUrl;
    private List<String> imgUrls;
}
