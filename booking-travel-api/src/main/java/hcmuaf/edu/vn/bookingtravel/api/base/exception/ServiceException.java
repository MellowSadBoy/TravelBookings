package hcmuaf.edu.vn.bookingtravel.api.base.exception;

import lombok.Data;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 08/04/2023, Thứ Bảy
 **/
@Data
public class ServiceException extends Exception {

    protected String errorCode;
    protected String errorMessage;

    public ServiceException(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
