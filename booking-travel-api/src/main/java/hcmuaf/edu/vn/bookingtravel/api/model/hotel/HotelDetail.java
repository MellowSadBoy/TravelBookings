package hcmuaf.edu.vn.bookingtravel.api.model.hotel;

import hcmuaf.edu.vn.bookingtravel.api.base.model.BaseModel;
import lombok.Data;

import java.util.List;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 21/04/2023, Thứ Sáu
 **/
@Data
public class HotelDetail extends BaseModel {
    private Hotel hotel;
    private List<Room> rooms;
}
