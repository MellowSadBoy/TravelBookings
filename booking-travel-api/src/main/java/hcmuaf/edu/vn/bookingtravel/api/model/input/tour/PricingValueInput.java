package hcmuaf.edu.vn.bookingtravel.api.model.input.tour;

import hcmuaf.edu.vn.bookingtravel.api.base.model.MoneyV2;
import hcmuaf.edu.vn.bookingtravel.api.model.user.enums.AgeType;
import lombok.Data;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 14/04/2023, Thứ Sáu
 **/
@Data
public class PricingValueInput {
    private MoneyV2 price;
    private AgeType aboutAgeType;
    private String note;
    private double sale;
}
