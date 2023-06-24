package hcmuaf.edu.vn.bookingtravel.api.base.manager;

import hcmuaf.edu.vn.bookingtravel.api.base.logs.ActivityLog;
import hcmuaf.edu.vn.bookingtravel.api.enums.ActivityLogType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 11/04/2023, Thứ Ba
 **/
@Repository
public class ActivityLogManager extends BaseManager<ActivityLog> {
    public ActivityLogManager() {
        super(ActivityLog.class.getSimpleName(), ActivityLog.class);
    }

    public List<ActivityLog> getActivityLogs(String requestId) {
        return getList("requestId", requestId);
    }

    public ActivityLog addActivityLog(String requestId, String requestType,
                                         ActivityLogType type, String userName, String description) {
        return create(ActivityLog.addActivityLog(requestId, requestType, type, userName, description));


    }
}
