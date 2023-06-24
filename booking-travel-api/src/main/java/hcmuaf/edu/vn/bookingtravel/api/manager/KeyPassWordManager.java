package hcmuaf.edu.vn.bookingtravel.api.manager;

import hcmuaf.edu.vn.bookingtravel.api.base.manager.BaseManager;
import hcmuaf.edu.vn.bookingtravel.api.model.user.enums.PasswordStatus;
import hcmuaf.edu.vn.bookingtravel.api.model.user.KeyPassword;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 11/04/2023, Thứ Ba
 **/
@Repository
public class KeyPassWordManager extends BaseManager<KeyPassword> {
    public KeyPassWordManager() {
        super(KeyPassword.class.getSimpleName(), KeyPassword.class);
    }

    public KeyPassword createKeyPassWord(String userId, KeyPassword keyPassword, String updateBy) {
        keyPassword.setCreatedAt(new Date());
        keyPassword.setUpdatedAt(null);
        keyPassword.setId(generateId());
        keyPassword.setUserId(userId);
        keyPassword.setStatus(PasswordStatus.NEW);
        //cập nhật mật khẩu cũ
        updateAll("userId", userId, "status", PasswordStatus.OLD.toString(), updateBy,
                "Tạo mật khẩu mới cho " + userId);
        return create(keyPassword);
    }

    public List<KeyPassword> getKeyPasswords(String userId) {
        return getList("userId", userId);
    }

    public KeyPassword getKeyPasswordNew(String userId) {
        return getByQuery(Query.query(Criteria.where("userId").is(userId).and("status").is(PasswordStatus.NEW.toString())));
    }

    public KeyPassword updatePassword(String userId, String password, String byUser) {
        HashMap<String, Object> document = new HashMap<>();
        document.put("updatedAt", new Date());
        document.put("password", password);
        update(Query.query(Criteria.where("userId").is(userId)), document, byUser, "Cập nhật mật khẩu cho tài khoản :" + userId);
        return getByQuery(Query.query(Criteria.where("userId").is(userId)));

    }

    public void deletePassword(String userId, String byUser) {
        String note = "Xoá tài khoản tất cả các mật khẩu của " + userId;
        deleteByAttribute("userId", userId, byUser, note);
    }
}
