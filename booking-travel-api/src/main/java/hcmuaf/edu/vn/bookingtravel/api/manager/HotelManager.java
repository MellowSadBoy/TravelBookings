package hcmuaf.edu.vn.bookingtravel.api.manager;

import hcmuaf.edu.vn.bookingtravel.api.base.filter.ResultList;
import hcmuaf.edu.vn.bookingtravel.api.base.manager.BaseManager;
import hcmuaf.edu.vn.bookingtravel.api.base.utils.DateUtils;
import hcmuaf.edu.vn.bookingtravel.api.model.hotel.Hotel;
import hcmuaf.edu.vn.bookingtravel.api.model.hotel.HotelFilter;
import hcmuaf.edu.vn.bookingtravel.api.model.input.hotel.UpdateInfoHotelInput;
import hcmuaf.edu.vn.bookingtravel.api.model.tour.Tour;
import hcmuaf.edu.vn.bookingtravel.api.model.tour.TourFilter;
import hcmuaf.edu.vn.bookingtravel.api.model.tour.enums.TourFilterType;
import hcmuaf.edu.vn.bookingtravel.api.model.user.User;
import org.bson.Document;
import org.springframework.data.mongodb.core.index.TextIndexDefinition;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.*;


@Repository
public class HotelManager extends BaseManager<Hotel> {
    public HotelManager() {
        super(Hotel.class.getSimpleName(), Hotel.class);
    }

    public Hotel createHotel(Hotel hotel) {
        hotel.setId(generateId());
        hotel.setCreatedAt(new Date());
        return create(hotel);
    }

    public Hotel getHotel(String hotelId) {
        return getById(hotelId);
    }
    @PostConstruct
    public void createTextIndex() {
        mongoTemplate.indexOps(Hotel.class).ensureIndex(new TextIndexDefinition.TextIndexDefinitionBuilder().onField("name")
                .onField("fax").onField("_id").build());
    }
    public void deleteHotel(String hotelId, String byUser) {
        String note = "Xoá khách sạn " + hotelId;
        delete(hotelId, byUser, note);
    }


    public Hotel updateInfoHotel(String hotelId, UpdateInfoHotelInput updateInfoHotel) {

        HashMap<String, Object> updateDocument = new HashMap<>();
        updateDocument.put("updatedAt", new Date());
        if (updateInfoHotel.getName() != null)
            updateDocument.put("name", updateInfoHotel.getName());
        if (updateInfoHotel.getFax() != null)
            updateDocument.put("fax", updateInfoHotel.getFax());
        if (updateInfoHotel.getAddress() != null)
            updateDocument.put("gender", updateInfoHotel.getAddress().toString());
        if (updateInfoHotel.getImgUrls() != null)
            updateDocument.put("imageUrl", updateInfoHotel.getImgUrls());

        return update(hotelId, updateDocument, null, "Cập nhật thông tin khách sạn " + hotelId);
    }

    public List<Hotel> getAllHotels() {
        return mongoTemplate.findAll(Hotel.class);
    }

    public ResultList<Hotel> filterHotel(HotelFilter filterData) {
        HashMap<String, Object> mapFilter = filterData.getExtendFilters();
        Query query = filterData.getQuerys(mapFilter);

        return getResultList(filterData.getSearch(), filterData.getCreatedAtFrom(),
                filterData.getCreatedAtTo(),
                filterData.getOffset(),
                filterData.getMaxResult(),
                query);

    }


}
