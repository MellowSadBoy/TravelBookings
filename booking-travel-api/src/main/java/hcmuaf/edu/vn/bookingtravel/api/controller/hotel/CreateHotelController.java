package hcmuaf.edu.vn.bookingtravel.api.controller.hotel;

import hcmuaf.edu.vn.bookingtravel.api.base.controller.BaseController;
import hcmuaf.edu.vn.bookingtravel.api.base.exception.ExceptionCode;
import hcmuaf.edu.vn.bookingtravel.api.base.exception.ServiceException;
import hcmuaf.edu.vn.bookingtravel.api.manager.HotelManager;
import hcmuaf.edu.vn.bookingtravel.api.manager.RoomManager;
import hcmuaf.edu.vn.bookingtravel.api.model.hotel.Hotel;
import hcmuaf.edu.vn.bookingtravel.api.model.hotel.Room;
import hcmuaf.edu.vn.bookingtravel.api.model.input.hotel.CreateHotelInput;
import hcmuaf.edu.vn.bookingtravel.api.model.input.hotel.HotelInput;
import hcmuaf.edu.vn.bookingtravel.api.model.input.hotel.RoomInput;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 26/04/2023, Thứ Tư
 **/
@Component
public class CreateHotelController extends BaseController {
    @Autowired
    private RoomManager roomManager;
    @Autowired
    private HotelManager hotelManager;

    private void validate(CreateHotelInput createHotelInput) throws ServiceException {

        HotelInput hotelInput = createHotelInput.getHotelInput();
        if (hotelInput == null) {
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Thiếu thông tin khách sạn");
        }
        if (StringUtils.isBlank(hotelInput.getName())) {
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Tên khách sạn không được để trống");
        }
        if (hotelInput.getAddress() == null) {
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Địa chỉ không được để trống");

        }
        if (hotelInput.getType() == null) {
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Loại khách sạn không được để trống");
        }
        if (hotelInput.getImgUrls() == null || hotelInput.getImgUrls().isEmpty()) {
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Ít nhất một đường dẫn ảnh là bắt buộc");
        }
        if (StringUtils.isBlank(hotelInput.getFax())) {
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Số hotline không được để trống");
        }
        List<RoomInput> roomsInput = createHotelInput.getRoomsInput();
        if (roomsInput == null || roomsInput.isEmpty()) {
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Ít nhất một phòng là bắt buộc");
        }
        for (RoomInput roomInput : roomsInput) {
            if (StringUtils.isBlank(roomInput.getNumberRoom())) {
                throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Số phòng không được để trống");
            }
            if (roomInput.getType() == null) {
                throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Loại phòng không được để trống");
            }
            if (roomInput.getImgUrls() == null || roomInput.getImgUrls().isEmpty()) {
                throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Ít nhất một đường dẫn ảnh là bắt buộc cho phòng");
            }
            if (roomInput.getMaxPeople() <= 0) {
                throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Số người tối đa phải lớn hơn 0");
            }
            if (roomInput.getPrice() == null) {
                throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Giá phòng là bắt buộc");
            }
        }
    }

    public Hotel createHotel(CreateHotelInput createHotelInput) throws ServiceException {
        //validate
        validate(createHotelInput);
        Hotel hotel = new Hotel();
        HotelInput hotelInput = createHotelInput.getHotelInput();
        // Set thông tin của Hotel từ HotelInput
        hotel.setName(hotelInput.getName());
        hotel.setAddress(hotelInput.getAddress());
        hotel.setFax(hotelInput.getFax());
        hotel.setType(hotelInput.getType());
        hotel.setImgUrls(hotelInput.getImgUrls());
        hotel = hotelManager.createHotel(hotel);

        // Tạo danh sách các phòng từ RoomInput và thêm vào Hotel
        List<RoomInput> roomsInput = createHotelInput.getRoomsInput();
        List<Room> rooms = new ArrayList<>();
        for (RoomInput roomInput : roomsInput) {
            Room room = new Room();
            room.setHotelId(hotel.getId());
            room.setHotelName(hotel.getName());
            room.setProvince(hotel.getAddress().getProvinceCode());
            room.setHotelType(hotel.getType());
            room.setNumberRoom(roomInput.getNumberRoom());
            room.setType(roomInput.getType());
            room.setImgUrls(roomInput.getImgUrls());
            room.setMaxPeople(roomInput.getMaxPeople());
            room.setVacant(roomInput.isVacant());
            room.setPrice(roomInput.getPrice());
            room.setDiscount(roomInput.getDiscount());
            rooms.add(room);
        }
        roomManager.createRooms(rooms);
        return hotel;
    }
}
