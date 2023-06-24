package hcmuaf.edu.vn.bookingtravel.api.model.hotelBooked;

import hcmuaf.edu.vn.bookingtravel.api.base.filter.BaseFilter;
import hcmuaf.edu.vn.bookingtravel.api.model.hotelBooked.enums.HotelBookedStatus;
import hcmuaf.edu.vn.bookingtravel.api.model.hotelBooked.enums.PaymentMethod;
import lombok.Data;

import java.util.HashMap;

@Data
public class HotelBookedFilter extends BaseFilter {
    private HotelBookedStatus status;
    private String hotelId;
    private String customerId;
    private PaymentMethod paymentMethod;

    @Override
    public HashMap<String, Object> getExtendFilters() {
        HashMap<String, Object> filters = new HashMap<>();
        if (status != null) {
            filters.put("status", status.toString());
        }
        if (hotelId != null) {
            filters.put("hotelId", hotelId);
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
