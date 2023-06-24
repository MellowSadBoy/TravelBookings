package hcmuaf.edu.vn.bookingtravel.api.model.input.tour;

import lombok.Data;

import java.util.List;

@Data
public class UpdateInfoTourInput {
    private TourInput tourInput;
    private List<ScheduleInput> scheduleInput;
    private VehicleInput vehicleInput;
    private List<PricingValueInput> pricingValues;
}
