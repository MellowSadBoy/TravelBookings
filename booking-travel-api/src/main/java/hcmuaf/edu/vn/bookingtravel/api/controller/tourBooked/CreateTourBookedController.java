package hcmuaf.edu.vn.bookingtravel.api.controller.tourBooked;

import hcmuaf.edu.vn.bookingtravel.api.base.exception.ExceptionCode;
import hcmuaf.edu.vn.bookingtravel.api.base.exception.ServiceException;
import hcmuaf.edu.vn.bookingtravel.api.controller.tour.TourProfileController;
import hcmuaf.edu.vn.bookingtravel.api.manager.TourBookedManager;
import hcmuaf.edu.vn.bookingtravel.api.manager.TourManager;
import hcmuaf.edu.vn.bookingtravel.api.model.input.tourBooked.TourBookedInput;
import hcmuaf.edu.vn.bookingtravel.api.model.tour.Tour;
import hcmuaf.edu.vn.bookingtravel.api.model.tourBooked.TourBooked;
import hcmuaf.edu.vn.bookingtravel.api.model.tourBooked.enums.TourBookedStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 09/05/2023, Thứ Ba
 **/
@Component
public class CreateTourBookedController {
    @Autowired
    private TourBookedManager tourBookedManager;
    @Autowired
    private TourProfileController tourProfileController;
    @Autowired
    private TourManager tourManager;

    private void validateTourBooked(TourBookedInput tourBookedInput) throws ServiceException {
        // Kiểm tra startAt
        if (tourBookedInput.getStartAt() == null) {
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Start date is required.");
        }

        // Kiểm tra tourId
        if (tourBookedInput.getTourId() == null) {
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Tour ID is required.");
        }

        // Kiểm tra totalPrice
        if (tourBookedInput.getTotalPrice() == null) {
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Total price must be a positive number.");
        }

        // Kiểm tra customerId
        if (tourBookedInput.getCustomerId() == null) {
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Customer ID is required.");
        }

        // Kiểm tra customerName
        if (tourBookedInput.getCustomerName() == null || tourBookedInput.getCustomerName().isEmpty()) {
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Customer name is required.");
        }

        // Kiểm tra customerEmail
        if (tourBookedInput.getCustomerEmail() == null || tourBookedInput.getCustomerEmail().isEmpty()) {
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Customer email is required.");
        }

        // Kiểm tra customerPhone
        if (tourBookedInput.getCustomerPhone() == null || tourBookedInput.getCustomerPhone().isEmpty()) {
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Customer phone number is required.");
        }

        // Kiểm tra quantityAdult
        if (tourBookedInput.getQuantityAdult() == 0 || tourBookedInput.getQuantityChildren() == 0) {
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Quantity of adults must be a non-negative number.");
        }

        // Kiểm tra paymentMethod
        if (tourBookedInput.getPaymentMethod() == null) {
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Payment method is required.");
        }
    }

    public TourBooked createTourBooked(TourBookedInput tourBookedInput) throws ServiceException {
        //validate input
        validateTourBooked(tourBookedInput);
        Tour tour = tourManager.getTour(tourBookedInput.getTourId());
        if (tour == null) {
            throw new ServiceException(ExceptionCode.NOT_FOUND_EXCEPTION.toString(), "Không có thông tin tour");
        }

        //Cập nhật lại số lượng còn trống của tour
        tourProfileController.updateQuantityEmptyRemove(tourBookedInput.getTourId(),
                tourBookedInput.getQuantityAdult() + tourBookedInput.getQuantityChildren());
        //Tạo tour đã đặt
        TourBooked tourBooked = new TourBooked();
        tourBooked.setRequesteds(tourBookedInput.getRequesteds());
        tourBooked.setStartAt(tourBookedInput.getStartAt());
        tourBooked.setTourId(tourBookedInput.getTourId());
        tourBooked.setTourName(tour.getName());
        tourBooked.setFeaturedImgTour(tour.getFeatureImgUrl());
        tourBooked.setNote(tourBookedInput.getNote());
        tourBooked.setTax(tourBookedInput.getTax());
        tourBooked.setTimeHowLong(tour.getTimeHowLong());
        tourBooked.setTourType(tour.getType());
        tourBooked.setSalePrice(tourBookedInput.getSalePrice());
        tourBooked.setStatus(TourBookedStatus.READY);
        tourBooked.setTotalPrice(tourBookedInput.getTotalPrice());
        tourBooked.setCustomerId(tourBookedInput.getCustomerId());
        tourBooked.setCustomerName(tourBookedInput.getCustomerName());
        tourBooked.setCustomerEmail(tourBookedInput.getCustomerEmail());
        tourBooked.setCustomerPhone(tourBookedInput.getCustomerPhone());
        tourBooked.setQuantityChildren(tourBookedInput.getQuantityChildren());
        tourBooked.setQuantityAdult(tourBookedInput.getQuantityAdult());
        tourBooked.setPaymentMethod(tourBookedInput.getPaymentMethod());
        return tourBookedManager.createTourBooked(tourBooked);
    }

}
