package hcmuaf.edu.vn.bookingtravel.api.model.hotel;

import hcmuaf.edu.vn.bookingtravel.api.base.model.BaseModel;
import hcmuaf.edu.vn.bookingtravel.api.base.model.MoneyV2;
import hcmuaf.edu.vn.bookingtravel.api.model.hotel.enums.HotelFilterType;
import hcmuaf.edu.vn.bookingtravel.api.model.hotel.enums.RankHotelType;
import hcmuaf.edu.vn.bookingtravel.api.model.hotel.enums.RoomType;
import lombok.Data;

import java.util.List;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 13/04/2023, Thứ Năm
 **/
@Data
public class Room extends BaseModel {
    private String numberRoom;
    private String hotelId;
    private String hotelName;
    private RoomType type;
    private RankHotelType hotelType;
    private int province;
    private List<String> imgUrls;
    private long maxPeople;
    //true = phòng trống
    private boolean vacant;
    private MoneyV2 price;
    private double discount;
    private MoneyV2 taxRate;
}
