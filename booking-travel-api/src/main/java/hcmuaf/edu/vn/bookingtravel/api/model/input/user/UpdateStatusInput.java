package hcmuaf.edu.vn.bookingtravel.api.model.input.user;

import lombok.Data;

@Data
public class UpdateStatusInput {
    private String note;
    private String status;
    private String byUser;
}
