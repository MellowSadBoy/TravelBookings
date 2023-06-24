package hcmuaf.edu.vn.bookingtravel.api;

import hcmuaf.edu.vn.bookingtravel.api.model.hotel.Hotel;
import hcmuaf.edu.vn.bookingtravel.api.model.hotel.enums.HotelStatus;
import hcmuaf.edu.vn.bookingtravel.api.restcontroller.HotelController;
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
public class TestDeleteHotel {
    @Autowired
    private HotelController hotelController;

    @Test
    public void testDeleteHotel() throws Exception {
        Hotel hotel = hotelController.deletedHotel("1687242700791022", "Thanh");
        assertEquals(hotel.getStatus(), HotelStatus.INACTIVE);
    }
}
