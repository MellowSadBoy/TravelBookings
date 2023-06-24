package hcmuaf.edu.vn.bookingtravel.api.manager;

import hcmuaf.edu.vn.bookingtravel.api.base.filter.ResultList;
import hcmuaf.edu.vn.bookingtravel.api.base.manager.BaseManager;
import hcmuaf.edu.vn.bookingtravel.api.model.user.enums.UserStatus;
import hcmuaf.edu.vn.bookingtravel.api.model.geo.Address;
import hcmuaf.edu.vn.bookingtravel.api.model.input.user.UpdateInfoUserInput;
import hcmuaf.edu.vn.bookingtravel.api.model.input.user.UpdateStatusInput;
import hcmuaf.edu.vn.bookingtravel.api.model.user.*;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;

@Repository
public class UserManager extends BaseManager<User> {


    public UserManager() {
        super(User.class.getSimpleName(), User.class);
    }

    public User createUser(User user) {
        user.setId(generateId());
        user.setCreatedAt(new Date());
        user.setUpdatedAt(null);
        user.setStatus(user.getStatus() != null ? user.getStatus() : UserStatus.ACTIVE);
        return create(user);
    }


    public User getUser(String userId) {
        return getById(userId);
    }


    public User updateInfoUser(String userId, UpdateInfoUserInput updateInfoUser) {

        HashMap<String, Object> updateDocument = new HashMap<>();
        updateDocument.put("updatedAt", new Date());
        if (updateInfoUser.getUsername() != null)
            updateDocument.put("username", updateInfoUser.getUsername());
        if (updateInfoUser.getBirthday() != null)
            updateDocument.put("birthday", updateInfoUser.getBirthday());
        if (updateInfoUser.getGender() != null)
            updateDocument.put("gender", updateInfoUser.getGender().toString());
        if (updateInfoUser.getImageUrl() != null)
            updateDocument.put("imageUrl", updateInfoUser.getImageUrl());
        if (updateInfoUser.getEmail() != null)
            updateDocument.put("email", updateInfoUser.getEmail());
        if (updateInfoUser.getTelephone() != null)
            updateDocument.put("telephone", updateInfoUser.getTelephone());
        if (updateInfoUser.getFullName() != null)
            updateDocument.put("fullName", updateInfoUser.getFullName());
        return update(userId, updateDocument, null, "Cập nhật thông tin tài khoản " + userId);

    }

    public User updateAvatar(String userId,String newAvatar) {
        HashMap<String, Object> updateDocument = new HashMap<>();
        updateDocument.put("updatedAt", new Date());
        if (newAvatar != null)
            updateDocument.put("imageUrl", newAvatar);
        return update(userId, updateDocument, null, "Cập nhật ảnh đại diện " + userId);
    }

    public User updateAddress(String userId, Address address) {
        HashMap<String, Object> updateDocument = new HashMap<>();
        updateDocument.put("updatedAt", new Date());
        if (address != null)
            updateDocument.put("address", address);

        return update(userId, updateDocument, null, "Cập nhật địa chỉ cho tài khoản " + userId);
    }

    public User updateUserStatus(String userId, UpdateStatusInput statusBody) {

        // update order status
        HashMap<String, Object> document = new HashMap<String, Object>();
        document.put("updatedAt", new Date());
        document.put("status", statusBody.getStatus());
        // add activity
        String description = "Cập nhật trạng thái" +
                ": " + statusBody.getStatus();
        if (StringUtils.hasText(statusBody.getNote())) {
            description += ". " + statusBody.getNote();
        }
        return update(userId, document, statusBody.getByUser(), description);
    }


    public void deleteUser(String userId, String byUser) {
        String note = "Xoá tài khoản " + userId;
        delete(userId, byUser, note);
    }


    public ResultList<User> filterUser(UserFilter filterData) {
        HashMap<String, Object> mapFilter = filterData.getExtendFilters();
        Query query = filterData.getQuerys(mapFilter);
        return getResultList(filterData.getSearch(), filterData.getCreatedAtFrom(),
                filterData.getCreatedAtTo(),
                filterData.getOffset(),
                filterData.getMaxResult(),
                query);


    }


}
