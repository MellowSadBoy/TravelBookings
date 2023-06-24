package hcmuaf.edu.vn.bookingtravel.api.model.input.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import hcmuaf.edu.vn.bookingtravel.api.model.user.enums.GenderType;
import lombok.Data;

import java.util.Date;

@Data
public class UpdateInfoUserInput {
    private String fullName;
    private String telephone;
    private String email;
    private String username;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date birthday;
    private String imageUrl;
    private GenderType gender;



}
