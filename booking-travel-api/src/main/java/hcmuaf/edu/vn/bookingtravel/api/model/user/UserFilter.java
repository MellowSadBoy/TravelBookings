package hcmuaf.edu.vn.bookingtravel.api.model.user;

import hcmuaf.edu.vn.bookingtravel.api.base.filter.BaseFilter;
import hcmuaf.edu.vn.bookingtravel.api.model.user.enums.GenderType;
import hcmuaf.edu.vn.bookingtravel.api.model.user.enums.ServiceType;
import hcmuaf.edu.vn.bookingtravel.api.model.user.enums.UserStatus;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;


@Data
public class UserFilter extends BaseFilter {
    private String userId;
    private String fullName;
    private String email;
    private String telephone;
    private GenderType gender;
    private ServiceType serviceType;
    private UserStatus status;


    @Override
    public HashMap<String, Object> getExtendFilters() {
        HashMap<String, Object> hashMap = new HashMap<>();
        if (!StringUtils.isBlank(this.fullName))
            hashMap.put("fullName", this.fullName);
        if (null != this.gender)
            hashMap.put("gender", this.gender.toString());
        if (null != this.status)
            hashMap.put("status", this.status.toString());
        if (null != this.serviceType)
            hashMap.put("serviceType", this.serviceType.toString());
        if (!StringUtils.isBlank(this.email))
            hashMap.put("email", this.email);
        if (!StringUtils.isBlank(this.telephone))
            hashMap.put("telephone", this.telephone);
        if (!StringUtils.isBlank(this.userId))
            hashMap.put("_id", this.userId);
        return hashMap;
    }


}

