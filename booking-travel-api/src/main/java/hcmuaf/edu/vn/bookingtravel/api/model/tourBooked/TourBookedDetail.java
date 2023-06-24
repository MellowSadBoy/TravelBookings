package hcmuaf.edu.vn.bookingtravel.api.model.tourBooked;

import hcmuaf.edu.vn.bookingtravel.api.model.tour.Tour;
import lombok.Data;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 19/05/2023, Thứ Sáu
 **/
@Data
public class TourBookedDetail {
    private TourBooked tourBooked;
    private Tour tour;
}
