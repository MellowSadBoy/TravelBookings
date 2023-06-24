package hcmuaf.edu.vn.bookingtravel.api.restcontroller;

import hcmuaf.edu.vn.bookingtravel.api.base.controller.BaseController;
import hcmuaf.edu.vn.bookingtravel.api.base.exception.ServiceException;
import hcmuaf.edu.vn.bookingtravel.api.base.filter.ResultList;
import hcmuaf.edu.vn.bookingtravel.api.controller.user.DeleteUserController;
import hcmuaf.edu.vn.bookingtravel.api.controller.user.UserProfileController;
import hcmuaf.edu.vn.bookingtravel.api.controller.user.UserUpdateController;
import hcmuaf.edu.vn.bookingtravel.api.controller.utils.UserUtilsController;
import hcmuaf.edu.vn.bookingtravel.api.model.geo.Address;
import hcmuaf.edu.vn.bookingtravel.api.model.input.user.UpdateInfoUserInput;
import hcmuaf.edu.vn.bookingtravel.api.model.user.User;
import hcmuaf.edu.vn.bookingtravel.api.model.user.UserFilter;
import hcmuaf.edu.vn.bookingtravel.api.model.user.UserProfile;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 12/04/2023, Thứ Tư
 **/
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user/1.0.0/")
public class UserController extends BaseController {
    private final static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserUpdateController userUpdateController;
    @Autowired
    private UserUtilsController userUtilsController;
    @Autowired
    private UserProfileController userProfileController;
    @Autowired
    private DeleteUserController deleteUserController;


    @ApiOperation(value = "Lấy thông tin của tài khoản")
    @GetMapping("user/{userId}")
    public User getUser(@PathVariable String userId) throws ServiceException {
        return userUtilsController.getUser(userId);
    }

    @ApiOperation(value = "Lấy thông tin chi tiết của tài khoản")
    @GetMapping("user/{userId}/profile")
    public UserProfile getUserProfile(@PathVariable String userId) throws ServiceException {
        return userProfileController.getUserProfile(userId);
    }

    @ApiOperation(value = "Cập nhật trạng thái đang hoạt động cho tài khoản")
    @PutMapping("user/{userId}/active/{activeBy}")
    public User activeUser(@PathVariable String userId, @PathVariable String activeBy) throws ServiceException {
        return userUpdateController.updateActiveUser(userId, activeBy);
    }

    @ApiOperation(value = "Cập nhật trạng thái ngưng hoạt động cho tài khoản")
    @PutMapping("user/{userId}/inactive/{activeBy}")
    public User inactiveUser(@PathVariable String userId, @PathVariable String activeBy) throws ServiceException {
        return userUpdateController.updateInActiveUser(userId, activeBy);
    }

    @ApiOperation(value = "Cập nhật thông tin tài khoản")
    @PutMapping("user/{userId}/info-basic")
    public User updateUserInfo(@PathVariable String userId, @RequestBody UpdateInfoUserInput statusBody) throws ServiceException {
        return userUpdateController.updateUserInfo(userId, statusBody);
    }

    @ApiOperation(value = "Cập nhật địa chỉ tài khoản")
    @PutMapping("user/{userId}/info-address")
    public User updateUserAddress(@PathVariable String userId, @RequestBody Address address) throws ServiceException {
        return userUpdateController.updateUserAddress(userId, address);
    }

    @ApiOperation(value = "Cập nhật mật khẩu cho tài khoản (mật khẩu truyền vào phải mã hoá Base64)")
    @PutMapping("user/{userId}/info-password/{byUser}")
    public User updatePassword(@PathVariable String userId, @PathVariable String byUser, @RequestParam("pwd") String password) throws ServiceException {
        return userUpdateController.updatePassword(userId, password, byUser);
    }

    @ApiOperation(value = "Cập nhật điểm tích luỹ cho tài khoản")
    @PutMapping(value = "user/{userId}/score/{byUser}")
    public User updateScore(@PathVariable String userId, @PathVariable String byUser, @RequestParam("score") long score) throws ServiceException {
        return userUpdateController.updateScore(userId, byUser, score);
    }

    @ApiOperation(value = "Xoá tài khoản")
    @DeleteMapping(value = "user/{userId}/deleted/{byUser}")
    public User deletedUser(@PathVariable String userId, @PathVariable String byUser) throws ServiceException {
        return deleteUserController.deleteUser(userId, byUser);
    }

    @ApiOperation(value = "Cập nhật ảnh đại diện ")
    @PutMapping(value = "user/{userId}/avatar/update")
    public User updateAvatar(@PathVariable String userId, @RequestParam("newAvatar") String newAvatar) throws ServiceException {
        return userUpdateController.updateUserAvatr(userId,newAvatar);
    }


    @ApiOperation(value = "Tìm kiếm tài khoản theo thông tin")
    @PostMapping("user/filter")
    public ResultList<User> searchUser(
            @RequestBody UserFilter userFilter) {
        return userProfileController.filterUsers(userFilter);
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Object handleAllServiceException(ServiceException e) {
        LOGGER.error("ServiceException error.", e);
        return error(e.getErrorCode(), e.getErrorMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Object handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return error("internal_server_error", e.getMessage());
    }
}
