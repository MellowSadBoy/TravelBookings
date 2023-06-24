package hcmuaf.edu.vn.bookingtravel.api.controller.user;

import hcmuaf.edu.vn.bookingtravel.api.base.exception.ExceptionCode;
import hcmuaf.edu.vn.bookingtravel.api.base.exception.ServiceException;
import hcmuaf.edu.vn.bookingtravel.api.base.manager.ActivityLogManager;
import hcmuaf.edu.vn.bookingtravel.api.base.utils.KeyUtils;
import hcmuaf.edu.vn.bookingtravel.api.controller.utils.UserUtilsController;
import hcmuaf.edu.vn.bookingtravel.api.enums.ActivityLogType;
import hcmuaf.edu.vn.bookingtravel.api.manager.ScoreManager;
import hcmuaf.edu.vn.bookingtravel.api.model.user.Score;
import hcmuaf.edu.vn.bookingtravel.api.model.user.enums.ScoreType;
import hcmuaf.edu.vn.bookingtravel.api.model.user.enums.UserStatus;
import hcmuaf.edu.vn.bookingtravel.api.manager.KeyPassWordManager;
import hcmuaf.edu.vn.bookingtravel.api.manager.UserManager;
import hcmuaf.edu.vn.bookingtravel.api.model.geo.Address;
import hcmuaf.edu.vn.bookingtravel.api.model.input.user.UpdateInfoUserInput;
import hcmuaf.edu.vn.bookingtravel.api.model.input.user.UpdateStatusInput;
import hcmuaf.edu.vn.bookingtravel.api.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 13/04/2023, Thứ Năm
 **/
@Component
public class UserUpdateController {
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

    private void validateUpdateStatus(UpdateStatusInput statusBody) throws ServiceException {
        if (null == statusBody) {
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Thông tin không hợp lệ");
        }
        if (null == statusBody.getStatus()) {
            throw new ServiceException(ExceptionCode.NOT_FOUND_EXCEPTION.toString(), "Vui lòng nhập trạng thái cần cập nhật");
        }
        if (!UserStatus.isExist(statusBody.getStatus())) {
            throw new ServiceException(ExceptionCode.NOT_EXIST_STATUS_EXCEPTION.toString(), "Trang thái tài khoản không tồn tại (" + UserStatus.getListName() + ")");
        }

    }

    public User updateActiveUser(String userId, String updateBy) throws ServiceException {
        if (null == userId || userId.length() == 0 || "null".equalsIgnoreCase(userId)) {
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Vui lòng nhập mã tài khoản cần tìm");
        }
        User user = userUtilsController.getUser(userId);
        if (UserStatus.ACTIVE.equals(user.getStatus())) {
            throw new ServiceException(ExceptionCode.ALREADY_EXIST_EXCEPTION.toString(), "Tài khoản đã là trạng thái đang hoạt động");

        }
        UpdateStatusInput updateStatusInput = new UpdateStatusInput();
        updateStatusInput.setStatus(UserStatus.ACTIVE.toString());
        updateStatusInput.setNote("Cập nhật trạng thái đang hoạt động cho tài khoản " + userId);
        updateStatusInput.setByUser(updateBy);
        return userManager.updateUserStatus(userId, updateStatusInput);

    }

    public User updateInActiveUser(String userId, String updateBy) throws ServiceException {
        if (null == userId || userId.length() == 0 || "null".equalsIgnoreCase(userId)) {
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Vui lòng nhập mã tài khoản cần tìm");
        }
        User user = userUtilsController.getUser(userId);
        if (UserStatus.INACTIVE.equals(user.getStatus())) {
            throw new ServiceException(ExceptionCode.ALREADY_EXIST_EXCEPTION.toString(), "Tài khoản đã là trạng thái ngưng hoạt động");

        }
        UpdateStatusInput updateStatusInput = new UpdateStatusInput();
        updateStatusInput.setStatus(UserStatus.INACTIVE.toString());
        updateStatusInput.setNote("Cập nhật trạng thái ngưng hoạt động cho tài khoản " + userId);
        updateStatusInput.setByUser(updateBy);
        return userManager.updateUserStatus(userId, updateStatusInput);

    }


    public User updateUserInfo(String userId, UpdateInfoUserInput updateInfoUser) throws ServiceException {
        if (updateInfoUser == null) {
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Vui lòng nhập thông tin cần cập nhật của tài khoản");
        }
        User user = userUtilsController.getUser(userId);
        return userManager.updateInfoUser(userId, updateInfoUser);
    }

    public User updateUserAvatr(String userId, String newAvtar) throws ServiceException {
        if (newAvtar == null) {
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Vui lòng nhập thông tin cần cập nhật của tài khoản");
        }
        User user = userUtilsController.getUser(userId);
        return userManager.updateAvatar(userId, newAvtar);
    }


    public User updateUserAddress(String userId, Address address) throws ServiceException {
        User user = userUtilsController.getUser(userId);
        return userManager.updateAddress(userId, address);
    }


    public User updatePassword(String userId, String password, String byUser) throws ServiceException {
        User user = userUtilsController.getUser(userId);
        String token = KeyUtils.getToken();
        password = KeyUtils.SHA256(KeyUtils.decodeBase64Encoder(password) + token);
        keyPassWordManager.updatePassword(userId, password, byUser);
        return user;
    }

    public User updateScore(String userId, String byUser, long score) throws ServiceException {
        userUtilsController.getUser(userId);
        Score data = scoreManager.getScoreByUser(userId);
        long scoreAfter = score + data.getScore();
        if (scoreAfter <= 3000 && scoreAfter > 1500)
            scoreManager.updateScoreType(userId, ScoreType.RANK_SILVER);
        if (scoreAfter <= 9000 && scoreAfter > 3000)
            scoreManager.updateScoreType(userId, ScoreType.RANK_GOLD);
        if (scoreAfter > 9000)
            scoreManager.updateScoreType(userId, ScoreType.RANK_VIP);
        scoreManager.updateScore(userId, score);
        // add log
        activityLogManager.addActivityLog(userId, User.class.getSimpleName(),
                ActivityLogType.UPDATE_INFO, byUser, "Cập nhật điểm tích luỹ cho" + userId);

        return userUtilsController.getUser(userId);

    }
}
