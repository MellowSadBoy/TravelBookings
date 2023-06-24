package hcmuaf.edu.vn.bookingtravel.api.model.hotel;

import hcmuaf.edu.vn.bookingtravel.api.base.filter.BaseFilter;
import hcmuaf.edu.vn.bookingtravel.api.model.geo.Address;
import hcmuaf.edu.vn.bookingtravel.api.model.hotel.enums.RankHotelType;
import lombok.Data;
import org.springframework.data.mongodb.core.index.TextIndexed;

import java.util.HashMap;
import java.util.List;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 11/05/2023, Thứ Năm
 **/
@Data
public class HotelFilter extends BaseFilter {
    private String name;
    private String fax;
    private int provinceCode;
    private RankHotelType type;

    @Override
    public HashMap<String, Object> getExtendFilters() {
        HashMap<String, Object> filters = new HashMap<>();
        if (name != null) {
            filters.put("name", name);
        }
        if (fax != null) {
            filters.put("fax", fax);
        }
        if (provinceCode != 0) {
            filters.put("provinceCode", provinceCode);
        }
        if (type != null) {
            filters.put("type", type.toString());
        }
        return filters;
    }
}
