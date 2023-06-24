package hcmuaf.edu.vn.bookingtravel.api.restcontroller;

import hcmuaf.edu.vn.bookingtravel.api.base.controller.BaseController;
import hcmuaf.edu.vn.bookingtravel.api.base.exception.ServiceException;
import hcmuaf.edu.vn.bookingtravel.api.base.filter.ResultList;
import hcmuaf.edu.vn.bookingtravel.api.controller.tour.CreateTourController;
import hcmuaf.edu.vn.bookingtravel.api.controller.tour.TourProfileController;
import hcmuaf.edu.vn.bookingtravel.api.manager.TourManager;
import hcmuaf.edu.vn.bookingtravel.api.model.input.tour.CreateTourInput;
import hcmuaf.edu.vn.bookingtravel.api.model.input.tour.TourInput;
import hcmuaf.edu.vn.bookingtravel.api.model.tour.Tour;
import hcmuaf.edu.vn.bookingtravel.api.model.tour.TourDetail;
import hcmuaf.edu.vn.bookingtravel.api.model.tour.TourFilter;
import hcmuaf.edu.vn.bookingtravel.api.model.user.UserProfile;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 14/04/2023, Thứ Sáu
 **/
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/tour/1.0.0/")
public class TourController extends BaseController {
    private final static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private CreateTourController createTourController;
    @Autowired
    private TourProfileController tourProfileController;

    @ApiOperation(value = "Thêm tour du lịch")
    @PostMapping("tour")
    public Tour createTour(@RequestBody CreateTourInput createTourInput) throws ServiceException {
        return createTourController.createTour(createTourInput);
    }

    @ApiOperation(value = "Cập nhật tour du lịch")
    @PutMapping("tour/id/{tourId}/update")
    public Tour updateTour(@PathVariable String tourId, @RequestBody TourInput tourInput) throws ServiceException {
        return tourProfileController.updateTour(tourId, tourInput);
    }

    @ApiOperation(value = "Lấy thông tin chi tiết của tour")
    @GetMapping("tour/{tourId}/detail")
    public TourDetail getTourDetail(@PathVariable String tourId) throws ServiceException {
        return tourProfileController.getTourDetail(tourId);
    }

    @ApiOperation(value = "Xóa tour du lịch")
    @DeleteMapping("tour/id/{id}/by-user/{byUser}/note/{note}")
    public Tour deleteTour(@PathVariable String id, @PathVariable String byUser, @PathVariable String note) throws ServiceException {
        return tourProfileController.deleteTour(id, byUser, note);
    }

    @ApiOperation(value = "Tìm kiếm tour du lịch theo thông tin")
    @PostMapping("tour/filter")
    public ResultList<Tour> searchTour(
            @RequestBody TourFilter tourFilter) {
        return tourProfileController.searchTour(tourFilter);
    }

    @ApiOperation(value = "Lấy tất cả tour")
    @GetMapping("tour/all")
    public List<Tour> getTourAll() {
        return tourProfileController.getTourAll();
    }


    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Object handleAllServiceException(ServiceException e) {
        LOGGER.error("ServiceException error.", e);
        return error(e.getErrorCode(), e.getErrorMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Object handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return error("internal_server_error", e.getMessage());
    }

}
