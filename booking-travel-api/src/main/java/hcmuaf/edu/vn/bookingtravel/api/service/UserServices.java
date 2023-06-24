package hcmuaf.edu.vn.bookingtravel.api.service;

import hcmuaf.edu.vn.bookingtravel.api.model.user.enums.PasswordStatus;
import hcmuaf.edu.vn.bookingtravel.api.manager.KeyPassWordManager;
import hcmuaf.edu.vn.bookingtravel.api.manager.UserManager;
import hcmuaf.edu.vn.bookingtravel.api.model.user.KeyPassword;
import hcmuaf.edu.vn.bookingtravel.api.model.user.User;
import hcmuaf.edu.vn.bookingtravel.api.model.user.UserFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServices implements UserDetailsService {
    @Autowired
    private UserManager userManager;
    @Autowired
    private KeyPassWordManager keyPassWordManager;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserFilter userFilter = new UserFilter();
        userFilter.setEmail(email);
        User user = userManager.filterUser(userFilter).getSearchList().get(0);
        List<KeyPassword> keyPasswords = keyPassWordManager.getKeyPasswords(user.getId());
        String pwd = "";
        for (KeyPassword key : keyPasswords) {
            if (PasswordStatus.NEW.equals(key.getStatus())){
                pwd= key.getPassword();
                break;
            }


        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), pwd, new ArrayList<>());
    }
}
