package hcmuaf.edu.vn.bookingtravel.api.controller.utils;

import hcmuaf.edu.vn.bookingtravel.api.base.exception.ExceptionCode;
import hcmuaf.edu.vn.bookingtravel.api.base.exception.ServiceException;
import hcmuaf.edu.vn.bookingtravel.api.manager.UserManager;
import hcmuaf.edu.vn.bookingtravel.api.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 13/04/2023, Thứ Năm
 **/
@Component
public class UserUtilsController {
    @Autowired
    private UserManager userManager;

    public User getUser(String userId) throws ServiceException {
        User user = userManager.getUser(userId);
        if (null == user)
            throw new ServiceException(ExceptionCode.NOT_FOUND_EXCEPTION.toString(),
                    "Không tìm thấy thông tin tài khoản");
        return user;
    }
}
