package hcmuaf.edu.vn.bookingtravel.api.restcontroller;

import hcmuaf.edu.vn.bookingtravel.api.base.controller.BaseController;
import hcmuaf.edu.vn.bookingtravel.api.base.exception.ExceptionCode;
import hcmuaf.edu.vn.bookingtravel.api.base.exception.ServiceException;
import hcmuaf.edu.vn.bookingtravel.api.base.model.ResponseData;
import hcmuaf.edu.vn.bookingtravel.api.base.utils.GeneralIdUtils;
import hcmuaf.edu.vn.bookingtravel.api.base.utils.SendMailUtils;
import hcmuaf.edu.vn.bookingtravel.api.controller.user.UserProfileController;
import hcmuaf.edu.vn.bookingtravel.api.model.user.User;
import hcmuaf.edu.vn.bookingtravel.api.model.user.UserFilter;
import hcmuaf.edu.vn.bookingtravel.api.model.user.enums.ServiceType;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.mail.MessagingException;
import javax.mail.Session;
import java.io.UnsupportedEncodingException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/mail/1.0.0")
public class SenMailController extends BaseController {
    private final static Logger LOGGER = LoggerFactory.getLogger(SenMailController.class);


    @Autowired
    private UserProfileController userProfileController;

    @Value("${ecommerce_floor.store.name}")
    private String storeName;
    @Value("${ecommerce_floor.store.mail}")
    private String emailEcommerce;
    @Value("${mellow.password}")
    private String passwordEcommerce;


    @ApiOperation(value = "Send email")
    @PostMapping("/send")
    public ResponseData sendMail(@RequestParam("email") String email)  {

        UserFilter filterUser = new UserFilter();
        filterUser.setEmail(email);
        filterUser.setServiceType(ServiceType.NORMALLY);
        User result = userProfileController.filterUsers(filterUser).getSearchList().get(0);
        if (null != result) {
            String code = GeneralIdUtils.generateId();
            String subject = "Ma xac Thuc";
            String messSendMail = code + " la ma xac thuc OTP tai khoan tren san thuong mai MELLOW. " +
                    "De tranh bi mat tien, tuyet doi KHONG chia se ma nay voi bat ky ai.";
            //Send mail login
            Session session = SendMailUtils.loginMail(emailEcommerce, passwordEcommerce);
            //Send mail to customer
            try {
                SendMailUtils.sendMailTo(session, emailEcommerce, storeName, result.getEmail(), subject, messSendMail);

            } catch (UnsupportedEncodingException | MessagingException e) {
                return new ResponseData(ExceptionCode.ERROR_EXCEPTION.code, e.getMessage(), e.getStackTrace());
            }
            //Send mail to store

            return new ResponseData(ExceptionCode.SUCCESS_EXCEPTION.code, "Gửi mã xác thực thành công.", code);
        }
        else {
            return new ResponseData(ExceptionCode.NOT_FOUND_EXCEPTION.code, "Gửi mã xác thực không thành công.Không tìm thấy thông tin tài khoản","Register failed");
        }
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
        return error("internal_server_error",  e.getMessage());
    }
}
