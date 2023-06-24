package hcmuaf.edu.vn.bookingtravel.api.restcontroller;

import hcmuaf.edu.vn.bookingtravel.api.base.controller.BaseController;
import hcmuaf.edu.vn.bookingtravel.api.base.model.ResponseData;
import hcmuaf.edu.vn.bookingtravel.api.base.utils.JwtUtils;
import hcmuaf.edu.vn.bookingtravel.api.base.utils.KeyUtils;
import hcmuaf.edu.vn.bookingtravel.api.controller.user.UserCreateController;
import hcmuaf.edu.vn.bookingtravel.api.base.exception.ExceptionCode;
import hcmuaf.edu.vn.bookingtravel.api.base.exception.ServiceException;
import hcmuaf.edu.vn.bookingtravel.api.manager.RoleManager;
import hcmuaf.edu.vn.bookingtravel.api.manager.UserManager;
import hcmuaf.edu.vn.bookingtravel.api.model.input.user.CreateUserInput;
import hcmuaf.edu.vn.bookingtravel.api.model.input.user.UserInput;
import hcmuaf.edu.vn.bookingtravel.api.model.user.Role;
import hcmuaf.edu.vn.bookingtravel.api.model.user.User;
import hcmuaf.edu.vn.bookingtravel.api.model.user.UserFilter;
import hcmuaf.edu.vn.bookingtravel.api.model.user.enums.RoleStatus;
import hcmuaf.edu.vn.bookingtravel.api.model.user.enums.RoleType;
import hcmuaf.edu.vn.bookingtravel.api.model.user.enums.ServiceType;
import hcmuaf.edu.vn.bookingtravel.api.model.user.enums.UserStatus;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import java.util.Base64;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user/1.0.0/")
public class LoginController extends BaseController {
    private final static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RoleManager roleManager;
    @Autowired
    private UserManager userManager;
    @Autowired
    private UserCreateController userCreateController;


    private void validateLoginInput(String email, String password, boolean admin, ServiceType serviceType, String fullName) throws ServiceException {
        if (null == email) {
            throw new ServiceException(ExceptionCode.NOT_FOUND_EXCEPTION.toString(), "Chưa nhập thông tin Email");

        }
        if (ServiceType.NORMALLY.equals(serviceType))
            if (null == password) {
                throw new ServiceException(ExceptionCode.NOT_FOUND_EXCEPTION.toString(), "Chưa nhập mật khẩu");

            }
        if (!admin)
            if (null == serviceType ||
                    !ServiceType.isExist(serviceType.toString())) {
                throw new ServiceException("exists_type", "Loại dịch vụ không tồn tại." +
                        " ( " + ServiceType.getListName() + " )");
            }
    }

    @ApiOperation(value = "Login account admin")
    @PostMapping("admin/login")
    public ResponseData loginAdmin(
            @RequestParam("email") String email, @RequestParam("password") String password) {
        // validateLoginInput
        System.out.println(email+password);
        try {
            validateLoginInput(email, password, true, null, null);
            UserFilter userFilter = new UserFilter();
            userFilter.setEmail(email);
            User user = userManager.filterUser(userFilter).getSearchList().get(0);
            RoleType roleType = null;
            List<Role> roleList = roleManager.getRoles(user.getId());
            for (Role role : roleList) {
                if (RoleStatus.ACTIVE.equals(role.getRoleStatus()))
                    if (role.getRoleType().equals(RoleType.ADMIN) || role.getRoleType().equals(RoleType.EMPLOYEE)) {
                        roleType = role.getRoleType();
                        break;
                    }

            }
            if (null == roleType) {
                return new ResponseData(ExceptionCode.NOT_FOUND_EXCEPTION.code,
                        "Đăng nhập thất bại. Không tìm thấy thông tin tài khoản", "Account is not admin");
            }
            String decodePassword = KeyUtils.decodeBase64Encoder(password) + KeyUtils.getToken();
            Authentication auth = null;
            try {
                auth = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(email, KeyUtils.SHA256(decodePassword)));
            } catch (Exception e) {
                return new ResponseData(ExceptionCode.ERROR_EXCEPTION.code, e.getMessage(), e.getLocalizedMessage());

            }
            if (auth != null && auth.isAuthenticated()) {
                return new ResponseData(ExceptionCode.SUCCESS_EXCEPTION.code,
                        "Đăng nhập thành công", jwtUtils.generateToken(email));

            } else {
                return new ResponseData(ExceptionCode.NOT_FOUND_EXCEPTION.code,
                        "Đăng nhập thất bại. Không tìm thấy thông tin tài khoản", "Account is not admin");

            }
        } catch (ServiceException e) {
            return new ResponseData(ExceptionCode.ERROR_EXCEPTION.code, e.getMessage(), null);
        }
    }

    @ApiOperation(value = "Login account customer")
    @PostMapping("customer/login")
    public ResponseData loginCustomer(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("service-type") ServiceType serviceType,
            @RequestParam("full-name") String fullName,
            @RequestParam("image") String imageUrl) {
        // validateLoginInput
        try {
            validateLoginInput(email, password, false, serviceType, fullName);
            UserFilter userFilter = new UserFilter();
            userFilter.setEmail(email);
            List<User> users = userManager.filterUser(userFilter).getSearchList();
            User userUpdate = null;
            for (User user : users) {
                if (user.getServiceType().equals(serviceType)) {
                    userUpdate = user;
                    break;
                }
            }
            //kiểm tra khi tài khoản đăng nhập là tài khoản thường
            if (ServiceType.NORMALLY.equals(serviceType)) {
                if (null == userUpdate) {
                    return new ResponseData(ExceptionCode.NOT_FOUND_EXCEPTION.code,
                            "Đăng nhập thất bại. Không tìm thấy thông tin tài khoản", "Account is not personal");
                }
                if (!UserStatus.ACTIVE.equals(userUpdate.getStatus())) {
                    return new ResponseData(ExceptionCode.NOT_FOUND_EXCEPTION.code,
                            "Đăng nhập thất bại. Không tìm thấy thông tin tài khoản", "Account is not personal");

                }
                RoleType roleType = null;
                List<Role> roleList = roleManager.getRoles(userUpdate.getId());
                for (Role role : roleList) {
                    // quyền đang hoạt động
                    if (RoleStatus.ACTIVE.equals(role.getRoleStatus()))
                        if (role.getRoleType().equals(RoleType.CUSTOMER)) {
                            roleType = role.getRoleType();
                            break;
                        }

                }
                if (null == roleType) {
                    return new ResponseData(ExceptionCode.NOT_EXISTING_EXCEPTION.code,
                            "Đăng nhập thất bại. Không tìm thấy thông tin tài khoản", "Account is not personal");
                }
                String decodePassword = KeyUtils.decodeBase64Encoder(password) + KeyUtils.getToken();

                Authentication auth = null;
                try {
                    auth = authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(email, KeyUtils.SHA256(decodePassword)));
                } catch (Exception e) {
                    return new ResponseData(0, e.getMessage(), e.getLocalizedMessage());

                }
                if (auth != null && auth.isAuthenticated()) {
                    return new ResponseData(ExceptionCode.SUCCESS_EXCEPTION.code,
                            "Đăng nhập thành công", jwtUtils.generateToken(email));


                }

            } else {
                if (userUpdate == null) {
                    CreateUserInput createUserInput = new CreateUserInput();
                    UserInput userInput = new UserInput();
                    userInput.setImageUrl(imageUrl);
                    userInput.setEmail(email);
                    userInput.setServiceType(serviceType);
                    userInput.setFullName(fullName);
                    createUserInput.setUser(userInput);
                    createUserInput.setPassword(password);
                    createUserInput.setRoleType(RoleType.CUSTOMER);
                    User result = userCreateController.createUser(createUserInput);
                    return new ResponseData(ExceptionCode.SUCCESS_EXCEPTION.code,
                            "Đăng nhập thành công", result);
                } else {
                    return new ResponseData(ExceptionCode.SUCCESS_EXCEPTION.code,
                            "Đăng nhập thành công", userUpdate);

                }

            }
        } catch (ServiceException e) {
            return new ResponseData(ExceptionCode.ERROR_EXCEPTION.code, e.getErrorMessage(), null);
        }
        return new ResponseData(ExceptionCode.NOT_FOUND_EXCEPTION.code,
                "Đăng nhập thất bại. Không tìm thấy thông tin tài khoản", "Account is not personal");


    }

    @ApiOperation(value = "get info account login by token")
    @GetMapping("login/info-token")
    public ResponseData getUser(@RequestParam("code-token") String token, @RequestParam("service-type") ServiceType serviceType) {
        if (null == serviceType ||
                !ServiceType.isExist(serviceType.toString())) {
            return new ResponseData(ExceptionCode.NOT_EXISTING_EXCEPTION.code,
                    "Loại dịch vụ không tồn tại. ( " + ServiceType.getListName() + " )", null);
        }
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));
        String sub = payload.split("\\,")[0];
        String email = sub.substring(8, sub.length() - 1);
        UserFilter filter = new UserFilter();
        filter.setEmail(email);
        filter.setServiceType(serviceType);
        List<User> users = userManager.filterUser(filter).getSearchList();
        if (users.isEmpty()) {
            return new ResponseData(ExceptionCode.NOT_FOUND_EXCEPTION.code, "Không tìm thấy tài khoản", null);

        }
        return new ResponseData(ExceptionCode.SUCCESS_EXCEPTION.code, "Lấy thông tin tài khoản thành công", users.get(0));
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
