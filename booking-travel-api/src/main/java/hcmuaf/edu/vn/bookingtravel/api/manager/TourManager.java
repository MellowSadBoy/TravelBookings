package hcmuaf.edu.vn.bookingtravel.api.manager;

import hcmuaf.edu.vn.bookingtravel.api.base.filter.ResultList;
import hcmuaf.edu.vn.bookingtravel.api.base.manager.BaseManager;
import hcmuaf.edu.vn.bookingtravel.api.base.utils.DateUtils;
import hcmuaf.edu.vn.bookingtravel.api.model.input.tour.TourInput;
import hcmuaf.edu.vn.bookingtravel.api.model.input.user.UpdateInfoUserInput;
import hcmuaf.edu.vn.bookingtravel.api.model.tour.TourFilter;
import hcmuaf.edu.vn.bookingtravel.api.model.tour.enums.TourFilterType;
import hcmuaf.edu.vn.bookingtravel.api.model.tour.enums.TourStatus;
import hcmuaf.edu.vn.bookingtravel.api.model.tour.Tour;
import hcmuaf.edu.vn.bookingtravel.api.model.tour.enums.TourType;
import org.springframework.data.mongodb.core.index.TextIndexDefinition;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.*;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 13/04/2023, Thứ Năm
 **/
@Repository
public class TourManager extends BaseManager<Tour> {
    public TourManager() {
        super(Tour.class.getSimpleName(), Tour.class);
    }

    public Tour createTour(Tour tour) {
        tour.setId(generateId());
        tour.setCreatedAt(new Date());
        tour.setStatus(null == tour.getStatus() ? TourStatus.ACTIVE : tour.getStatus());
        return create(tour);
    }

    @PostConstruct
    public void createTextIndex() {
        mongoTemplate.indexOps(Tour.class).ensureIndex(new TextIndexDefinition.TextIndexDefinitionBuilder().onField("name").build());
    }

    public Tour updateInfoTour(String tourId, TourInput updateInput) {
        HashMap<String, Object> updateDocument = new HashMap<>();
        updateDocument.put("updatedAt", new Date());
        Field[] fields = updateInput.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(updateInput);
                if (value != null)
                    updateDocument.put(field.getName(), value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return update(tourId, updateDocument, null, "Cập nhật thông tin tour " + tourId);

    }


    public Tour getTour(String tourId) {
        return getById(tourId);
    }

    public Tour updateQuantityEmpty(String tourId, long quantityEmpty) {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("quantityEmpty", quantityEmpty);
        return update(tourId, hashMap, "Vũ Văn Minh", "Cập nhật lại số lượng còn trống");
    }

    public Tour updateStartDate(String tourId, Date startDate) {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("startDate", startDate);
        return update(tourId, hashMap, "Vũ Văn Minh", "Cập nhật lại ngày bắt đầu");
    }

    public void deleteTour(String tourId, String byUser, String note) {
        delete(tourId, byUser, note);
    }

    public ResultList<Tour> filterTour(TourFilter filterData) {
        HashMap<String, Object> mapFilter = filterData.getExtendFilters();
        Query query = filterData.getQuerys(mapFilter);
        if (filterData.getFilterType() != null) {
            if (filterData.getFilterType().equals(TourFilterType.TOUR_SUMMER)) {
                query.addCriteria(Criteria.where("startDate").
                        gte(Objects.requireNonNull(DateUtils.getStartDay(
                                new Date(LocalDate.ofYearDay(LocalDate.now().getYear(), 1).withMonth(6).withDayOfMonth(1).atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli())))).
                        lte(Objects.requireNonNull(DateUtils.getEndDay(
                                new Date(LocalDate.ofYearDay(LocalDate.now().getYear(), 1).withMonth(8).withDayOfMonth(31).atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli())))));
            }
            if (filterData.getFilterType().equals(TourFilterType.TOUR_LAST_HOUR)) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DATE, 2);
                Date dateTwo = calendar.getTime();
                query.addCriteria(Criteria.where("startDate").
                        gte(Objects.requireNonNull(DateUtils.getStartDay(
                                new Date()))).
                        lte(Objects.requireNonNull(DateUtils.getEndDay(dateTwo))));

            }
        }
        return getResultList(filterData.getSearch(), filterData.getCreatedAtFrom(),
                filterData.getCreatedAtTo(),
                filterData.getOffset(),
                filterData.getMaxResult(),
                query);

    }


    public List<Tour> getTourAll() {
        List<Tour> tours = mongoTemplate.findAll(Tour.class);
        return tours;

    }

}
