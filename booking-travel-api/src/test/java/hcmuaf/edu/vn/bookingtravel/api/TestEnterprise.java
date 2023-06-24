package hcmuaf.edu.vn.bookingtravel.api;

import hcmuaf.edu.vn.bookingtravel.api.base.utils.GeoUtils;
import hcmuaf.edu.vn.bookingtravel.api.enums.GeoType;
import hcmuaf.edu.vn.bookingtravel.api.manager.EnterpriseManager;
import hcmuaf.edu.vn.bookingtravel.api.manager.GeoManager;
import hcmuaf.edu.vn.bookingtravel.api.model.Enterprise;
import hcmuaf.edu.vn.bookingtravel.api.model.geo.Address;
import hcmuaf.edu.vn.bookingtravel.api.model.geo.Geo;
import hcmuaf.edu.vn.bookingtravel.api.restcontroller.EnterpriseController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 15/04/2023, Thứ Bảy
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = BookingTravelApiApplication.class)
@AutoConfigureMockMvc
public class TestEnterprise {
    @Autowired
    private EnterpriseManager enterpriseManager;
    @Autowired
    EnterpriseController enterpriseController;
    @Autowired
    private GeoUtils geoUtils;
    @Autowired
    private GeoManager geoManager;

    @Test
    public void testEnterprise() throws Exception {
        Enterprise enterprise = new Enterprise();
        enterprise.setName("Mellow");
        enterprise.setHotLine("1900 3398");
        enterprise.setDescription("Khám phá những điều tuyệt vời trên thế giới");
        enterprise.setImgLogoUrl("https://firebasestorage.googleapis.com/v0/b/booking-travel-71400.appspot.com/o/Mellow.png?alt=media&token=0d781006-892d-4193-9aa0-b5bf43efd647");
        Address address = new Address();
        address.setAddress("106A Mai Anh Đào,Phường 1, Quận 2, Tp. HCM, Việt Nam");
        address.setProvince("Thành phố Hồ Chí Minh");
        address.setProvinceCode(209);
        address.setDistrict("Quận 2");
        address.setDistrictCode(1550);
        address.setWard("Phường 1");
        address.setWardCode(420111);
        enterprise.setAddress(address);
        enterpriseManager.createEnterprise(enterprise);

    }
}
