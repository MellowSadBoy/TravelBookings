package hcmuaf.edu.vn.bookingtravel.api.restcontroller;

import hcmuaf.edu.vn.bookingtravel.api.base.exception.ServiceException;
import hcmuaf.edu.vn.bookingtravel.api.base.utils.DateUtils;
import hcmuaf.edu.vn.bookingtravel.api.controller.tour.TourProfileController;
import hcmuaf.edu.vn.bookingtravel.api.controller.tourBooked.TourBookedProfileController;
import hcmuaf.edu.vn.bookingtravel.api.model.tour.Tour;
import hcmuaf.edu.vn.bookingtravel.api.model.tour.TourFilter;
import hcmuaf.edu.vn.bookingtravel.api.model.tourBooked.TourBooked;
import hcmuaf.edu.vn.bookingtravel.api.model.tourBooked.TourBookedFilter;
import hcmuaf.edu.vn.bookingtravel.api.model.tourBooked.enums.TourBookedStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 28/04/2023, Thứ Sáu
 **/
@Service
public class SyncTourChanelController {
    private final static Logger LOGGER = LoggerFactory.getLogger(SyncTourChanelController.class);
    @Autowired
    private TourProfileController profileController;
    @Autowired
    private TourBookedProfileController tourBookedProfileController;

    // Tự động cập nhật lại thông tin tour
    @Scheduled(fixedDelay = 100000)
    public void syncTimeTour() throws ServiceException {
        TourFilter tourFilter = new TourFilter();
        List<Tour> tourList = profileController.searchTour(tourFilter).getSearchList();
        for (Tour tour : tourList) {
            //Kiểm tra ngày bắt đầu của tour trước ngày hiện tại
            if (tour.getStartDate().before(new Date())) {
                Date startDate = DateUtils.augmentDay(tour.getStartDate(), 7);
                //Cập nhật lại ngày khởi hành
                profileController.updateStartDate(tour.getId(), startDate);
                //Cập nhật lại số lượng còn trống
                profileController.updateQuantityEmpty(tour.getId(), tour.getQuantityVisit());
                LOGGER.info("UPDATE_TOUR", "Cập nhật lại ngày khởi hành của tour " + tour.getId());

            }
        }
    }

    @Scheduled(fixedDelay = 100000)
    public void syncStatusTourBooked() throws ServiceException {
        TourBookedFilter tourFilter = new TourBookedFilter();
        List<TourBooked> tourList = tourBookedProfileController.searchTourBooked(tourFilter).getSearchList();
        for (TourBooked tourBooked : tourList) {
            Date dateTo = DateUtils.augmentDaySeconds(tourBooked.getStartAt(), tourBooked.getTimeHowLong().getSeconds());
            if (!TourBookedStatus.CANCELLED.equals(tourBooked.getStatus())
                    && !TourBookedStatus.END.equals(tourBooked.getStatus())) {
                //Kiểm tra nếu tour đã đặt mà có ngày bắt đầu khởi hành trước ngày hiện tại thì cập nhật trạng thái
                if (tourBooked.getStartAt().before(new Date())) {
                    tourBookedProfileController.updateStatusTourBooked(tourBooked.getId(), TourBookedStatus.START, "automatic");
                }
                //Kiểm tra nếu tour đã đặt mà có ngày bắt đầu khởi hành trước ngày hiện tại thì cập nhật trạng thái

                if (dateTo.before(new Date())) {
                    tourBookedProfileController.updateStatusTourBooked(tourBooked.getId(), TourBookedStatus.END, "automatic");

                }
            }
        }
    }

}
