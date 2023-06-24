package hcmuaf.edu.vn.bookingtravel.api.restcontroller;

import hcmuaf.edu.vn.bookingtravel.api.base.controller.BaseController;
import hcmuaf.edu.vn.bookingtravel.api.base.exception.ServiceException;
import hcmuaf.edu.vn.bookingtravel.api.base.filter.ResultList;
import hcmuaf.edu.vn.bookingtravel.api.controller.hotel.DeleteHotelController;
import hcmuaf.edu.vn.bookingtravel.api.controller.hotel.CreateHotelController;
import hcmuaf.edu.vn.bookingtravel.api.controller.hotel.HotelProfileController;
import hcmuaf.edu.vn.bookingtravel.api.controller.hotel.UpdateHotelController;
import hcmuaf.edu.vn.bookingtravel.api.controller.utils.HotelUtilsController;
import hcmuaf.edu.vn.bookingtravel.api.model.hotel.*;
import hcmuaf.edu.vn.bookingtravel.api.model.input.hotel.CreateHotelInput;
import hcmuaf.edu.vn.bookingtravel.api.model.input.hotel.UpdateInfoHotelInput;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/hotel/1.0.0/")
public class HotelController extends BaseController {

    private final static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private CreateHotelController createHotelController;

    @Autowired
    private HotelUtilsController hotelUtilsController;

    @Autowired
    private DeleteHotelController deleteHotelController;

    @Autowired
    private UpdateHotelController hotelUpdateController;

    @Autowired
    private HotelProfileController hotelProfileController;

    @ApiOperation(value = "Thêm khách sạn")
    @PostMapping("hotel")
    public Hotel createHotel(@RequestBody CreateHotelInput hotelInput) throws ServiceException {
        return createHotelController.createHotel(hotelInput);
    }


    @ApiOperation(value = "Xoá khách sạn")
    @DeleteMapping(value = "hotel/{hotelId}/deleted/{byUser}")
    public Hotel deletedHotel(@PathVariable String hotelId, @PathVariable String byUser) throws ServiceException {
        return deleteHotelController.deleteHotel(hotelId, byUser);
    }

    @ApiOperation(value = "Lấy thông tin của khách sạn")
    @GetMapping("hotel/{hotelId}")
    public Hotel getHotel(@PathVariable String hotelId) throws ServiceException {
        return hotelUtilsController.getHotel(hotelId);
    }

    @ApiOperation(value = "Lấy thông tin của khách sạn")
    @GetMapping("hotel/{hotelId}/detail")
    public HotelDetail getHotelDetail(@PathVariable String hotelId) throws ServiceException {
        return hotelProfileController.getHotelDetail(hotelId);
    }

    @ApiOperation(value = "Cập nhật thông tin cơ bản của khách sạn")
    @PutMapping("hotel/{hotelId}/info-basic")
    public Hotel updateHotelInfo(@PathVariable String hotelId, @RequestBody UpdateInfoHotelInput statusBody) throws ServiceException {
        return hotelUpdateController.updateHotelInfo(hotelId, statusBody);
    }

    @ApiOperation(value = "Lấy tất cả hotel")
    @GetMapping("hotel/all")
    public List<Hotel> getAllHotels() {
        return hotelProfileController.getAllHotels();
    }

    @ApiOperation(value = "Tìm kiếm hotel du lịch theo thông tin")
    @PostMapping("hotel/filter")
    public ResultList<Hotel> searchHotel(
            @RequestBody HotelFilter hotelFilter) {
        return hotelProfileController.searchHotel(hotelFilter);
    }

    @ApiOperation(value = "Lấy thông tin chi tiết của phòng khách sạn")
    @GetMapping("room/{roomId}/detail")
    public RoomDetail getRoomDetail(@PathVariable String roomId) throws ServiceException {
        return hotelProfileController.getRoomDetail(roomId);
    }
    @ApiOperation(value = "Tìm kiếm phòng khách sạn theo thông tin")
    @PostMapping("room/filter")
    public ResultList<Room> searchRoom(
            @RequestBody RoomFilter roomFilter) {
        return hotelProfileController.searchRoom(roomFilter);
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