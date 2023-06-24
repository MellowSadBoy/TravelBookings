package hcmuaf.edu.vn.bookingtravel.api.model.hotel.enums;

import hcmuaf.edu.vn.bookingtravel.api.enums.utils.BaseEnum;

import java.util.HashMap;
import java.util.Random;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 13/04/2023, Thứ Năm
 **/
public enum RoomType {
    SINGLE_ROOM("phòng đơn", 1),
    DOUBLE_ROOM("phòng đôi", 2),
    TWIN_ROOM("phòng đôi có hai giường đơn", 2),
    TRIPLE_ROOM("phòng ba giường", 3),
    QUAD_ROOM("phòng bốn giường", 4),
    SUITE("phòng sang trọng", 2),
    EXECUTIVE_SUITE("phòng sang trọng cao cấp", 2),
    CONNECTING_ROOMS("hai phòng kết nối với nhau", 8),
    ADJOINING_ROOMS("hai phòng sát cạnh nhau", 8),
    PENTHOUSE("phòng trên cùng của khách sạn", 4);
    private final String description;
    private final long numberOfSeat;

    private RoomType(String description, long numberOfSeat) {
        this.numberOfSeat = numberOfSeat;
        this.description = description;
    }

    public long getNumberOfSeat() {
        return numberOfSeat;
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return this.name();
    }

    public static String getListName() {
        StringBuilder listName = new StringBuilder();
        for (RoomType status : values()) {
            listName.append(status.toString()).append(", ");
        }
        return listName.substring(0, listName.length() - 2);

    }

    public static RoomType getRandomRoomType() {
        HashMap<Integer, RoomType> roomTypeHashMap = new HashMap<>();
        int i = 0;
        for (RoomType status : values()) {
            i++;
            roomTypeHashMap.put(i, status);
        }
        return roomTypeHashMap.get(new Random().nextInt(9) + 1);

    }

    public static boolean isExist(Object current) {
        return BaseEnum.isExist(values(), current);
    }
}
