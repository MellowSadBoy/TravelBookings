package hcmuaf.edu.vn.bookingtravel.api.base.filter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mongodb.client.model.Filters;
import hcmuaf.edu.vn.bookingtravel.api.base.utils.DateUtils;
import lombok.Data;
import org.bson.conversions.Bson;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Data
public abstract class BaseFilter {

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date createdAtFrom;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date createdAtTo;
    private int maxResult = 20;
    private int offset = 0;
    private String search; // search fulltext

    public int getMaxResult() {
        if (maxResult <= 0) {
            maxResult = 20;
        }
        return maxResult;
    }

    public int getOffset() {
        if (offset < 0) {
            offset = 0;
        }
        return offset;
    }

    public Query getQuerys(HashMap<String, Object> filters) {
        if (null == filters) {
            filters = new HashMap<>();
        }
        Query query = new Query();
        for (String key : filters.keySet()) {
            if (null != filters.get(key)) {
                String cValue = String.valueOf(filters.get(key));
                if (cValue.trim().length() > 0) {
                    query.addCriteria(Criteria.where(key).is(filters.get(key)));
                }
            }
        }
        return query;
    }

    protected abstract HashMap<String,Object> getExtendFilters();

}
