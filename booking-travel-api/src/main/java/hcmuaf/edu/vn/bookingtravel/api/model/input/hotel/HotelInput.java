package hcmuaf.edu.vn.bookingtravel.api.model.input.hotel;

import hcmuaf.edu.vn.bookingtravel.api.model.geo.Address;
import hcmuaf.edu.vn.bookingtravel.api.model.hotel.enums.RankHotelType;
import lombok.Data;

import java.util.List;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 27/04/2023, Thứ Năm
 **/
@Data
public class HotelInput {
    private String name;
    private String fax;
    private Address address;
    private RankHotelType type;
    private List<String> imgUrls;
}
