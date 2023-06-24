package hcmuaf.edu.vn.bookingtravel.api.manager;

import hcmuaf.edu.vn.bookingtravel.api.base.filter.ResultList;
import hcmuaf.edu.vn.bookingtravel.api.base.manager.BaseManager;
import hcmuaf.edu.vn.bookingtravel.api.model.hotel.Room;
import hcmuaf.edu.vn.bookingtravel.api.model.hotel.RoomFilter;
import org.bson.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public class RoomManager extends BaseManager<Room> {
    public RoomManager() {
        super(Room.class.getSimpleName(), Room.class);
    }

    public void createRooms(List<Room> rooms) {
        rooms.forEach(room -> {
            room.setId(generateId());
            room.setCreatedAt(new Date());
        });
        create(rooms, "Nguyễn Văn Thanh", "Tạo danh sách các phòng cho khách sạn");
    }

    public Room getRoom(String roomId) {
        return getById(roomId);
    }

    public List<Room> getRooms(String hotelId) {
        return getList("hotelId", hotelId);
    }

    public void deleteRoom(String hotelId, String byUser) {
        String note = "Xoá phòng của khách sạn " + hotelId;
        deleteByAttribute("hotelId", hotelId, byUser, note);
    }

    public ResultList<Room> filterRoom(RoomFilter roomFilter) {
        Query query = roomFilter.getQuerys(roomFilter.getExtendFilters());
        query.addCriteria(Criteria.where("price.amount").gte(roomFilter.getPriceFrom() == null
                ? 0 : roomFilter.getPriceFrom()).lte(
                (roomFilter.getPriceTo() == null
                        || roomFilter.getPriceTo() == 0) ? Double.MAX_VALUE : roomFilter.getPriceTo()));
        if (null != roomFilter.getTypes() && roomFilter.getTypes().size() != 0) {
            query.addCriteria(Criteria.where("type").in(roomFilter.getTypes()));
        }
        if (null != roomFilter.getProvinceCodes() && roomFilter.getProvinceCodes().size() != 0) {
            query.addCriteria(Criteria.where("province").in(roomFilter.getProvinceCodes()));
        }
        if (null != roomFilter.getHotelTypes() && roomFilter.getHotelTypes().size() != 0) {
            query.addCriteria(Criteria.where("hotelType").in(roomFilter.getHotelTypes()));
        }
        return getResultList(roomFilter.getSearch(), roomFilter.getCreatedAtTo(),
                roomFilter.getCreatedAtTo(), roomFilter.getOffset(), roomFilter.getMaxResult(), query);
    }
}
