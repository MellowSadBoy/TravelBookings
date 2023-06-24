package hcmuaf.edu.vn.bookingtravel.api.base.logs;

import com.fasterxml.jackson.annotation.JsonFormat;
import hcmuaf.edu.vn.bookingtravel.api.enums.ActivityLogType;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

import static hcmuaf.edu.vn.bookingtravel.api.base.utils.GeneralIdUtils.generateId;

@Data
public class ActivityLog {
    @Id
    private String id;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date createdAt;
    private String userName;
    private String requestId;
    private String requestType;
    private ActivityLogType type;
    private String description;

    public static ActivityLog addActivityLog(String requestId, String requestType, ActivityLogType type, String userName, String description) {
        ActivityLog activityLog = new ActivityLog();
        activityLog.setCreatedAt(new Date());
        activityLog.setUserName(userName);
        activityLog.setDescription(description);
        activityLog.setRequestId(requestId);
        activityLog.setRequestType(requestType);
        activityLog.setType(type);
        activityLog.setId(generateId());
        return activityLog;
    }
}
