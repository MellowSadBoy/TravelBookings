package hcmuaf.edu.vn.bookingtravel.api;

import hcmuaf.edu.vn.bookingtravel.api.model.hotelBooked.HotelBooked;
import hcmuaf.edu.vn.bookingtravel.api.model.hotelBooked.enums.HotelBookedStatus;
import hcmuaf.edu.vn.bookingtravel.api.restcontroller.HotelBookedController;
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
public class TestCancelHotelBooked {
    @Autowired
    private HotelBookedController hotelBookedController;

    @Test
    public void testCancelHotelBooked() throws Exception {
        HotelBooked hotelBooked = hotelBookedController.cancelHotelBooked("1687242700791022", "Thanh");
        assertEquals(hotelBooked.getStatus(), HotelBookedStatus.CANCELLED);
    }
}
