package hcmuaf.edu.vn.bookingtravel.api.restcontroller;

import hcmuaf.edu.vn.bookingtravel.api.base.controller.BaseController;
import hcmuaf.edu.vn.bookingtravel.api.base.exception.ServiceException;
import hcmuaf.edu.vn.bookingtravel.api.base.filter.ResultList;
import hcmuaf.edu.vn.bookingtravel.api.controller.tourBooked.CreateTourBookedController;
import hcmuaf.edu.vn.bookingtravel.api.controller.tourBooked.TourBookedProfileController;
import hcmuaf.edu.vn.bookingtravel.api.model.input.tourBooked.TourBookedInput;
import hcmuaf.edu.vn.bookingtravel.api.model.input.tourBooked.TourBookedUpdateInput;
import hcmuaf.edu.vn.bookingtravel.api.model.tourBooked.TourBooked;
import hcmuaf.edu.vn.bookingtravel.api.model.tourBooked.TourBookedDetail;
import hcmuaf.edu.vn.bookingtravel.api.model.tourBooked.TourBookedFilter;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 09/05/2023, Thứ Ba
 **/
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/tour-booked/1.0.0/")
public class TourBookedController extends BaseController {
    private final static Logger LOGGER = LoggerFactory.getLogger(TourBookedController.class);


    @Autowired
    private CreateTourBookedController createTourBookedController;
    @Autowired
    private TourBookedProfileController tourBookedProfileController;

    @ApiOperation(value = "Thêm tour đã đặt")
    @PostMapping("tour-booked")
    public TourBooked createTourBooked(@RequestBody TourBookedInput createTourInput) throws ServiceException {
        return createTourBookedController.createTourBooked(createTourInput);
    }
    @ApiOperation(value = "Lấy thông tin chi tiết của tour đã đặt")
    @GetMapping("tour-booked/{tourBookedId}/detail")
    public TourBookedDetail getTourBookedDetail(@PathVariable String tourBookedId) throws ServiceException {
        return tourBookedProfileController.getTourBookedDetail(tourBookedId);
    }
    @ApiOperation(value = "Cập nhật thông tin tour đã đặt")
    @PutMapping("tour-booked/info/{tourBookedId}")
    public TourBooked updateTourBooked(@PathVariable String tourBookedId, @RequestBody TourBookedUpdateInput bookedInput) throws ServiceException {
        return tourBookedProfileController.updateTourBooked(tourBookedId, bookedInput);
    }

    @ApiOperation(value = "Huỷ tour đã đặt")
    @PutMapping("tour-booked/cancel/{tourBookedId}/{byUser}")
    public TourBooked cancelTourBooked(@PathVariable String tourBookedId, @PathVariable String byUser) throws ServiceException {
        return tourBookedProfileController.cancelTourBooked(tourBookedId, byUser);
    }
    @ApiOperation(value = "Tìm kiếm tour đã đặt theo thông tin")
    @PostMapping("tour-booked/filter")
    public ResultList<TourBooked> searchTourBooked(
            @RequestBody TourBookedFilter tourFilter) {
        return tourBookedProfileController.searchTourBooked(tourFilter);
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
