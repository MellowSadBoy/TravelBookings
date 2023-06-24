package hcmuaf.edu.vn.bookingtravel.api.controller.user;

import hcmuaf.edu.vn.bookingtravel.api.base.exception.ExceptionCode;
import hcmuaf.edu.vn.bookingtravel.api.base.exception.ServiceException;
import hcmuaf.edu.vn.bookingtravel.api.manager.KeyPassWordManager;
import hcmuaf.edu.vn.bookingtravel.api.manager.RoleManager;
import hcmuaf.edu.vn.bookingtravel.api.manager.ScoreManager;
import hcmuaf.edu.vn.bookingtravel.api.manager.UserManager;
import hcmuaf.edu.vn.bookingtravel.api.model.input.user.CreateUserInput;

import hcmuaf.edu.vn.bookingtravel.api.model.input.user.UserInput;
import hcmuaf.edu.vn.bookingtravel.api.model.user.*;
import hcmuaf.edu.vn.bookingtravel.api.base.utils.KeyUtils;
import hcmuaf.edu.vn.bookingtravel.api.base.utils.RemoveAccentUtils;
import hcmuaf.edu.vn.bookingtravel.api.model.user.enums.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserCreateController {
    @Autowired
    private UserManager userManager;
    @Autowired
    private KeyPassWordManager keyPassWordManager;
    @Autowired
    private RoleManager roleManager;
    @Autowired
    private ScoreManager scoreManager;

    public User createUser(CreateUserInput createUserInput) throws ServiceException {
        //validateCreateUserInput
        validateCreateUserInput(createUserInput);
        UserInput userInput = createUserInput.getUser();
        User user = new User();
        user.setId(userManager.generateId());
        user.setCreatedBy(userInput.getByUser());
        user.setUsername(userInput.getUsername());
        if (null == userInput.getUsername()) {
            user.setUsername(RemoveAccentUtils.generateUserName(userInput.getFullName()));
        }
        user.setTelephone(userInput.getTelephone());
        UserStatus userStatus = UserStatus.INACTIVE;
        user.setStatus(userStatus);
        user.setGender(userInput.getGender());
        user.setEmail(userInput.getEmail());
        user.setFullName(userInput.getFullName());
        user.setBirthday(userInput.getBirthday());
        user.setAddress(userInput.getAddress());
        user.setImageUrl(userInput.getImageUrl());
        if (null == userInput.getImageUrl()) {
            String imageUrl = userInput.getFullName() == null ? user.getUsername() : userInput.getFullName();
            user.setImageUrl("https://ui-avatars.com/api/?name=" + imageUrl.replaceAll(" ", ""));
        }
        ServiceType serviceType = ServiceType.NORMALLY;
        if (null != userInput.getServiceType()) {
            serviceType = userInput.getServiceType();
        }
        user.setServiceType(serviceType);
        user.setDescription("Login " + user.getServiceType().getDescription());
        //set key password
        KeyPassword keyPassword = new KeyPassword();
        String token = KeyUtils.getToken();
        String password = KeyUtils.SHA256(KeyUtils.decodeBase64Encoder(createUserInput.getPassword()) + token);
        keyPassword.setPassword(password);
        keyPassword.setToken(token);
        keyPassword.setNote("Tạo mật khẩu cho tài khoản " + user.getId());
        PasswordStatus passwordStatus = PasswordStatus.NEW;

        keyPassword.setStatus(passwordStatus);

        //set role for user
        Role role = new Role();
        role.setNote("Phân quyền cho tài khoản " + user.getId());
        role.setRoleType(createUserInput.getRoleType());
        RoleStatus roleStatus = RoleStatus.ACTIVE;
        role.setRoleStatus(roleStatus);
        UserFilter userFilter = new UserFilter();
        if (null != createUserInput.getUser().getEmail()) {
            userFilter.setEmail(createUserInput.getUser().getEmail());
            userFilter.setServiceType(createUserInput.getUser().getServiceType());
        }
        List<User> userList = userManager.filterUser(userFilter).getSearchList();

        if (userList.size() != 0) {
            if (UserStatus.ACTIVE.equals(userList.get(0).getStatus())) {
                throw new ServiceException("exist_account", "Email của bạn đã tồn tại");
            }
            if (UserStatus.INACTIVE.equals(userList.get(0).getStatus())) {
                keyPassWordManager.updatePassword(userList.get(0).getId(), password, "Vũ Văn Minh");
                return userManager.getUser(userList.get(0).getId());

            }
        }
        user = userManager.createUser(user);
        keyPassWordManager.createKeyPassWord(user.getId(), keyPassword, user.getCreatedBy());
        roleManager.createRole(user.getId(), role);
        // create score
        Score score = new Score();
        score.setUserId(user.getId());
        score.setType(ScoreType.RANK_BRONZE);
        score.setScore(0);
        scoreManager.createScore(score);
        return user;
    }

    private void validateCreateUserInput(CreateUserInput createUserInput) throws ServiceException {
        if (null == createUserInput) {
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "createUserInput is null");
        }
        if (null == createUserInput.getUser()) {
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Chưa điền thông tin cho user");
        }
        if (null == createUserInput.getUser().getEmail()) {
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Chưa điền thông tin email");
        }
        if (null != createUserInput.getUser().getGender() &&
                !GenderType.isExist(createUserInput.getUser().getGender().toString())) {
            throw new ServiceException(ExceptionCode.NOT_EXIST_TYPE_EXCEPTION.toString(), "Loại giới tính không tồn tại.( " + GenderType.getListName() + " )");
        }
        if (!ServiceType.isExist(createUserInput.getUser().getServiceType().toString())) {
            throw new ServiceException(ExceptionCode.NOT_EXIST_TYPE_EXCEPTION.toString(), "Loại dịch vụ không tồn tại. ( " + ServiceType.getListName() + " )");
        }


        if (null == createUserInput.getPassword()
                || createUserInput.getPassword().length() == 0
                || "null".equalsIgnoreCase(createUserInput.getPassword())) {
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Chưa tạo mật khẩu cho user");
        }

        if (null == createUserInput.getRoleType()) {
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Chưa phân quyền cho user");
        }

        if (!RoleType.isExist(createUserInput.getRoleType().toString())) {
            throw new ServiceException(ExceptionCode.NOT_EXIST_TYPE_EXCEPTION.toString(), "Loại phân quyền không tồn tại. ( " + RoleType.getListName() + " )");

        }
    }

}
