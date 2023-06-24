package hcmuaf.edu.vn.bookingtravel.api.client.model;

import lombok.Data;

import javax.swing.*;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 24/04/2023, Thứ Hai
 **/
@Data
public class Location {
    private String Id;
    private int Kind;
    private String Name;
    private int SortOrder;
}
