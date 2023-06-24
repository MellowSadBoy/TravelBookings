package hcmuaf.edu.vn.bookingtravel.api.model.hotel;

import hcmuaf.edu.vn.bookingtravel.api.base.filter.BaseFilter;
import hcmuaf.edu.vn.bookingtravel.api.model.hotel.enums.RankHotelType;
import hcmuaf.edu.vn.bookingtravel.api.model.hotel.enums.RoomType;
import lombok.Data;

import java.util.HashMap;
import java.util.List;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 17/05/2023, Thứ Tư
 **/
@Data
public class RoomFilter extends BaseFilter {
    private RoomType type;
    private List<String> types;
    private int provinceCode;
    private List<Integer> provinceCodes;
    private RankHotelType hotelType;
    private List<String> hotelTypes;
    private Double priceFrom;
    private Double priceTo;

    @Override
    public HashMap<String, Object> getExtendFilters() {
        HashMap<String, Object> filters = new HashMap<>();

        if (provinceCode != 0) {
            filters.put("provinceCode", provinceCode);
        }
        if (type != null) {
            filters.put("type", type.toString());
        }
        if (hotelType != null) {
            filters.put("hotelType", hotelType.toString());
        }
        return filters;
    }
}
