package hcmuaf.edu.vn.bookingtravel.api.model.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import hcmuaf.edu.vn.bookingtravel.api.base.model.BaseModel;
import hcmuaf.edu.vn.bookingtravel.api.model.user.enums.PasswordStatus;
import lombok.Data;

import java.util.Date;

@Data
public class KeyPassword extends BaseModel {
    private String userId;
    private String password;
    private String note;
    private String token;
    private PasswordStatus status;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date cancelledAt;

}
