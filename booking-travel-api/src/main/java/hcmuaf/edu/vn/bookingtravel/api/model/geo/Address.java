package hcmuaf.edu.vn.bookingtravel.api.model.geo;

import hcmuaf.edu.vn.bookingtravel.api.base.utils.FulltextIndex;
import hcmuaf.edu.vn.bookingtravel.api.enums.CountryCode;
import lombok.Data;

@Data
public class Address {
    private String address;
    @FulltextIndex
    private String province;
    private int provinceCode;

    private int province_child_id;
    private String district;
    private int districtCode;

    private int district_child_id;
    private String ward;
    private int wardCode;
    private String city;
    private CountryCode countryCode;
    private String zip;
}

