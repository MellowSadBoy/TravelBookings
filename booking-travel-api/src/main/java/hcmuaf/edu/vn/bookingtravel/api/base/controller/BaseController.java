package hcmuaf.edu.vn.bookingtravel.api.base.controller;

import java.util.HashMap;

public class BaseController {
    protected  int a;

    protected Object error(String errorCode, String errorMessage) {
        HashMap<String, String> result = new HashMap<>();
        result.put("errorCode", errorCode);
        result.put("errorMessage", errorMessage);
        return result;
    }

    protected Object success(Object data) {
        return data;
    }
}
