package hcmuaf.edu.vn.bookingtravel.api.model.input.tour;

import hcmuaf.edu.vn.bookingtravel.api.model.geo.Address;
import lombok.Data;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 14/04/2023, Thứ Sáu
 **/
@Data
public class ScheduleInput {
    private String title;
    private String nameLocation;
    //điểm dừng chân
    private Address locationStop;
    private String description;
    //thời gian dừng chân
    private Long arrivalTime;
    //thời gian tiếp tục hành trình
    private Long leaveTime;
}
