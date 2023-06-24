package hcmuaf.edu.vn.bookingtravel.api.controller.hotel;

import hcmuaf.edu.vn.bookingtravel.api.base.controller.BaseController;
import hcmuaf.edu.vn.bookingtravel.api.base.exception.ExceptionCode;
import hcmuaf.edu.vn.bookingtravel.api.base.exception.ServiceException;
import hcmuaf.edu.vn.bookingtravel.api.base.filter.ResultList;
import hcmuaf.edu.vn.bookingtravel.api.manager.HotelManager;
import hcmuaf.edu.vn.bookingtravel.api.manager.RoomManager;
import hcmuaf.edu.vn.bookingtravel.api.model.hotel.*;
import hcmuaf.edu.vn.bookingtravel.api.model.tour.Tour;
import hcmuaf.edu.vn.bookingtravel.api.model.tour.TourFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component

public class HotelProfileController extends BaseController {
    @Autowired
    private HotelManager hotelManager;
    @Autowired
    private RoomManager roomManager;


    public ResultList<Hotel> searchHotel(HotelFilter hotelFilter) {
        return hotelManager.filterHotel(hotelFilter);
    }

    public ResultList<Room> searchRoom(RoomFilter roomFilter) {
        return roomManager.filterRoom(roomFilter);
    }

    public List<Hotel> getAllHotels() {
        return hotelManager.getAllHotels();
    }

    public Hotel getHotel(String hotelId) throws ServiceException {
        Hotel hotel = hotelManager.getHotel(hotelId);
        if (null == hotel)
            throw new ServiceException(ExceptionCode.NOT_FOUND_EXCEPTION.toString(),
                    "Không tìm thấy thông tin khách sạn");
        return hotel;
    }

    public HotelDetail getHotelDetail(String hotelId) throws ServiceException {
        HotelDetail hotelDetail = new HotelDetail();
        Hotel hotel = getHotel(hotelId);
        List<Room> rooms = roomManager.getRooms(hotelId);
        hotelDetail.setRooms(rooms);
        hotelDetail.setHotel(hotel);
        return hotelDetail;
    }

    public RoomDetail getRoomDetail(String roomId) throws ServiceException {
        Room room = roomManager.getRoom(roomId);
        if (room == null)
            throw new ServiceException(ExceptionCode.NOT_FOUND_EXCEPTION.toString(),
                    "Không tìm thấy thông tin phòng khách sạn");
        Hotel hotel = getHotel(room.getHotelId());

        RoomDetail roomDetail = new RoomDetail();
        roomDetail.setRoom(room);
        roomDetail.setHotel(hotel);
        return roomDetail;
    }
}
