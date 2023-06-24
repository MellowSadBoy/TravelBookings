package hcmuaf.edu.vn.bookingtravel.api.model.input.tour;

import hcmuaf.edu.vn.bookingtravel.api.model.tour.enums.VehicleType;
import lombok.Data;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 14/04/2023, Thứ Sáu
 **/
@Data
public class VehicleInput {
    //biển số phương tiện
    private String code;
    //tên phương tiện
    private String name;
    //hãng phương tiện
    private String automaker;
    //loại phương tiện
    private VehicleType vehicleType;
    //số lượng vận tải
    private long numberOfSeats;
}
