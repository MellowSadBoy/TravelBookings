package hcmuaf.edu.vn.bookingtravel.api.client.model;

import lombok.Data;

import java.util.List;

@Data
public class ResultGHN<T> {
    private int code;
    private String message;
    private List<T> data;

}
