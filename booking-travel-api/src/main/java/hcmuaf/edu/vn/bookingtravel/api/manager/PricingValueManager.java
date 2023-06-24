package hcmuaf.edu.vn.bookingtravel.api.manager;

import hcmuaf.edu.vn.bookingtravel.api.base.manager.BaseManager;
import hcmuaf.edu.vn.bookingtravel.api.model.tour.PricingValue;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 14/04/2023, Thứ Sáu
 **/
@Repository
public class PricingValueManager extends BaseManager<PricingValue> {
    public PricingValueManager() {
        super(PricingValue.class.getSimpleName(), PricingValue.class);
    }

    public PricingValue createPricingValue(PricingValue value) {
        value.setCreatedAt(new Date());
        value.setId(value.getId());
        return create(value);
    }

    public void createPricingValues(List<PricingValue> pricingValues) {
        pricingValues.forEach(p -> {
            p.setCreatedAt(new Date());
            p.setId(generateId());
        });
        create(pricingValues, "Vũ Văn Minh", "Tạo tiền ");
    }

    public List<PricingValue> getPricingValues(String tourId) {
        return getList("tourId", tourId);
    }
}
