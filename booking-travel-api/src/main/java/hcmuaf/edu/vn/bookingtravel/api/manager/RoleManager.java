package hcmuaf.edu.vn.bookingtravel.api.manager;


import hcmuaf.edu.vn.bookingtravel.api.base.manager.BaseManager;
import hcmuaf.edu.vn.bookingtravel.api.model.user.enums.RoleType;
import hcmuaf.edu.vn.bookingtravel.api.model.user.Role;

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
public class RoleManager extends BaseManager<Role> {
    public RoleManager() {
        super(Role.class.getSimpleName(), Role.class);
    }

    public Role createRole(String userId, Role role) {
        role.setId(generateId());
        role.setCreatedAt(new Date());
        role.setUpdatedAt(null);
        role.setUserId(userId);
        return create(role);

    }

    public List<Role> getRoles(String userId) {
        return getList("userId", userId);
    }

    public Role updateRole(String userId, RoleType type, String activityUser) {
        HashMap<String, Object> document = new HashMap<>();
        document.put("updatedAt", new Date());
        document.put("type", type.toString());
        update(Query.query(Criteria.where("userId").is(userId)), document, activityUser, "Cập nhật phân quyền " + type.getDescription() + " cho tài khoản :" + userId);
        return getByQuery(Query.query(Criteria.where("userId").is(userId)));
    }

    public void deleteRole(String userId, String byUser) {
        String note = "Xoá tất cả các quyền của tài khoản " + userId;
        deleteByAttribute("userId", userId, byUser, note);
    }

}
