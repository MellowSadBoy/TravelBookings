package hcmuaf.edu.vn.bookingtravel.api.controller.hotel;

import hcmuaf.edu.vn.bookingtravel.api.base.controller.BaseController;
import hcmuaf.edu.vn.bookingtravel.api.base.exception.ServiceException;
import hcmuaf.edu.vn.bookingtravel.api.base.manager.ActivityLogManager;
import hcmuaf.edu.vn.bookingtravel.api.controller.utils.HotelUtilsController;
import hcmuaf.edu.vn.bookingtravel.api.controller.utils.UserUtilsController;
import hcmuaf.edu.vn.bookingtravel.api.enums.ActivityLogType;
import hcmuaf.edu.vn.bookingtravel.api.manager.HotelManager;
import hcmuaf.edu.vn.bookingtravel.api.manager.RoomManager;
import hcmuaf.edu.vn.bookingtravel.api.model.hotel.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class DeleteHotelController extends BaseController {
    @Autowired
    private RoomManager roomManager;

    @Autowired
    private HotelManager hotelManager;


    @Autowired
    private HotelUtilsController hotelUtilsController;

    @Autowired
    private ActivityLogManager activityLogManager;

    public Hotel deleteHotel (String hotelId, String byUser )throws ServiceException {
        Hotel hotel = hotelUtilsController.getHotel(hotelId);
        hotelManager.deleteHotel(hotelId, byUser);

        return hotel;

    }
}
