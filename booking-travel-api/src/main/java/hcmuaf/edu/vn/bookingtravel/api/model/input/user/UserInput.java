package hcmuaf.edu.vn.bookingtravel.api.model.input.user;

import hcmuaf.edu.vn.bookingtravel.api.model.user.enums.GenderType;
import hcmuaf.edu.vn.bookingtravel.api.model.user.enums.ServiceType;
import hcmuaf.edu.vn.bookingtravel.api.model.geo.Address;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;

@Data
public class UserInput {
    private String username;
    private String fullName;
    private String email;
    private String telephone;
    private Date birthday;
    private Address address;
    private GenderType gender;
    private String imageUrl;
    @NonNull
    private ServiceType serviceType;
    private String description;
    private String byUser;

    public UserInput() {

    }
}
