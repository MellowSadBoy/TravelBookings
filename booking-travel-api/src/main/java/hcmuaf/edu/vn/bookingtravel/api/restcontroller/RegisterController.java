package hcmuaf.edu.vn.bookingtravel.api.restcontroller;

import hcmuaf.edu.vn.bookingtravel.api.base.controller.BaseController;
import hcmuaf.edu.vn.bookingtravel.api.base.model.ResponseData;
import hcmuaf.edu.vn.bookingtravel.api.controller.user.UserCreateController;
import hcmuaf.edu.vn.bookingtravel.api.base.exception.ExceptionCode;
import hcmuaf.edu.vn.bookingtravel.api.base.exception.ServiceException;
import hcmuaf.edu.vn.bookingtravel.api.model.input.user.CreateUserInput;
import hcmuaf.edu.vn.bookingtravel.api.model.user.DataRegister;
import hcmuaf.edu.vn.bookingtravel.api.model.user.User;
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

import static hcmuaf.edu.vn.bookingtravel.api.base.utils.GeneralIdUtils.generateId;
import static hcmuaf.edu.vn.bookingtravel.api.base.utils.SendMailUtils.loginMail;
import static hcmuaf.edu.vn.bookingtravel.api.base.utils.SendMailUtils.sendMailTo;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user/1.0.0/")
public class RegisterController extends BaseController {
    private final static Logger LOGGER = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    private UserCreateController userCreateController;

    @Value("${ecommerce_floor.store.name}")
    private String storeName;
    @Value("${ecommerce_floor.store.mail}")
    private String emailEcommerce;
    @Value("${mellow.password}")
    private String passwordEcommerce;


    @ApiOperation(value = "Đăng ký tài khoản mật khẩu truyền vào phải mã hoá Base64( sau khi đăng ký tài khoản thì phải check mã code gửi mail trả" +
            " về với mã code người dùng nhập vào đúng thì cập nhật status(trạng thái) tài khoản ACTIVE)")
    @PostMapping("register")
    public ResponseData registerUser(@RequestBody CreateUserInput createInput) {
        User result = null;
        try {
            result = userCreateController.createUser(createInput);
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        if (null != result) {
            String code = generateId();
            String subject = "Ma xac Thuc";
            String messSendMail = code + " la ma xac thuc OTP dang ky tai khoan trên Mellow. " +
                    "De tranh bi mat tien, tuyet doi KHONG chia se ma nay voi bat ky ai.";
            //Send mail login
            Session session = loginMail(emailEcommerce, passwordEcommerce);
            //Send mail to customer
            try {
                sendMailTo(session, emailEcommerce, storeName, result.getEmail(), subject, messSendMail);

            } catch (UnsupportedEncodingException | MessagingException e) {
                return new ResponseData(ExceptionCode.NOT_FOUND_EXCEPTION.code, "Email không hợp lệ", "Email " + result.getEmail() + " not valid");
            }
            DataRegister dataRegister = new DataRegister();
            dataRegister.setCode(code);
            dataRegister.setUserId(result.getId());
            return new ResponseData(ExceptionCode.SUCCESS_EXCEPTION.code,
                    "Đăng ký tài khoản thành công vui lòng nhập mã xác thực để kích hoạt tài khoản", dataRegister);
        } else {
            return new ResponseData(ExceptionCode.ERROR_EXCEPTION.code,
                    "Đăng ký tài khoản không thành công", null);
        }
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Object handleAllServiceException(ServiceException e) {
        LOGGER.error("ServiceException error.", e);
        return error(e.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Object handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return error("internal_server_error", e.getMessage());
    }
}
