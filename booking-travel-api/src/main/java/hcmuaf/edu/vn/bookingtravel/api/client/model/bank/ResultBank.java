package hcmuaf.edu.vn.bookingtravel.api.client.model.bank;

import lombok.Data;

import java.util.List;

@Data
public class ResultBank<T> {
    private String code;
    private String desc;
    private List<T> data;
}
