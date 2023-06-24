package hcmuaf.edu.vn.bookingtravel.api.restcontroller;

import hcmuaf.edu.vn.bookingtravel.api.base.controller.BaseController;
import hcmuaf.edu.vn.bookingtravel.api.base.exception.ServiceException;
import hcmuaf.edu.vn.bookingtravel.api.manager.EnterpriseManager;
import hcmuaf.edu.vn.bookingtravel.api.model.Enterprise;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 15/04/2023, Thứ Bảy
 **/
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/enterprise/1.0.0/")
public class EnterpriseController extends BaseController {
    private final static Logger LOGGER = LoggerFactory.getLogger(EnterpriseController.class);

    @Autowired
    private EnterpriseManager enterpriseManager;

    @ApiOperation(value = "Thông tin công ty ")
    @GetMapping("enterprise/info")
    public Enterprise getEnterprise() {
        return enterpriseManager.getEnterprise();
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
