package hcmuaf.edu.vn.bookingtravel.api.model;

import hcmuaf.edu.vn.bookingtravel.api.base.model.BaseModel;
import hcmuaf.edu.vn.bookingtravel.api.model.geo.Address;
import lombok.Data;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 15/04/2023, Thứ Bảy
 **/
@Data
public class Enterprise extends BaseModel {
    private String name;
    private String description;
    private String imgLogoUrl;
    private String hotLine;
    private Address address;
}
