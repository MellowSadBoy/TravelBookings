package hcmuaf.edu.vn.bookingtravel.api.base.model;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ResponseData {
    private int status;
    private String message;
    private Object data;

}
