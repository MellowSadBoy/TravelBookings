package hcmuaf.edu.vn.bookingtravel.api.base.exception;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 08/04/2023, Thứ Bảy
 **/
public enum ExceptionCode {
    NOT_FOUND_EXCEPTION(404),
    NOT_EXISTING_EXCEPTION(415),
    ERROR_EXCEPTION(500),
    SUCCESS_EXCEPTION(200),
    INVALID_DATA_EXCEPTION(501),
    NOT_EXIST_STATUS_EXCEPTION(504),
    NOT_EXIST_TYPE_EXCEPTION(505),
    CANCELED_ERROR(507),
    ALREADY_EXIST_EXCEPTION(512);

    public final int code;

    private ExceptionCode(int code) {
        this.code = code;

    }
}
