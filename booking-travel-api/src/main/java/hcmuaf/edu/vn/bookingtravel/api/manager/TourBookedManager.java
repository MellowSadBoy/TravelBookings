package hcmuaf.edu.vn.bookingtravel.api.manager;

import hcmuaf.edu.vn.bookingtravel.api.base.filter.ResultList;
import hcmuaf.edu.vn.bookingtravel.api.base.manager.BaseManager;
import hcmuaf.edu.vn.bookingtravel.api.model.input.tourBooked.TourBookedUpdateInput;
import hcmuaf.edu.vn.bookingtravel.api.model.tour.Tour;
import hcmuaf.edu.vn.bookingtravel.api.model.tourBooked.TourBooked;
import hcmuaf.edu.vn.bookingtravel.api.model.tourBooked.TourBookedFilter;
import hcmuaf.edu.vn.bookingtravel.api.model.tourBooked.enums.TourBookedStatus;
import org.springframework.data.mongodb.core.index.TextIndexDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;


/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 09/05/2023, Thứ Ba
 **/
@Repository
public class TourBookedManager extends BaseManager<TourBooked> {

    public TourBookedManager() {
        super(TourBooked.class.getSimpleName(), TourBooked.class);
    }

    public TourBooked createTourBooked(TourBooked tourBooked) {
        tourBooked.setId(generateId());
        tourBooked.setCreatedAt(new Date());
        return create(tourBooked);
    }

    @PostConstruct
    public void createTextIndex() {
        mongoTemplate.indexOps(TourBooked.class).ensureIndex(new TextIndexDefinition.TextIndexDefinitionBuilder()
                .onField("tourName").onField("customerName").build());
    }

    public ResultList<TourBooked> filterTourBooked(TourBookedFilter filterData) {
        HashMap<String, Object> mapFilter = filterData.getExtendFilters();
        Query query = filterData.getQuerys(mapFilter);
        return getResultList(filterData.getSearch(), filterData.getCreatedAtFrom(),
                filterData.getCreatedAtTo(),
                filterData.getOffset(),
                filterData.getMaxResult(),
                query);

    }

    public TourBooked updateTourBooked(String tourBookedId, TourBookedUpdateInput bookedInput) {
        HashMap<String, Object> updateDocument = new HashMap<>();
        updateDocument.put("updatedAt", new Date());
        Field[] fields = bookedInput.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getName().equals("tourId")) {
                continue;
            }
            try {
                Object value = field.get(bookedInput);
                if (value != null)
                    updateDocument.put(field.getName(), value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return update(tourBookedId, updateDocument, null, "Cập nhật thông tin tour booked " + tourBookedId);

    }

    public TourBooked cancelTourBooked(String tourBookedId, String byUser) {
        HashMap<String, Object> updateDocument = new HashMap<>();
        updateDocument.put("updatedAt", new Date());
        updateDocument.put("cancelledAt", new Date());
        updateDocument.put("cancelName", byUser);
        updateDocument.put("status", TourBookedStatus.CANCELLED.toString());
        return update(tourBookedId, updateDocument, byUser, "Huỷ tour booked " + tourBookedId);

    }

    public TourBooked updateStatusTourBooked(String tourBookedId, TourBookedStatus status, String byUser) {
        HashMap<String, Object> updateDocument = new HashMap<>();
        updateDocument.put("updatedAt", new Date());
        updateDocument.put("status", status.toString());
        return update(tourBookedId, updateDocument, byUser, "Cập nhật trạng thái tour booked " + tourBookedId);

    }

    public TourBooked getTourBooked(String tourBookedId) {
        return getById(tourBookedId);
    }

    public TourBooked updateTourBookedTour(String tourBookedId, Tour tour) {
        HashMap<String, Object> updateDocument = new HashMap<>();
        updateDocument.put("updatedAt", new Date());
        updateDocument.put("tourId", tour.getId());
        updateDocument.put("tourName", tour.getName());
        updateDocument.put("featuredImgTour", tour.getFeatureImgUrl());
        updateDocument.put("timeHowLong", tour.getTimeHowLong().toString());
        return update(tourBookedId, updateDocument, null, "Cập nhật thông tin tour booked " + tourBookedId);
    }
}
