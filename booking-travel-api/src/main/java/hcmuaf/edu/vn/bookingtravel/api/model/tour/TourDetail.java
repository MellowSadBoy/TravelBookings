package hcmuaf.edu.vn.bookingtravel.api.model.tour;

import lombok.Data;

import java.util.List;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 21/04/2023, Thứ Sáu
 **/
@Data
public class TourDetail {
    private Tour tour;
    //lịch trình
    private List<Schedule> schedules;
    //phương tiện
    private Vehicle vehicle;
    private List<PricingValue> pricingValues;

}
