package hcmuaf.edu.vn.bookingtravel.api;

import hcmuaf.edu.vn.bookingtravel.api.model.tour.Tour;
import hcmuaf.edu.vn.bookingtravel.api.model.tour.enums.TourStatus;
import hcmuaf.edu.vn.bookingtravel.api.restcontroller.TourController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = BookingTravelApiApplication.class)
@AutoConfigureMockMvc
public class TestDeleteTour {
    @Autowired
    private TourController tourController;

    @Test
    public void testDeleteTour() throws Exception {
        Tour tour = tourController.deleteTour("1687242700791022", "Thanh", "");
        assertEquals(tour.getStatus(), TourStatus.INACTIVE);
    }
}
