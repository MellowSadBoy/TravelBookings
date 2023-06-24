package hcmuaf.edu.vn.bookingtravel.api.model.tour;

import hcmuaf.edu.vn.bookingtravel.api.base.filter.BaseFilter;
import hcmuaf.edu.vn.bookingtravel.api.base.model.MoneyV2;
import hcmuaf.edu.vn.bookingtravel.api.model.geo.Address;
import hcmuaf.edu.vn.bookingtravel.api.model.tour.enums.TimeHowLong;
import hcmuaf.edu.vn.bookingtravel.api.model.tour.enums.TourFilterType;
import hcmuaf.edu.vn.bookingtravel.api.model.tour.enums.TourStatus;
import hcmuaf.edu.vn.bookingtravel.api.model.tour.enums.TourType;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 14/04/2023, Thứ Sáu
 **/
@Data
public class TourFilter extends BaseFilter {
    private String name;
    private double priceStandard;
    private TourStatus status;
    private Date startDate;
    private int provinceCodeStart;
    private int provinceCodeLocation;
    private TimeHowLong timeHowLong;
    private TourType type;
    private String hotelId;
    private TourFilterType filterType;

    @Override
    public HashMap<String, Object> getExtendFilters() {
        HashMap<String, Object> filters = new HashMap<>();
        if (null != name)
            filters.put("name", name);
        if (priceStandard != 0)
            filters.put("priceStandard.amount", priceStandard);
        if (status != null)
            filters.put("status", status.toString());
        if (startDate != null)
            filters.put("startDate", startDate);
        if (provinceCodeStart != 0)
            filters.put("addressStart.provinceCode", provinceCodeStart);
        if (provinceCodeLocation != 0)
            filters.put("location.provinceCode", provinceCodeLocation);
        if (timeHowLong != null)
            filters.put("timeHowLong", timeHowLong.toString());
        if (type != null)
            filters.put("type", type.toString());
        if (hotelId != null)
            filters.put("hotelId", hotelId);
        return filters;
    }
}
