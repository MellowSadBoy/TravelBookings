package hcmuaf.edu.vn.bookingtravel.api.model.hotel.enums;

import hcmuaf.edu.vn.bookingtravel.api.enums.utils.BaseEnum;
import hcmuaf.edu.vn.bookingtravel.api.model.hotel.RoomAdjustment;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 27/04/2023, Thứ Năm
 **/
public enum RoomAdjustmentType {
    DISCOUNT("Giảm giá"), SURCHARGE("Phụ thu");
    private final String description;

    private RoomAdjustmentType(String description) {
        this.description = description;
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
        for (RoomAdjustmentType status : values()) {
            listName.append(status.toString()).append(", ");
        }
        return listName.substring(0, listName.length() - 2);

    }

    public static boolean isExist(Object current) {
        return BaseEnum.isExist(values(), current);
    }
}
