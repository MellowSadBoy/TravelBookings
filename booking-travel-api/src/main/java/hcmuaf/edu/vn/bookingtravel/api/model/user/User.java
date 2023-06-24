package hcmuaf.edu.vn.bookingtravel.api.model.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import hcmuaf.edu.vn.bookingtravel.api.base.model.BaseModel;
import hcmuaf.edu.vn.bookingtravel.api.model.user.enums.GenderType;
import hcmuaf.edu.vn.bookingtravel.api.model.user.enums.ServiceType;
import hcmuaf.edu.vn.bookingtravel.api.model.user.enums.UserStatus;
import hcmuaf.edu.vn.bookingtravel.api.model.geo.Address;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;


import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class User extends BaseModel {
    private String username;
    private String fullName;
    private String email;
    private String telephone;
    private String imageUrl;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date birthday;
    private Address address;
    private GenderType gender;
    private ServiceType serviceType;
    private String description;
    private UserStatus status;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date cancelledAt;

    public User() {
    }
}
