package hcmuaf.edu.vn.bookingtravel.api.manager;

import hcmuaf.edu.vn.bookingtravel.api.base.manager.BaseManager;
import hcmuaf.edu.vn.bookingtravel.api.model.tour.Tour;
import hcmuaf.edu.vn.bookingtravel.api.model.tour.Vehicle;
import hcmuaf.edu.vn.bookingtravel.api.model.tour.enums.TourStatus;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 14/04/2023, Thứ Sáu
 **/
@Repository
public class VehicleManager extends BaseManager<Vehicle> {
    public VehicleManager() {
        super(Vehicle.class.getSimpleName(), Vehicle.class);
    }

    public Vehicle createVehicle(Vehicle vehicle) {
        vehicle.setId(generateId());
        vehicle.setCreatedAt(new Date());
        return create(vehicle);
    }

    public Vehicle getVehicleByTourId(String tourId) {
        return getByQuery(Query.query(Criteria.where("tourId").is(tourId)));
    }
}
