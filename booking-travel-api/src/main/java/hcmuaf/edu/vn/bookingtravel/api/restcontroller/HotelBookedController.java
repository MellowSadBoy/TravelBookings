package hcmuaf.edu.vn.bookingtravel.api.restcontroller;

import hcmuaf.edu.vn.bookingtravel.api.base.controller.BaseController;
import hcmuaf.edu.vn.bookingtravel.api.base.exception.ServiceException;
import hcmuaf.edu.vn.bookingtravel.api.base.filter.ResultList;
import hcmuaf.edu.vn.bookingtravel.api.controller.hotelBooked.CreateHotelBookedController;
import hcmuaf.edu.vn.bookingtravel.api.controller.hotelBooked.HotelBookedProfileController;
import hcmuaf.edu.vn.bookingtravel.api.model.input.hotelBooked.HotelBookedInput;
import hcmuaf.edu.vn.bookingtravel.api.model.hotelBooked.HotelBooked;
import hcmuaf.edu.vn.bookingtravel.api.model.hotelBooked.HotelBookedFilter;
import hcmuaf.edu.vn.bookingtravel.api.model.input.hotelBooked.HotelBookedUpdateInput;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/hotel-booked/1.0.0/")
public class HotelBookedController extends BaseController {
    private final static Logger LOGGER = LoggerFactory.getLogger(HotelBookedController.class);

    @Autowired
    private CreateHotelBookedController createHotelBookedController;
    @Autowired
    private HotelBookedProfileController hotelBookedProfileController;

    @ApiOperation(value = "Thêm hotel đã đặt")
    @PostMapping("hotel-booked")
    public HotelBooked createHotelBooked(@RequestBody HotelBookedInput createHotelInput) throws ServiceException {
        return createHotelBookedController.createHotelBooked(createHotelInput);
    }
    @ApiOperation(value = "Lấy thông tin chi tiết của hotel đã đặt")
    @GetMapping("hotel-booked/{hotelBookedId}/detail")
    public HotelBooked getHotelBookedDetail(@PathVariable String hotelBookedId) throws ServiceException {
        return hotelBookedProfileController.getHotelBooked(hotelBookedId);
    }
    @ApiOperation(value = "Cập nhật thông tin hotel đã đặt")
    @PutMapping("hotel-booked/info/{hotelBookedId}")
    public HotelBooked updateHotelBooked(@PathVariable String hotelBookedId, @RequestBody HotelBookedUpdateInput bookedInput) throws ServiceException {
        return hotelBookedProfileController.updateHotelBooked(hotelBookedId, bookedInput);
    }

    @ApiOperation(value = "Huỷ hotel đã đặt")
    @PutMapping("hotel-booked/cancel/{hotelBookedId}/{byUser}")
    public HotelBooked cancelHotelBooked(@PathVariable String hotelBookedId, @PathVariable String byUser) throws ServiceException {
        return hotelBookedProfileController.cancelHotelBooked(hotelBookedId, byUser);
    }

    @ApiOperation(value = "Tìm kiếm hotel đã đặt theo thông tin")
    @PostMapping("hotel-booked/filter")
    public ResultList<HotelBooked> searchHotelBooked(
            @RequestBody HotelBookedFilter hotelFilter) {
        return hotelBookedProfileController.searchHotelBooked(hotelFilter);
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
