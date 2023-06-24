package hcmuaf.edu.vn.bookingtravel.api.manager;

import hcmuaf.edu.vn.bookingtravel.api.base.manager.BaseManager;
import hcmuaf.edu.vn.bookingtravel.api.model.Enterprise;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 15/04/2023, Thứ Bảy
 **/
@Repository
public class EnterpriseManager extends BaseManager<Enterprise> {
    public EnterpriseManager() {
        super(Enterprise.class.getSimpleName(), Enterprise.class);
    }

    public Enterprise createEnterprise(Enterprise enterprise) {
        enterprise.setId(generateId());
        enterprise.setCreatedAt(new Date());
        return create(enterprise);
    }

    public Enterprise getEnterprise() {
        return getEntity();
    }
}
