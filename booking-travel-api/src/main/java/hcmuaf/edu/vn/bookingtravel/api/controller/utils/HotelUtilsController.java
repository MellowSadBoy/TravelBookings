package hcmuaf.edu.vn.bookingtravel.api.controller.utils;

import hcmuaf.edu.vn.bookingtravel.api.base.exception.ExceptionCode;
import hcmuaf.edu.vn.bookingtravel.api.base.exception.ServiceException;
import hcmuaf.edu.vn.bookingtravel.api.model.hotel.Hotel;
import hcmuaf.edu.vn.bookingtravel.api.manager.HotelManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class HotelUtilsController {
    @Autowired
    private HotelManager hotelManager;

    public Hotel getHotel(String hotelId) throws ServiceException {
        Hotel hotel = hotelManager.getHotel(hotelId);
        if (null == hotel)
            throw new ServiceException(ExceptionCode.NOT_FOUND_EXCEPTION.toString(),
                    "Không tìm thấy thông tin khách sạn");
        return hotel;
    }
}
