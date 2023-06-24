package hcmuaf.edu.vn.bookingtravel.api.model.hotel;

import hcmuaf.edu.vn.bookingtravel.api.base.model.BaseModel;
import hcmuaf.edu.vn.bookingtravel.api.base.model.MoneyV2;
import hcmuaf.edu.vn.bookingtravel.api.model.hotel.enums.RoomAdjustmentType;
import lombok.Data;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 21/04/2023, Thứ Sáu
 **/
@Data
public class RoomAdjustment {
    private String  tourId;
    private double amount;
    private RoomAdjustmentType type;
}
