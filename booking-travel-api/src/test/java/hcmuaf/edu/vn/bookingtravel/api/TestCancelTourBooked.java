package hcmuaf.edu.vn.bookingtravel.api;

import hcmuaf.edu.vn.bookingtravel.api.model.tourBooked.TourBooked;
import hcmuaf.edu.vn.bookingtravel.api.model.tourBooked.enums.TourBookedStatus;
import hcmuaf.edu.vn.bookingtravel.api.restcontroller.TourBookedController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 15/06/2023, Thứ Năm
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = BookingTravelApiApplication.class)
@AutoConfigureMockMvc
public class TestCancelTourBooked {
    @Autowired
    private TourBookedController tourBookedController;

    @Test
    public void testCancelTourBooked() throws Exception {
        TourBooked tourBooked = tourBookedController.cancelTourBooked("1686839513325772", "Minh");
        assertEquals(tourBooked.getStatus(), TourBookedStatus.CANCELLED);
    }
}
