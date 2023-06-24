package hcmuaf.edu.vn.bookingtravel.api.controller.tour;

import hcmuaf.edu.vn.bookingtravel.api.base.controller.BaseController;
import hcmuaf.edu.vn.bookingtravel.api.base.exception.ExceptionCode;
import hcmuaf.edu.vn.bookingtravel.api.base.exception.ServiceException;
import hcmuaf.edu.vn.bookingtravel.api.base.filter.ResultList;
import hcmuaf.edu.vn.bookingtravel.api.manager.PricingValueManager;
import hcmuaf.edu.vn.bookingtravel.api.manager.ScheduleManager;
import hcmuaf.edu.vn.bookingtravel.api.manager.TourManager;
import hcmuaf.edu.vn.bookingtravel.api.manager.VehicleManager;
import hcmuaf.edu.vn.bookingtravel.api.model.input.tour.TourInput;
import hcmuaf.edu.vn.bookingtravel.api.model.tour.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 14/04/2023, Thứ Sáu
 **/
@Component
public class TourProfileController extends BaseController {

    @Autowired
    private TourManager tourManager;
    @Autowired
    private ScheduleManager scheduleManager;
    @Autowired
    private VehicleManager vehicleManager;
    @Autowired
    private PricingValueManager pricingValueManager;


    public TourDetail getTourDetail(String tourId) throws ServiceException {
        TourDetail tourDetail = new TourDetail();
        Tour tour = tourManager.getTour(tourId);
        if (tour == null) {
            throw new ServiceException(ExceptionCode.NOT_FOUND_EXCEPTION.toString(), "Không tìm thấy thông tin tour");
        }
        List<Schedule> schedules = scheduleManager.getScheduleList(tourId);
        List<PricingValue> pricingValues = pricingValueManager.getPricingValues(tourId);
        Vehicle vehicle = vehicleManager.getVehicleByTourId(tourId);
        //set data
        tourDetail.setVehicle(vehicle);
        tourDetail.setSchedules(schedules);
        tourDetail.setPricingValues(pricingValues);
        tourDetail.setTour(tour);
        return tourDetail;

    }

    public Tour updateTour(String tourId, TourInput tourInput) throws ServiceException {
        Tour tour = tourManager.getTour(tourId);
        if (tour == null) {
            throw new ServiceException(ExceptionCode.NOT_FOUND_EXCEPTION.toString(), "Không tìm thấy thông tin tour");
        }
        return tourManager.updateInfoTour(tourId, tourInput);
    }

    public Tour updateQuantityEmpty(String tourId, long quantityEmpty) throws ServiceException {
        Tour tour = tourManager.getTour(tourId);
        if (tour == null) {
            throw new ServiceException(ExceptionCode.NOT_FOUND_EXCEPTION.toString(), "Không tìm thấy thông tin tour");
        }
        return tourManager.updateQuantityEmpty(tourId, quantityEmpty);
    }

    public Tour updateQuantityEmptyRemove(String tourId, long quantityEmpty) throws ServiceException {
        Tour tour = tourManager.getTour(tourId);
        if (tour == null) {
            throw new ServiceException(ExceptionCode.NOT_FOUND_EXCEPTION.toString(), "Không tìm thấy thông tin tour");
        }
        if (tour.getQuantityEmpty() - quantityEmpty < 0) {
            throw new ServiceException(ExceptionCode.ERROR_EXCEPTION.toString(), "Số lượng còn lại của tour không đủ để đặt");
        }
        return tourManager.updateQuantityEmpty(tourId, tour.getQuantityEmpty() - quantityEmpty);
    }

    public Tour updateQuantityEmptyAdd(String tourId, long quantityEmpty) throws ServiceException {
        Tour tour = tourManager.getTour(tourId);
        if (tour == null) {
            throw new ServiceException(ExceptionCode.NOT_FOUND_EXCEPTION.toString(), "Không tìm thấy thông tin tour");
        }
        if (tour.getQuantityEmpty() + quantityEmpty > tour.getQuantityVisit()) {
            throw new ServiceException(ExceptionCode.ERROR_EXCEPTION.toString(), "Số lượng còn lại của tour không được lớn hơn số lượng hiện có");
        }
        return tourManager.updateQuantityEmpty(tourId, tour.getQuantityEmpty() + quantityEmpty);
    }

    public Tour updateStartDate(String tourId, Date startDate) throws ServiceException {
        Tour tour = tourManager.getTour(tourId);
        if (tour == null) {
            throw new ServiceException(ExceptionCode.NOT_FOUND_EXCEPTION.toString(), "Không tìm thấy thông tin tour");
        }
        return tourManager.updateStartDate(tourId, startDate);
    }

    public ResultList<Tour> searchTour(TourFilter tourFilter) {
        return tourManager.filterTour(tourFilter);
    }

    public Tour deleteTour(String tourId, String byUser, String note) throws ServiceException {
        Tour tour = tourManager.getTour(tourId);
        if (tour == null) {
            throw new ServiceException(ExceptionCode.NOT_FOUND_EXCEPTION.toString(), "Không tìm thấy thông tin tour");
        }
        tourManager.deleteTour(tourId, byUser, note);
        return tour;
    }

    public List<Tour> getTourAll() {
        return tourManager.getTourAll();
    }

}
