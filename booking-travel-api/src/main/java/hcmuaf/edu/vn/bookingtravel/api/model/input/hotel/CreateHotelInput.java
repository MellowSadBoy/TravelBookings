package hcmuaf.edu.vn.bookingtravel.api.model.input.hotel;

import lombok.Data;

import java.util.List;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 27/04/2023, Thứ Năm
 **/
@Data
public class CreateHotelInput {
    private HotelInput hotelInput;
    private List<RoomInput> roomsInput;
}
