package hcmuaf.edu.vn.bookingtravel.api.client.model;

import lombok.Data;

import java.util.List;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 24/04/2023, Thứ Hai
 **/
@Data
public class LocationResult {
    private int Code;
    private List<Location> Data;
}
