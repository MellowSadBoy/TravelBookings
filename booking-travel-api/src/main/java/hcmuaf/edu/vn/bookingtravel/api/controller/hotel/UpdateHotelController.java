package hcmuaf.edu.vn.bookingtravel.api.controller.hotel;

import hcmuaf.edu.vn.bookingtravel.api.base.exception.ExceptionCode;
import hcmuaf.edu.vn.bookingtravel.api.base.exception.ServiceException;
import hcmuaf.edu.vn.bookingtravel.api.base.manager.ActivityLogManager;
import hcmuaf.edu.vn.bookingtravel.api.controller.utils.HotelUtilsController;
import hcmuaf.edu.vn.bookingtravel.api.manager.HotelManager;
import hcmuaf.edu.vn.bookingtravel.api.manager.RoomManager;
import hcmuaf.edu.vn.bookingtravel.api.model.hotel.Hotel;
import hcmuaf.edu.vn.bookingtravel.api.model.hotel.enums.HotelStatus;
import hcmuaf.edu.vn.bookingtravel.api.model.input.hotel.UpdateInfoHotelInput;
import hcmuaf.edu.vn.bookingtravel.api.model.input.user.UpdateInfoUserInput;
import hcmuaf.edu.vn.bookingtravel.api.model.input.user.UpdateStatusInput;
import hcmuaf.edu.vn.bookingtravel.api.model.user.User;
import hcmuaf.edu.vn.bookingtravel.api.model.user.enums.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateHotelController {
    @Autowired
    private RoomManager roomManager;
    @Autowired
    private HotelManager hotelManager;

    private HotelUtilsController hotelUtilsController;
    @Autowired
    private ActivityLogManager activityLogManager;

    private void validateUpdateStatus(UpdateStatusInput statusBody) throws ServiceException {
        if (null == statusBody) {
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Thông tin không hợp lệ");
        }
        if (null == statusBody.getStatus()) {
            throw new ServiceException(ExceptionCode.NOT_FOUND_EXCEPTION.toString(), "Vui lòng nhập trạng thái cần cập nhật");
        }
        if (!HotelStatus.isExist(statusBody.getStatus())) {
            throw new ServiceException(ExceptionCode.NOT_EXIST_STATUS_EXCEPTION.toString(), "Trang thái khách sạn không tồn tại (" + HotelStatus.getListName() + ")");
        }

    }

    public Hotel updateHotelInfo(String hotelId, UpdateInfoHotelInput updateInfoHotel) throws ServiceException {
        if (updateInfoHotel == null) {
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Vui lòng nhập thông tin cần cập nhật của khách sạn");
        }
        Hotel hotel = hotelUtilsController.getHotel(hotelId);
        return hotelManager.updateInfoHotel(hotelId, updateInfoHotel);
    }

}
