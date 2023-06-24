package hcmuaf.edu.vn.bookingtravel.api.model.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import hcmuaf.edu.vn.bookingtravel.api.base.model.BaseModel;
import hcmuaf.edu.vn.bookingtravel.api.model.user.enums.RoleStatus;
import hcmuaf.edu.vn.bookingtravel.api.model.user.enums.RoleType;
import lombok.Data;


import java.util.Date;

@Data
public class Role extends BaseModel {
    private String userId;
    private String note;
    private String description;
    private RoleType roleType;
    private RoleStatus roleStatus;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date cancelledAt;

}
