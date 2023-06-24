package hcmuaf.edu.vn.bookingtravel.api.manager;

import hcmuaf.edu.vn.bookingtravel.api.base.filter.ResultList;
import hcmuaf.edu.vn.bookingtravel.api.base.manager.BaseManager;
import hcmuaf.edu.vn.bookingtravel.api.model.hotel.Room;
import hcmuaf.edu.vn.bookingtravel.api.model.hotelBooked.HotelBooked;
import hcmuaf.edu.vn.bookingtravel.api.model.hotelBooked.HotelBookedFilter;
import hcmuaf.edu.vn.bookingtravel.api.model.hotelBooked.enums.HotelBookedStatus;
import hcmuaf.edu.vn.bookingtravel.api.model.input.hotelBooked.HotelBookedInput;
import hcmuaf.edu.vn.bookingtravel.api.model.input.hotelBooked.HotelBookedUpdateInput;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;


import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;


@Repository
public class HotelBookedManager extends BaseManager<HotelBooked> {

    public HotelBookedManager() {
        super(HotelBooked.class.getSimpleName(), HotelBooked.class);
    }

    public HotelBooked createHotelBooked(HotelBooked hotelBooked) {
        hotelBooked.setId(generateId());
        hotelBooked.setCreatedAt(new Date());
        return create(hotelBooked);
    }

    public HotelBooked getHotelBooked(String hotelBookedId) {
        return getById(hotelBookedId);
    }

    public ResultList<HotelBooked> filterHotelBooked(HotelBookedFilter filterData) {
        HashMap<String, Object> mapFilter = filterData.getExtendFilters();
        Query query = filterData.getQuerys(mapFilter);
        return getResultList(filterData.getSearch(), filterData.getCreatedAtFrom(),
                filterData.getCreatedAtTo(),
                filterData.getOffset(),
                filterData.getMaxResult(),
                query);

    }

    public HotelBooked updateHotelBooked(String hotelBookedId, HotelBookedUpdateInput bookedInput) {
        HashMap<String, Object> updateDocument = new HashMap<>();
        updateDocument.put("updatedAt", new Date());
        Field[] fields = bookedInput.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getName().equals("roomId")) {
                continue;
            }
            try {
                Object value = field.get(bookedInput);
                updateDocument.put(field.getName(), value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return update(hotelBookedId, updateDocument, null, "Cập nhật thông tin hotel booked " + hotelBookedId);

    }

    public HotelBooked updateHotelBookedRoom(String hotelBookedId, Room room) {
        HashMap<String, Object> updateDocument = new HashMap<>();
        updateDocument.put("updatedAt", new Date());
        updateDocument.put("room", room);
        updateDocument.put("hotelId", room.getHotelId());
        updateDocument.put("hotelName", room.getHotelName());
        return update(hotelBookedId, updateDocument, null, "Cập nhật thông tin hotel booked " + hotelBookedId);

    }

    public HotelBooked cancelHotelBooked(String hotelBookedId, String byUser) {
        HashMap<String, Object> updateDocument = new HashMap<>();
        updateDocument.put("updatedAt", new Date());
        updateDocument.put("cancelledAt", new Date());
        updateDocument.put("cancelName", byUser);
        updateDocument.put("status", HotelBookedStatus.CANCELLED.toString());
        return update(hotelBookedId, updateDocument, byUser, "Huỷ hotel booked " + hotelBookedId);

    }
}
