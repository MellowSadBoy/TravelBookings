package hcmuaf.edu.vn.bookingtravel.api.controller.user;

import hcmuaf.edu.vn.bookingtravel.api.base.controller.BaseController;
import hcmuaf.edu.vn.bookingtravel.api.base.exception.ServiceException;
import hcmuaf.edu.vn.bookingtravel.api.base.manager.ActivityLogManager;
import hcmuaf.edu.vn.bookingtravel.api.controller.utils.UserUtilsController;
import hcmuaf.edu.vn.bookingtravel.api.enums.ActivityLogType;
import hcmuaf.edu.vn.bookingtravel.api.manager.KeyPassWordManager;
import hcmuaf.edu.vn.bookingtravel.api.manager.RoleManager;
import hcmuaf.edu.vn.bookingtravel.api.manager.ScoreManager;
import hcmuaf.edu.vn.bookingtravel.api.manager.UserManager;
import hcmuaf.edu.vn.bookingtravel.api.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 14/04/2023, Thứ Sáu
 **/
@Component
public class DeleteUserController extends BaseController {
    @Autowired
    private UserManager userManager;

    @Autowired
    private KeyPassWordManager keyPassWordManager;

    @Autowired
    private UserUtilsController userUtilsController;

    @Autowired
    private ScoreManager scoreManager;

    @Autowired
    private ActivityLogManager activityLogManager;

    @Autowired
    private RoleManager roleManager;

    public User deleteUser(String userId, String byUser) throws ServiceException {

        User user = userUtilsController.getUser(userId);
        //delete score
        scoreManager.deleteScore(userId, byUser);
        //delete password
        keyPassWordManager.deletePassword(userId, byUser);
        //delete role
        roleManager.deleteRole(userId, byUser);
        //delete user
        userManager.deleteUser(userId, byUser);

        //add log
        activityLogManager.addActivityLog(userId, User.class.getSimpleName(),
                ActivityLogType.DELETED, byUser, "Xoá tài khoản " + userId + " ra khỏi hệ thống");

        return user;
    }
}
