package hcmuaf.edu.vn.bookingtravel.api.controller.tourBooked;

import hcmuaf.edu.vn.bookingtravel.api.base.exception.ExceptionCode;
import hcmuaf.edu.vn.bookingtravel.api.base.exception.ServiceException;
import hcmuaf.edu.vn.bookingtravel.api.base.filter.ResultList;
import hcmuaf.edu.vn.bookingtravel.api.controller.tour.TourProfileController;
import hcmuaf.edu.vn.bookingtravel.api.manager.TourBookedManager;
import hcmuaf.edu.vn.bookingtravel.api.manager.TourManager;
import hcmuaf.edu.vn.bookingtravel.api.model.input.tourBooked.TourBookedUpdateInput;
import hcmuaf.edu.vn.bookingtravel.api.model.tour.Tour;
import hcmuaf.edu.vn.bookingtravel.api.model.tourBooked.TourBooked;
import hcmuaf.edu.vn.bookingtravel.api.model.tourBooked.TourBookedDetail;
import hcmuaf.edu.vn.bookingtravel.api.model.tourBooked.TourBookedFilter;
import hcmuaf.edu.vn.bookingtravel.api.model.tourBooked.enums.TourBookedStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 09/05/2023, Thứ Ba
 **/
@Component
public class TourBookedProfileController {
    @Autowired
    private TourBookedManager tourBookedManager;
    @Autowired
    TourManager tourManager;
    @Autowired
    private TourProfileController tourProfileController;

    public ResultList<TourBooked> searchTourBooked(TourBookedFilter tourBookedFilter) {
        return tourBookedManager.filterTourBooked(tourBookedFilter);
    }

    public TourBooked getTourBooked(String tourBookedId) throws ServiceException {
        TourBooked tourBooked = tourBookedManager.getTourBooked(tourBookedId);
        if (tourBooked == null) {
            throw new ServiceException(ExceptionCode.NOT_FOUND_EXCEPTION.toString(), "Không tìm thấy tour đã đặt");
        }
        return tourBooked;
    }

    public TourBookedDetail getTourBookedDetail(String tourBookedId) throws ServiceException {
        TourBookedDetail detail = new TourBookedDetail();
        TourBooked tourBooked = getTourBooked(tourBookedId);
        Tour tour = tourManager.getTour(tourBooked.getTourId());
        detail.setTourBooked(tourBooked);
        detail.setTour(tour);
        return detail;
    }

    public TourBooked updateTourBooked(String tourBookedId, TourBookedUpdateInput bookedInput) throws ServiceException {
        TourBooked tourBooked = getTourBooked(tourBookedId);
        if (bookedInput == null) {
            return tourBooked;
        }
        Tour tour = tourManager.getTour(tourBooked.getTourId());
        if (tour != null) {
            tourBookedManager.updateTourBookedTour(tourBookedId, tour);
        }
        return tourBookedManager.updateTourBooked(tourBookedId, bookedInput);
    }

    public TourBooked cancelTourBooked(String tourBookedId, String byUser) throws ServiceException {
        TourBookedDetail detail = getTourBookedDetail(tourBookedId);
        TourBooked tourBooked = detail.getTourBooked();
        if (tourBooked.getStartAt().before(new Date())) {
            throw new ServiceException(ExceptionCode.CANCELED_ERROR.toString(), "Huỷ tour đã đặt không thành công." +
                    "Do ngày khởi hành đã đến");
        }
        //Cập nhật lại số lượng còn trống của tour
        tourProfileController.updateQuantityEmptyAdd(tourBooked.getTourId(),
                tourBooked.getQuantityAdult() + tourBooked.getQuantityChildren());
        return tourBookedManager.cancelTourBooked(tourBookedId, byUser);
    }

    public void updateStatusTourBooked(String id, TourBookedStatus status, String byUser) throws ServiceException {
        getTourBooked(id);
        tourBookedManager.updateStatusTourBooked(id, status, byUser);
    }
}
