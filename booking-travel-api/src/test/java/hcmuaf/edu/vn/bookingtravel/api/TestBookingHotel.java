package hcmuaf.edu.vn.bookingtravel.api;

import hcmuaf.edu.vn.bookingtravel.api.base.exception.ServiceException;
import hcmuaf.edu.vn.bookingtravel.api.base.model.ResponseData;
import hcmuaf.edu.vn.bookingtravel.api.base.utils.*;
import hcmuaf.edu.vn.bookingtravel.api.controller.tour.CreateTourController;
import hcmuaf.edu.vn.bookingtravel.api.controller.user.UserProfileController;
import hcmuaf.edu.vn.bookingtravel.api.controller.user.UserUpdateController;
import hcmuaf.edu.vn.bookingtravel.api.controller.utils.UserUtilsController;
import hcmuaf.edu.vn.bookingtravel.api.enums.CountryCode;
import hcmuaf.edu.vn.bookingtravel.api.enums.GeoType;
import hcmuaf.edu.vn.bookingtravel.api.manager.GeoManager;
import hcmuaf.edu.vn.bookingtravel.api.model.geo.Address;
import hcmuaf.edu.vn.bookingtravel.api.model.geo.Geo;
import hcmuaf.edu.vn.bookingtravel.api.model.input.tour.*;
import hcmuaf.edu.vn.bookingtravel.api.model.input.user.CreateUserInput;
import hcmuaf.edu.vn.bookingtravel.api.model.input.user.UserInput;
import hcmuaf.edu.vn.bookingtravel.api.model.tour.enums.TimeHowLong;
import hcmuaf.edu.vn.bookingtravel.api.model.tour.enums.TourType;
import hcmuaf.edu.vn.bookingtravel.api.model.tour.enums.VehicleType;
import hcmuaf.edu.vn.bookingtravel.api.model.user.DataRegister;
import hcmuaf.edu.vn.bookingtravel.api.model.user.UserFilter;
import hcmuaf.edu.vn.bookingtravel.api.model.user.enums.AgeType;
import hcmuaf.edu.vn.bookingtravel.api.model.user.enums.GenderType;
import hcmuaf.edu.vn.bookingtravel.api.model.user.enums.RoleType;
import hcmuaf.edu.vn.bookingtravel.api.model.user.enums.ServiceType;
import hcmuaf.edu.vn.bookingtravel.api.restcontroller.GeoController;
import hcmuaf.edu.vn.bookingtravel.api.restcontroller.HotelController;
import hcmuaf.edu.vn.bookingtravel.api.restcontroller.LoginController;
import hcmuaf.edu.vn.bookingtravel.api.restcontroller.RegisterController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.*;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 12/04/2023, Thứ Tư
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookingTravelApiApplication.class)
@AutoConfigureMockMvc
public class TestBookingHotel {
    @Autowired
    private GeoController geoController;
    @Autowired
    private GeoManager geoManager;
    @Autowired
    private LoginController loginController;
    @Autowired
    private RegisterController registerController;
    @Autowired
    UserUpdateController userUpdateController;
    @Autowired
    UserProfileController userProfileController;
    @Autowired
    private UserUtilsController userUtilsController;
    @Autowired
    private CreateTourController createTourController;
    @Autowired
    private HotelController hotelController;
    private String byUser;
    private List<String> fullNames = new ArrayList<String>();
    @Autowired
    private GeoUtils geoUtils;
    @Value("${market.url}")
    private String url;
    @Value("${market.token}")
    private String token;
    @Value("${market.code}")
    private String code;

    public TestBookingHotel() {
        this.byUser = "Vũ Văn Minh";
        fullNames.add("Vũ Văn Minh");
    }

    @Test
    public void testUser() throws ServiceException {
        for (String fullName : fullNames) {
            CreateUserInput createUserInput = new CreateUserInput();
            UserInput user = new UserInput();
            user.setBirthday(NumberUtils.generateDate());
            user.setByUser(byUser);
            user.setEmail(RemoveAccentUtils.generateUserName("minh") + "@gmail.com");
            user.setEmail("adminMinh@gmail.com");
            user.setDescription("Tài khoản khách hàng");
            user.setFullName(fullName);
            user.setGender(GenderType.MAN);
            user.setServiceType(ServiceType.NORMALLY);
            user.setTelephone(NumberUtils.generateTelePhone());
            HashMap<String, String> mapGeo = geoUtils.generateGeo();
            Address address = new Address();
            address.setAddress(mapGeo.get("address"));
            address.setWardCode(Integer.parseInt(mapGeo.get("ward")));
            Geo geoWard = geoManager.getGeoChild(GeoType.WARD, Integer.parseInt(mapGeo.get("ward")));
            address.setWard(geoWard.getName());
            Geo geoDistrict = geoManager.getGeoChild(GeoType.DISTRICT, Integer.parseInt(mapGeo.get("district")));
            address.setDistrict(geoDistrict.getName());
            address.setDistrictCode(Integer.parseInt(mapGeo.get("district")));
            Geo geoProvince = geoManager.getGeoChild(GeoType.PROVINCE, Integer.parseInt(mapGeo.get("province")));
            address.setProvince(geoProvince.getName());
            address.setProvinceCode(Integer.parseInt(mapGeo.get("province")));
            user.setAddress(address);
            createUserInput.setUser(user);
            createUserInput.setPassword("MTIzNDU2");
            createUserInput.setRoleType(RoleType.ADMIN);
            // Đăng kí tài khoản khách hàng
            ResponseData result = registerController.registerUser(createUserInput);
            if (result.getData() != null) {
                DataRegister dataRegister = (DataRegister) result.getData();
                userUpdateController.updateActiveUser(dataRegister.getUserId(), byUser);
            }

        }

    }

    @Test
    public void testFilter() {
        UserFilter userFilter = new UserFilter();
        userProfileController.filterUsers(userFilter);
    }

    @Test
    public void testTour_1() throws ServiceException {
        CreateTourInput createTourInput = new CreateTourInput();
        TourInput tourInput = new TourInput();
        tourInput.setDescription(
                "    Du ngoạn Vịnh Nha Trang - một trong 29 vịnh biển đẹp nhất hành tinh với các danh thắng Hòn Tằm, Hòn Chồng độc đáo\n"
                        +
                        "    Tham quan công trình Chăm nổi bật Tháp Bà Ponagar tại Nha Trang\n" +
                        "    Vui chơi thỏa thích tại VinPearl Land Nha Trang , khám phá Đảo Hòn Tre xinh đẹp\n" +
                        "    Ăn uống \"thả ga\" với buffet Hòn Tằm và BBQ hải sản ngon miệng\n" +
                        "    Check-in cà phê Mê Linh nổi tiếng với view đồi núi đẹp mê mẩn\n" +
                        "    Trầm trồ trước những công trình độc đáo như Nhà ga Đà Lạt, Chùa Linh Phước,...\n" +
                        "\n" +
                        "Nghỉ lễ mà được thỏa thích tắm biển, leo núi, thưởng thức các món ngon, cảnh đẹp thì còn gì bằng. Nhanh chóng liên hệ Mellow Booking đặt tour bạn nhé!\n");
        Address address = new Address();
        address.setAddress("106A Mai Anh Đào,Phường 1, Quận 2, Tp. HCM, Việt Nam");
        address.setProvince("Thành phố Hồ Chí Minh");
        address.setProvinceCode(209);
        address.setDistrict("Quận 2");
        address.setDistrictCode(1550);
        address.setWard("Phường 1");
        address.setWardCode(420111);
        address.setCountryCode(CountryCode.VN);
        tourInput.setAddressStart(address);
        tourInput.setHotelId(hotelController.getAllHotels().get(0).getId());
        tourInput.setFeatureImgUrl(
                "https://www.vietnambooking.com/wp-content/uploads/2017/09/tour-du-lich-nha-trang-da-lat-20-9-20173-300x194.jpg");
        tourInput.setImgUrls(Arrays.asList(
                "https://www.vietnambooking.com/wp-content/uploads/2020/07/tour-nha-trang-da-lat-4-ngay-3-dem-11.jpg",
                "https://www.vietnambooking.com/wp-content/uploads/2017/04/tour-nha-trang-da-lat-4-ngay-3-dem-1.jpg",
                "https://www.vietnambooking.com/wp-content/uploads/2017/04/tour-nha-trang-da-lat-4-ngay-3-dem-5.jpg",
                "https://www.vietnambooking.com/wp-content/uploads/2020/07/tour-nha-trang-da-lat-4-ngay-3-dem-77.jpg",
                "https://www.vietnambooking.com/wp-content/uploads/2020/07/tour-nha-trang-da-lat-4-ngay-3-dem-88.jpg",
                "https://www.vietnambooking.com/wp-content/uploads/2020/07/tour-nha-trang-da-lat-4-ngay-3-dem-99.jpg",
                "https://www.vietnambooking.com/wp-content/uploads/2017/09/tour-du-lich-nha-trang-da-lat-20-9-2017.jpg"));
        Address localAddress = new Address();
        localAddress.setAddress("Turtle Bay, Phạm Văn Đồng, Nha Trang, Việt Nam");
        localAddress.setProvince("Khánh Hoà");
        localAddress.setProvinceCode(208);
        localAddress.setDistrict("Thành phố Nha Trang");
        localAddress.setDistrictCode(1548);
        localAddress.setWard("Xã Vĩnh Hoà");
        localAddress.setWardCode(410114);
        localAddress.setCountryCode(CountryCode.VN);
        tourInput.setLocation(localAddress);
        tourInput.setName("Tour Tp.HCM – Nha Trang 2 ngày 1 đêm: Khám phá thành phố biển đến xứ ngàn hoa");
        tourInput.setNote("Tour note");
        tourInput.setDiscount(DiscountUtils.discount());
        tourInput.setTimeHowLong(TimeHowLong.TWO_DAY_ONE_NIGHT);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.APRIL, 29); // Tháng bắt đầu từ 0
        tourInput.setStartDate(new Date(calendar.getTimeInMillis()));
        tourInput.setPriceStandard(MoneyCalculateUtils.getMoney(5000000));
        tourInput.setQuantityVisit(10);
        tourInput.setType(TourType.DOMESTIC);
        createTourInput.setTourInput(tourInput);

        VehicleInput vehicleInput = new VehicleInput();
        vehicleInput.setAutomaker("Phương Trang");
        vehicleInput.setName("Xe Limousine 16 chổ");
        vehicleInput.setVehicleType(VehicleType.CAR_16);
        vehicleInput.setNumberOfSeats(VehicleType.CAR_16.getNumberOfSeats());
        vehicleInput.setCode("51A-5555");
        createTourInput.setVehicleInput(vehicleInput);

        List<ScheduleInput> scheduleInputs = new ArrayList<>();
        ScheduleInput scheduleInput1 = new ScheduleInput();
        scheduleInput1.setTitle("NGÀY 1 | HÀ NỘI/ TP.HCM – NHA TRANG");
        scheduleInput1.setNameLocation("HÀ NỘI/ TP.HCM – NHA TRANG");
        scheduleInput1.setLocationStop(localAddress);
        scheduleInput1.setDescription(
                "Sáng/ Trưa: Xe và HDV Mellow Booking đón khách tại sân bay Cam Ranh/ Bến xe/ Nhà ga,\n" +
                        "sau đó đưa khách về trung tâm Thành phố Nha Trang nhận phòng khách sạn.\n" +
                        "Chiều: Tự do tham quan thành phố biển và thưởng thức những món ngon đường phố Nha Trang.");
        scheduleInput1.setArrivalTime(Instant.parse("2023-05-01T09:00:00Z").toEpochMilli());
        scheduleInput1.setLeaveTime(Instant.parse("2023-05-01T11:00:00Z").toEpochMilli());
        scheduleInputs.add(scheduleInput1);
        ScheduleInput scheduleInput2 = new ScheduleInput();
        scheduleInput2.setTitle("NGÀY 2 | VỊNH NHA TRANG – HÒN TẰM – CITY TOUR (ĂN SÁNG, TRƯA, TỐI)");
        scheduleInput2.setNameLocation("VỊNH NHA TRANG");
        scheduleInput2.setLocationStop(new Address());
        scheduleInput2.setDescription(
                "Sáng: Quý khách dùng bữa sáng, sau đó bắt đầu hành trình tour Nha Trang Đà Lạt 4 Ngày 3\n" +
                        "Đêm với các địa điểm tham quan:\n" +
                        "\uF0FC Tháp Bà Ponagar: Tòa tháp Chăm được xây dựng từ thế kỷ 12 đã trở thành một biểu\n" +
                        "tượng du lịch của thành phố biển. Tìm hiểu về những câu chuyện bí ẩn đằng sau phương\n" +
                        "pháp xây dựng đến nay vẫn chưa được giải mã.\n" +
                        "\uF0FC Hòn Chồng: Tham quan danh thắng Hòn Chồng với những truyền thuyết thú vị về bàn\n" +
                        "tay khổng lồ trên vách đá. Ngắm cảnh biển từ Bãi Hòn Chồng tuyệt đẹp.\n" +
                        "\uF0FC Hòn Tằm: Lên cano vượt sóng để khám phá Vịnh Nha Trang – một trong 29 vịnh biển\n" +
                        "đẹp nhất hành tinh và ghé thăm Hòn Tằm đẹp như tranh vẽ.\n" +
                        "Trưa: Đoàn dùng bữa trưa BBQ hải sản tại nhà hàng ở Hòn Tằm.\n" +
                        "Chiều: Quý khách tham quan Công viên giải trí VinWonders Nha Trang (tự túc chi phí) với\n" +
                        "hàng loạt trò chơi đỉnh cao, khám phá đại dương với thủy cung khổng lồ và cả bãi biển dài bất\n"
                        +
                        "tận.\n" +
                        "Tối: Đoàn dùng bữa tối tại nhà hàng. Sau đó, quý khách tự do khám phá Nha Trang về đêm.");
        scheduleInput2.setArrivalTime(Instant.parse("2023-05-01T11:30:00Z").toEpochMilli());
        scheduleInput2.setLeaveTime(Instant.parse("2023-05-01T13:00:00Z").toEpochMilli());
        scheduleInputs.add(scheduleInput2);
        createTourInput.setScheduleInput(scheduleInputs);

        List<PricingValueInput> pricingValueInputs = new ArrayList<>();
        PricingValueInput pricingValueInput1 = new PricingValueInput();

        pricingValueInput1.setPrice(MoneyCalculateUtils.getMoney(5000000));
        pricingValueInput1.setAboutAgeType(AgeType.ADULTS);
        pricingValueInput1.setNote("Người lớn");
        pricingValueInput1.setSale(0.0);
        pricingValueInputs.add(pricingValueInput1);

        PricingValueInput pricingValueInput2 = new PricingValueInput();

        pricingValueInput2.setPrice(MoneyCalculateUtils.getMoney(4000000));
        pricingValueInput2.setAboutAgeType(AgeType.CHILDREN);
        pricingValueInput2.setNote("Trẻ em");
        pricingValueInput2.setSale(0.3);
        pricingValueInputs.add(pricingValueInput2);

        createTourInput.setPricingValues(pricingValueInputs);
        createTourController.createTour(createTourInput);
    }

}
