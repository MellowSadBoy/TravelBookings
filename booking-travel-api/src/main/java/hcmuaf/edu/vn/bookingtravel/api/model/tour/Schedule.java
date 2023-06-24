package hcmuaf.edu.vn.bookingtravel.api.model.tour;

import hcmuaf.edu.vn.bookingtravel.api.base.model.BaseModel;
import hcmuaf.edu.vn.bookingtravel.api.model.geo.Address;
import lombok.Data;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 13/04/2023, Thứ Năm
 **/
@Data
public class Schedule extends BaseModel {
    private String tourId;
    //tiêu đề
    private String title;
    //tên vị trí
    private String nameLocation;
    //địa điểm
    private Address locationStop;
    //giới thiệu
    private String description;
    //thời gian bắt đầu
    private Long arrivalTime;
    //thời gian khởi hành lại
    private Long leaveTime;
}
