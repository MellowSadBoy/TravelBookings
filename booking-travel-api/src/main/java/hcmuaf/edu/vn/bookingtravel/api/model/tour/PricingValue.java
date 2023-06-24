package hcmuaf.edu.vn.bookingtravel.api.model.tour;

import hcmuaf.edu.vn.bookingtravel.api.base.model.BaseModel;
import hcmuaf.edu.vn.bookingtravel.api.base.model.MoneyV2;
import hcmuaf.edu.vn.bookingtravel.api.model.user.enums.AgeType;
import lombok.Data;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 13/04/2023, Thứ Năm
 **/
@Data
public class PricingValue extends BaseModel {
    //giá
    private MoneyV2 price;
    //loại hành khách
    private AgeType aboutAgeType;
    private String note;
    //giảm giá
    private double sale;
    private String tourId;
}
