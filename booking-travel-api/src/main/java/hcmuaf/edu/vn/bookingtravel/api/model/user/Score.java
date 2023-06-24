package hcmuaf.edu.vn.bookingtravel.api.model.user;

import hcmuaf.edu.vn.bookingtravel.api.base.model.BaseModel;
import hcmuaf.edu.vn.bookingtravel.api.model.user.enums.ScoreType;
import lombok.Data;

@Data
public class Score extends BaseModel {
    private String userId;
    private ScoreType type;
    private long score;
}
