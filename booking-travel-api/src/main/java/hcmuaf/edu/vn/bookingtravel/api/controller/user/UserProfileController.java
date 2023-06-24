package hcmuaf.edu.vn.bookingtravel.api.controller.user;

import hcmuaf.edu.vn.bookingtravel.api.base.exception.ExceptionCode;
import hcmuaf.edu.vn.bookingtravel.api.base.exception.ServiceException;
import hcmuaf.edu.vn.bookingtravel.api.base.filter.ResultList;
import hcmuaf.edu.vn.bookingtravel.api.base.logs.ActivityLog;
import hcmuaf.edu.vn.bookingtravel.api.base.manager.ActivityLogManager;
import hcmuaf.edu.vn.bookingtravel.api.controller.utils.UserUtilsController;
import hcmuaf.edu.vn.bookingtravel.api.model.user.enums.UserStatus;
import hcmuaf.edu.vn.bookingtravel.api.manager.KeyPassWordManager;
import hcmuaf.edu.vn.bookingtravel.api.manager.RoleManager;
import hcmuaf.edu.vn.bookingtravel.api.manager.ScoreManager;
import hcmuaf.edu.vn.bookingtravel.api.manager.UserManager;
import hcmuaf.edu.vn.bookingtravel.api.model.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserProfileController {
    @Autowired
    private UserManager userManager;

    @Autowired
    private ScoreManager scoreManager;

    @Autowired
    private RoleManager roleManager;

    @Autowired
    private KeyPassWordManager keyPassWordManager;

    @Autowired
    private UserUtilsController userUtilsController;

    @Autowired
    private ActivityLogManager activityLogManager;


    public User getUser(String userId) throws ServiceException {
        return userUtilsController.getUser(userId);

    }

    public UserProfile getUserProfile(String userId) throws ServiceException {
        if (null == userId || userId.length() == 0 || "null".equalsIgnoreCase(userId)) {
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Vui lòng nhập mã tài khoản cần tìm");
        }
        User data = userManager.getUser(userId);
        if (null == data || !data.getStatus().equals(UserStatus.ACTIVE)) {
            throw new ServiceException(ExceptionCode.NOT_FOUND_EXCEPTION.toString(), "Không tìm thấy thông tin tài khoản " + userId);
        }
        List<Role> roles = roleManager.getRoles(userId);
        List<KeyPassword> passwords = keyPassWordManager.getKeyPasswords(userId);
        List<ActivityLog> activityLogs = activityLogManager.getActivityLogs(userId);
        UserProfile userProfile = new UserProfile();
        userProfile.setKeyPassword(passwords);
        userProfile.setRole(roles);
        userProfile.setUser(data);
        userProfile.setActivityLogs(activityLogs);
        Score score = scoreManager.getScoreByUser(userId);
        if (null != score) {
            userProfile.setScore(score);
        }
        return userProfile;
    }

    public ResultList<User> filterUsers(UserFilter userFilter) {
        return userManager.filterUser(userFilter);
    }
}
