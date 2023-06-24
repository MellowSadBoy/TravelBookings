package hcmuaf.edu.vn.bookingtravel.api;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import hcmuaf.edu.vn.bookingtravel.api.base.model.MoneyV2;
import hcmuaf.edu.vn.bookingtravel.api.base.utils.DiscountUtils;
import hcmuaf.edu.vn.bookingtravel.api.controller.hotel.CreateHotelController;
import hcmuaf.edu.vn.bookingtravel.api.enums.CountryCode;
import hcmuaf.edu.vn.bookingtravel.api.enums.CurrencyCode;
import hcmuaf.edu.vn.bookingtravel.api.model.geo.Address;
import hcmuaf.edu.vn.bookingtravel.api.model.hotel.enums.RankHotelType;
import hcmuaf.edu.vn.bookingtravel.api.model.hotel.enums.RoomType;
import hcmuaf.edu.vn.bookingtravel.api.model.input.hotel.CreateHotelInput;
import hcmuaf.edu.vn.bookingtravel.api.model.input.hotel.HotelInput;
import hcmuaf.edu.vn.bookingtravel.api.model.input.hotel.RoomInput;
import org.bson.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 27/04/2023, Thứ Năm
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = BookingTravelApiApplication.class)
@AutoConfigureMockMvc
public class TestHotel {
    @Autowired
    private CreateHotelController createHotelController;

    @Test
    public void testHotel() throws Exception {
        HotelInput hotelInput = new HotelInput();
        hotelInput.setName("LaDalat Hotel");
        hotelInput.setFax("+84 263 3555 568");
        hotelInput.setType(RankHotelType.FIVE_STAR);
        Address address = new Address();
        address.setAddress("106A Mai Anh Đào, Phường 8, Tp. Đà Lạt, Tỉnh Lâm Đồng, Việt Nam");
        address.setProvince("Lâm Đồng");
        address.setProvinceCode(209);
        address.setDistrict("Thành phố Đà Lạt");
        address.setDistrictCode(1550);
        address.setWard("Phường 8");
        address.setWardCode(420111);
        address.setCountryCode(CountryCode.VN);
        hotelInput.setAddress(address);
        List<String> imgUrls = new ArrayList<>();
        imgUrls.add("https://i0.wp.com/ladalathotel.com.vn/wp-content/uploads/2022/06/Khach-san.jpg?fit=760%2C1024&ssl=1");
        imgUrls.add("https://i0.wp.com/ladalathotel.com.vn/wp-content/uploads/2021/02/banner_brush_larose.png?fit=680%2C800&ssl=1");
        imgUrls.add("https://i0.wp.com/ladalathotel.com.vn/wp-content/uploads/2022/06/Lamore.jpg?fit=760%2C1024&ssl=1");
        imgUrls.add("https://i0.wp.com/ladalathotel.com.vn/wp-content/uploads/2022/06/La-rose.jpg?fit=760%2C1024&ssl=1");
        hotelInput.setImgUrls(imgUrls);

        List<RoomInput> roomsInput = new ArrayList<>();

// Thêm 15 phòng khách sạn
        for (int i = 1; i < 6; i++) {
            for (int j = 1; j <= 5; j++) {
                RoomInput room = new RoomInput();
                room.setNumberRoom(i + "0" + j);
                RoomType roomType = RoomType.getRandomRoomType();
                if (roomType == null)
                    roomType = RoomType.SINGLE_ROOM;
                room.setType(roomType);
                List<String> roomImgUrls = new ArrayList<>();
                roomImgUrls.add("https://cdn.ladulichsens.com/wp-content/uploads/2021/02/ladulichsens-ladalat-room" + i + ".jpg");
                room.setImgUrls(roomImgUrls);
                room.setMaxPeople(roomType.getNumberOfSeat());
                room.setVacant(true);
                MoneyV2 roomPrice = new MoneyV2();
                roomPrice.setAmount((new Random().nextInt(9) + 1) * 500000);
                roomPrice.setCurrencyCode(CurrencyCode.VND);
                room.setPrice(roomPrice);
                room.setDiscount(DiscountUtils.discount());
                roomsInput.add(room);
            }

        }

        CreateHotelInput createHotelInput = new CreateHotelInput();
        createHotelInput.setHotelInput(hotelInput);
        createHotelInput.setRoomsInput(roomsInput);
        createHotelController.createHotel(createHotelInput);

    }

    @Test
    public void testHotel_2() throws Exception {
        HotelInput hotelInput = new HotelInput();
        hotelInput.setName("AMIANA RESORT");
        hotelInput.setFax("(+84 258) 355 3333");
        hotelInput.setType(RankHotelType.FIVE_STAR);
        Address address = new Address();
        address.setAddress("Turtle Bay, Phạm Văn Đồng, Nha Trang, Việt Nam");
        address.setProvince("Khánh Hoà");
        address.setProvinceCode(208);
        address.setDistrict("Thành phố Nha Trang");
        address.setDistrictCode(1548);
        address.setWard("Xã Vĩnh Hoà");
        address.setWardCode(410114);
        address.setCountryCode(CountryCode.VN);
        hotelInput.setAddress(address);
        List<String> imgUrls = new ArrayList<>();
        imgUrls.add("https://www.amianaresort.com/wp-content/uploads/2018/12/4-4.jpg.webp");
        imgUrls.add("https://www.amianaresort.com/wp-content/uploads/2018/12/2-1.jpg.webp");
        imgUrls.add("https://www.amianaresort.com/wp-content/uploads/2018/12/3-1.jpg.webp");
        imgUrls.add("https://www.amianaresort.com/wp-content/uploads/2018/12/1-7-e1661228053460.jpg");
        hotelInput.setImgUrls(imgUrls);

        List<RoomInput> roomsInput = new ArrayList<>();

// Thêm 15 phòng khách sạn
        for (int i = 1; i < 6; i++) {
            for (int j = 1; j <= 5; j++) {
                RoomInput room = new RoomInput();
                room.setNumberRoom(i + "0" + j);
                RoomType roomType = RoomType.getRandomRoomType();
                if (roomType == null)
                    roomType = RoomType.SINGLE_ROOM;
                room.setType(roomType);
                List<String> roomImgUrls = new ArrayList<>();
                roomImgUrls.add("https://cdn.ladulichsens.com/wp-content/uploads/2021/02/ladulichsens-ladalat-room" + i + ".jpg");
                room.setImgUrls(roomImgUrls);
                room.setMaxPeople(roomType.getNumberOfSeat());
                room.setVacant(true);
                MoneyV2 roomPrice = new MoneyV2();
                roomPrice.setAmount((new Random().nextInt(9) + 1) * 500000);
                roomPrice.setCurrencyCode(CurrencyCode.VND);
                room.setPrice(roomPrice);
                room.setDiscount(DiscountUtils.discount());
                roomsInput.add(room);
            }

        }

        CreateHotelInput createHotelInput = new CreateHotelInput();
        createHotelInput.setHotelInput(hotelInput);
        createHotelInput.setRoomsInput(roomsInput);
        createHotelController.createHotel(createHotelInput);

    }


}
