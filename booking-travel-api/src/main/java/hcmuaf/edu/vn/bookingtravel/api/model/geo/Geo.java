package hcmuaf.edu.vn.bookingtravel.api.model.geo;

import hcmuaf.edu.vn.bookingtravel.api.base.model.BaseModel;
import hcmuaf.edu.vn.bookingtravel.api.enums.CountryCode;
import hcmuaf.edu.vn.bookingtravel.api.enums.GeoType;
import lombok.Data;


@Data
public class Geo extends BaseModel {
    private String name;
    private int child_id;
    private int parent_id;
    private String code;
    private CountryCode countryCode;
    private GeoType type;
    private String description;
}
