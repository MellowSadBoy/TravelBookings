package hcmuaf.edu.vn.bookingtravel.api.controller.hotelBooked;

import hcmuaf.edu.vn.bookingtravel.api.base.exception.ExceptionCode;
import hcmuaf.edu.vn.bookingtravel.api.base.exception.ServiceException;
import hcmuaf.edu.vn.bookingtravel.api.base.filter.ResultList;
import hcmuaf.edu.vn.bookingtravel.api.manager.HotelBookedManager;
import hcmuaf.edu.vn.bookingtravel.api.manager.RoomManager;
import hcmuaf.edu.vn.bookingtravel.api.model.hotel.Room;
import hcmuaf.edu.vn.bookingtravel.api.model.hotelBooked.HotelBooked;
import hcmuaf.edu.vn.bookingtravel.api.model.hotelBooked.HotelBookedFilter;
import hcmuaf.edu.vn.bookingtravel.api.model.input.hotelBooked.HotelBookedInput;
import hcmuaf.edu.vn.bookingtravel.api.model.input.hotelBooked.HotelBookedUpdateInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class HotelBookedProfileController {
    @Autowired
    private HotelBookedManager hotelBookedManager;
    @Autowired
    private RoomManager roomManager;


    public ResultList<HotelBooked> searchHotelBooked(HotelBookedFilter hotelBookedFilter) {
        return hotelBookedManager.filterHotelBooked(hotelBookedFilter);
    }

    public HotelBooked getHotelBooked(String hotelBookedId) throws ServiceException {
        HotelBooked hotelBooked = hotelBookedManager.getHotelBooked(hotelBookedId);
        if (hotelBooked == null) {
            throw new ServiceException(ExceptionCode.NOT_FOUND_EXCEPTION.toString(), "Không tìm thấy hotel đã đặt");
        }
        return hotelBooked;
    }

    public HotelBooked updateHotelBooked(String hotelBookedId, HotelBookedUpdateInput bookedInput) throws ServiceException {
        HotelBooked hotelBooked = getHotelBooked(hotelBookedId);
        if (bookedInput == null) {
            return hotelBooked;
        }
        Room room = roomManager.getRoom(bookedInput.getRoomId());
        if (room != null) {
            hotelBookedManager.updateHotelBookedRoom(hotelBookedId, room);
        }
        return hotelBookedManager.updateHotelBooked(hotelBookedId, bookedInput);
    }

    public HotelBooked cancelHotelBooked(String hotelBookedId, String byUser) throws ServiceException {
        HotelBooked hotelBooked = getHotelBooked(hotelBookedId);
        return hotelBookedManager.cancelHotelBooked(hotelBookedId, byUser);
    }
}
