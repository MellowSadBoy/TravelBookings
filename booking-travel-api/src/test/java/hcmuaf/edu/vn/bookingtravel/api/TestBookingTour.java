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
import hcmuaf.edu.vn.bookingtravel.api.model.input.user.CreateUserInput;
import hcmuaf.edu.vn.bookingtravel.api.model.input.user.UserInput;
import hcmuaf.edu.vn.bookingtravel.api.model.input.tour.*;
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

import static org.junit.Assert.assertNotNull;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 12/04/2023, Thứ Tư
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = BookingTravelApiApplication.class)
@AutoConfigureMockMvc
public class TestBookingTour {
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

    public TestBookingTour() {
        this.byUser = "Vũ Văn Minh";
        fullNames.add("Vũ Văn Minh");
//        fullNames.add("Nguyễn Duy Sơn");
//        fullNames.add("Nguyễn Thị Hoa");
//        fullNames.add("Đậu Thị Nhi");
//        fullNames.add("Hồ Xuân Thịnh");
//        fullNames.add("Huỳnh Thị Diễm Ngân");
//        fullNames.add("Trần Thanh Tú");
//        fullNames.add("Trấn Thành");
//        fullNames.add("Trường Giang");
//        fullNames.add("Diệu Nhi");
//        fullNames.add("Hà Anh Tuấn");
//        fullNames.add("Ngô Kiến Huy");
//        fullNames.add("Nguyễn Thị Cẩm Tiên");
//        fullNames.add("Lâm Chấn Khang");
//        fullNames.add("Lâm Chấn Huy");
//        fullNames.add("Châu Khải Phong");
//        fullNames.add("Chi Dân");
//        fullNames.add("Ngô Tấn Tài");
//        fullNames.add("Huấn Hoa Hồng");
//        fullNames.add("Linh Ka");
//        fullNames.add("Sơn Tùng MTP");
//        fullNames.add("Hoài Linh");
//        fullNames.add("Đan Trường");
//        fullNames.add("Noo Phước Thịnh");
//        fullNames.add("ChiPu");
//        fullNames.add("Thuỷ Tiên");
//        fullNames.add("Ngọc Trinh");
//        fullNames.add("Thuỷ Chi");
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
            //Đăng kí tài khoản khách hàng
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
                "    Du ngoạn Vịnh Nha Trang - một trong 29 vịnh biển đẹp nhất hành tinh với các danh thắng Hòn Tằm, Hòn Chồng độc đáo\n" +
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
        tourInput.setFeatureImgUrl("https://www.vietnambooking.com/wp-content/uploads/2017/09/tour-du-lich-nha-trang-da-lat-20-9-20173-300x194.jpg");
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
        scheduleInput1.setDescription("Sáng/ Trưa: Xe và HDV Mellow Booking đón khách tại sân bay Cam Ranh/ Bến xe/ Nhà ga,\n" +
                "sau đó đưa khách về trung tâm Thành phố Nha Trang nhận phòng khách sạn.\n" +
                "Chiều: Tự do tham quan thành phố biển và thưởng thức những món ngon đường phố Nha Trang.");
        scheduleInput1.setArrivalTime(Instant.parse("2023-05-01T09:00:00Z").toEpochMilli());
        scheduleInput1.setLeaveTime(Instant.parse("2023-05-01T11:00:00Z").toEpochMilli());
        scheduleInputs.add(scheduleInput1);
        ScheduleInput scheduleInput2 = new ScheduleInput();
        scheduleInput2.setTitle("NGÀY 2 | VỊNH NHA TRANG – HÒN TẰM – CITY TOUR (ĂN SÁNG, TRƯA, TỐI)");
        scheduleInput2.setNameLocation("VỊNH NHA TRANG");
        scheduleInput2.setLocationStop(new Address());
        scheduleInput2.setDescription("Sáng: Quý khách dùng bữa sáng, sau đó bắt đầu hành trình tour Nha Trang Đà Lạt 4 Ngày 3\n" +
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
                "hàng loạt trò chơi đỉnh cao, khám phá đại dương với thủy cung khổng lồ và cả bãi biển dài bất\n" +
                "tận.\n" +
                "Tối: Đoàn dùng bữa tối tại nhà hàng. Sau đó, quý khách tự do khám phá Nha Trang về đêm.");
        scheduleInput2.setArrivalTime(Instant.parse("2023-05-01T11:30:00Z").toEpochMilli());
        scheduleInput2.setLeaveTime(Instant.parse("2023-05-01T13:00:00Z").toEpochMilli());
        scheduleInputs.add(scheduleInput2);
//        ScheduleInput scheduleInput3 = new ScheduleInput();
//        scheduleInput3.setTitle("NGÀY 3 | NHA TRANG – ĐÀ LẠT (ĂN SÁNG, TRƯA, TỐI)");
//        scheduleInput3.setNameLocation("ĐÀ LẠT");
//        scheduleInput3.setLocationStop(localAddress);
//        scheduleInput3.setDescription("Sáng: Quý khách dùng điểm tâm sáng tại nhà hàng, trả phòng khách sạn. Trên đường khởi hành\n" +
//                "đi Đà Lạt, đoàn dừng chân mua sắm đặc sản Nha Trang tại Khu tổ hợp đồ nông sản Khánh\n" +
//                "Sơn.\n" +
//                "Trưa: Đến Đà Lạt, đoàn dùng bữa trưa, nhận phòng khách sạn, nghỉ ngơi.\n" +
//                "Chiều: Tiếp tục tour du lịch Nha Trang Đà Lạt 4 Ngày 3 Đêm, đoàn tham quan:\n" +
//                "\uF0FC Mê Linh Coffee Garden: Thỏa sức \"sống ảo\" tại quán cafe nổi bật nhất Đà Lạt với\n" +
//                "khung cảnh đồi núi bao la.\n" +
//                "\uF0FC Nhà ga Đà Lạt: Tận mắt chiêm ngưỡng nhà ga cổ nhất Đông Dương với kiến trúc được\n" +
//                "thiết kế từ dáng núi LangBiang huyền thoại.\n" +
//                "\uF0FC Chùa Linh Phước: Trầm trồ trước vẻ đẹp của Chùa Ve Chai – ngôi chùa được trang trí\n" +
//                "bởi hàng trăm ngàn mảnh sành sứ, tạo nên hoa văn và kiến trúc không thể hòa lẫn.\n" +
//                "Tối: Quý khách dùng cơm tối tại Nhà hàng, tự do tham quan chợ đêm và Thành phố Đà lạt.");
//        scheduleInput3.setArrivalTime(Instant.parse("2023-05-01T09:00:00Z").toEpochMilli());
//        scheduleInput3.setLeaveTime(Instant.parse("2023-05-01T11:00:00Z").toEpochMilli());
//        scheduleInputs.add(scheduleInput3);
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

    @Test
    public void testTour_2() throws ServiceException {
        CreateTourInput createTourInput = new CreateTourInput();
        TourInput tourInput = new TourInput();
        tourInput.setDescription(
                "\n" +
                        "    Check in tại Thung Lũng Đèn với \"nấc thang lên thiên đường\".\n" +
                        "    Khám phá những công trình đặc sắc như Chùa Linh Phước,...\n" +
                        "    Vui đùa bên những chú cún vô cùng dễ thương tại Puppy Farm.\n" +
                        "    Hóa thân thú vị với muôn kiểu tạo dáng tại Bảo tàng tranh 3D.\n");
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
        tourInput.setFeatureImgUrl("https://www.vietnambooking.com/wp-content/uploads/2022/09/tour-da-lat-3-ngay-3-dem-1-300x194.jpg");
        tourInput.setImgUrls(Arrays.asList(
                "https://www.vietnambooking.com/wp-content/uploads/2022/09/tour-da-lat-3-ngay-3-dem-1-300x194.jpg",
                "https://www.vietnambooking.com/wp-content/uploads/2022/09/tour-da-lat-3-ngay-3-dem-3.jpg",
                "https://www.vietnambooking.com/wp-content/uploads/2022/09/tour-da-lat-3-ngay-3-dem-2.jpg",
                "https://www.vietnambooking.com/wp-content/uploads/2022/09/tour-da-lat-3-ngay-3-dem-5.jpg",
                "https://www.vietnambooking.com/wp-content/uploads/2022/09/tour-da-lat-3-ngay-3-dem-4.jpg"));
        Address localAddress = new Address();
        localAddress.setAddress("Thị trấn Cát Tiên, Huyện Các Tiên, Lâm Đồng, Việt Nam");
        localAddress.setProvince("Lâm Đồng");
        localAddress.setProvinceCode(209);
        localAddress.setDistrict("Huyện Cát Tiên");
        localAddress.setDistrictCode(3146);
        localAddress.setWard("Thị trấn Cát Tiên");
        localAddress.setWardCode(420901);
        localAddress.setCountryCode(CountryCode.VN);
        tourInput.setLocation(localAddress);
        tourInput.setName("Tour Đà Lạt 3 ngày 2 đêm l Lạc vào chốn tiên cảnh giữa nhân gian");
        tourInput.setNote("Tour note");
        tourInput.setDiscount(DiscountUtils.discount());
        tourInput.setTimeHowLong(TimeHowLong.THREE_DAY_THREE_NIGHT);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.MAY, 15); // Tháng bắt đầu từ 0
        tourInput.setStartDate(new Date(calendar.getTimeInMillis()));
        tourInput.setPriceStandard(MoneyCalculateUtils.getMoney(30000));
        tourInput.setQuantityVisit(14);
        tourInput.setType(TourType.DOMESTIC);
        createTourInput.setTourInput(tourInput);

        VehicleInput vehicleInput = new VehicleInput();
        vehicleInput.setAutomaker("Phương Trang");
        vehicleInput.setName("Xe Limousine 16 chổ");
        vehicleInput.setVehicleType(VehicleType.CAR_16);
        vehicleInput.setNumberOfSeats(VehicleType.CAR_16.getNumberOfSeats());
        vehicleInput.setCode("51A-1111");
        createTourInput.setVehicleInput(vehicleInput);

        List<ScheduleInput> scheduleInputs = new ArrayList<>();
        ScheduleInput scheduleInput1 = new ScheduleInput();
        scheduleInput1.setTitle(" ĐÊM NGÀY 01 | HỒ CHÍ MINH – ĐÀ LẠT (NGHỈ NGƠI TRÊN XE) ");
        scheduleInput1.setNameLocation("TP.HCM – ĐÀ LẠT");
        scheduleInput1.setLocationStop(localAddress);
        scheduleInput1.setDescription(" Tối: Xe và HDV của công ty Vietnam Booking sẽ đón quý khách tại điểm hẹn như trong lịch trình. Sau khi đã ổn định, đoàn sẽ xuất phát đến Đà Lạt, bắt đầu hành trình tour Đà Lạt 3 ngày 3 đêm.\n" +
                "\n" +
                "Trên đường đi, HDV sẽ thuyết minh sơ lược về những địa điểm đoàn sẽ đi. Du khách sẽ nghỉ ngơi trên xe và dừng chân ăn nhẹ tại các trạm dừng chân trong chuyến đi. ");
        scheduleInput1.setArrivalTime(calendar.getTimeInMillis());
        scheduleInput1.setLeaveTime(calendar.getTimeInMillis());
        scheduleInputs.add(scheduleInput1);
        ScheduleInput scheduleInput2 = new ScheduleInput();
        scheduleInput2.setTitle(" NGÀY 01 | CÁNH ĐỒNG CẨM TÚ CẦU – CHÙA LINH PHƯỚC – BẢO TÀNG 3D (ĂN SÁNG, TRƯA) ");
        scheduleInput2.setNameLocation("CÁNH ĐỒNG CẨM TÚ CẦU – CHÙA LINH PHƯỚC – BẢO TÀNG 3D");
        scheduleInput2.setLocationStop(new Address());
        scheduleInput2.setDescription(" Sáng: Đến Đà Lạt, đoàn sẽ dùng bữa sáng tại Thung Lũng Đèn. Nơi đây nổi tiếng với \"nấc thang lên thiên đường\" và \"phím đàn trên mây\" vô cùng ảo diệu (chi phí chụp hình tự túc). Quý khách có thể phóng tầm mắt bao trọn khung cảnh hữu tình của Đà Lạt ngay tại chính nơi này. \n" +
                "\n" +
                "Tiếp tục lịch trình đi Đà Lạt 3 ngày 3 đêm, HDV sẽ đưa du khách đi tham quan và chụp hình tại Cánh Đồng Hoa Cẩm Tú Cầu. Quý khách sẽ được ngắm nhìn sắc đẹp muôn màu của những bông hoa cẩm tú cầu. Từ đó, du khách có thể tạo cho mình một bộ ảnh vô cùng độc đáo bên cạnh những bông hoa. Ngoài ra, du khách còn thể chụp hình tại những địa điểm mới vô cùng lý thú như: Cầu Bàn Tay Vàng , Nấc Thang Lên Thiên Đường , Cầu Tình Yêu v.v…. \n" +
                "\n" +
                "Sau khi ngắm nhìn vẻ đẹp của những bông hoa, xe và HDV đưa đoàn đến thăm Chùa Linh Phước - một trong những ngôi chùa nổi tiếng bậc nhất không thể bỏ qua. \n" +
                "\n" +
                "Trưa: Đoàn ăn trưa tại nhà hàng và di chuyển về khách sạn nhận phòng nghỉ ngơi.\n" +
                "\n" +
                "Chiều: Tiếp tục chuyến du lịch tour Đà Lạt 3 ngày 3 đêm, Quý khách sẽ di chuyển đến tham quan và check in tại Bảo tàng tranh 3D World Đà Lạt - Bảo tàng được biết đến là phim trường 3D thực tế đầu tiên tại Đà Lạt với hơn 50 khung cảnh khác nhau.\n" +
                "\n" +
                "Tiếp tục hành trình của tour du lịch Đà Lạt, đoàn đến tham quan và chụp ảnh tại Quảng trường Lâm Viên. Sau đó, xe và HDV đưa Quý khách về khách sạn.\n" +
                "\n" +
                "Tối: Du khách tự túc ăn tối và tự do khám phá Đà Lạt về đêm. Vào khoảng thời gian này, du khách có thể nhâm nhi một ly sữa nóng bên cạnh những thung lũng đèn mờ ảo hoặc thưởng thức những món ăn đặc trưng nơi đây.  ");
        calendar.add(Calendar.DATE, 1);
        scheduleInput2.setArrivalTime(Objects.requireNonNull(DateUtils.getStartDay(calendar.getTime())).getTime());
        scheduleInput2.setLeaveTime(Objects.requireNonNull(DateUtils.getEndDay(calendar.getTime())).getTime());
        scheduleInputs.add(scheduleInput2);
        ScheduleInput scheduleInput3 = new ScheduleInput();
        scheduleInput3.setTitle("NGÀY 02 | PUPPY FARM – NHÀ THỜ DOMAIN – HAPPY HILL – BUFFET RAU (ĂN SÁNG, TRƯA, TỐI)");
        scheduleInput3.setNameLocation("PUPPY FARM – NHÀ THỜ DOMAIN – HAPPY HILL");
        scheduleInput3.setLocationStop(new Address());
        scheduleInput3.setDescription(" Sáng: Đoàn dùng bữa sáng buffet tại nhà hàng. Sau đó, xe và HDV đưa đoàn đến ghé tham quan trang trại cún Puppy Farm. Tại đây, du khách sẽ không thoát khỏi sự dễ thương của những chú cún. Cùng với đó, du khách còn được tham quan vườn cà chua, dâu tây công nghệ cao, khu vực rau quả sạch và những ngọn đồi đầy hoa giữa ánh nắng chan hòa.\n" +
                "\n" +
                "Tạm biệt những chú cún đáng yêu, đoàn tiếp tục di chuyển đến tham quan Nhà Thờ Domain De Marie Đà Lạt - một trong những nhà thờ màu hồng nổi tiếng của Việt Nam. Kế tiếp, đoàn đến thăm cơ sở sản xuất mứt. Tại đây, du khách có thể tự do mua sắm những món quà về cho người thân và bạn bè.\n" +
                "\n" +
                "Trưa: Đoàn dùng cơm trưa tại nhà hàng địa phương và nghỉ ngơi tại khách sạn.\n" +
                "\n" +
                "Chiều: Đoàn tiếp tục hành trình du lịch Đà Lạt khi di chuyển đến Phim trường Happy Hill. Đến với nơi này, du khách có thể sống ảo \"cháy\" máy với muôn vàn góc sống ảo được thiết kế và chăm chút rất tỉ mỉ.\n" +
                "\n" +
                "Tối: Quý khách dùng bữa tối buffet rau tại nhà hàng. Buổi tối tự do khám phá Đà Lạt và nghỉ ngơi tại khách sạn. ");
        calendar.add(Calendar.DATE, 1);
        scheduleInput3.setArrivalTime(Objects.requireNonNull(DateUtils.getStartDay(calendar.getTime())).getTime());
        scheduleInput3.setLeaveTime(Objects.requireNonNull(DateUtils.getEndDay(calendar.getTime())).getTime());
        scheduleInputs.add(scheduleInput3);
        ScheduleInput scheduleInput4 = new ScheduleInput();
        scheduleInput4.setTitle(" NGÀY 03 | THÁC PONGOUR – TP.HCM (ĂN SÁNG, TRƯA) ");
        scheduleInput4.setNameLocation("THÁC PONGOUR");
        scheduleInput4.setLocationStop(localAddress);
        scheduleInput4.setDescription(" Sáng: Quý khách dùng bữa sáng buffet tại khách sạn và làm thủ tục trả phòng.\n" +
                "\n" +
                "Sau đó, đoàn đi đến Thác Pongour để bắt đầu cuộc hành trình khám phá Nam Thiên Đệ Nhất Thác trong tour du lịch Đà Lạt.\n" +
                "\n" +
                "Trưa: Quý khách dùng bữa trưa nhà hàng. Sau khi dùng bữa, du khách có thể lắng nghe câu chuyện về “Tứ đại danh trà”. Cùng với đó, Quý khách có thể thưởng thức trà, cafe miễn phí và mua về làm quà cho người thân. Đoàn tiếp tục hành trình trở về TP.HCM.\n" +
                "\n" +
                "Tối: Về đến TP.HCM, xe đưa Quý khách về lại điểm đón ban đầu. Kết thúc chương trình tour Đà Lạt 3 ngày 3 đêm, HDV chào tạm biệt và hẹn gặp lại Quý khách vào những hành trình tiếp theo. ");
        calendar.add(Calendar.DATE, 1);
        scheduleInput4.setArrivalTime(Objects.requireNonNull(DateUtils.getStartDay(DateUtils.getStartDay(calendar.getTime()))).getTime());
        scheduleInput4.setLeaveTime(Objects.requireNonNull(DateUtils.getStartDay(DateUtils.getEndDay(calendar.getTime()))).getTime());
        scheduleInputs.add(scheduleInput4);
        createTourInput.setScheduleInput(scheduleInputs);

        List<PricingValueInput> pricingValueInputs = new ArrayList<>();
        PricingValueInput pricingValueInput1 = new PricingValueInput();

        pricingValueInput1.setPrice(MoneyCalculateUtils.getMoney(30000));
        pricingValueInput1.setAboutAgeType(AgeType.ADULTS);
        pricingValueInput1.setNote("Người lớn");
        pricingValueInput1.setSale(0.1);
        pricingValueInputs.add(pricingValueInput1);

        PricingValueInput pricingValueInput2 = new PricingValueInput();

        pricingValueInput2.setPrice(MoneyCalculateUtils.getMoney(25000));
        pricingValueInput2.setAboutAgeType(AgeType.CHILDREN);
        pricingValueInput2.setNote("Trẻ em");
        pricingValueInput2.setSale(0.3);
        pricingValueInputs.add(pricingValueInput2);

        createTourInput.setPricingValues(pricingValueInputs);
        createTourController.createTour(createTourInput);
    }

    @Test
    public void testTour_3() throws ServiceException {
        CreateTourInput createTourInput = new CreateTourInput();
        TourInput tourInput = new TourInput();
        tourInput.setDescription(
                "\n" +
                        "    Khám phá Khu du lịch Bà Nà Hills - Đường lên tiên cảnh\n" +
                        "    Ngắm nhìn Phố cổ Hội An lung linh ánh đèn về đêm\n" +
                        "    Chiêm ngưỡng động Phong Nha - hang động đá vôi huyền bí \n" +
                        "    Tham quan mảnh đất cố đô Huế với nhiều công trình kiến trúc tuyệt đẹp\n" +
                        "    Thưởng thức ẩm thực địa phương độc đáo.\n" +
                        "    Trọn gói Vé máy bay (kèm hành lý) + Khách sạn 4 sao\n");
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
        tourInput.setFeatureImgUrl("https://www.vietnambooking.com/wp-content/uploads/2022/03/tour-da-nang-le-30-4-le-hoi-phao-hoa-1-300x194.png");
        tourInput.setImgUrls(Arrays.asList(
                "https://www.vietnambooking.com/wp-content/uploads/2021/04/tour-da-nang-di-hue-1-ngay-6.jpg",
                "https://www.vietnambooking.com/wp-content/uploads/2021/04/tour-da-nang-hoi-an-3-ngay-2-dem-1.jpg",
                "https://www.vietnambooking.com/wp-content/uploads/2021/04/tour-da-nang-hoi-an-3-ngay-2-dem-4.jpg",
                "https://www.vietnambooking.com/wp-content/uploads/2021/04/HA-NOI-BA-NA-HOI-AN-3N2D-1.jpg"));
        Address localAddress = new Address();
        localAddress.setAddress("Phường Thanh Khê, Quận Thanh Khê, Đà Nẵng, Việt Nam");
        localAddress.setProvince("Đà Nẵng");
        localAddress.setProvinceCode(203);
        localAddress.setDistrict("Quận Thanh Khê");
        localAddress.setDistrictCode(3146);
        localAddress.setWard("Phường An Khê");
        localAddress.setWardCode(40201);
        localAddress.setCountryCode(CountryCode.VN);
        tourInput.setLocation(localAddress);
        tourInput.setName("Tour du lịch Đà Nẵng Lễ 30/4 4N3Đ | Bà Nà Hills – Hội An |Thứ 5 hàng tuần");
        tourInput.setNote("Tour note");
        tourInput.setDiscount(DiscountUtils.discount());
        tourInput.setTimeHowLong(TimeHowLong.FOUR_DAY_THREE_NIGHT);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.MAY, 14); // Tháng bắt đầu từ 0
        tourInput.setStartDate(new Date(calendar.getTimeInMillis()));
        tourInput.setPriceStandard(MoneyCalculateUtils.getMoney(50000));
        tourInput.setQuantityVisit(28);
        tourInput.setType(TourType.DOMESTIC);
        createTourInput.setTourInput(tourInput);

        VehicleInput vehicleInput = new VehicleInput();
        vehicleInput.setAutomaker("Phương Trang");
        vehicleInput.setName("Xe Limousine 30 chổ");
        vehicleInput.setVehicleType(VehicleType.CAR_16);
        vehicleInput.setNumberOfSeats(VehicleType.CAR_30.getNumberOfSeats());
        vehicleInput.setCode("51A-1122");
        createTourInput.setVehicleInput(vehicleInput);

        List<ScheduleInput> scheduleInputs = new ArrayList<>();
        ScheduleInput scheduleInput1 = new ScheduleInput();
        scheduleInput1.setTitle("NGÀY 1: TP.HCM - ĐÀ NẴNG - BÀ NÀ HILL - HỘI AN (ĂN SÁNG/TRƯA/TỐI)");
        scheduleInput1.setNameLocation("TP.HCM – ĐÀ NẴNG - BÀ NÀ HILL");
        scheduleInput1.setLocationStop(localAddress);
        scheduleInput1.setDescription("  Sáng: Quý khách có mặt tại sân bay Tân Sơn Nhất theo giờ hẹn. Hướng dẫn viên của Vietnam Booking hỗ trợ quý khách làm thủ tục lên chuyến bay khởi hành đi Đà Nẵng và bắt đầu tour Đà Nẵng 30/4 4N3Đ.\n" +
                "\n" +
                "Đến Đà Nẵng, xe sẽ đưa quý khách vào trung tâm thành phố men theo con đường biển Sơn Trà – ngắm bãi biển Mỹ Khê. Đoàn bắt đầu tham quan cac danh thắng:\n" +
                "\n" +
                "    Bán đảo Sơn Trà - Vẻ đẹp thiên nhiên nguyên sơ, thơ mộng với những tán cây xanh ôm trọn bãi biển trong vắt\n" +
                "    Viếng chùa Linh Ứng - nơi nổi tiếng có tượng Quan Âm cao nhất Việt Nam \n" +
                "\n" +
                "Trưa: Xe đưa đoàn đến nhà hàng dùng cơm trưa với các món đặc sản của Đà thành. Sau đó đoàn về khách sạn nhận phòng nghỉ ngơi. \n" +
                "\n" +
                "Chiều: Đoàn tự do tắm biển Mỹ Khê. Sau đó khởi hành đi tham quan:\n" +
                "\n" +
                "    Ngũ Hành Sơn: Danh thắng tuyệt đẹp với thuyết ngũ hành Kim Mộc Thủy Hỏa Thổ. Tại đây, quý khách sẽ được chiêm ngưỡng ngọn Thủy Sơn, động Huyền Không, động Tàng Chơn, động Vân Thông, Vọng Giang Đài, chùa Linh Ứng,... \n" +
                "\n" +
                "    Làng đá mỹ nghệ Non Nước: Đây là làng nghề chế tác đá truyền thống của Đà Nẵng. Quý khách tham quan cơ sở sản xuất mỹ nghệ và mua quà lưu niệm.\n" +
                "\n" +
                "Tiếp tục chương trình tour Đà Nẵng 30/4 4N3Đ, xe đưa quý khách đến Phố cổ Hội An với những điểm đến hấp dẫn: \n" +
                "\n" +
                "    Chùa Cầu Nhật Bản, Nhà Cổ trăm tuổi.\n" +
                "    Hội Quán Phước Kiến.\n" +
                "    Bảo tàng văn hóa Sa Huỳnh, xưởng thủ công mỹ nghệ,....\n" +
                "\n" +
                "Tối: Quý khách dùng bữa tối với những món ăn đặc sản Hội An nổi tiếng: Cao Lầu, Hoành Thánh, Bánh Bao, Bánh Vạc,... Sau bữa tối, quý khách có thể tự do dạo chơi Phố cổ về đêm lung linh ánh đèn.\n" +
                "\n" +
                "20h00: Xe đưa đoàn về lại thành phố Đà Nẵng và tự do ngắm thành phố biển về đêm.\n" +
                "\n" +
                "Nghỉ đêm tại Đà Nẵng. ");
        calendar.add(Calendar.DATE, 1);
        scheduleInput1.setArrivalTime(Objects.requireNonNull(DateUtils.getStartDay(calendar.getTime())).getTime());
        scheduleInput1.setLeaveTime(Objects.requireNonNull(DateUtils.getEndDay(calendar.getTime())).getTime());
        scheduleInputs.add(scheduleInput1);
        ScheduleInput scheduleInput2 = new ScheduleInput();
        scheduleInput2.setTitle(" NGÀY 2: BÀ NÀ HILLS - VỊNH LĂNG CÔ - HUẾ (ĂN SÁNG/TỐI)");
        scheduleInput2.setNameLocation("CÁNH ĐỒNG CẨM TÚ CẦU – CHÙA LINH PHƯỚC – BẢO TÀNG 3D");
        scheduleInput2.setLocationStop(new Address());
        scheduleInput2.setDescription("  Sáng: Quý khách dùng điểm tâm sáng tại nhà hàng và bắt đầu đi tham quan Khu du lịch Bà Nà Núi Chúa (Bà Nà Hills) - Đường lên tiên cảnh (chi phí tự túc).\n" +
                "\n" +
                "    Trải nghiệm khoảnh khắc giao mùa Xuân - Hạ - Thu - Đông trong 1 ngày.\n" +
                "    Tham quan khu Le Jardin với các di tích của người Pháp: khu buộc ngựa của Pháp, cây bưởi gần 100 tuổi, vết tích các khu biệt thự cổ.\n" +
                "    Viếng chùa Linh Ứng với tượng Phật Thích Ca cao 27m, tham quan Vườn Lộc Uyển, Quan Âm Các,...\n" +
                "    Chinh phục đỉnh núi Chúa ở độ cao 1.487m so với mực nước biển.\n" +
                "    Check - in CẦU VÀNG – biểu tượng mới của du lịch Đà Nẵng.\n" +
                "    vui chơi thỏa thích tại Fantasy Park với hơn 135 trò chơi phiêu lưu hấp dẫn: vòng quay tình yêu, Phi công Skiver, Đường đua lửa, Xe điện đụng Ngôi nhà ma…  \n" +
                "\n" +
                "Trưa: Quý khách ăn trưa tại khu du lịch Bà Nà (chi phí tự túc).\n" +
                "\n" +
                "Chiều: Quý khách khởi hành đi Cố Đô Huế, xuyên hầm đường bộ đèo Hải vân, dừng chân chụp ảnh tại Vịnh biển Lăng Cô trứ danh.\n" +
                "\n" +
                "Đến Huế, xe đưa đoàn về khách sạn nhận phòng và nghỉ ngơi.\n" +
                "\n" +
                "Tối: Quý khách dùng cơm tối tại nhà hàng. Sau bữa tối, quý khách tự do dạo phố về đêm. \n" +
                "\n" +
                "Đoàn Nghỉ đêm tại Huế. ");
        calendar.add(Calendar.DATE, 1);
        scheduleInput2.setArrivalTime(Objects.requireNonNull(DateUtils.getStartDay(calendar.getTime())).getTime());
        scheduleInput2.setLeaveTime(Objects.requireNonNull(DateUtils.getEndDay(calendar.getTime())).getTime());
        scheduleInputs.add(scheduleInput2);
        ScheduleInput scheduleInput3 = new ScheduleInput();
        scheduleInput3.setTitle("NGÀY 3: THÁNH ĐỊA LA VANG - CẦU HIỀN LƯƠNG - ĐỘNG PHONG NHA (ĂN SÁNG/TRƯA/TỐI)");
        scheduleInput3.setNameLocation("THÁNH ĐỊA LA VANG - CẦU HIỀN LƯƠNG - ĐỘNG PHONG NHA");
        scheduleInput3.setLocationStop(new Address());
        scheduleInput3.setDescription("  Sáng: Quý khách dùng bữa sáng và làm thủ tục trả phòng. Tiếp tục chương trình tour Đà Nẵng 30/4, xe đưa quý khách đến với vườn quốc gia Phong Nha - Kẻ Bàng địa điểm nổi tiếng nhất của du lịch Quảng Bình. Trên đường đi, đoàn sẽ ghé thăm:\n" +
                "\n" +
                "    Thánh địa La Vang: Quý khách tham quan và viếng Đức Mẹ La Vang linh thiêng nổi tiếng của đạo Công Giáo. \n" +
                "    Vĩ tuyến 17 - Cầu Hiền Lương - sông Bến Hải: dừng chân ngắm những địa danh lịch sử \n" +
                "\n" +
                "Trưa: Đoàn dùng bữa trưa tại nhà hàng ở Phong Nha, nghỉ ngơi nhẹ nhàng. Sau đó, đoàn ngồi thuyền ngược dòng sông Son, chinh phục Động Phong Nha kỳ vĩ. Đoàn sẽ có cơ hội chiêm ngưỡng Hang Bi Ký, Cô Tiên và Cung Đình dưới sâu lòng núi.\n" +
                "\n" +
                "Chiều: Đoàn khởi hành về lại Cố Đô Huế theo con đường Trường Sơn - Hồ Chí Minh Huyền Thoại.\n" +
                "\n" +
                "Tối: Đoàn dùng bữa tối tại nhà hàng Đông Hà, Quảng Trị.\n" +
                "\n" +
                "Đến Huế, quý khách về khách sạn nghỉ ngơi, tự do thưởng thức đặc sản và tham quan thành phố Huế về đêm. ");
        calendar.add(Calendar.DATE, 1);
        scheduleInput3.setArrivalTime(Objects.requireNonNull(DateUtils.getStartDay(calendar.getTime())).getTime());
        scheduleInput3.setLeaveTime(Objects.requireNonNull(DateUtils.getEndDay(calendar.getTime())).getTime());
        scheduleInputs.add(scheduleInput3);
        ScheduleInput scheduleInput4 = new ScheduleInput();
        scheduleInput4.setTitle(" NGÀY 4: ĐẠI NỘI HUẾ - CHÙA THIÊN MỤ - HỒ CHÍ MINH (ĂN SÁNG/TRƯA)");
        scheduleInput4.setNameLocation("ĐẠI NỘI HUẾ - CHÙA THIÊN MỤ - HỒ CHÍ MINH");
        scheduleInput4.setLocationStop(localAddress);
        scheduleInput4.setDescription(" Sáng: Quý khách dùng bữa sáng tại nhà hàng và làm thủ tục trả phòng. Tiếp tục hành trình tour Đà Nẵng 30/4, xe đưa quý khách đi du lịch Huế với các điểm tham quan:\n" +
                "\n" +
                "    Đại nội kinh thành Huế: Công trình đồ sộ từng là nơi ở của các vị vua nhà Nguyễn. Quý khách tham quan Ngọ Môn, Điện Thái Hòa, Tử Cấm Thành, Thế Miếu, Hiển Lâm Các, Cửu Đỉnh...\n" +
                "    Chùa Thiên Mụ: Quý khách viếng một trong những ngôi chùa cổ kính nhất xứ Huế được xây dựng từ thế kỉ 18. \n" +
                "\n" +
                "Trưa: Quý khách dùng bữa trưa tại nhà hàng. \n" +
                "\n" +
                "Chiều: Đoàn ghé trạm dừng chân, mua sắm các đặc sản về làm quà cho người thân, bạn bè.\n" +
                "\n" +
                "Sau đó, xe đưa quý khách ra sân bay Đà Nẵng và làm thủ tục đáp chuyến bay về TPHCM. Đến sân bay Tân Sơn Nhất, hướng dẫn viên chia tay đoàn và kết thúc chương trình tour Đà Nẵng 30/4 4N3Đ. Hẹn gặp lại quý khách trong những chương trình tour du lịch giá rẻ tiếp theo!\n" +
                "\n" +
                "Lưu ý:\n" +
                "\n" +
                "Thứ tự các điểm tham quan có thể thay đổi cho phù hợp với tình hình thực tế nhưng vẫn đảm bảo thực hiện đầy đủ nội dung chương trình. \n" +
                "\n" +
                "Trong trường hợp giờ bay có thay đổi đi, về sớm/ muộn hơn chuyến bay dự kiến. Hướng dẫn viên sẽ linh động thay đổi lịch trình tham quan ngày cuối cùng cho phù hợp giờ bay của hãng hàng không thay đổi (mong quý khách thông cảm về việc thay đổi này). ");
        calendar.add(Calendar.DATE, 1);
        scheduleInput4.setArrivalTime(DateUtils.getStartDay(DateUtils.getStartDay(calendar.getTime())).getTime());
        scheduleInput4.setLeaveTime(DateUtils.getStartDay(DateUtils.getEndDay(calendar.getTime())).getTime());
        scheduleInputs.add(scheduleInput4);
        createTourInput.setScheduleInput(scheduleInputs);

        List<PricingValueInput> pricingValueInputs = new ArrayList<>();
        PricingValueInput pricingValueInput1 = new PricingValueInput();

        pricingValueInput1.setPrice(MoneyCalculateUtils.getMoney(50000));
        pricingValueInput1.setAboutAgeType(AgeType.ADULTS);
        pricingValueInput1.setNote("Người lớn");
        pricingValueInput1.setSale(0.1);
        pricingValueInputs.add(pricingValueInput1);

        PricingValueInput pricingValueInput2 = new PricingValueInput();

        pricingValueInput2.setPrice(MoneyCalculateUtils.getMoney(45000));
        pricingValueInput2.setAboutAgeType(AgeType.CHILDREN);
        pricingValueInput2.setNote("Trẻ em");
        pricingValueInput2.setSale(0.3);
        pricingValueInputs.add(pricingValueInput2);

        createTourInput.setPricingValues(pricingValueInputs);
        createTourController.createTour(createTourInput);
    }

    @Test
    public void testTour_4() throws ServiceException {
        CreateTourInput createTourInput = new CreateTourInput();
        TourInput tourInput = new TourInput();
        tourInput.setDescription(
                "\n" +
                        "    Mỹ Tho: chiêm bái chùa Vĩnh Tràng, lênh đênh sông Tiền ngắm 4 cù lao Long, Lân, Qui, Phụng \n" +
                        "    Thưởng thức đặc sản kẹo dừa Bến Tre, hòa mình vào nếp sống văn hóa của người dân Nam Bộ hiền lành, chất phác.\n" +
                        "    Châu Đốc: Rừng Tràm Trà Sư, Miếu Bà Chúa Xứ, Lăng Thoại Ngọc Hầu, Tây An Cổ Tự...\n" +
                        "    Chơi các trò dân gian, ăn trái cây miệt vườn, lắng nghe Đờn Ca Tài Tử\n" +
                        "    Cần Thơ: Chợ nổi Cái Răng, ăn tối trên du thuyền Cần Thơ\n" +
                        "    Ghé Sóc Trăng, tham quan chùa Chén Kiều của người Khmer\n" +
                        "    Bạc Liêu: viếng mộ Cha Trương Bửu Diệp, cánh đồng quạt gió, nhà công tử Bạc Liêu,...\n" +
                        "    Cà Mau: Mốc tọa độ Quốc gia – GPS 0001, Panô biểu tượng Mũi Cà Mau, Vườn Quốc Gia\n" +
                        "    Check in Cột Cờ Đất Mũi, tượng Mẹ Âu Cơ; thưởng thức đặc sản Hậu Giang\n");
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
        tourInput.setFeatureImgUrl("https://www.vietnambooking.com/wp-content/uploads/2023/04/tour-dong-bang-song-cuu-long-10-300x194.jpg");
        tourInput.setImgUrls(Arrays.asList(
                "https://www.vietnambooking.com/wp-content/uploads/2023/04/tour-dong-bang-song-cuu-long-2.jpg",
                "https://www.vietnambooking.com/wp-content/uploads/2023/04/tour-dong-bang-song-cuu-long-9.jpg",
                "https://www.vietnambooking.com/wp-content/uploads/2023/04/tour-dong-bang-song-cuu-long-4.jpg",
                "https://www.vietnambooking.com/wp-content/uploads/2023/04/tour-dong-bang-song-cuu-long-3.jpg"));
        Address localAddress = new Address();
        localAddress.setAddress("Thị trấn Cờ Đỏ, Huyện Cờ Đỏ, Cần Thơ, Việt Nam");
        localAddress.setProvince("Cần Thơ");
        localAddress.setProvinceCode(220);
        localAddress.setDistrict("Huyện Cờ Đỏ");
        localAddress.setDistrictCode(3150);
        localAddress.setWard("Thị trấn Cờ Đỏ");
        localAddress.setWardCode(550601);
        localAddress.setCountryCode(CountryCode.VN);
        tourInput.setLocation(localAddress);
        tourInput.setName("\n" +
                "Tour Đồng bằng sông Cửu Long 4N3Đ: Hành trình khám phá 8 tỉnh miền Tây");
        tourInput.setNote("Tour note");
        tourInput.setDiscount(0.9);
        tourInput.setTimeHowLong(TimeHowLong.FOUR_DAY_THREE_NIGHT);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.MAY, 13); // Tháng bắt đầu từ 0
        tourInput.setStartDate(new Date(calendar.getTimeInMillis()));
        tourInput.setPriceStandard(MoneyCalculateUtils.getMoney(40000));
        tourInput.setQuantityVisit(14);
        tourInput.setType(TourType.DOMESTIC);
        createTourInput.setTourInput(tourInput);

        VehicleInput vehicleInput = new VehicleInput();
        vehicleInput.setAutomaker("Phương Trang");
        vehicleInput.setName("Xe Limousine 16 chổ");
        vehicleInput.setVehicleType(VehicleType.CAR_16);
        vehicleInput.setNumberOfSeats(VehicleType.CAR_16.getNumberOfSeats());
        vehicleInput.setCode("51A-1234");
        createTourInput.setVehicleInput(vehicleInput);

        List<ScheduleInput> scheduleInputs = new ArrayList<>();
        ScheduleInput scheduleInput1 = new ScheduleInput();
        scheduleInput1.setTitle(" NGÀY 01 | SÀI GÒN – MỸ THO – CHÙA VĨNH TRÀNG – 4 CÙ LAO – BẾN TRE – CHÂU ĐỐC (ĂN SÁNG/ TRƯA/ TỐI) ");
        scheduleInput1.setNameLocation("SÀI GÒN – MỸ THO – CHÙA VĨNH TRÀNG");
        scheduleInput1.setLocationStop(localAddress);
        scheduleInput1.setDescription("  Sáng: Quý khách tập trung tại điểm hẹn, xe và hướng dẫn viên (HDV) của Vietnam Booking đón du khách bắt đầu chương trình khám phá tour Đồng bằng sông Cửu Long 4N3Đ. \n" +
                "\n" +
                "Đoàn dừng chân ăn sáng tại Mỹ Tho với món hủ tiếu trứ danh, sau đó xe đưa đoàn đi tham quan: \n" +
                "\n" +
                "    Chùa Vĩnh Tràng - ngôi chùa được xếp hạng di tích lịch sử - văn hóa cấp quốc gia năm 1984 và là ngôi chùa Phật giáo lớn nhất Tiền Giang.\n" +
                "\n" +
                "Sau đó đoàn đến với Cảng Du Thuyền Mỹ Tho lên tàu di ngao du trên sông Tiền, ngắm cảnh 4 cù lao Long, Lân, Qui, Phụng. Dọc theo sông, du khách sẽ được trải nghiệm:\n" +
                "\n" +
                "    Tìm hiểu cách nuôi cá trên sông ở những bè cá nổi của người dân địa phương;\n" +
                "    Chiêm ngưỡng cầu Rạch Miễu;\n" +
                "\n" +
                "Tàu đến cù lao Thới Sơn (cồn Lân), du khách ghé tham quan:\n" +
                "\n" +
                "    Nhà dân và vườn Bưởi Da Xanh với diện tích hơn 5000m2;\n" +
                "    Trại nuôi ong mật, thưởng thức những ly trà chanh mật ong;\n" +
                "    Dạo bước trên con đường làng xanh mát;\n" +
                "\n" +
                "Đoàn lắng nghe những giai điệu Đờn Ca Tài Tử Xứ Dừa, thưởng thức miễn phí trái cây miễn phí. Sau đó, quý khách sẽ được thử ngồi xuồng chèo len lỏi vào các rạch nhỏ rợp bóng 2 hàng dừa nước. \n" +
                "\n" +
                "Quay lại thuyền lớn, đoàn đi xuôi dòng sông Tiền đến Bến Tre thực hiện \"tour đặc sản ẩm thực\":\n" +
                "\n" +
                "    Lò kẹo dừa đặc sản Bến Tre;\n" +
                "    Lò bánh tráng xã Tân Thạch;\n" +
                "\n" +
                "Ngoài ra, đoàn còn được trải nghiệm đi xe ngựa trên đường làng. \n" +
                "\n" +
                "Trưa: Tàu đưa quý khách đến Khu du lịch sinh thái Việt Nhật dùng bữa trưa. Trong lúc ăn cơm tại đây, đoàn có thể chiêm ngưỡng các loài động vật như cá sấu, nhím,... hoặc tham quan vườn Dừa, vườn trái cây. Đặc biệt quý khách còn có thể trải nghiệm miễn phí các trò chơi dân dã như đu dây, đi bộ cầu khỉ, đi xe đạp qua cầu,...\n" +
                "\n" +
                "Chiều: Tàu đưa quý khách trở lại bến tàu về Mỹ Tho, lên xe khởi hành đi Châu Đốc - An Giang. Đến Châu Đốc, đoàn làm thủ tục nhận phòng khách sạn.\n" +
                "\n" +
                "Tối: Quý khách dùng bữa tối tại nhà hàng Sunrise và tự do khám phá vẻ đẹp Châu Đốc về đêm. ");
        scheduleInput1.setArrivalTime(Objects.requireNonNull(DateUtils.getStartDay(calendar.getTime())).getTime());
        scheduleInput1.setLeaveTime(Objects.requireNonNull(DateUtils.getEndDay(calendar.getTime())).getTime());
        scheduleInputs.add(scheduleInput1);
        ScheduleInput scheduleInput2 = new ScheduleInput();
        scheduleInput2.setTitle(" NGÀY 02 | CHÂU ĐỐC – RỪNG TRÀM TRÀ SƯ – ĂN TỐI DU THUYỀN CẦN THƠ (ĂN SÁNG/ TRƯA/ TỐI)  ");
        scheduleInput2.setNameLocation(" CHÂU ĐỐC – RỪNG TRÀM TRÀ SƯ");
        scheduleInput2.setLocationStop(new Address());
        scheduleInput2.setDescription("  Sáng: Quý khách ăn sáng tại nhà hàng của khách sạn.\n" +
                "\n" +
                "Sau đó tiếp tục tour Đồng bằng sông Cửu Long 4 ngày 3 đêm xe và HDV đưa quý khách vào núi Sam viếng:\n" +
                "\n" +
                "    Miếu Bà Chúa Xứ: Công trình linh thiêng nổi tiếng cả nước với kiến trúc đẹp và tôn nghiêm tọa lạc dưới chân núi;\n" +
                "    Tây An Cổ Tự: Ngôi chùa kết hợp kiến trúc của Ấn Độ và Việt Nam xưa nên vô cùng độc đáo;\n" +
                "    Lăng Thoại Ngọc Hầu: Công trình có từ thời Nguyễn đã được chứng nhận là di tích lịch sử cấp quốc gia;\n" +
                "\n" +
                "Tiếp tục hành trình đoàn di chuyển theo hướng Tịnh Biên dọc kênh Vĩnh Tế để quý khách có thể ngắm nhìn vẻ đẹp của dãy Thất Sơn – Núi Cấm, Núi Két và những ngôi chùa Khmer độc đáo.\n" +
                "\n" +
                "Đi đến huyện Nhà Bàng, đoàn bắt đầu chuyến tham quan rừng tràm Trà Sư - điểm đến HOT nhất của du lịch An Giang. Rừng tràm là 1 hệ sinh thái rừng tuyệt đẹp, xanh tươi mơn mởn với những dải bèo trên mặt nước, được che phủ bởi bóng mát của những cây tràm.\n" +
                "\n" +
                "Đoàn sẽ ngồi tắc ráng để lướt đi giữa rừng tràm lên đài quan sát toàn cảnh thiên nhiên hoang sơ mà hùng vĩ. Đoàn còn được dạo bộ trên cây cầu gỗ giữa không xanh thơ mộng, trong lành. \n" +
                "\n" +
                "Trưa: Đoàn đi về Long Xuyên để dùng bữa trưa với các món dân dã.\n" +
                "\n" +
                "Chiều: Xe đưa quý khách khởi hành đi du lịch Cần Thơ - nơi được mệnh danh là thủ phủ miền sông nước, thường được gọi với cái tên Tây Đô. \n" +
                "\n" +
                "Tối: Đến nơi quý khách nhận phòng và ra bến tàu. Đoàn lên tàu dùng cơm tối trên nhà hàng du thuyền Cần Thơ giữa tiếng Đờn ca tài tử. Sau đó đoàn tự do khám phá Tây Đô về đêm. ");
        calendar.add(Calendar.DATE, 1);
        scheduleInput2.setArrivalTime(Objects.requireNonNull(DateUtils.getStartDay(calendar.getTime())).getTime());
        scheduleInput2.setLeaveTime(Objects.requireNonNull(DateUtils.getEndDay(calendar.getTime())).getTime());
        scheduleInputs.add(scheduleInput2);
        ScheduleInput scheduleInput3 = new ScheduleInput();
        scheduleInput3.setTitle("NGÀY 03 | CẦN THƠ – CHỢ NỔI CÁI RĂNG – MŨI CÀ MAU (ĂN SÁNG/ TRƯA/ TỐI)");
        scheduleInput3.setNameLocation("CẦN THƠ – CHỢ NỔI CÁI RĂNG");
        scheduleInput3.setLocationStop(new Address());
        scheduleInput3.setDescription("  Sáng: Đoàn tham quan đi chợ nổi Cái Răng - khu chợ trên sông sầm uất và là đặc trưng miền sông nước.\n" +
                "\n" +
                "Đoàn về khách sạn dùng điểm tâm sáng, làm thủ tục trả phòng, sau đó lên xe tiếp tục chuyến tham quan tour Đồng bằng sông Cửu Long 4N3Đ. Đoàn hướng đến Cà Mau.\n" +
                "\n" +
                "Đến Sóc Trăng quý khách tham quan ngôi chùa Chén Kiểu có kiến trúc độc đáo của người Khmer\n" +
                "\n" +
                "Tiếp tục hành trình, đoàn đi tham quan danh thắng của Bạc Liêu:\n" +
                "\n" +
                "    Nhà thờ Tắc Sậy - Giá Rai Bạc Liêu viếng mộ vị linh mục nổi tiếng - Cha Trương Bửu Diệp;\n" +
                "\n" +
                "Trưa: Đến thành phố Cà Mau quý khách dùng cơm trưa, nghỉ ngơi.\n" +
                "\n" +
                "Chiều: Xe đưa đoàn đi tham quan khám phá Đất Mũi Cà Mau. Tại đây, đoàn được đi xuyên rừng ngập mặn với khung cảnh thiên nhiên hoang sơ:\n" +
                "\n" +
                "    Tận mắt chiêm ngưỡng và chụp ảnh lưu niệm tại Mốc tọa độ Quốc gia – GPS 0001, Panô biểu tượng Mũi Cà Mau đầy thiêng liêng của tổ quốc;\n" +
                "    Đi lên Vọng Hải Đài cao 20,5 mét ngắm nhìn toàn cảnh điểm cực Nam của đất nước;\n" +
                "    Tham quan Vườn Quốc Gia Mũi Cà Mau: ngắm nhìn Hệ sinh thái rừng ngập mặn tại Khu Dự trữ Sinh quyển Thế giới;  \n" +
                "    Cột cờ tại đất mũi Cà Mau, tượng mẹ Âu Cơ, ghé thăm bảo tàng;\n" +
                "\n" +
                "Đoàn quay lại trung tâm Thành phố Cà Mau làm thủ tục nhận phòng khách sạn, dùng cơm tối. \n" +
                "\n" +
                "Tối: Quý khách tự do khám phá ẩm thực địa phương của Cà Mau về đêm.  ");
        calendar.add(Calendar.DATE, 1);
        scheduleInput3.setArrivalTime(Objects.requireNonNull(DateUtils.getStartDay(calendar.getTime())).getTime());
        scheduleInput3.setLeaveTime(Objects.requireNonNull(DateUtils.getEndDay(calendar.getTime())).getTime());
        scheduleInputs.add(scheduleInput3);
        ScheduleInput scheduleInput4 = new ScheduleInput();
        scheduleInput4.setTitle(" NGÀY 04 | CÀ MAU – NHÀ CÔNG TỬ BẠC LIÊU – CÁNH ĐỒNG QUẠT GIÓ – SÓC TRĂNG – SÀI GÒN (ĂN SÁNG/ TRƯA) ");
        scheduleInput4.setNameLocation("CÀ MAU – NHÀ CÔNG TỬ BẠC LIÊU – CÁNH ĐỒNG QUẠT GIÓ");
        scheduleInput4.setLocationStop(localAddress);
        scheduleInput4.setDescription(" Sáng: Đoàn ăn sáng tại khách sạn, làm thủ tục trả phòng, sau đó quay về Bạc Liêu tham quan các địa điểm du lịch miền Tây đặc sắc:\n" +
                "\n" +
                "    Cánh đồng quạt gió: Đây là điểm check-in cực HOT của giới trẻ Bạc Liêu; \n" +
                "    Nhà Công Tử Bạc Liêu: Tham quan ngôi nhà có lịch sử hơn 100 năm và lắng nghe giai thoại về cuộc sống vàng son một thời của cậu Ba Huy (Trần Trinh Huy);\n" +
                "    Phật Bà Nam Hải: Chiêm bái và cầu nguyện may mắn cho người thân;\n" +
                "    Khu tưởng niệm cố nhạc sĩ Cao Văn Lầu: Tìm hiểu về vị cố nhạc sĩ tài ba; \n" +
                "\n" +
                "Trưa: Đoàn về Sóc Trăng tham quan cơ sở sản xuất bánh Pía nổi tiếng. Đến nhà hàng ở Hậu Giang dùng cơm trưa với những món đặc sản Nam Bộ, nghỉ ngơi. \n" +
                "\n" +
                "Chiều: Sau khi dùng cơm trưa, đoàn tiếp tục khởi hành về thành phố Hồ Chí Minh.\n" +
                "\n" +
                "Tối: Khoảng 19h00 Đoàn tham quan về đến TPHCM, kết thúc chương trình tour đồng bằng sông Cửu Long 4N3Đ đầy thú vị và ý nghĩa. HDV chia tay và hẹn gặp lại du khách trong các tour du lịch giá rẻ thú vị khác. ");
        calendar.add(Calendar.DATE, 1);
        scheduleInput4.setArrivalTime(DateUtils.getStartDay(DateUtils.getStartDay(calendar.getTime())).getTime());
        scheduleInput4.setLeaveTime(DateUtils.getStartDay(DateUtils.getEndDay(calendar.getTime())).getTime());
        scheduleInputs.add(scheduleInput4);
        createTourInput.setScheduleInput(scheduleInputs);

        List<PricingValueInput> pricingValueInputs = new ArrayList<>();
        PricingValueInput pricingValueInput1 = new PricingValueInput();

        pricingValueInput1.setPrice(MoneyCalculateUtils.getMoney(40000));
        pricingValueInput1.setAboutAgeType(AgeType.ADULTS);
        pricingValueInput1.setNote("Người lớn");
        pricingValueInput1.setSale(0.9);
        pricingValueInputs.add(pricingValueInput1);

        PricingValueInput pricingValueInput2 = new PricingValueInput();

        pricingValueInput2.setPrice(MoneyCalculateUtils.getMoney(35000));
        pricingValueInput2.setAboutAgeType(AgeType.CHILDREN);
        pricingValueInput2.setNote("Trẻ em");
        pricingValueInput2.setSale(0.9);
        pricingValueInputs.add(pricingValueInput2);

        createTourInput.setPricingValues(pricingValueInputs);
        createTourController.createTour(createTourInput);
    }

    @Test
    public void testTour_5() throws ServiceException {
        CreateTourInput createTourInput = new CreateTourInput();
        TourInput tourInput = new TourInput();
        tourInput.setDescription(
                "\n" +
                        "    Xem trực tiếp giải đấu vòng bảng của đội tuyển Việt Nam tại Seagame 32.\n" +
                        "    Tham quan quần thể Hoàng cung - Chùa Vàng - Chùa Bạc.\n" +
                        "    Check-in tại Đài Độc Lập, nơi đánh dấu hòa bình của đất nước Campuchia.\n" +
                        "    Đặt chân đến Casino Naga World.\n" +
                        "    Mua sắm thả ga tại chợ Th’mey.\n" +
                        "    Dùng bữa tại nhà hàng buffet lẩu Suki.\n" +
                        "    Ăn thả ga tại nhà hàng buffet tự chọn lớn nhất Phnom Penh.\n");
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
        tourInput.setFeatureImgUrl("https://www.vietnambooking.com/wp-content/uploads/2023/04/tour-seagame-32-1-300x194.jpg");
        tourInput.setImgUrls(Arrays.asList(
                "https://www.vietnambooking.com/wp-content/uploads/2023/04/tour-seagame-32-2-1.jpg",
                "https://www.vietnambooking.com/wp-content/uploads/2023/04/tour-seagame-32-3-1.jpg",
                "https://www.vietnambooking.com/wp-content/uploads/2023/04/tour-seagame-32-4.jpg",
                "https://www.vietnambooking.com/wp-content/uploads/2023/04/tour-seagame-32-5.jpg"));
        Address localAddress = new Address();
        localAddress.setAddress("Phsar Chas, Daun Penh, Phnom Penh, Campuchia");
        localAddress.setProvince("Phnom Penh");
        localAddress.setProvinceCode(1);
        localAddress.setDistrict("Daun Penh");
        localAddress.setDistrictCode(101);
        localAddress.setWard("Phsar Chas");
        localAddress.setWardCode(1001);
        localAddress.setCountryCode(CountryCode.KH);
        tourInput.setLocation(localAddress);
        tourInput.setName("\n" +
                "Tour Seagame 32 2 ngày 1 đêm – Hành trình đến với đất nước chùa tháp Campuchia");
        tourInput.setNote("Tour note");
        tourInput.setDiscount(DiscountUtils.discount());
        tourInput.setTimeHowLong(TimeHowLong.TWO_DAY_ONE_NIGHT);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.MAY, 13); // Tháng bắt đầu từ 0
        tourInput.setStartDate(new Date(calendar.getTimeInMillis()));
        tourInput.setPriceStandard(MoneyCalculateUtils.getMoney(5000000));
        tourInput.setQuantityVisit(162);
        tourInput.setType(TourType.ABROAD);
        createTourInput.setTourInput(tourInput);

        VehicleInput vehicleInput = new VehicleInput();
        vehicleInput.setAutomaker("VietNam Ariline");
        vehicleInput.setName("máy Bay");
        vehicleInput.setVehicleType(VehicleType.PLANE);
        vehicleInput.setNumberOfSeats(VehicleType.PLANE.getNumberOfSeats());
        vehicleInput.setCode("51A-1122");
        createTourInput.setVehicleInput(vehicleInput);

        List<ScheduleInput> scheduleInputs = new ArrayList<>();
        ScheduleInput scheduleInput1 = new ScheduleInput();
        scheduleInput1.setTitle("NGÀY 01 | SÀI GÒN - SÂN VẬN ĐỘNG PRINCE - PHNOMPENH (ĂN SÁNG/TRƯA)");
        scheduleInput1.setNameLocation("SÂN VẬN ĐỘNG PRINCE - PHNOMPENH (ĂN SÁNG/TRƯA)");
        scheduleInput1.setLocationStop(localAddress);
        scheduleInput1.setDescription(" Sáng: Xe và hướng dẫn viên (HDV) của Vietnam Booking sẽ đến đón quý khách tại điểm hẹn và bắt đầu hành trình tour Seagame 32 2N1Đ.\n" +
                "\n" +
                "Đoàn sẽ dừng chân dùng bữa sáng tại Trảng Bàng. Và sau đó thẳng tiến đến cửa khẩu Mộc Bài, làm thủ tục xuất cảnh Việt Nam và nhập cảnh vào Campuchia. Đoàn sẽ tiếp tục tiến thẳng vào Siem Reap.\n" +
                "\n" +
                "Trưa: Sau khi đến Phnom Penh, quý khách sẽ ăn trưa tại nhà hàng lẩu băng chuyền Sou Sou Suki. Tại đây, quý khách sẽ được thưởng thức những món ăn mang đậm hương vị Campuchia. Dùng bữa xong, xe sẽ đưa quý khách về khách sạn để nhận phòng và nghỉ ngơi. \n" +
                "\n" +
                "Chiều: Theo giờ hẹn, quý khách sẽ được xe đưa đến:\n" +
                "\n" +
                "    Sân vận động Prince tại thủ đô Phnom Penh: Quý khách sẽ được tận mắt xem trực tiếp và cổ vũ đội tuyển quốc gia Việt Nam. Hòa cùng không khí sôi động, cuồng nhiệt, tiếp lửa cho các cầu thủ trẻ Việt Nam “BẢO VỆ HUY CHƯƠNG VÀNG MÔN BÓNG ĐÁ NAM U22 – SEAGAME 32”.\n" +
                "\n" +
                "Tối: Sau khi trận đấu kết thúc, quý khách sẽ về và nghỉ ngơi tại khách sạn. Ngoài ra, quý khách có thể dự do đi dạo, khám phá thành phố Campuchia về đêm. ");
        calendar.add(Calendar.DATE, 1);
        scheduleInput1.setArrivalTime(Objects.requireNonNull(DateUtils.getStartDay(calendar.getTime())).getTime());
        scheduleInput1.setLeaveTime(Objects.requireNonNull(DateUtils.getEndDay(calendar.getTime())).getTime());
        scheduleInputs.add(scheduleInput1);
        ScheduleInput scheduleInput2 = new ScheduleInput();
        scheduleInput2.setTitle("NGÀY 02 | HOÀNG CUNG - CHÙA VÀNG - CHÙA BẠC - CHỢ LỚN MỚI PHNOM PENH (PHSAR TH’MEY) - ĐÀI ĐỘC LẬP - SÀI GÒN (ĂN SÁNG/ TRƯA)");
        scheduleInput2.setNameLocation("CHỢ LỚN MỚI PHNOM PENH");
        scheduleInput2.setLocationStop(new Address());
        scheduleInput2.setDescription(" Sáng: Quý khách ăn sáng tại nhà hàng của khách sạn.\n" +
                "\n" +
                "Sau khi trả phòng, xe sẽ tiếp tục hành trình tour Seagame 32 đưa quý khách tham quan những điểm nổi tiếng trong tour đi Campuchia. Đầu tiên phải nhắc đến là Quần thể Hoàng Cung.\n" +
                "\n" +
                "    Hoàng Cung: Đây là nơi sinh sống và làm việc của nhà vua hiện thời. Thế nên khu vực này không mở cửa cho quý khách vào trong tham quan.\n" +
                "\n" +
                "    Chùa Vàng - Chùa Bạc: Đây là ngôi chùa nằm trong quần thể Hoàng Cung. Sở dĩ có tên như vậy là vì nền chùa được lát từ gạch làm từ bạc nguyên khối. Cùng với đó là  tượng Phật bằng vàng và Phật Ngọc Lục Bảo nổi tiếng.\n" +
                "\n" +
                "Một số điều bạn cần lưu ý khi tham quan Quần thể Hoàng Cung là:\n" +
                "\n" +
                "Lưu ý 1: Mặc áo có tay, không được mặc áo cổ rộng. Nếu mặc váy đầm thì phải dài qua gối.\n" +
                "\n" +
                "Lưu ý 2: Hoàng Cung có thể đóng cửa ngừng hoạt động bất cứ lúc nào mà không thông báo trước.\n" +
                "\n" +
                "Sau đó, quý khách sẽ tiếp tục cuộc hành trình của mình với những điểm:\n" +
                "\n" +
                "    Chợ Lớn Mới Phnom Penh (Phsar Th’mey): Quý khách sẽ được mua sắm tại một trong những ngôi chợ lớn nhất Campuchia. Những mặt hàng tại đây vô cùng đa dạng, thích hợp để quý khách mua về làm quà sau chuyến đi.\n" +
                "\n" +
                "    Đài Độc Lập: Đoàn sẽ chụp hình kỷ niệm tại Đài Độc Lập. Đây là nơi đánh dấu, ghi lại thời kỳ hòa bình của đất nước Campuchia.\n" +
                "\n" +
                "Trưa: Quý khách sẽ được dùng cơm trưa tại nhà hàng Buffet Tonle Bassac II. Đây là một trong những nhà hàng lớn nhất Campuchia. Dùng bữa xong xe sẽ đưa quý khách về lại cửa khẩu Bavet.\n" +
                "\n" +
                "Chiều: Đến cửa khẩu Mộc Bài, quý khách sẽ được làm thủ tục nhập cảnh Việt Nam. Sau khi đến TP. HCM, xe sẽ đưa quý khách về lại điểm đón ban đầu. Kết thúc chương trình và Vietnam Booking xin hẹn gặp lại quý khách tại những tour du lịch giá rẻ khác. ");
        calendar.add(Calendar.DATE, 1);
        scheduleInput2.setArrivalTime(Objects.requireNonNull(DateUtils.getStartDay(calendar.getTime())).getTime());
        scheduleInput2.setLeaveTime(Objects.requireNonNull(DateUtils.getEndDay(calendar.getTime())).getTime());
        scheduleInputs.add(scheduleInput2);
        createTourInput.setScheduleInput(scheduleInputs);
        List<PricingValueInput> pricingValueInputs = new ArrayList<>();
        PricingValueInput pricingValueInput1 = new PricingValueInput();

        pricingValueInput1.setPrice(MoneyCalculateUtils.getMoney(5000000));
        pricingValueInput1.setAboutAgeType(AgeType.ADULTS);
        pricingValueInput1.setNote("Người lớn");
        pricingValueInput1.setSale(0.1);
        pricingValueInputs.add(pricingValueInput1);

        PricingValueInput pricingValueInput2 = new PricingValueInput();

        pricingValueInput2.setPrice(MoneyCalculateUtils.getMoney(4500000));
        pricingValueInput2.setAboutAgeType(AgeType.CHILDREN);
        pricingValueInput2.setNote("Trẻ em");
        pricingValueInput2.setSale(0.3);
        pricingValueInputs.add(pricingValueInput2);

        createTourInput.setPricingValues(pricingValueInputs);
        createTourController.createTour(createTourInput);
    }

    @Test
    public void testTour_6() throws ServiceException {
        CreateTourInput createTourInput = new CreateTourInput();
        TourInput tourInput = new TourInput();
        tourInput.setDescription(
                "\n" +
                        "    Tham quan, chiêm ngưỡng cảnh đẹp lãng mạn đảo Nami\n" +
                        "    Tham quan Blue House – Dinh tổng thống Hàn Quốc\n" +
                        "    Dạo chơi, chụp ảnh ở quảng trường Gwanghwamun – quảng trường đẹp nhất ở thủ đô Seoul\n" +
                        "    Tìm hiểu về cuộc sống bình dị của người Hàn Quốc ở làng dân tộc Namsan, chiêm ngưỡng cảnh đẹp thủ đô Seoul ở tháp Seoul\n" +
                        "    Chiêm ngưỡng cảnh đẹp thiên nhiên ở suối Cheonggyecheon – con suối nhỏ chảy quanh thủ đô Seoul\n" +
                        "    Tìm hiểu về lịch sử, văn hóa Hàn Quốc tại Cung điện Kyeongbok, bảo tàng truyền thống dân gian quốc gia Triều Tiên\n" +
                        "    Vui chơi giải trí thoải mái tại công viên giải trí Everland - Top 10 công viên lớn nhất thế giới.\n" +
                        "    Thỏa sức tham quan, mua sắm tại các cửa hàng miễn thuế, cửa hàng bán đồ mỹ phẩm Hàn Quốc, nhân sâm và các trung tâm mua sắm khác\n" +
                        "    Thưởng thức những món ăn đặc trưng của xứ sở Kim Chi.\n");
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
        tourInput.setFeatureImgUrl("https://www.vietnambooking.com/wp-content/uploads/2018/10/tour-dulich-hanquoc-5n4d-don-nam-moi-tai-xuso-kim-chi-08102018-17-300x173.jpg");
        tourInput.setImgUrls(Arrays.asList(
                "https://www.vietnambooking.com/wp-content/uploads/2022/06/tour-han-quoc-5-ngay-4-dem-1.jpg",
                "https://www.vietnambooking.com/wp-content/uploads/2022/06/tour-han-quoc-5-ngay-4-dem-3.jpg",
                "https://www.vietnambooking.com/wp-content/uploads/2022/06/tour-han-quoc-5-ngay-4-dem-4.jpg",
                "https://www.vietnambooking.com/wp-content/uploads/2022/06/tour-han-quoc-5-ngay-4-dem-5.jpg"));
        Address localAddress = new Address();
        localAddress.setAddress("Gangnam-gu, Jongno-gu, Seoul, Korea");
        localAddress.setProvince("Seoul");
        localAddress.setProvinceCode(1);
        localAddress.setDistrict("Gangnam-gu");
        localAddress.setDistrictCode(101);
        localAddress.setWard("Jongno-gu");
        localAddress.setWardCode(1001);
        localAddress.setCountryCode(CountryCode.KP);
        tourInput.setLocation(localAddress);
        tourInput.setName("Tour Hàn Quốc 5 ngày 4 đêm – Lãng mạn xứ sở kim chi");
        tourInput.setNote("Tour note");
        tourInput.setDiscount(DiscountUtils.discount());
        tourInput.setTimeHowLong(TimeHowLong.FIVE_DAY_FOUR_NIGHT);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.MAY, 14); // Tháng bắt đầu từ 0
        tourInput.setStartDate(new Date(calendar.getTimeInMillis()));
        tourInput.setPriceStandard(MoneyCalculateUtils.getMoney(5000000));
        tourInput.setQuantityVisit(162);
        tourInput.setType(TourType.ABROAD);
        createTourInput.setTourInput(tourInput);

        VehicleInput vehicleInput = new VehicleInput();
        vehicleInput.setAutomaker("VietNam Ariline");
        vehicleInput.setName("Máy Bay");
        vehicleInput.setVehicleType(VehicleType.PLANE);
        vehicleInput.setNumberOfSeats(VehicleType.PLANE.getNumberOfSeats());
        vehicleInput.setCode("51A-1322");
        createTourInput.setVehicleInput(vehicleInput);

        List<ScheduleInput> scheduleInputs = new ArrayList<>();
        ScheduleInput scheduleInput1 = new ScheduleInput();
        scheduleInput1.setTitle("NGÀY 1: TPHCM - SEOUL (NGHỈ ĐÊM TRÊN MÁY BAY - ĂN NHẸ)");
        scheduleInput1.setNameLocation("TPHCM - SEOUL)");
        scheduleInput1.setLocationStop(localAddress);
        scheduleInput1.setDescription("Tất cả hành khách tập trung tại sân bay quốc tế Tân Sơn Nhất theo thông báo đã hẹn từ trước." +
                " Trưởng đoàn đưa đến cho quý khách các phần ăn nhẹ và hỗ trợ hoàn tất thủ tục hàng không bay đi Hàn Quốc. ");
        scheduleInput1.setArrivalTime(Objects.requireNonNull(DateUtils.getStartDay(calendar.getTime())).getTime());
        scheduleInput1.setLeaveTime(Objects.requireNonNull(DateUtils.getEndDay(calendar.getTime())).getTime());
        scheduleInputs.add(scheduleInput1);
        ScheduleInput scheduleInput2 = new ScheduleInput();
        scheduleInput2.setTitle(" NGÀY 2: SEOUL - ĐẢO NAMI (ĂN SÁNG/ TRƯA/ TỐI) ");
        scheduleInput2.setNameLocation("SEOUL - ĐẢO NAMI");
        scheduleInput2.setLocationStop(new Address());
        scheduleInput2.setDescription(" Sáng: Máy bay hạ cánh tại sân bay Quốc Tế Incheon. HDV nhiệt liệt chào đón quý khách đã đến Thủ đô của xứ sở kim chi -Seoul. Tiếp theo, đoàn khách di chuyển đến nhà hàng địa phương để dùng bữa sáng.\n" +
                "\n" +
                "Sau đó, du khách xuất phát đi tham quan theo lộ trình trong tour Hàn Quốc 5 ngày 4 đêm:\n" +
                "\n" +
                "Đảo Nami: nơi đây đã quá nổi tiếng với khách du lịch khi đến Hàn Quốc. Mỗi mùa sẽ có một đặc điểm riêng biệt. mùa hè nắng vàng óng ả, cây cỏ xanh ngát; mùa thu tiết trời dịu nhẹ hòa cùng sắc vàng đỏ của những tán cây lá phong; mùa đông buốt giá với những mảng tuyết trắng tinh. Đặc sắc hơn hết chính là mùa xuân với những nhành hoa anh đào nở rộ, điểm tô ánh hồng lên khắp mọi nơi. Hình ảnh này thường xuất hiện rất nhiều trong các bộ phim Hàn, tạo nên trào lưu hot một thời.\n" +
                "\n" +
                "Trưa: Đoàn ăn trưa tại nhà hàng với món gà nướng BBQ nổi tiếng nhất ở đảo Nami.\n" +
                "\n" +
                "Chiều: Chuyến du lịch vẫn tiếp tục diễn ra với các địa điểm tham quan: \n" +
                "\n" +
                "    Quảng trường Gwanghwamun: đây là điểm nhấn độc đạo cho thành phố Seoul hoa lệ. Tại đây có diện tích rộng lớn với nhiều công trình phong phú như cây xanh, thảm hoa, đài phun nước,... Người dân thủ đô luôn tự hào về nơi đặc biệt như thế này.\n" +
                "    Suối Cheonggyecheon: con suối nằm ngay giữa thủ đô Seoul, có chiều dài gần 6km.\n" +
                "\n" +
                "Tối: Quý khách được phục vụ cơm tối tại nhà hàng địa phương. Chuyến hành trình ngày đầu kết thúc. Quý khách trở lại khách sạn để nhận phòng rồi nghỉ ngơi tự do. ");
        calendar.add(Calendar.DATE, 1);
        scheduleInput2.setArrivalTime(Objects.requireNonNull(DateUtils.getStartDay(calendar.getTime())).getTime());
        scheduleInput2.setLeaveTime(Objects.requireNonNull(DateUtils.getEndDay(calendar.getTime())).getTime());
        scheduleInputs.add(scheduleInput2);
        ScheduleInput scheduleInput3 = new ScheduleInput();
        scheduleInput3.setTitle(" NGÀY 3: SEOUL - KHU VUI CHƠI EVERLAND (ĂN SÁNG / TRƯA/ TỐI) ");
        scheduleInput3.setNameLocation("SEOUL - KHU VUI CHƠI EVERLAND");
        scheduleInput3.setLocationStop(localAddress);
        scheduleInput3.setDescription(" Sáng: Quý khách dùng điểm tâm sáng với các món ăn ngon. Kế tiếp, tour Hàn Quốc 5 ngày 4 đêm vẫn chưa dừng lại:\n" +
                "\n" +
                "    Công viên giải trí liên hợp Everland: ở đây có nhiều màn trình diễn thú vị và trò chơi hấp dẫn cho nhiều lứa tuổi khác nhau. Nơi này được xếp vào top 10 công viên lớn nhất thế giới có các trò chơi cảm giác mạnh, khu gấu trúc, khu vườn thú hoang dã Safari,... Du khách có được vui chơi thỏa thích tại những vườn hoa lộng lẫy. Bên cạnh đó còn có cơ hội diễu hành cùng đoàn vũ công của công viên.\n" +
                "    Đăng ký tham gia lớp học Kim Chi để tìm hiểu thêm thông tin về món ăn này. Đồng thời được mặc đồ Hanbok Hàn Quốc truyền thống đẹp mắt.\n" +
                "    Hàn là một nước cực kỳ phát triển về ngành công nghiệp làm đẹp. Không chỉ nữ giới mà cả nam giới Hàn cũng rất thích làm đẹp và quan tâm rất nhiều về vẻ bề ngoài của mình. Xe chở quý khách đến cửa hàng mỹ phẩm nội địa để tham quan và dùng thử sản phẩm hoàn toàn miễn phí.\n" +
                "\n" +
                "Trưa: Du khách sẽ được đưa đi ăn trưa tại nhà hàng, nghỉ ngơi tại chỗ.\n" +
                "\n" +
                "Chiều: Cuộc hành trình vào buổi chiều tiếp tục diễn ra:\n" +
                "\n" +
                "    Đoàn khách di chuyển đến Trung tâm nhân sâm chính phủ: nơi này được bảo trợ bởi chính phủ Hàn Quốc và tập đoàn Samsung. Du khách hoàn toàn an tâm về chất lượng sản phẩm khi mua tại đây.\n" +
                "    Tháp N Seoul: nằm ngay trên đỉnh núi Namsan. Đứng từ trên cao, du khách sẽ chiêm ngưỡng bao quát thành phố. Đây là một trong các biểu tượng đặc sắc nhất của thành phố. (Chi phí cho thang máy sẽ do quý khách tự túc).\n" +
                "    Địa điểm dừng chân cuối ngày chính là chợ Myeongdong nô nức nhất. Quý khách được mua sắm thoải mái và đi lại tự do.\n" +
                "\n" +
                "Tối: Xe chở đoàn khách đi dùng bữa tối rồi về lại khách sạn để nghỉ ngơi. ");
        calendar.add(Calendar.DATE, 1);
        scheduleInput3.setArrivalTime(Objects.requireNonNull(DateUtils.getStartDay(calendar.getTime())).getTime());
        scheduleInput3.setLeaveTime(Objects.requireNonNull(DateUtils.getEndDay(calendar.getTime())).getTime());
        scheduleInputs.add(scheduleInput3);
        ScheduleInput scheduleInput4 = new ScheduleInput();
        scheduleInput4.setTitle("  NGÀY 4: CUNG ĐIỆN CẢNH PHÚC - BLUE HOUSE (ĂN SÁNG/ TRƯA/ TỐI) ");
        scheduleInput4.setNameLocation("CUNG ĐIỆN CẢNH PHÚC");
        scheduleInput4.setLocationStop(new Address());
        scheduleInput4.setDescription("  Sáng: Sau khi dùng xong bữa sáng, xe chở quý khách đi tham quan các địa danh kế tiếp trong tour Hàn Quốc 5 ngày 4 đêm:\n" +
                "\n" +
                "    Cung điện Kyeongbok (Cảnh Phúc Cung): tọa lạc tại khu vực phía Bắc của Seoul. Cung điện hoàng gia này xuất hiện từ cuối thế kỷ XIV, vào thời kỳ Joseon.\n" +
                "    Viện bảo tàng truyền thống dân gian Quốc Gia Triều Tiên: hiện vẫn còn cất giữ và bảo vệ cho nhiều di sản văn hóa của đất nước Triều Tiên. Tại đây có đến hơn 10.000 mẫu vật thể hiện cho nhiều hình thức tôn giáo, lễ nghi, các vật dụng và cách bài trí truyền thống của người Triều Tiên xưa.\n" +
                "    Blue House - Nhà xanh: hay còn gọi là Phủ Tổng Thống Hàn Quốc, nằm trong lòng trung tâm thủ đô Seoul. Nhà Xanh có không gian thoáng rộng và khuôn viên có thiết kế đẹp mắt, vừa có sự hiện đại nhưng vẫn giữ được được nét văn hóa Hàn Quốc truyền thống.\n" +
                "    Kế đến, du khách sẽ được tham gia vào Trò chơi con mực (Squid Game) - lấy ý tưởng từ một bộ phim đình đám. Có nhiều loại trò chơi khác nhau như: trò chơi câu mực, cầu thủy tinh, kéo co, viên bi, Dalgona Candy, HoneyComb, Greenlight, Redlight,...\n" +
                "\n" +
                "Trưa: Du khách dùng bữa trưa và nghỉ ngơi để tiếp tục hành trình tham quan, \n" +
                "\n" +
                "Chiều: Xe đưa quý khách đến cửa hàng Tinh dầu Thông đỏ để tìm hiểu và mua sắm nếu có nhu cầu. Du khách sẽ được nghe về bài thuốc huyền diệu - Thần dược Detox máu - được tạo ra từ bàn tay của Thần y Herjun. Nó có nhiều công dụng bổ ích như giảm mỡ trong máu, ngăn ngừa đột quỵ, hạn chế tăng huyết áp và phòng chống tiểu đường,...\n" +
                "\n" +
                "Tối: Xe đưa du khách đến nhà hàng địa phương để dùng bữa tối. Sau đó về lại khách sạn để nghỉ ngơi, nạp lại năng lượng. ");
        calendar.add(Calendar.DATE, 1);
        scheduleInput4.setArrivalTime(Objects.requireNonNull(DateUtils.getStartDay(calendar.getTime())).getTime());
        scheduleInput4.setLeaveTime(Objects.requireNonNull(DateUtils.getEndDay(calendar.getTime())).getTime());
        scheduleInputs.add(scheduleInput4);
        ScheduleInput scheduleInput5 = new ScheduleInput();
        scheduleInput5.setTitle(" NGÀY 5: SEOUL - TPHCM (ĂN SÁNG - FASTFOOD) ");
        scheduleInput5.setNameLocation("SEOUL - TPHCM");
        scheduleInput5.setLocationStop(new Address());
        scheduleInput5.setDescription("  Sáng: HDV hỗ trợ quý khách hoàn tất thủ tục trả phòng rồi đi đến nhà hàng để ăn sáng.\n" +
                "\n" +
                "    Du khách được mua sắm vật dụng mình thích tại trung tâm bách hóa và đóng kiện hành lý mua sắm tại trung tâm bách hóa và đóng kiện hành lý.\n" +
                "    Quý khách được đưa ra sân bay Incheon, làm thủ tục đáp chuyến bay về sân bay Tân Sơn Nhất.\n" +
                "\n" +
                "Tối: Về đến sân bay Tân Sơn Nhất, quý khách hoàn thành thủ tục nhập cảnh Việt Nam và nhận lại hành lý cá nhân. Trưởng đoàn chào tạm biệt Quý khách. \n" +
                "\n" +
                "Kết thúc chuyến đi tour Hàn Quốc 5 ngày 4 đêm thú vị đến xứ sở Kim Chi và hẹn gặp lại du khách trong những tour du lịch nước ngoài hấp dẫn khác. ");
        calendar.add(Calendar.DATE, 1);
        scheduleInput5.setArrivalTime(Objects.requireNonNull(DateUtils.getStartDay(calendar.getTime())).getTime());
        scheduleInput5.setLeaveTime(Objects.requireNonNull(DateUtils.getEndDay(calendar.getTime())).getTime());
        scheduleInputs.add(scheduleInput5);
        createTourInput.setScheduleInput(scheduleInputs);
        List<PricingValueInput> pricingValueInputs = new ArrayList<>();
        PricingValueInput pricingValueInput1 = new PricingValueInput();

        pricingValueInput1.setPrice(MoneyCalculateUtils.getMoney(5000000));
        pricingValueInput1.setAboutAgeType(AgeType.ADULTS);
        pricingValueInput1.setNote("Người lớn");
        pricingValueInput1.setSale(0.1);
        pricingValueInputs.add(pricingValueInput1);

        PricingValueInput pricingValueInput2 = new PricingValueInput();

        pricingValueInput2.setPrice(MoneyCalculateUtils.getMoney(4500000));
        pricingValueInput2.setAboutAgeType(AgeType.CHILDREN);
        pricingValueInput2.setNote("Trẻ em");
        pricingValueInput2.setSale(0.3);
        pricingValueInputs.add(pricingValueInput2);

        createTourInput.setPricingValues(pricingValueInputs);
        createTourController.createTour(createTourInput);
    }

    @Test
    public void testTour_7() throws ServiceException {
        CreateTourInput createTourInput = new CreateTourInput();
        TourInput tourInput = new TourInput();
        tourInput.setDescription(
                "\n" +
                        "    Trọn gói vé máy bay khứ hồi đi Bangkok, được ở lại khách sạn 4 - 5 sao.\n" +
                        "    Tặng 01 suất Massage Thái Cổ Truyền\n" +
                        "    Hàng loạt điểm đến hot nhất Thái Lan: Chùa Phật Vàng, Thành Phố Cổ Muang Boran, Chợ Nổi Bốn Miền, Chùa Thuyền Wat Yannawa,...\n" +
                        "    Đi tàu tham quan đảo Coral – còn gọi là Đảo San Hô.\n" +
                        "    Ăn buffet tại Nhà Hàng Xoay Baiyoke Sky 86 tầng cao nhất tại thủ đô Bangkok.\n" +
                        "    Thưởng thức các loại Đặc sản Thái Lan trong thực đơn hàng ngày.\n");
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
        tourInput.setFeatureImgUrl("https://www.vietnambooking.com/wp-content/uploads/2020/06/tour-bangkok-pattaya-5-ngay-4-dem-2-300x194.jpg");
        tourInput.setImgUrls(Arrays.asList(
                "https://www.vietnambooking.com/wp-content/uploads/2020/06/tour-bangkok-pattaya-5-ngay-4-dem-2.jpg",
                "https://www.vietnambooking.com/wp-content/uploads/2019/12/Ancient-city.jpg",
                "https://www.vietnambooking.com/wp-content/uploads/2019/12/tour-bangkok-pattaya-5-ngay-4-dem-1.jpg",
                "https://www.vietnambooking.com/wp-content/uploads/2019/12/Ancient-city2.jpg"));
        Address localAddress = new Address();
        localAddress.setAddress("Lumpini, Pathum Wan, Bangkok, ThaiLan");
        localAddress.setProvince("Bangkok");
        localAddress.setProvinceCode(1);
        localAddress.setDistrict("Pathum Wan");
        localAddress.setDistrictCode(101);
        localAddress.setWard("Lumpini");
        localAddress.setWardCode(1001);
        localAddress.setCountryCode(CountryCode.TH);
        tourInput.setLocation(localAddress);
        tourInput.setName("Tour du lịch Bangkok – Pattaya 5 ngày 4 đêm");
        tourInput.setNote("Tour note");
        tourInput.setDiscount(DiscountUtils.discount());
        tourInput.setTimeHowLong(TimeHowLong.FIVE_DAY_FOUR_NIGHT);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.MAY, 13); // Tháng bắt đầu từ 0
        tourInput.setStartDate(new Date(calendar.getTimeInMillis()));
        tourInput.setPriceStandard(MoneyCalculateUtils.getMoney(10000000));
        tourInput.setQuantityVisit(162);
        tourInput.setType(TourType.ABROAD);
        createTourInput.setTourInput(tourInput);

        VehicleInput vehicleInput = new VehicleInput();
        vehicleInput.setAutomaker("VietNam Ariline");
        vehicleInput.setName("máy Bay");
        vehicleInput.setVehicleType(VehicleType.PLANE);
        vehicleInput.setNumberOfSeats(VehicleType.PLANE.getNumberOfSeats());
        vehicleInput.setCode("51A-0322");
        createTourInput.setVehicleInput(vehicleInput);

        List<ScheduleInput> scheduleInputs = new ArrayList<>();
        ScheduleInput scheduleInput1 = new ScheduleInput();
        scheduleInput1.setTitle("NGÀY 01: TP HỒ CHÍ MINH - BANGKOK (ĂN TỐI)");
        scheduleInput1.setNameLocation("TP HỒ CHÍ MINH - BANGKOK");
        scheduleInput1.setLocationStop(localAddress);
        scheduleInput1.setDescription(" Sáng: Trưởng đoàn của Vietnam Booking sẽ đón quý khách tại sân bay Tân Sơn Nhất. HDV hỗ trợ làm thủ tục đáp chuyến bay VJ803 đi BANGKOK vào 11:15. Còn với chuyến bay QH325 thì sẽ xuất phát lúc 14:00.\n" +
                "\n" +
                "Chiều: Máy bay hạ cánh, quý khách hoàn tất thủ tục nhập cảnh đất nước Thái Lan. Tour Bangkok Pattaya 5 ngày 4 đêm chính thức bắt đầu:\n" +
                "\n" +
                "Địa điểm đầu tiên là Chùa Wat Sake: được nhiều người biết đến là một ngôi chùa linh thiêng nằm trên ngọn núi vàng. Nơi đây nằm trong quần thể Phu Khao Thong, có chánh điện lớn, thư viện và nhiều nhà nguyện xung quanh. Ngoài ra, chùa vẫn còn đang giữ lại nhiều bảo vật Xá Phúc Lợi cực kỳ linh thiêng.\n" +
                "\n" +
                "Tối: Du khách theo đoàn xe trở về khách sạn để dùng bữa tối. Sau đó là nhận phòng nghỉ ngơi. Quý khách cũng có thể tự đi tản bộ, du ngoạn thành phố Bangkok khi đêm xuống.\n" +
                "\n" +
                "Nghỉ đêm tại khách sạn. ");
        scheduleInput1.setArrivalTime(Objects.requireNonNull(DateUtils.getStartDay(calendar.getTime())).getTime());
        scheduleInput1.setLeaveTime(Objects.requireNonNull(DateUtils.getEndDay(calendar.getTime())).getTime());
        scheduleInputs.add(scheduleInput1);
        ScheduleInput scheduleInput2 = new ScheduleInput();
        scheduleInput2.setTitle("NGÀY 02: BANGKOK - THÀNH PHỐ CỔ MUANG BORAN - PATTAYA (ĂN SÁNG/ TRƯA/ TỐI)");
        scheduleInput2.setNameLocation("THÀNH PHỐ CỔ MUANG BORAN");
        scheduleInput2.setLocationStop(new Address());
        scheduleInput2.setDescription(" Sáng: Quý khách được phục vụ điểm tâm với nhiều món ngon. Tiếp theo là trả phòng và di chuyển đến thành phố Pattaya. Tour du lịch Bangkok Pattaya 5 ngày 4 đêm vẫn tiếp tục.\n" +
                "\n" +
                "Bảo tàng mở Muang Boran: du khách có cơ hội nhìn tận mắt các công trình kiến trúc cổ xưa trên đất Thái tâm linh. Hầu hết chúng đều được phục dựng lại theo nguyên mẫu địa danh ban đầu, gồm cả các công trình đã hủy từ lâu.\n" +
                "\n" +
                "Trưa: Đoàn khách dùng bữa trưa tại nhà hàng địa phương. Sau đó khởi hành về thành phố biển Pattaya nổi tiếng.\n" +
                "\n" +
                "Chiều: Xe chở quý khách đi tham quan vào buổi chiều:\n" +
                "\n" +
                "Khao Phra Tamnak: nơi đậy tọa lạc trên khu cao nhất của Pattaya. Quý khách sẽ được chiêm ngưỡng bao quát cả thành phố lung linh, thơ mộng từ góc nhìn trên cao.\n" +
                "\n" +
                "Tối: Đoàn dùng bữa tối với các món BBQ hải sản, sau đó về khách sạn nhận phòng, nghỉ ngơi. Du khách cũng có thể đi chơi đêm, khám phá mọi ngóc ngách của thành phố sầm uất, đông vui hàng đầu của Thái Lan khi màn đêm buông xuống\n" +
                "\n" +
                "Nghỉ đêm tại Pattaya. ");
        calendar.add(Calendar.DATE, 1);
        scheduleInput2.setArrivalTime(Objects.requireNonNull(DateUtils.getStartDay(calendar.getTime())).getTime());
        scheduleInput2.setLeaveTime(Objects.requireNonNull(DateUtils.getEndDay(calendar.getTime())).getTime());
        scheduleInputs.add(scheduleInput2);
        ScheduleInput scheduleInput3 = new ScheduleInput();
        scheduleInput3.setTitle("NGÀY 03: PATTAYA - ĐẢO SAN HÔ - NONGNOOCH (ĂN SÁNG/ TRƯA/ TỐI)");
        scheduleInput3.setNameLocation("ĐẢO SAN HÔ - NONGNOOCH");
        scheduleInput3.setLocationStop(new Address());
        scheduleInput3.setDescription(" Sáng: Sau khi dùng xong điểm tâm sáng ở khách sạn, HDV đưa quý khách đến đảo san hô với tàu cao tốc.\n" +
                "\n" +
                "Đảo san hô là địa điểm du lịch được nhiều người yêu thích, có nước biển trong xanh và bờ cát trắng trải dài ngút ngàn. Đến đây, bên cạnh được tắm biển thỏa thích thì du khách còn được tham gia các trò chơi thú vị khác gồm dù kéo, mô tô nước, lặn biển,...\n" +
                "\n" +
                "Trưa: Quý khách được đưa về lại đất liền, trở lại khách sạn để vệ sinh cá nhân. Đoàn dùng bữa trưa xong nghỉ ngơi ngắn để nạp lại năng lượng.\n" +
                "\n" +
                "Chiều: Đoàn khởi hành đi tham quan các địa điểm kế tiếp trong tour Bangkok Pattaya 5 ngày 4 đêm:\n" +
                "\n" +
                "    Trung tâm World Gem: ở đây có vô vàn các loại đá quý khác nhau, do Hoàng Gia Thái Lan xây dựng và trông giữ cẩn thận.\n" +
                "    Trân Bảo Phật Sơn: du khách được tận mắt nhìn thấy tượng Phật to lớn được điêu khắc trên một ngọn núi đá vôi đồ sộ.\n" +
                "    Chợ nổi 4 miền: là một nét đặc sắc tại đất Thái, có nhiều món ăn đặc trưng đậm hương vị địa phương.\n" +
                "\n" +
                "Tối: Quý khách dùng bữa tối có thực đơn đa dạng. Tiếp đến, xe chở quý khách đi tận hưởng Massage Thái cổ truyền - một loại hình xoa bóp độc đáo. Phương pháp này giúp du khách đánh bay mệt mỏi sau một ngày đi chơi. Cuối cùng là trở về khách sạn nghỉ ngơi.\n" +
                "\n" +
                "Nghỉ đêm tại Pattaya. ");
        calendar.add(Calendar.DATE, 1);
        scheduleInput3.setArrivalTime(Objects.requireNonNull(DateUtils.getStartDay(calendar.getTime())).getTime());
        scheduleInput3.setLeaveTime(Objects.requireNonNull(DateUtils.getEndDay(calendar.getTime())).getTime());
        scheduleInputs.add(scheduleInput3);
        ScheduleInput scheduleInput4 = new ScheduleInput();
        scheduleInput4.setTitle("NGÀY 04: PATTAYA - BANGKOK (ĂN SÁNG/ TRƯA)");
        scheduleInput4.setNameLocation("PATTAYA - BANGKOK");
        scheduleInput4.setLocationStop(new Address());
        scheduleInput4.setDescription("  Sáng: Đoàn dùng bữa sáng tại khách sạn. Sau đó quý khách làm thủ tục trả phòng. Xe và HDV đưa đoàn khởi hành về thủ đô Bangkok. Trên đường đi, đoàn dừng chân tham quan:\n" +
                "\n" +
                "    Big Bee Farm – quý khách sẽ có cơ hội chiêm ngưỡng cách người Thái nuôi và thu hoạch mật ong, mua các sản phẩm bổ dưỡng từ ong.\n" +
                "    Trại rắn: xem màn biểu diễn rắn độc đáo, được thưởng thức và tìm hiểu quy trình lấy nọc rắn chế biến thành dược phẩm chữa bệnh.\n" +
                "\n" +
                "Trưa: Đoàn khách được xe đưa đến nhà hàng xoay Buffet quốc tế Baiyoke Sky để dùng bữa trưa. Tại đây, quý khách có thể thưởng thức hơn 100 món ăn Âu - Á thật đa dạng và thơm ngon. Đặc biệt đối với những du khách có nhu cầu ăn chay thì sẽ có phần đồ chay riêng, được chuẩn bị cẩn thận và chu đáo. Ngoài ra, du khách được phép chụp hình thoải mái tại đây, thu vào tầm mắt sự chằng chịt của mạng lưới giao thông Thái theo góc nhìn toàn cảnh từ phía trên.\n" +
                "\n" +
                "Chiều: Khoảng thời gian dùng bữa trưa kết thúc. Đoàn khách ghé tham quan đền Phật Bốn Mặt nằm ngay giữa lòng thủ đô, thắp hương và cúng bái cầu bình an.\n" +
                "\n" +
                "Kế tiếp, xe chở khách đi tham quan và mua sắm.\n" +
                "\n" +
                "Tối: Bữa tối ngày hôm nay sẽ do quý khách tự túc. Du khách có thể dùng bữa tại nhiều siêu thị như Platinum, Siam Paragon, Central World, Big C,...\n" +
                "\n" +
                "Nghỉ đêm tại Bangkok. ");
        calendar.add(Calendar.DATE, 1);
        scheduleInput4.setArrivalTime(Objects.requireNonNull(DateUtils.getStartDay(calendar.getTime())).getTime());
        scheduleInput4.setLeaveTime(Objects.requireNonNull(DateUtils.getEndDay(calendar.getTime())).getTime());
        scheduleInputs.add(scheduleInput4);
        ScheduleInput scheduleInput5 = new ScheduleInput();
        scheduleInput5.setTitle("NGÀY 05: DU NGOẠN BANGKOK - TPHCM (ĂN SÁNG/ TRƯA)");
        scheduleInput5.setNameLocation("DU NGOẠN BANGKOK - TPHCM");
        scheduleInput5.setLocationStop(new Address());
        scheduleInput5.setDescription(" Sáng: Khách sạn mang đến bữa sáng ngon miệng cho quý khách. Sau đó bắt đầu hành trình tour Thái Lan ngày thứ 5. Các địa danh tiếp theo trong lộ trình gồm:\n" +
                "\n" +
                "    Chùa Phật Vàng Wat Traimit là nơi không thể bỏ qua của nhiều du khách. Tại đây có tượng Phật linh thiêng nặng đến hàng tấn, được tạo ra hoàn toàn từ vàng nguyên khối, đặt giữa trung tâm Yaowarat. \n" +
                "    Ngắm cảnh bằng cách đi du ngoạn bằng thuyền trên dòng sông Chaophraya đẹp ngất ngây, cảm nhận được sự khác biệt giữa cái cũ và cái mới của Bangkok. \n" +
                "    Du khách ghé ngang qua chùa thuyền Wat Yannawa để thăm viếng. Quý khách được chiêm bái Xá Lợi Phật Thích Ca và Xá Lợi của các vị sư tu hành đắc đạo.\n" +
                "\n" +
                "Trưa: Xe và HDV đưa quý khách ra sân bay dùng bữa trưa. Sau đó, Trưởng đoàn làm thủ tục đáp chuyến bay VJ804 trở về TP.HCM cho quý khách. \n" +
                "\n" +
                "(Đối với các tour có chuyến bay về VJ806 lúc 19:55 hoặc QH326 lúc 16:55, HDV sẽ sắp xếp điểm tham quan và thời gian tham quan lại cho hợp lý)\n" +
                "\n" +
                "Chiều: Đến sân bay Tân Sơn Nhất, kết thúc chương trình tour du lịch Thái Lan Bangkok - Pattaya 5 ngày 4 đêm. Trưởng đoàn của Vietnam Booking chào tạm biệt. Hẹn gặp lại quý khách trong những tour du lịch kế tiếp!\n" +
                "\n" +
                "* LƯU Ý: Thứ tự và chi tiết trong chương trình có thể thay đổi cho phù hợp với tình hình khách quan nhưng vẫn đảm bảo đầy đủ các điểm tham quan! ");
        calendar.add(Calendar.DATE, 1);
        scheduleInput5.setArrivalTime(Objects.requireNonNull(DateUtils.getStartDay(calendar.getTime())).getTime());
        scheduleInput5.setLeaveTime(Objects.requireNonNull(DateUtils.getEndDay(calendar.getTime())).getTime());
        scheduleInputs.add(scheduleInput5);

        createTourInput.setScheduleInput(scheduleInputs);
        List<PricingValueInput> pricingValueInputs = new ArrayList<>();
        PricingValueInput pricingValueInput1 = new PricingValueInput();

        pricingValueInput1.setPrice(MoneyCalculateUtils.getMoney(10000000));
        pricingValueInput1.setAboutAgeType(AgeType.ADULTS);
        pricingValueInput1.setNote("Người lớn");
        pricingValueInput1.setSale(0.1);
        pricingValueInputs.add(pricingValueInput1);

        PricingValueInput pricingValueInput2 = new PricingValueInput();

        pricingValueInput2.setPrice(MoneyCalculateUtils.getMoney(9500000));
        pricingValueInput2.setAboutAgeType(AgeType.CHILDREN);
        pricingValueInput2.setNote("Trẻ em");
        pricingValueInput2.setSale(0.3);
        pricingValueInputs.add(pricingValueInput2);

        createTourInput.setPricingValues(pricingValueInputs);
        createTourController.createTour(createTourInput);
    }

    @Test
    public void testTour_8() throws ServiceException {
        CreateTourInput createTourInput = new CreateTourInput();
        TourInput tourInput = new TourInput();
        tourInput.setDescription("\n" +
                "    Khám phá những di tích của đất Hà Tiên như: Chùa Phù Dung, Lăng Mạc Cửu, Thạch Động Thôn Vân\n" +
                "    Hít thở bầu không khí trong lành của đảo Phú Quốc, chìm vào làn nước mát của khu du lịch Suối Tranh hay viếng ngôi chùa Hộ Quốc linh thiêng.\n" +
                "    Check-in tại cáp treo Hòn Thơm - cáp treo vượt biển dài nhất Thế Giới.\n" +
                "    Khám phá khu vui chơi Vinwonders xịn xò hay Grand World với khung cảnh hệt như trời Âu\n");
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
        tourInput.setFeatureImgUrl("https://www.vietnambooking.com/wp-content/uploads/2021/03/ha-tien-phu-quoc-3n3d-4-300x194.jpg");
        tourInput.setImgUrls(Arrays.asList(
                "https://www.vietnambooking.com/wp-content/uploads/2020/05/tour-ha-tien-phu-quoc-3-ngay-3-dem-8.jpg",
                "https://www.vietnambooking.com/wp-content/uploads/2021/03/ha-tien-phu-quoc-3n3d-3.jpg",
                "https://www.vietnambooking.com/wp-content/uploads/2021/03/ha-tien-phu-quoc-3n3d-2.jpg",
                "https://www.vietnambooking.com/wp-content/uploads/2021/03/tour-ha-tien-phu-quoc-1.jpg"));
        Address localAddress = new Address();
        localAddress.setAddress("Phường Vĩnh Thanh, Thành phố Rạch Giá, Kiên Giang, VietNam");
        localAddress.setProvince("Kiên Giang");
        localAddress.setProvinceCode(219);
        localAddress.setDistrict("Thành phố Rạch Giá");
        localAddress.setDistrictCode(1570);
        localAddress.setWard("Phường Vĩnh Thanh");
        localAddress.setWardCode(540109);
        localAddress.setCountryCode(CountryCode.VN);
        tourInput.setLocation(localAddress);
        tourInput.setName("Tour du lịch Hà Tiên – Phú Quốc 3 ngày 3 đêm | Khám phá Đảo Ngọc từ TP HCM");
        tourInput.setNote("Tour note");
        tourInput.setDiscount(DiscountUtils.discount());
        tourInput.setTimeHowLong(TimeHowLong.THREE_DAY_THREE_NIGHT);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.JUNE, 20); // Tháng bắt đầu từ 0
        tourInput.setStartDate(new Date(calendar.getTimeInMillis()));
        tourInput.setPriceStandard(MoneyCalculateUtils.getMoney(7000000));
        tourInput.setQuantityVisit(162);
        tourInput.setType(TourType.DOMESTIC);
        createTourInput.setTourInput(tourInput);

        VehicleInput vehicleInput = new VehicleInput();
        vehicleInput.setAutomaker("VietNam Ariline");
        vehicleInput.setName("Xe 40 chổ");
        vehicleInput.setVehicleType(VehicleType.CAR_40);
        vehicleInput.setNumberOfSeats(VehicleType.CAR_40.getNumberOfSeats());
        vehicleInput.setCode("51A-3233");
        createTourInput.setVehicleInput(vehicleInput);

        List<ScheduleInput> scheduleInputs = new ArrayList<>();
        ScheduleInput scheduleInput1 = new ScheduleInput();
        scheduleInput1.setTitle("ĐÊM 01 | THÀNH PHỐ HỒ CHÍ MINH – HÀ TIÊN (Nghỉ đêm trên xe) ");
        scheduleInput1.setNameLocation("THÀNH PHỐ HỒ CHÍ MINH – HÀ TIÊN");
        scheduleInput1.setLocationStop(localAddress);
        scheduleInput1.setDescription("Tối: Xe và HDV Vietnam Booking đón khách theo điểm hẹn đã quy định trước vào khoảng 21h30. Sau khi sắp xếp hành lý, đoàn bắt đầu hành trình tour Hà Tiên Phú Quốc 3N3Đ. Trên xe, quý khách sẽ nghỉ ngơi tự do trên xe. ");
        scheduleInput1.setArrivalTime(Objects.requireNonNull(DateUtils.getStartDay(calendar.getTime())).getTime());
        scheduleInput1.setLeaveTime(Objects.requireNonNull(DateUtils.getEndDay(calendar.getTime())).getTime());
        scheduleInputs.add(scheduleInput1);
        ScheduleInput scheduleInput2 = new ScheduleInput();
        scheduleInput2.setTitle("NGÀY 01 | HÀ TIÊN – GRAND WORLD PHÚ QUỐC – CHÙA PHÙ DUNG - LĂNG MẠC CỬU - THẠCH ĐỘNG THÔN VÂN - VINWONDERS (ĂN SÁNG, TRƯA, TỐI)");
        scheduleInput2.setNameLocation("HÀ TIÊN – GRAND WORLD PHÚ QUỐC");
        scheduleInput2.setLocationStop(new Address());
        scheduleInput2.setDescription(" Sáng: Xe đưa đoàn đến thành phố Hà Tiên thuộc tỉnh Kiên Giang. Đến đây, du khách sẽ được vệ sinh cá nhân, nghỉ ngơi, dùng bữa sáng trước khi bước vào hành trình tham quan các địa điểm của tour Hà Tiên - Phú Quốc 3N3Đ. Tiếp đó, du khách sẽ tham quan những địa danh sau: \n" +
                "\n" +
                "    Chùa Phù Dung: Ngôi chùa tọa lạc dưới chân núi Bình San của Hà Tiên. Phù Dung cổ tự không chỉ nổi tiếng với kiến trúc cổ của những ngôi chùa thời xưa. Ngôi chùa này còn thu hút nhiều du khách bởi câu chuyện về nguồn gốc ra đời vô cùng đặc sắc. \n" +
                "\n" +
                "    Lăng Mạc Cửu: Đây là nơi thờ phượng dòng họ Mạc - những người đầu tiên khai phá vùng đất Hà Tiên vào hơn 300 năm trước. \n" +
                "\n" +
                "    Thạch Động Thôn Vân: Nơi khởi nguồn của câu chuyện cổ tích Thạch Sanh Lý Thông - nơi người anh hùng giết đại bàng.\n" +
                "\n" +
                "Trưa: Đoàn dùng bữa trưa với những món đặc sản, menu đa dạng tại nhà hàng địa phương. Sau bữa trưa, đoàn sẽ được Hướng dẫn viên hướng dẫn làm thủ tục lên tàu vượt biển để đến đảo Phú Quốc. Trong khoảng thời gian trên tàu, du khách có thể tận hưởng khung cảnh biển xanh tuyệt đẹp và checkin những bức ảnh trên tàu với nền biển xanh. \n" +
                "\n" +
                "Sau khi tàu cập bến Phú Quốc, du khách sẽ lên xe đoàn đã chuẩn bị về khách sạn để làm thủ tục nhận phòng và nghỉ ngơi\n" +
                "\n" +
                "Chiều: Du khách có thể chọn 1 trong 2 sự lựa chọn:\n" +
                "\n" +
                "CHƯƠNG TRÌNH 1: KHÁM PHÁ GRAND WORLD PHÚ QUỐC (Miễn phí vé cổng)\n" +
                "\n" +
                "Du khách sẽ được tận hưởng không gian xinh đẹp như một quốc gia ở Châu Âu với những điểm nổi bật sau: \n" +
                "\n" +
                "    Tự do tham quan và khám phá Grand World. Du khách không nên bỏ qua việc chụp ảnh check in với những tòa nhà nhiều màu sắc tựa như các nước Bắc Âu.\n" +
                "\n" +
                "Hà Tiên - Phú Quốc 3N3Đ -Dạo thuyền ở Grand World\n" +
                "\n" +
                "Du khách check in một góc Grand World hệt như Châu Âu. \n" +
                "\n" +
                "    Tại đây du khách có thể trải nghiệm đi du thuyền trên dòng sông Venice thu nhỏ (Chi phí tự túc). \n" +
                "\n" +
                "    Tham quan và chụp ảnh check in ở phố đèn lồng Shanghai. Du khách sẽ có cảm giác như mình đang ở thành phố sầm uất bậc nhất Trung Quốc. \n" +
                "\n" +
                "    Khám phá khu mái vòm lấy cảm hứng từ Clarke Quay - Singapore. \n" +
                "\n" +
                "    Du khách còn có thể phá đảo khu phố đi bộ lấy cảm hứng từ Asiatique của Thái Lan\n" +
                "\n" +
                "Buổi tối: Quý khách lên xe về trung tâm thành phố và dùng bữa tối tại nhà hàng. Sau đó quý khách có thể tự do khám phá Phú Quốc về đêm. \n" +
                "\n" +
                "CHƯƠNG TRÌNH 2: PHÁ ĐẢO VINWONDERS PHÚ QUỐC (Chi phí tự túc)\n" +
                "\n" +
                "Có thể nói đây là một thiên đường dành cho bất kỳ du khách nào khi đến khám phá Phú Quốc. Bởi du khách có thể trải nghiệm vô vàn điều thú vị như chương trình biểu diễn nhạc nước, chương trình biểu diễn cá heo, chương trình hoạt náo đường phố vô cùng vui nhộn (Chi phí từ 750.000 đồng, bao gồm xe buýt trung chuyển)\n" +
                "\n" +
                "Buổi tối: Đoàn sẽ thưởng thức bữa tối tại nhà hàng, sau đó về lại khách sạn nghỉ ngơi. Du khách cũng có thể lựa chọn đi dạo biển hoặc khám phá Phú Quốc về đêm. Một gợi ý nhỏ để chuyến đi thêm phần tuyệt vời là du khách có thể dạo Chợ đêm Phú Quốc - nơi nổi tiếng là nhộn nhịp nhất huyện đảo. \n" +
                "\n" +
                "Lưu ý: Nếu du khách lựa chọn không đi tham quan thì có thể nghỉ ngơi tại khách sạn hoặc tự do khám phá Phú Quốc, đến giờ ăn tối xe sẽ đưa quý khách đến nhà hàng để dùng bữa. ");
        calendar.add(Calendar.DATE, 1);
        scheduleInput2.setArrivalTime(Objects.requireNonNull(DateUtils.getStartDay(calendar.getTime())).getTime());
        scheduleInput2.setLeaveTime(Objects.requireNonNull(DateUtils.getEndDay(calendar.getTime())).getTime());
        scheduleInputs.add(scheduleInput2);
        ScheduleInput scheduleInput3 = new ScheduleInput();
        scheduleInput3.setTitle("NGÀY 02 | THIỀN VIỆN TRÚC LÂM HỘ QUÓC - CÁP TREO HÒN THƠM - CÔNG VIÊN NƯỚC HÒN THƠM - NHÀ THÙNG NƯỚC MẮM - KHU NUÔI CẤY NGỌC TRAI - ỐC SÊN BAR ( ĂN SÁNG, TRƯA, CHIỀU)");
        scheduleInput3.setNameLocation("THIỀN VIỆN TRÚC LÂM HỘ QUÓC");
        scheduleInput3.setLocationStop(new Address());
        scheduleInput3.setDescription(" Sáng: Du khách dùng điểm tâm sáng và lên xe khởi hành đi khám phá khu vực phía Nam đảo Phú Quốc. \n" +
                "\n" +
                "    Thiền Viện Trúc Lâm Hộ Quốc: Nơi đây còn được biết đến là Chùa Hộ Quốc. Đến đây, du khách sẽ được chiêm ngưỡng ngôi chùa uy nghiêm mang đậm phong cách kiến trúc thời Lý Trần. Chùa có vị thế tựa núi hướng biển. Do vậy, ngoài việc cầu bình an, lễ Phật, du khách cũng sẽ có thể chụp ảnh check in với khung cảnh tuyệt đẹp này. \n" +
                "\n" +
                "    Nhà Thùng Nước Mắm Phú Quốc: Quý khách sẽ được tham quan nhà thùng - nơi sản xuất ra loại nước mắm truyền thống của nước ta. Sau khi tham quan, quý khách cũng có thể mua sản phẩm về dùng hoặc làm quà. \n" +
                "\n" +
                "    Khu Nuôi Cấy Ngọc Trai: Du khách đến đây sẽ được chứng kiến quy trình nuôi cấy và lấy ngọc trai chuyên nghiệp. \n" +
                "\n" +
                "Trưa: Đoàn dùng bữa trưa tại nhà hàng địa phương. Sau khi ăn, đoàn sẽ bắt đầu đi tham quan những địa điểm tiếp theo của hành trình tour Hà Tiên - Phú Quốc 3N3Đ. \n" +
                "\n" +
                "    Cáp treo Hòn Thơm: Đây là cáp treo vượt biển dài nhất thế giới nối từ Phú Quốc đến Hòn Thơm. Du khách có thể ngắm toàn cảnh biển Phú Quốc từ trên cao khi ngồi trên cabin. \n" +
                "\n" +
                "    Aquatopia Water Park: Đây còn được gọi là Công viên nước Hòn Thơm. Tại đây, du khách sẽ được trải nghiệm 20 trò chơi cảm giác mạnh dành cho cả trẻ em và người lớn. Không chỉ vậy, du khách còn có thể khám phá nhiều điều thú vị khác như chụp ảnh phong cách thổ dân, vui chơi tại khu dành cho trẻ em,... \n" +
                "\n" +
                "    Công viên chủ đề: Du khách cũng sẽ trải nghiệm được nhiều trò chơi cảm giác mạnh độc đáo như “Mộc Xà Thịnh Nộ”, “Mắt đại bàng”... \n" +
                "\n" +
                "Chiều: Sau khi khám phá Hòn Thơm, quý khách sẽ được đi cáp treo để về lại Phú Quốc. \n" +
                "\n" +
                "    Tận hưởng hoàng hôn trên biển tại Ốc Sên Bar. Tại đây du khách sẽ có thể vừa nhâm nhi một ly cocktail, vừa ngắm biển ráng hồng khi mặt trời lặn. \n" +
                "\n" +
                "Hà Tiên - Phú Quốc 3N3Đ - du khách check in ở Ốc Sên Bar\n" +
                "\n" +
                "Du khách không nên bỏ qua địa điểm ngắm hoàng hôn siêu đẹp ở Phú Quốc - Ốc Sên Bar. \n" +
                "\n" +
                "Tối: Sau khi ăn tối tại nhà hàng địa phương, du khách có thể vui chơi, mua quà kỷ niệm tại khu bar Phố Tây. ");
        calendar.add(Calendar.DATE, 1);
        scheduleInput3.setArrivalTime(Objects.requireNonNull(DateUtils.getStartDay(calendar.getTime())).getTime());
        scheduleInput3.setLeaveTime(Objects.requireNonNull(DateUtils.getEndDay(calendar.getTime())).getTime());
        scheduleInputs.add(scheduleInput3);
        ScheduleInput scheduleInput4 = new ScheduleInput();
        scheduleInput4.setTitle("NGÀY 03 | PHÚ QUỐC - TP HỒ CHÍ MINH (ĂN SÁNG, TRƯA)");
        scheduleInput4.setNameLocation("PHÚ QUỐC - TP HỒ CHÍ MINH");
        scheduleInput4.setLocationStop(new Address());
        scheduleInput4.setDescription(" Sáng: Đoàn dùng bữa sáng tại khách sạn. Sau đó, du khách làm thủ tục trả phòng khách sạn. Trước khi rời khỏi Phú Quốc, du khách sẽ tham quan khu du lịch Suối Tranh. Kế đó, đoàn chuyển ra bến tàu để về lại thành phố Hà Tiên. \n" +
                "\n" +
                "Hà Tiên - Phú Quốc 3N3Đ - Khu du lịch Suối Tranh\n" +
                "\n" +
                "Khu du lịch Suối Tranh là một trong những địa điểm check in nổi tiếng ở Phú Quốc. \n" +
                "\n" +
                "Trưa: Đoàn dùng cơm trưa tại nhà hàng địa phương. \n" +
                "\n" +
                "Chiều: Từ Hà Tiên, đoàn xuất phát quay về thành phố Hồ Chí Minh. Trên đường đi, du khách có thể mua thêm quà, bánh kẹo, đặc sản địa phương về làm quà cho người thân. Về đến Sài Gòn, HDV sẽ chia tay chào tạm biệt và hẹn gặp lại quý khách trong chuyến đi tới. Về đến TP. Hồ Chí Minh, xe đưa đoàn về điểm đón ban đầu, kết thúc Chương trình tour du lịch Hà Tiên - Phú Quốc 3N3Đ, HDV chào tạm biệt & hẹn gặp lại quý khách trong những tour du lịch giá rẻ tiếp theo. ");
        calendar.add(Calendar.DATE, 1);
        scheduleInput4.setArrivalTime(Objects.requireNonNull(DateUtils.getStartDay(calendar.getTime())).getTime());
        scheduleInput4.setLeaveTime(Objects.requireNonNull(DateUtils.getEndDay(calendar.getTime())).getTime());
        scheduleInputs.add(scheduleInput4);

        createTourInput.setScheduleInput(scheduleInputs);
        List<PricingValueInput> pricingValueInputs = new ArrayList<>();
        PricingValueInput pricingValueInput1 = new PricingValueInput();

        pricingValueInput1.setPrice(MoneyCalculateUtils.getMoney(7000000));
        pricingValueInput1.setAboutAgeType(AgeType.ADULTS);
        pricingValueInput1.setNote("Người lớn");
        pricingValueInput1.setSale(0.1);
        pricingValueInputs.add(pricingValueInput1);

        PricingValueInput pricingValueInput2 = new PricingValueInput();

        pricingValueInput2.setPrice(MoneyCalculateUtils.getMoney(6500000));
        pricingValueInput2.setAboutAgeType(AgeType.CHILDREN);
        pricingValueInput2.setNote("Trẻ em");
        pricingValueInput2.setSale(0.3);
        pricingValueInputs.add(pricingValueInput2);

        createTourInput.setPricingValues(pricingValueInputs);
        createTourController.createTour(createTourInput);
    }
    @Test
    public void testTour_9() throws ServiceException {
        CreateTourInput createTourInput = new CreateTourInput();
        TourInput tourInput = new TourInput();
        tourInput.setDescription("\n" +
                "    Check in Cổng trời Lempuyang đẹp nhất Bali.\n" +
                "    Khám phá những giá trị thiên nhiên kỳ vĩ: Đảo Nusa Penida, cánh đồng lúa Tegalalang,..\n" +
                "    Tham quan các công trình kiến trúc: Heaven’s Gate, Cung điện nước Tirta Gangga.\n" +
                "    Trải nghiệm bản sắc văn hóa địa phương: làng Ubud, Làng Celuk, Làng khắc gỗ của Mas.\n" +
                "    Tặng khăn quấn truyền thống Sarong Siêu dễ thương.\n" +
                "    Tận hưởng khách sạn 4* trung tâm Kuta.\u200B\n" +
                "\n" +
                "Nhanh tay book ngay Tour Bali 4 ngày 3 đêm từ Hà Nội trọn gói giá tốt qua hotline 1900 3398 của Vietnam Booking thôi nào! ");
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
        tourInput.setFeatureImgUrl("https://www.vietnambooking.com/wp-content/uploads/2022/10/tour-ba-li-4-ngay-3-dem-tu-ha-noi-4.jpg");
        tourInput.setImgUrls(Arrays.asList(
                "https://www.vietnambooking.com/wp-content/uploads/2022/10/tour-ba-li-4-ngay-3-dem-tu-ha-noi-9.jpg",
                "https://www.vietnambooking.com/wp-content/uploads/2022/10/tour-ba-li-4-ngay-3-dem-tu-ha-noi-5.jpg",
                "https://www.vietnambooking.com/wp-content/uploads/2022/10/tour-ba-li-4-ngay-3-dem-tu-ha-noi-6.jpg",
                "https://www.vietnambooking.com/wp-content/uploads/2022/10/tour-ba-li-4-ngay-3-dem-tu-ha-noi-8.jpg"));
        Address localAddress = new Address();
        localAddress.setAddress("Kebayoran Lama,South Jakarta, Jakarta, Indonesia");
        localAddress.setProvince("Jakarta");
        localAddress.setProvinceCode(1);
        localAddress.setDistrict("South Jakarta");
        localAddress.setDistrictCode(101);
        localAddress.setWard("Kebayoran Lama");
        localAddress.setWardCode(1001);
        localAddress.setCountryCode(CountryCode.ID);
        tourInput.setLocation(localAddress);
        tourInput.setName("Tour Bali 4 ngày 3 đêm từ Tp.HCM | Aloha Swing – Kelingking Beach – Đền Uluwatu");
        tourInput.setNote("Tour quốc tế");
        tourInput.setDiscount(DiscountUtils.discount());
        tourInput.setTimeHowLong(TimeHowLong.FOUR_DAY_THREE_NIGHT);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.JUNE, 20); // Tháng bắt đầu từ 0
        tourInput.setStartDate(new Date(calendar.getTimeInMillis()));
        tourInput.setPriceStandard(MoneyCalculateUtils.getMoney(6000000));
        tourInput.setQuantityVisit(162);
        tourInput.setType(TourType.ABROAD);
        createTourInput.setTourInput(tourInput);

        VehicleInput vehicleInput = new VehicleInput();
        vehicleInput.setAutomaker("VietNam Ariline");
        vehicleInput.setName("Máy Bay");
        vehicleInput.setVehicleType(VehicleType.PLANE);
        vehicleInput.setNumberOfSeats(VehicleType.PLANE.getNumberOfSeats());
        vehicleInput.setCode("EE1-3888");
        createTourInput.setVehicleInput(vehicleInput);

        List<ScheduleInput> scheduleInputs = new ArrayList<>();
        ScheduleInput scheduleInput1 = new ScheduleInput();
        scheduleInput1.setTitle("NGÀY 1: HÀ NỘI – BALI (ĂN TỐI)");
        scheduleInput1.setNameLocation("HÀ NỘI – BALI");
        scheduleInput1.setLocationStop(localAddress);
        scheduleInput1.setDescription(" Sáng: Xe và HDV đón đoàn tại điểm hẹn theo lịch trình và di chuyển đến sân bay Nội Bài. HDV hỗ trợ đoàn làm thủ tục xuất phát đi tour Bali 4 ngày 3 đêm từ Hà Nội.\n" +
                "\n" +
                "Chiều: Xe và HDV sẽ đón đoàn tại sân bay quốc tế Ngurah Rai – Bali. Quý khách sẽ được tặng vòng hoa chào mừng đoàn đến với Bali. Xe sẽ đưa đoàn về khách sạn nhận phòng.\n" +
                "\n" +
                "Tối: Đoàn dùng bữa tối tại nhà hàng địa phương. Buổi tối du khách tự do khám phá Bali về đêm và nghỉ đêm tại khách sạn. ");
        scheduleInput1.setArrivalTime(Objects.requireNonNull(DateUtils.getStartDay(calendar.getTime())).getTime());
        scheduleInput1.setLeaveTime(Objects.requireNonNull(DateUtils.getEndDay(calendar.getTime())).getTime());
        scheduleInputs.add(scheduleInput1);
        ScheduleInput scheduleInput2 = new ScheduleInput();
        scheduleInput2.setTitle("NGÀY 2: BALI SWING (ĂN SÁNG, TRƯA, TỐI)");
        scheduleInput2.setNameLocation("BALI SWING");
        scheduleInput2.setLocationStop(new Address());
        scheduleInput2.setDescription(" Sáng: Đoàn dùng bữa sáng tại khách sạn và làm thủ tục trả phòng. Sau đó, du khách sẽ được thả mình trong làn nước mát tại tại bãi biển Yasa bengiat beach Nusa Dua. Bãi biển được biết đến là một trong những bãi biển đẹp nhất trên thế giới. Du khách có thể tự do vui chơi các hoạt động trên biển như: lướt ván, nhảy dù,.. (chi phí tự túc).\n" +
                "\n" +
                "Trưa: Đoàn dùng bữa trưa tại nhà hàng địa phương. Đặc biệt trong buổi trưa, du khách có thể vừa thưởng thức hương vị đặc trưng vừa nhìn ngắm khung cảnh núi lửa hùng vĩ.\n" +
                "\n" +
                "Chiều: Tiếp tục hành trình, xe và HDV đưa đoàn đến tham quan những địa điểm nổi tiếng trứ danh trong tour Bali 4 ngày 3 đêm từ Hà Nội:\n" +
                "\n" +
                "    Cánh đồng lúa Tegalalang: Du khách sẽ được chiêm ngưỡng sắc xanh từ những cánh đồng xanh mát bên sườn núi. Nơi đây như một bức tranh thiên nhiên tuyệt phẩm với điểm nhấn là những thửa ruộng bậc thang thoải dần trên sườn núi.\n" +
                "    Aloha Swing: Quý khách sẽ được “sống ảo thả ga” với muôn vàn địa điểm để đời. Không giống như những Swing khác, tại đây có tận 5 loại Swing khác nhau có du khách tham quan và trải nghiệm: những chiếc lồng chim treo lơ lửng giữa thiên nhiên hùng vĩ hay ấm trà bằng gỗ, mũi thuyền, nhà trên cây (chi phí tự túc).\n" +
                "    Ngôi làng Ubud với làng nghề Batik: Ngôi làng nổi tiếng với làng nghề Batik – nghề làm vải thủ công từ việc dùng sáp ong nóng chảy vẽ lên vải để tạo họa tiết. Tại đây, du khách sẽ được khám phá “kho báu văn hóa” của Bali qua lối kiến trúc và các thửa ruộng bậc thang tuyệt đẹp.\n" +
                "    Làng Celuk: Làng còn được gọi là làng Trang Sức khi đây là nơi thường xuyên diễn ra các chương trình triển lãm về vàng và bạc được chế biến tinh xảo.\n" +
                "    Làng khắc gỗ của Mas: Quý khách sẽ được tận mắt thấy được sự khéo léo của những người dân Bali khi tạo ra những tác phẩm điêu khắc gỗ mang tính nghệ thuật cao.\n" +
                "\n" +
                "Chương trình 2: Đảo Nusa Penida (Ăn sáng, trưa, tối)\n" +
                "\n" +
                "Sáng: Đoàn dùng bữa sáng tại khách sạn. Xe và HDV sẽ đưa đoàn di chuyển đến bến tàu tại cảng Sanur và đi tàu cao tốc ra đảo Nusa Penida. Đến nơi, đoàn tiếp tục hành trình tham quan trong tour Bali 4 ngày 3 đêm từ Hà Nội:\n" +
                "\n" +
                "    Crystal Bay: Đây chính là một địa điểm giúp du khách quên đi những âu lo bằng việc vui chơi các hoạt động biển: bơi, lặn,..\n" +
                "    Kelingking Beach: Sống lưng khủng long nổi tiếng trong giới đam mê du lịch. Đến đây, du khách sẽ được check in với biểu tượng của Nusa Penida nói riêng và Bali nói chung.\n" +
                "    Angel Billabong: Được xem như “bể bơi vô cực” giữa Bali, du khách sẽ tha hồ “sống ảo” với muôn góc nhìn tựa như thiên đường với biển cả bao la và vị trí độc lạ mà nó tạo thành.\n" +
                "\n" +
                "Trưa: Đoàn dùng bữa trưa buffet tại nhà hàng địa phương, nghỉ ngơi và tự do tắm biển. Sau đó Quý khách di chuyển lên tàu cao tốc trở về.\n" +
                "\n" +
                "Tối: Đoàn dùng bữa tối tại nhà hàng và nghỉ đêm tại khách sạn. ");
        calendar.add(Calendar.DATE, 1);
        scheduleInput2.setArrivalTime(Objects.requireNonNull(DateUtils.getStartDay(calendar.getTime())).getTime());
        scheduleInput2.setLeaveTime(Objects.requireNonNull(DateUtils.getEndDay(calendar.getTime())).getTime());
        scheduleInputs.add(scheduleInput2);
        ScheduleInput scheduleInput3 = new ScheduleInput();
        scheduleInput3.setTitle("NGÀY 3: LEMPUYANG – TIRTA GANGGA  – JIMBARAN (ĂN SÁNG, TRƯA, TỐI)");
        scheduleInput3.setNameLocation("LEMPUYANG – TIRTA GANGGA  – JIMBARAN");
        scheduleInput3.setLocationStop(new Address());
        scheduleInput3.setDescription(" Sáng: Đoàn dùng bữa sáng tại khách sạn. Xe và HDV sẽ đón đoàn và bắt đầu hành trình bằng việc di chuyển đến Cổng trời – Heaven’s Gate. Tại đây, du khách sẽ được viếng thăm ngôi đền cổ nhất với lối thiết kế đặc trưng nổi bật rất riêng. Ngoài ra, ngôi đền còn sở hữu “Cánh cổng tới thiên đường” nổi tiếng nhất Bali, nơi mà ai cũng muốn có cho mình một bức ảnh tại nơi này.\n" +
                "\n" +
                "Trưa: Du khách sẽ dùng bữa tối tại nhà hàng địa phương với món Vịt Chiên Giòn Truyền Thống nổi tiếng khi du lịch Indonesia.\n" +
                "\n" +
                "Chiều: Tiếp tục hành trình, đoàn sẽ di chuyển đến tham quan Cung điện nước Tirta Gangga. Du khách sẽ được tận mắt chiêm ngưỡng những nét đẹp kiến trúc nguy nga và các câu chuyện ý nghĩa tâm linh với người dân Bali.\n" +
                "\n" +
                "Tối: Xe và HDV đưa Quý khách ghé thăm bãi biển Jimbaran và chiêm ngưỡng cảnh sắc hoàng hôn khi buông xuống. Sau đó Quý khách trở về khách sạn nghỉ ngơi. ");
        calendar.add(Calendar.DATE, 1);
        scheduleInput3.setArrivalTime(Objects.requireNonNull(DateUtils.getStartDay(calendar.getTime())).getTime());
        scheduleInput3.setLeaveTime(Objects.requireNonNull(DateUtils.getEndDay(calendar.getTime())).getTime());
        scheduleInputs.add(scheduleInput3);
        ScheduleInput scheduleInput4 = new ScheduleInput();
        scheduleInput4.setTitle("NGÀY 4: YASA BENGIAT BEACH – BALI – HÀ NỘI (ĂN SÁNG, TRƯA)");
        scheduleInput4.setNameLocation("YASA BENGIAT BEACH – BALI – HÀ NỘI");
        scheduleInput4.setLocationStop(new Address());
        scheduleInput4.setDescription(" Sáng: Quý khách dùng bữa sáng tại khách sạn và làm thủ tục trả phòng. Sau đó đoàn tham quan điểm đến cuối trong lịch trình tour Bali 4 ngày 3 đêm từ Hà Nội:\n" +
                "\n" +
                "    Đền Uluwatu: Một trong những ngôi đền cổ xưa nhất tại Bali. Ngôi đền sở hữu phong cảnh hùng vĩ khi có vị trí trên vách núi gồ ghề. Du khách sẽ được khám phá muôn vàn câu chuyện bí ẩn ly kỳ của vùng đất Bali.\n" +
                "\n" +
                "Trưa: Dùng bữa trưa tại nhà hàng địa phương và tự do mua sắm trước khi ra sân bay. Đến giờ hẹn, xe và HDV đón đoàn di chuyển ra sân bay làm thủ tục trở về Hà Nội.\n" +
                "\n" +
                "Tối: Đến sân bay Nội Bài, xe đón đoàn về điểm hẹn ban đầu. Kết thúc chuyến đi, Trưởng Đoàn của Vietnam Booking chào tạm biệt Quý khách và hẹn gặp lại trong những tour Bali 4 ngày 3 đêm từ Hà Nội hấp dẫn khác.\n" +
                "\n" +
                "Lưu ý: Các điểm tham quan có thể được thay đổi để phù hợp với tình hình thực tế nhưng vẫn đảm bảo đủ điểm tham quan theo chương trình tour du lịch Bali đã đưa ra. ");
        calendar.add(Calendar.DATE, 1);
        scheduleInput4.setArrivalTime(Objects.requireNonNull(DateUtils.getStartDay(calendar.getTime())).getTime());
        scheduleInput4.setLeaveTime(Objects.requireNonNull(DateUtils.getEndDay(calendar.getTime())).getTime());
        scheduleInputs.add(scheduleInput4);

        createTourInput.setScheduleInput(scheduleInputs);
        List<PricingValueInput> pricingValueInputs = new ArrayList<>();
        PricingValueInput pricingValueInput1 = new PricingValueInput();

        pricingValueInput1.setPrice(MoneyCalculateUtils.getMoney(6000000));
        pricingValueInput1.setAboutAgeType(AgeType.ADULTS);
        pricingValueInput1.setNote("Người lớn");
        pricingValueInput1.setSale(0.1);
        pricingValueInputs.add(pricingValueInput1);

        PricingValueInput pricingValueInput2 = new PricingValueInput();

        pricingValueInput2.setPrice(MoneyCalculateUtils.getMoney(4500000));
        pricingValueInput2.setAboutAgeType(AgeType.CHILDREN);
        pricingValueInput2.setNote("Trẻ em");
        pricingValueInput2.setSale(0.3);
        pricingValueInputs.add(pricingValueInput2);

        createTourInput.setPricingValues(pricingValueInputs);
        createTourController.createTour(createTourInput);
    }
    @Test
    public void testTour_10() throws ServiceException {
        CreateTourInput createTourInput = new CreateTourInput();
        TourInput tourInput = new TourInput();
        tourInput.setDescription(
                "\n" +
                        "    Tham quan Cổ Thạch\n" +
                        "    Cắm Trại\n" +
                        "    Trải nghiệm chèo thuyền Sup\n" +
                        "    Khám phá vịnh Vĩnh Hy\n" +
                        "    Đi tàu đáy kính\n" +
                        "    Ngắm San Hô\n" +
                        "    Tham quan vườn nho\n" +
                        "    Tham quan làng Gốm của người Chăm\n" +
                        "    Tham quan làng dệt thổ cẩm Mỹ Nghiệp Ninh Thuận của dân tộc Chăm\n" +
                        "    Vui chơi team building hấp dẫn\n" +
                        "    Tham gia đốt lửa Trại\n" +
                        "\n" +
                        "Du khách sẽ vừa được khám phá vùng biển hoang sơ với tour Ninh Chữ - Vĩnh Hy 3N2D vừa được vui chơi team building lửa trại, " +
                        "liên hệ ngay Hotline Vietnam Booking 1900 3398 hoặc 090.4423.190 (Mr. Đông - tư vấn viên Tour Đoàn) để được tư vấn đặt tour ngay với giá ưu đãi nhất.");
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
        tourInput.setFeatureImgUrl("https://www.vietnambooking.com/wp-content/uploads/2023/01/vinh-hy-3n2d-lua-trai-10-300x194.jpg");
        tourInput.setImgUrls(Arrays.asList(
                "https://www.vietnambooking.com/wp-content/uploads/2023/01/vinh-hy-3n2d-lua-trai-3.jpg",
                "https://www.vietnambooking.com/wp-content/uploads/2023/01/vinh-hy-3n2d-lua-trai-2.jpg",
                "https://www.vietnambooking.com/wp-content/uploads/2023/01/vinh-hy-3n2d-lua-trai-4.jpg",
                "https://www.vietnambooking.com/wp-content/uploads/2023/01/vinh-hy-3n2d-lua-trai-9.jpg"));
        Address localAddress = new Address();
        localAddress.setAddress("huyện Ninh Hải, tỉnh Ninh Thuận, Việt Nam");
        localAddress.setProvince("Ninh Thuận");
        localAddress.setProvinceCode(261);
        localAddress.setDistrict("Ninh Hải");
        localAddress.setDistrictCode(1985);
        localAddress.setWard("Xã Xuân Hải");
        localAddress.setWardCode(450309);
        localAddress.setCountryCode(CountryCode.VN);
        tourInput.setLocation(localAddress);
        tourInput.setName("Tour Ninh Chữ – Vĩnh Hy 3N2D lửa trại: Chèo thuyền Sup – Đi tàu đáy kính ngắm san hô");
        tourInput.setNote("Tour note");
        tourInput.setDiscount(DiscountUtils.discount());
        tourInput.setTimeHowLong(TimeHowLong.FIVE_DAY_FOUR_NIGHT);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.JUNE, 23); // Tháng bắt đầu từ 0
        tourInput.setStartDate(new Date(calendar.getTimeInMillis()));
        tourInput.setPriceStandard(MoneyCalculateUtils.getMoney(3000000));
        tourInput.setQuantityVisit(40);
        tourInput.setType(TourType.DOMESTIC);
        createTourInput.setTourInput(tourInput);

        VehicleInput vehicleInput = new VehicleInput();
        vehicleInput.setAutomaker("Xe Tuấn Hưng");
        vehicleInput.setName("Xe Tuấn Hưng");
        vehicleInput.setVehicleType(VehicleType.CAR_40);
        vehicleInput.setNumberOfSeats(VehicleType.CAR_40.getNumberOfSeats());
        vehicleInput.setCode("51A-21322");
        createTourInput.setVehicleInput(vehicleInput);

        List<ScheduleInput> scheduleInputs = new ArrayList<>();
        ScheduleInput scheduleInput1 = new ScheduleInput();
        scheduleInput1.setTitle("Ngày 01 | SÀI GÒN - CỔ THẠCH - CHÙA HANG - NINH CHỮ (ĂN SÁNG, TRƯA, TỐI)");
        scheduleInput1.setNameLocation("SÀI GÒN - CỔ THẠCH - CHÙA HANG - NINH CHỮ");
        scheduleInput1.setLocationStop(localAddress);
        scheduleInput1.setDescription(" Sáng: Xe và hướng dẫn viên Vietnam Booking sẽ đón quý khách tại địa điểm đã hẹn trước xuất phát đi tour Vĩnh Hy 3N2D lửa trại. \n" +
                "\n" +
                "Đoàn sẽ ăn sáng tại Nhà Hàng Cao Phát. Sau khi dùng bữa sáng, quý khách tiếp tục lộ trình đi Cổ Thạch, trên xe đoàn tham gia các trò chơi vui nhộn, đố vui có thưởng, nhận quà lì xì đầu năm,…\n" +
                "\n" +
                "Trưa: Đoàn sẽ dùng bữa trưa tại Mũi Né. Sau bữa trưa tiếp tục di chuyển đến Cổ Thạch tham quan:\n" +
                "\n" +
                "    Chùa Hang (Cổ Thạch tự). Ngôi chùa nổi tiếng với tuổi đời hơn 100 năm nay, đây là ngôi chùa lớn nhất khu vực. Chùa tọa lạc trên đỉnh một ngọn đồi và được xây xen kẽ trong các hang đá tự nhiên, đứng trên đây quý khách có thể quan sát được toàn bộ khu vực Cổ Thạch.\n" +
                "    Sau khi dùng bữa trưa quý đoàn tiếp tục hành trình đi Ninh Chữ.\n" +
                "\n" +
                "Vĩnh Hy 3N3D lửa trại - Chùa Hang\n" +
                "\n" +
                " Chùa Hang nổi tiếng với phong cách kiến trúc độc đáo.\n" +
                "\n" +
                "Chiều: Đoàn đến Ninh Chữ. Quý khách cùng nhau tham gia dựng trại, đốt lửa trại và chuẩn bị thịt, hải sản nướng cho các hoạt động vui chơi bữa tối. \n" +
                "\n" +
                "    Sau đó khách cất đồ vào lều và tắm biển, chụp hình và chèo sup.\n" +
                "    Thưởng thức bữa tối BBQ hải sản tại nơi cắm trại bao gồm: Gà nướng, ghẹ hấp, tôm nướng, ốc hoặc hào, nhum biển nướng, cá nướng, sườn nướng, bắp và khoai nướng, thịt luộc mắm nêm và trái cây.\n" +
                "\n" +
                "Tối: Đoàn quây quần bên lửa trại, vui hết cỡ hát hò tham gia các trò chơi. Sau đó quý khách về lều nghỉ ngơi để sáng ngắm bình minh.   ");
        scheduleInput1.setArrivalTime(Objects.requireNonNull(DateUtils.getStartDay(calendar.getTime())).getTime());
        scheduleInput1.setLeaveTime(Objects.requireNonNull(DateUtils.getEndDay(calendar.getTime())).getTime());
        scheduleInputs.add(scheduleInput1);
        ScheduleInput scheduleInput2 = new ScheduleInput();
        scheduleInput2.setTitle("Ngày 02 | NINH CHỮ - VỊNH VĨNH HY - VƯỜN NHO (ĂN SÁNG, TRƯA, TỐI)");
        scheduleInput2.setNameLocation("NINH CHỮ - VỊNH VĨNH HY - VƯỜN NHO");
        scheduleInput2.setLocationStop(new Address());
        scheduleInput2.setDescription(" Sáng: Sau khi dùng bữa sáng, xe đưa quý khách đi vịnh Vĩnh Hy, tới bến tàu bắt đầu cuộc hành trình khám phá thiên nhiên của chuyến du lịch Ninh Thuận. \n" +
                "\n" +
                "    Du khách sẽ tham quan đầm Đăng, bãi Cóc, bãi Thùng, bãi Vách Đá. Quý khách lên tàu tới bãi Bà Điên tắm biển và lặn ngắm san hô (Chi phí thuê bình Oxy lặn ngắm san hô tự túc).\n" +
                "    Sau đó, quý khách tự do thưởng thức hải sản tại bè.\n" +
                "\n" +
                "Trưa: Đoàn về lại Vĩnh Hy dùng bữa trưa. Sau bữa trưa đoàn khởi hành về lại Resort. Trên đường về, đoàn ghé vườn Nho - Một vườn nho nổi tiếng nhất vùng Phan Rang. Tại đây quý khách được tìm hiểu về quy trình chăm sóc nho và thưởng thức thành phẩm tại chỗ.\n" +
                "\n" +
                "Vĩnh Hy 3N3D lửa trại - Vườn nho Ninh Thuận\n" +
                "\n" +
                "Du khách check in tại một vườn nho ở Ninh Thuận. \n" +
                "\n" +
                "Chiều: Qúy khách tập trung tại bãi biển để tham gia chương trình team Building.\n" +
                "\n" +
                "Tối: Quý khách tập trung ra xe để đi dùng cơm tối. ");
        calendar.add(Calendar.DATE, 1);
        scheduleInput2.setArrivalTime(Objects.requireNonNull(DateUtils.getStartDay(calendar.getTime())).getTime());
        scheduleInput2.setLeaveTime(Objects.requireNonNull(DateUtils.getEndDay(calendar.getTime())).getTime());
        scheduleInputs.add(scheduleInput2);
        ScheduleInput scheduleInput3 = new ScheduleInput();
        scheduleInput3.setTitle("Ngày 03 | NINH CHỮ - TP. HỒ CHÍ MINH (ĂN SÁNG, TRƯA)");
        scheduleInput3.setNameLocation("NINH CHỮ - TP. HỒ CHÍ MINH");
        scheduleInput3.setLocationStop(new Address());
        scheduleInput3.setDescription(" Sáng: Đoàn dùng điểm tâm sáng tại resort, sau đó đi tham quan các địa điểm cuối cùng của tour Vĩnh Hy 3N3D lửa trại:\n" +
                "\n" +
                "    Tham quan Làng Dệt Mỹ Nghiệp - Làng nghề dệt thổ cẩm của người Chăm tại Ninh Thuận. \n" +
                "    Làng gốm Bàu Trúc tìm hiểu về một trong những làng nghề lâu đời nhất tại Việt Nam. \n" +
                "\n" +
                "Vĩnh Hy 3N3D lửa trại - Làng gốm Bàu Trúc\n" +
                "\n" +
                "Bàu Trúc là làng gốm duy nhất còn làm thủ công. \n" +
                "\n" +
                "Sau đó đoàn về trả phòng resort, sau đó đoàn ghé tham quan mua sắm đặc sản mứt nho, rượu nho tại Ninh Thuận.\n" +
                "\n" +
                "Trưa:  Quý khách sẽ dùng cơm trưa tại nhà hàng Mũi Né. Tiếp tục về lại Tp. Hồ Chí Minh, trên đường ghé thăm mua sắm đặc sản tại Bình Thuận.\n" +
                "\n" +
                "Chiều: Đoàn về đến điểm đón khách ban đầu, kết thúc chuyến đi Vĩnh Hy 3N2D lửa trại. Hướng dẫn viên Vietnam Booking nói lời cảm ơn, chúc sức khỏe và chào tạm biệt và hẹn gặp quý khách trong những chuyến đi sau. ");
        calendar.add(Calendar.DATE, 1);
        scheduleInput3.setArrivalTime(Objects.requireNonNull(DateUtils.getStartDay(calendar.getTime())).getTime());
        scheduleInput3.setLeaveTime(Objects.requireNonNull(DateUtils.getEndDay(calendar.getTime())).getTime());
        scheduleInputs.add(scheduleInput3);
        createTourInput.setScheduleInput(scheduleInputs);
        List<PricingValueInput> pricingValueInputs = new ArrayList<>();
        PricingValueInput pricingValueInput1 = new PricingValueInput();

        pricingValueInput1.setPrice(MoneyCalculateUtils.getMoney(3000000));
        pricingValueInput1.setAboutAgeType(AgeType.ADULTS);
        pricingValueInput1.setNote("Người lớn");
        pricingValueInput1.setSale(0.1);
        pricingValueInputs.add(pricingValueInput1);

        PricingValueInput pricingValueInput2 = new PricingValueInput();

        pricingValueInput2.setPrice(MoneyCalculateUtils.getMoney(2500000));
        pricingValueInput2.setAboutAgeType(AgeType.CHILDREN);
        pricingValueInput2.setNote("Trẻ em");
        pricingValueInput2.setSale(0.3);
        pricingValueInputs.add(pricingValueInput2);

        createTourInput.setPricingValues(pricingValueInputs);
        createTourController.createTour(createTourInput);
    }
    @Test
    public void testTour_11() throws ServiceException {
        CreateTourInput createTourInput = new CreateTourInput();
        TourInput tourInput = new TourInput();
        tourInput.setDescription(
                "our Đà Nẵng Sapa 4 Ngày 3 đêm của Vietnam Booking đưa quý khách đi khám phá những cung đường ôm trọn vách đá, những thửa ruộng bậc thang tầng tầng trong màn sương và vẻ đẹp non nước hữu tình của đất Tràng An.\n" +
                        "\n" +
                        "TOUR CÓ GÌ HOT:\n" +
                        "\n" +
                        "    Khám phá thị trấn trên mây Sapa: Bản Cát Cát, Thác Bạc, Thác Thủy Điện, Nhà Thờ Đá...\n" +
                        "    Cơ hội chinh phục đèo Ô Quy Hồ, Cầu Kính Rồng Mây,...\n" +
                        "    Thăm thú chợ Cốc Lếu tại cửa khẩu Lào Cai\n" +
                        "    Tham quan Nhinh Bình: Tràng An - Ba Hang - Bái Đính\n" +
                        "    Ngồi đò dọc sông qua những cánh đồng\n" +
                        "\n" +
                        "Và không thể bỏ qua những món ăn thơm ngon cùng nét văn hóa đặc trưng của hai nơi này. Nhanh tay đăng ký mua tour ngay hôm nay để nhận ưu đãi HOT nhất nhé! ");
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
        tourInput.setFeatureImgUrl("https://www.vietnambooking.com/wp-content/uploads/2018/01/tour-sapa-4n3d-13-1-2018-2-300x200.jpg");
        tourInput.setImgUrls(Arrays.asList(
                "https://www.vietnambooking.com/wp-content/uploads/2019/10/tour-da-nang-sapa-4-ngay-3-dem-2.png",
                "https://www.vietnambooking.com/wp-content/uploads/2019/10/tour-da-nang-sapa-4-ngay-3-dem-1.jpg",
                "https://www.vietnambooking.com/wp-content/uploads/2019/10/tour-da-nang-sapa-4-ngay-3-dem-3.png",
                "https://www.vietnambooking.com/wp-content/uploads/2019/10/tour-da-nang-sapa-4-ngay-3-dem-4.jpg"));
        Address localAddress = new Address();
        localAddress.setAddress("Thị trấn Sa Pa, huyện Sa Pa, tỉnh Lào Cai, Việt Nam");
        localAddress.setProvince("Lào Cai");
        localAddress.setProvinceCode(269);
        localAddress.setDistrict("huyện Sa Pa");
        localAddress.setDistrictCode(2005);
        localAddress.setWard("Thị trấn Sa Pa");
        localAddress.setWardCode(80501);
        localAddress.setCountryCode(CountryCode.VN);
        tourInput.setLocation(localAddress);
        tourInput.setName("Tour du lịch Đà Nẵng – Hà Nội – Sapa – Ninh Bình 4N3Đ | Bao gồm vé máy bay khứ hồi");
        tourInput.setNote("Tour note");
        tourInput.setDiscount(DiscountUtils.discount());
        tourInput.setTimeHowLong(TimeHowLong.FOUR_DAY_THREE_NIGHT);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.JUNE, 27); // Tháng bắt đầu từ 0
        tourInput.setStartDate(new Date(calendar.getTimeInMillis()));
        tourInput.setPriceStandard(MoneyCalculateUtils.getMoney(4700000));
        tourInput.setQuantityVisit(32);
        tourInput.setType(TourType.DOMESTIC);
        createTourInput.setTourInput(tourInput);

        VehicleInput vehicleInput = new VehicleInput();
        vehicleInput.setAutomaker("Xe Phương Trang");
        vehicleInput.setName("Xe Phương Trang");
        vehicleInput.setVehicleType(VehicleType.CAR_35);
        vehicleInput.setNumberOfSeats(VehicleType.CAR_35.getNumberOfSeats());
        vehicleInput.setCode("11A-03322");
        createTourInput.setVehicleInput(vehicleInput);

        List<ScheduleInput> scheduleInputs = new ArrayList<>();
        ScheduleInput scheduleInput1 = new ScheduleInput();
        scheduleInput1.setTitle("NGÀY 1 | ĐÀ NẴNG – HÀ NỘI – NINH BÌNH (Ăn Trưa, Tối)");
        scheduleInput1.setNameLocation("ĐÀ NẴNG – HÀ NỘI – NINH BÌNH");
        scheduleInput1.setLocationStop(localAddress);
        scheduleInput1.setDescription("  Sáng: Theo lịch trình hẹn trước của Vietnam Booking HDV đón quý khách tại sân bay Đà Nẵng để làm thủ tục đáp chuyến bay đi Hà Nội, bắt đầu hành trình khám phá Tour đi Đà Nẵng Sapa 4 Ngày 3 Đêm. \n" +
                "\n" +
                "Đến Nội Bài, xe công ty sẽ đón và đưa đoàn khởi hành đi Ninh Bình.\n" +
                "\n" +
                "Trưa: Đến nơi, đoàn dùng bữa trưa tại Ninh Bình.\n" +
                "\n" +
                "Chiều: Sau đó, đoàn bắt đầu di chuyển ra bến đò Tràng An. Tại đây, đoàn ngồi thuyền du ngoạn trên dòng nước xanh thẫm dọc theo cánh đồng lúa và ngắm nhìn  những dải đá vôi, thung lũng huyền bí. Đoàn sẽ tiến vào trong Hang Sáng, Hang Tối, Hang Nấu Rượu để tham quan. \n" +
                "\n" +
                "Tiếp đến, đoàn viếng thăm ngôi chùa Bái Đính nổi tiếng với khuôn viên rộng đến 107ha. Quý khách sẽ được\n" +
                "\n" +
                "    Thăm Điện thờ Tam Thế và Pháp Chủ\n" +
                "    Chiêm bái tượng Phật Tổ Như Lai bằng đồng lớn nhất Đông Nam Á (nặng 100 tấn)\n" +
                "    Tận mắt chứng kiến ba pho tượng Tam Thế nặng 50 tấn. (xe điện tự túc)\n" +
                "\n" +
                "Sau buổi tham quan, đoàn lên xe về khách sạn nhận phòng nghỉ ngơi.\n" +
                "\n" +
                "Tối: Quý khác dùng cơm tối tại nhà hàng, nghỉ đêm tại Ninh Bình.  ");
        scheduleInput1.setArrivalTime(Objects.requireNonNull(DateUtils.getStartDay(calendar.getTime())).getTime());
        scheduleInput1.setLeaveTime(Objects.requireNonNull(DateUtils.getEndDay(calendar.getTime())).getTime());
        scheduleInputs.add(scheduleInput1);
        ScheduleInput scheduleInput2 = new ScheduleInput();
        scheduleInput2.setTitle("NGÀY 02 | KHÁM PHÁ SAPA – BẢN CÁT CÁT – THÁC THỦY ĐIỆN (Ăn Sáng, Trưa, Tối)");
        scheduleInput2.setNameLocation("KHÁM PHÁ SAPA – BẢN CÁT CÁT – THÁC THỦY ĐIỆN");
        scheduleInput2.setLocationStop(new Address());
        scheduleInput2.setDescription(" Sáng: Đoàn dùng điểm tâm và trả phòng khách sạn. Sau đó lên đường đi Sapa trên cung đường. Quý khách chiêm ngưỡng dãy Hoàng Liên Sơn trên đường đi.\n" +
                "\n" +
                "Trưa: Đoàn dùng bữa trưa và nghỉ ngơi nhẹ tại Lào Cai.\n" +
                "\n" +
                "Chiều: Đến Sapa, HDV đưa đoàn đi thăm Bản Cát Cát, nơi được mệnh danh là \"ngôi làng cổ đẹp nhất núi rừng Tây Bắc\". Tại đây, đoàn tham quan:\n" +
                "\n" +
                "    Tìm hiểu đời sống sinh hoạt của các đồng bào dân tộc H'mông Đen\n" +
                "    Thăm thác Cát Cát thơ mộng, hùng vĩ\n" +
                "    Vui chơi, chụp hình lưu niệm tại Nhà Máy Thủy Điện cũ\n" +
                "\n" +
                "Sau đó, quý khách sẽ lên xe đến khách sạn nhận phòng và nghỉ ngơi tự do.\n" +
                "\n" +
                "Tối: Quý khách dùng bữa tối. \n" +
                "\n" +
                "Sau đó, đoàn có thể tự do tham quan, khám phá các cảnh đẹp trong tour du lịch Sapa như: \n" +
                "\n" +
                "    Chợ Sapa - nơi trao đổi mua bán nhiều loại hàng hóa, sản phẩm địa phương.\n" +
                "    Nhà Thờ Đá - dấu ấn kiến trúc của người Pháp còn lại vẹn toàn nhất tại Sa Pa.\n");
        calendar.add(Calendar.DATE, 1);
        scheduleInput2.setArrivalTime(Objects.requireNonNull(DateUtils.getStartDay(calendar.getTime())).getTime());
        scheduleInput2.setLeaveTime(Objects.requireNonNull(DateUtils.getEndDay(calendar.getTime())).getTime());
        scheduleInputs.add(scheduleInput2);
        ScheduleInput scheduleInput3 = new ScheduleInput();
        scheduleInput3.setTitle("NGÀY 03 | SAPA – THÁC BẠC – CẦU KÍNH (Ăn Sáng, Trưa, Tối)");
        scheduleInput3.setNameLocation("SAPA – THÁC BẠC – CẦU KÍNH");
        scheduleInput3.setLocationStop(new Address());
        scheduleInput3.setDescription(" Sáng: Quý khách dùng điểm tâm và tách cafe sáng tại Khách sạn.\n" +
                "\n" +
                "Sau bữa sáng, đoàn tự do mua sắm hoặc tham quan khám phá Thị trấn trên mây như:\n" +
                "\n" +
                "    Ngắm cảnh Thung Lũng Mường Hoa\n" +
                "    Trải nghiệm tour đi cáp treo lên đỉnh Fansipan, ngắm nhìn toàn cảnh Sapa từ trên cao (phí tự túc).\n" +
                "\n" +
                "Lưu ý quý khách có thể liên hệ Vietnam Booking để được đặt tour trước.\n" +
                "\n" +
                "Trưa: Đoàn dùng bữa trưa tại Sapa.\n" +
                "\n" +
                "Chiều: Đoàn bắt đầu đi tham quan:\n" +
                "\n" +
                "    Khu du lịch Thác Bạc nơi sở hữu độ cao đến khoảng 200m, là thượng nguồn của dòng suối Mường Hoa và tọa lạc dưới chân đèo Ô Quy Hồ. hùng vĩ\n" +
                "    Khu du lịch Cầu Kính Rồng Mây với thang máy trong suốt đi cao hơn 300m sẽ đưa du khách xuyên qua các tầng mây, xa xa là ngọn núi Fansipan sừng sững. Hoặc du khách có thể đi trên cầu kính trên không dài hơn 500m để chinh phục đỉnh đèo Ô Quy Hồ (các chi phí tại đây là tự túc ).\n" +
                "\n" +
                "Tối: Quý khách dùng cơm tối tại nhà hàng. Buổi tối, đoàn tự do khám phá Sapa về đêm, thưởng thức các đặc sản nướng trong cái se se lạnh của núi rừng Tây Bắc. Nghỉ đêm tại Sapa. ");
        calendar.add(Calendar.DATE, 1);
        scheduleInput3.setArrivalTime(Objects.requireNonNull(DateUtils.getStartDay(calendar.getTime())).getTime());
        scheduleInput3.setLeaveTime(Objects.requireNonNull(DateUtils.getEndDay(calendar.getTime())).getTime());
        scheduleInputs.add(scheduleInput3);
        ScheduleInput scheduleInput4 = new ScheduleInput();
        scheduleInput4.setTitle("NGÀY 04 | SAPA – HÀ KHẨU – TIỄN KHÁCH (Ăn Sáng, Trưa)");
        scheduleInput4.setNameLocation("SAPA – HÀ KHẨU – TIỄN KHÁCH");
        scheduleInput4.setLocationStop(new Address());
        scheduleInput4.setDescription(" Sáng: Quý khách dùng điểm tâm sáng tại khách sạn, làm thủ tục trả phòng.\n" +
                "\n" +
                "Sau đó, đoàn khởi hành về Lào Cai, tham quan: \n" +
                "\n" +
                "    Cửa khẩu Quốc tế Lào Cai – Hà Khẩu: Nơi tiếp giáp với tỉnh Vân Nam, Trung Quốc. \n" +
                "    Mua sắm tại chợ Cốc Lếu – là trung tâm thương mại lớn nhất của thành phố nói riêng và của tỉnh Lào Cai nói chung với nhiều mặt hàng đa dạng như sản vật, thủ công mỹ nghệ, điện tử,...\n" +
                "\n" +
                "Trưa: Đoàn ăn trưa và nghỉ ngơi tại Nhà hàng. Sau đó tiếp tục đi về Hà Nội\n" +
                "\n" +
                "Chiều: Đến Hà Nội, xe đưa Quý khách ra sân bay, làm thủ tục đáp chuyến bay về Đà Nẵng. Kết thúc chương trình đi Đà Nẵng Sapa 4 Ngày 3 Đêm. HDV chia tay & hẹn gặp lại du khách trong những tour du lịch trong nước giá rẻ tiếp theo.\n" +
                "\n" +
                "LƯU Ý: Thứ tự và chi tiết trong chương trình có thể thay đổi cho phù hợp với tình hình thực tế, nhưng vẫn đảm bảo đủ điểm đến tham quan! ");
        calendar.add(Calendar.DATE, 1);
        scheduleInput4.setArrivalTime(Objects.requireNonNull(DateUtils.getStartDay(calendar.getTime())).getTime());
        scheduleInput4.setLeaveTime(Objects.requireNonNull(DateUtils.getEndDay(calendar.getTime())).getTime());
        scheduleInputs.add(scheduleInput4);

        createTourInput.setScheduleInput(scheduleInputs);
        List<PricingValueInput> pricingValueInputs = new ArrayList<>();
        PricingValueInput pricingValueInput1 = new PricingValueInput();

        pricingValueInput1.setPrice(MoneyCalculateUtils.getMoney(4700000));
        pricingValueInput1.setAboutAgeType(AgeType.ADULTS);
        pricingValueInput1.setNote("Người lớn");
        pricingValueInput1.setSale(0.1);
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
