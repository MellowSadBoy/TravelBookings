package hcmuaf.edu.vn.bookingtravel.api.model.tour;

import hcmuaf.edu.vn.bookingtravel.api.base.model.BaseModel;
import hcmuaf.edu.vn.bookingtravel.api.base.model.MoneyV2;
import hcmuaf.edu.vn.bookingtravel.api.base.utils.FulltextIndex;
import hcmuaf.edu.vn.bookingtravel.api.model.tour.enums.TimeHowLong;
import hcmuaf.edu.vn.bookingtravel.api.model.tour.enums.TourStatus;
import hcmuaf.edu.vn.bookingtravel.api.model.tour.enums.TourType;
import hcmuaf.edu.vn.bookingtravel.api.model.geo.Address;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.TextIndexed;

import java.util.Date;
import java.util.List;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 13/04/2023, Thứ Năm
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class Tour extends BaseModel {
    @TextIndexed
    private String name;
    private MoneyV2 priceStandard;
    private double discount;
    private MoneyV2 tax;
    private String note;
    private String description;
    private TourStatus status;
    private Date startDate;
    private Address addressStart;
    private Address location;
    private String vehicleId;
    private TimeHowLong timeHowLong;
    private TourType type;
    //Số lượng tối đa
    private long quantityVisit;
    //Số lượng còn trống
    private long quantityEmpty;
    private String hotelId;
    private String featureImgUrl;
    private List<String> imgUrls;
}
