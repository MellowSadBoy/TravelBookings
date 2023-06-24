package hcmuaf.edu.vn.bookingtravel.api.base.filter;

import lombok.Data;

import java.util.List;

@Data
public class ResultList<T> {
    private long total;
    private List<T> searchList;
    private long index;
    private long maxResult;

}
