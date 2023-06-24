package hcmuaf.edu.vn.bookingtravel.api.model.input.hotel;

import hcmuaf.edu.vn.bookingtravel.api.base.model.MoneyV2;
import hcmuaf.edu.vn.bookingtravel.api.model.hotel.enums.RoomType;
import lombok.Data;

import java.util.List;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 27/04/2023, Thứ Năm
 **/
@Data
public class RoomInput {
    private String numberRoom;
    private RoomType type;
    private List<String> imgUrls;
    private long maxPeople;
    private boolean vacant;
    private MoneyV2 price;
    private double discount;
}
