package hcmuaf.edu.vn.bookingtravel.api.client.model.bank;

import lombok.Data;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 28/03/2023, Thứ Ba
 **/
@Data
public class ResultHistory {
    private int error;
    private String message;
    private DataHistory data;
}
