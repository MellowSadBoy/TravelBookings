package hcmuaf.edu.vn.bookingtravel.api.model.input.user;

import hcmuaf.edu.vn.bookingtravel.api.model.user.enums.RoleType;
import lombok.Data;

@Data
public class CreateUserInput {
    private UserInput user;
    private String password;
    private RoleType roleType;
}
