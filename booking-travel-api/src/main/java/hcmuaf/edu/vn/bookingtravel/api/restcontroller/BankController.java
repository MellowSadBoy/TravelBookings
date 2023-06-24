package hcmuaf.edu.vn.bookingtravel.api.restcontroller;

import hcmuaf.edu.vn.bookingtravel.api.base.controller.BaseController;
import hcmuaf.edu.vn.bookingtravel.api.base.controller.ClientException;
import hcmuaf.edu.vn.bookingtravel.api.base.exception.ExceptionCode;
import hcmuaf.edu.vn.bookingtravel.api.base.exception.ServiceException;
import hcmuaf.edu.vn.bookingtravel.api.base.model.ResponseData;
import hcmuaf.edu.vn.bookingtravel.api.client.BankClient;
import hcmuaf.edu.vn.bookingtravel.api.client.model.bank.Bank;
import hcmuaf.edu.vn.bookingtravel.api.client.model.bank.RecordHistory;
import hcmuaf.edu.vn.bookingtravel.api.client.model.bank.ResultHistory;
import hcmuaf.edu.vn.bookingtravel.api.client.model.bank.SyncBank;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/bank/1.0.0/")
public class BankController extends BaseController {
    private final static Logger LOGGER = LoggerFactory.getLogger(BankController.class);
    @Value("${BankClient.url}")
    private String bankService;
    @Value("${qr.url}")
    private String qrService;
    @Value("${BankID}")
    private String bankID;
    @Value("${account.number.store.bank}")
    private String accountNumber;
    @Value("${template}")
    private String template;
    @Value("${add.info}")
    private String addInfo;
    @Value("${account.name}")
    private String accountName;
    @Value("${casso.token}")
    private String cassoToken;
    @Value("${casso.service}")
    private String cassoService;

    @ApiOperation(value = "Lấy ra danh sách ngân hàng")
    @GetMapping("banks")
    public List<Bank> getBanks() throws ServiceException {
        BankClient bankClient = null;
        List<Bank> banks = new ArrayList<>();
        try {
            bankClient = new BankClient(bankService);
            banks = bankClient.getBankList().getData();

        } catch (ClientException e) {
            throw new ServiceException(e.getErrorCode(), e.getMessage());
        }
        return banks;
    }

    @ApiOperation(value = "Thông tin lịch sử giao dịch")
    @GetMapping("bank/history")
    public ResultHistory getHistoryBank() throws ServiceException {
        BankClient bankClient = null;
        ResultHistory resultHistory = null;
        try {
            bankClient = new BankClient(cassoService);
            SyncBank syncBank = bankClient.syncBankNow(accountNumber, cassoToken);
            resultHistory = bankClient.getHistoryBank(cassoToken);

        } catch (ClientException e) {
            throw new ServiceException(e.getErrorCode(), e.getMessage());
        }
        return resultHistory;
    }

    @ApiOperation(value = "Kiểm tra đã thanh toán chưa")
    @GetMapping("bank/history/check-payment")
    public ResponseData checkPayment(@RequestParam double amount) throws ServiceException {
        BankClient bankClient = null;
        ResultHistory resultHistory = null;
        try {
            bankClient = new BankClient(cassoService);
            SyncBank syncBank = bankClient.syncBankNow(accountNumber, cassoToken);
            resultHistory = bankClient.getHistoryBank(cassoToken);
            if (resultHistory != null) {
                RecordHistory recordHistory = resultHistory.getData().getRecords().get(0);
                if (recordHistory.getAmount() == amount) {
                    return new ResponseData(ExceptionCode.SUCCESS_EXCEPTION.code, "Đã được thanh toán", true);
                } else
                    return new ResponseData(ExceptionCode.SUCCESS_EXCEPTION.code, "Chưa được thanh toán", false);

            }

        } catch (ClientException e) {
            return new ResponseData(ExceptionCode.ERROR_EXCEPTION.code, e.getErrorCode(), e.getMessage());
        }
        return new ResponseData(ExceptionCode.SUCCESS_EXCEPTION.code, "Chưa được thanh toán", false);

    }


    @ApiOperation(value = "Mã QR thanh toán VietQR")
    @PostMapping("qr-code-info")
    public URL getQrImage(@RequestParam("amount") double amount, @RequestParam("addInfo") String addInfo) throws ServiceException {
        URL urlImageQr = null;
        BankClient bankClient = null;

        try {
            bankClient = new BankClient(qrService);
            if (null == addInfo || addInfo.length() == 0 ||
                    "String".equalsIgnoreCase(addInfo) || "null".equalsIgnoreCase(addInfo)) {
                addInfo = this.addInfo;
            }
            if (amount <= 0) {
                throw new ServiceException("not_found", "Chưa nhập số tiền cần thanh toán");

            }
            urlImageQr = bankClient.getQRImage(bankID, accountNumber, template, amount, addInfo, accountName);

        } catch (ClientException | MalformedURLException e) {
            e.printStackTrace();

        }
        return urlImageQr;
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
