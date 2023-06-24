package hcmuaf.edu.vn.bookingtravel.api.model.tourBooked;

import hcmuaf.edu.vn.bookingtravel.api.base.filter.BaseFilter;
import hcmuaf.edu.vn.bookingtravel.api.model.tourBooked.enums.PaymentMethod;
import hcmuaf.edu.vn.bookingtravel.api.model.tourBooked.enums.TourBookedStatus;
import lombok.Data;

import java.util.HashMap;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 09/05/2023, Thứ Ba
 **/
@Data
public class TourBookedFilter extends BaseFilter {
    private TourBookedStatus status;
    private String tourId;
    private String customerId;
    private PaymentMethod paymentMethod;

    @Override
    public HashMap<String, Object> getExtendFilters() {
        HashMap<String, Object> filters = new HashMap<>();
        if (status != null) {
            filters.put("status", status.toString());
        }
        if (tourId != null) {
            filters.put("tourId", tourId);
        }
        if (customerId != null) {
            filters.put("customerId", customerId);
        }
        if (paymentMethod != null) {
            filters.put("paymentMethod", paymentMethod.toString());
        }
        return filters;
    }

}
