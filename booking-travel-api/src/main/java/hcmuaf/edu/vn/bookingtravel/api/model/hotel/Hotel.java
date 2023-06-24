package hcmuaf.edu.vn.bookingtravel.api.model.hotel;

import hcmuaf.edu.vn.bookingtravel.api.base.model.BaseModel;
import hcmuaf.edu.vn.bookingtravel.api.model.geo.Address;
import hcmuaf.edu.vn.bookingtravel.api.model.hotel.enums.RankHotelType;
import lombok.Data;
import hcmuaf.edu.vn.bookingtravel.api.model.hotel.enums.HotelStatus;
import org.springframework.data.mongodb.core.index.TextIndexed;

import java.util.List;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 13/04/2023, Thứ Năm
 **/
@Data
public class Hotel extends BaseModel {
    @TextIndexed
    private String name;
    @TextIndexed
    private String fax;
    private Address address;
    private RankHotelType type;
    private List<String> imgUrls;
    private HotelStatus status;
}
