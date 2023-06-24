package hcmuaf.edu.vn.bookingtravel.api.controller.hotelBooked;

import hcmuaf.edu.vn.bookingtravel.api.base.exception.ExceptionCode;
import hcmuaf.edu.vn.bookingtravel.api.base.exception.ServiceException;
import hcmuaf.edu.vn.bookingtravel.api.controller.hotel.HotelProfileController;
import hcmuaf.edu.vn.bookingtravel.api.manager.HotelBookedManager;
import hcmuaf.edu.vn.bookingtravel.api.manager.RoomManager;
import hcmuaf.edu.vn.bookingtravel.api.model.hotel.Room;
import hcmuaf.edu.vn.bookingtravel.api.model.input.hotelBooked.HotelBookedInput;
import hcmuaf.edu.vn.bookingtravel.api.model.hotelBooked.HotelBooked;
import hcmuaf.edu.vn.bookingtravel.api.model.hotelBooked.enums.HotelBookedStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateHotelBookedController {
    @Autowired
    private HotelBookedManager hotelBookedManager;
    @Autowired
    private HotelProfileController hotelProfileController;
    @Autowired
    private RoomManager roomManager;

    private void validateHotelBooked(HotelBookedInput hotelBookedInput) throws ServiceException {
        // Kiểm tra startAt
        if (hotelBookedInput.getStartAt() == null) {
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Start date is required.");
        }

        // Kiểm tra hotelId
        if (hotelBookedInput.getHotelId() == null) {
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Hotel ID is required.");
        }
        if (hotelBookedInput.getRoomId() == null) {
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Room ID is required.");
        }

        // Kiểm tra hotelName
        if (hotelBookedInput.getHotelName() == null || hotelBookedInput.getHotelName().isEmpty()) {
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Hotel name is required.");
        }

        // Kiểm tra featuredImgHotel
        if (hotelBookedInput.getFeaturedImgHotel() == null || hotelBookedInput.getFeaturedImgHotel().isEmpty()) {
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Featured hotel image is required.");
        }

        // Kiểm tra totalPrice
        if (hotelBookedInput.getTotalPrice() == null) {
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Total price must be a positive number.");
        }

        // Kiểm tra customerId
        if (hotelBookedInput.getCustomerId() == null) {
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Customer ID is required.");
        }

        // Kiểm tra customerName
        if (hotelBookedInput.getCustomerName() == null || hotelBookedInput.getCustomerName().isEmpty()) {
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Customer name is required.");
        }

        // Kiểm tra customerEmail
        if (hotelBookedInput.getCustomerEmail() == null || hotelBookedInput.getCustomerEmail().isEmpty()) {
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Customer email is required.");
        }

        // Kiểm tra customerPhone
        if (hotelBookedInput.getCustomerPhone() == null || hotelBookedInput.getCustomerPhone().isEmpty()) {
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Customer phone number is required.");
        }

        // Kiểm tra quantityAdult
        if (hotelBookedInput.getQuantityPeople() == 0) {
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Quantity of People must be a non-negative number.");
        }

        // Kiểm tra paymentMethod
        if (hotelBookedInput.getPaymentMethod() == null) {
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Payment method is required.");
        }
    }

    public HotelBooked createHotelBooked(HotelBookedInput hotelBookedInput) throws ServiceException {
        //validate input
        validateHotelBooked(hotelBookedInput);

        //Tạo hotel đã đặt
        HotelBooked hotelBooked = new HotelBooked();
        Room room = roomManager.getRoom(hotelBookedInput.getRoomId());
        if (room == null)
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Không có thông tin phòng khách sạn.");
        hotelBooked.setRoom(room);
        hotelBooked.setRequesteds(hotelBookedInput.getRequesteds());
        hotelBooked.setStartAt(hotelBookedInput.getStartAt());
        hotelBooked.setEndAt(hotelBookedInput.getEndAt());
        hotelBooked.setHotelId(hotelBookedInput.getHotelId());
        hotelBooked.setHotelName(hotelBookedInput.getHotelName());
        hotelBooked.setFeaturedImgHotel(hotelBookedInput.getFeaturedImgHotel());
        hotelBooked.setNote(hotelBookedInput.getNote());
        hotelBooked.setTax(hotelBookedInput.getTax());
        hotelBooked.setSalePrice(hotelBookedInput.getSalePrice());
        hotelBooked.setStatus(HotelBookedStatus.READY);
        hotelBooked.setTotalPrice(hotelBookedInput.getTotalPrice());
        hotelBooked.setCustomerId(hotelBookedInput.getCustomerId());
        hotelBooked.setCustomerName(hotelBookedInput.getCustomerName());
        hotelBooked.setCustomerEmail(hotelBookedInput.getCustomerEmail());
        hotelBooked.setCustomerPhone(hotelBookedInput.getCustomerPhone());
        hotelBooked.setQuantityPeople(hotelBookedInput.getQuantityPeople());
        hotelBooked.setPaymentMethod(hotelBookedInput.getPaymentMethod());
        return hotelBookedManager.createHotelBooked(hotelBooked);
    }

}
