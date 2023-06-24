package hcmuaf.edu.vn.bookingtravel.api;

import hcmuaf.edu.vn.bookingtravel.api.client.model.bank.ResultHistory;
import hcmuaf.edu.vn.bookingtravel.api.restcontroller.BankController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 10/05/2023, Thứ Tư
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = BookingTravelApiApplication.class)
@AutoConfigureMockMvc
public class TestBank {
    @Autowired
    private BankController bankController;
    @Test
    public void testBank() throws Exception {
        ResultHistory resultHistory = bankController.getHistoryBank();
        assertNotNull(resultHistory);
    }
}
