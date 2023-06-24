package hcmuaf.edu.vn.bookingtravel.api.model.user;

import hcmuaf.edu.vn.bookingtravel.api.base.logs.ActivityLog;
import lombok.Data;

import java.util.List;

@Data
public class UserProfile {
    private User user;
    private List<KeyPassword> keyPassword;
    private List<Role> role;
    private Score score;
    private List<ActivityLog> activityLogs;

}
