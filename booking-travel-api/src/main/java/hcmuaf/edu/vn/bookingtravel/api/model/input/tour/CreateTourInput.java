package hcmuaf.edu.vn.bookingtravel.api.model.input.tour;

import lombok.Data;

import java.util.List;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 13/04/2023, Thứ Năm
 **/
@Data
public class CreateTourInput {
    private TourInput tourInput;
    private List<ScheduleInput> scheduleInput;
    private VehicleInput vehicleInput;
    private List<PricingValueInput> pricingValues;
}
