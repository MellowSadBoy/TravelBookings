package hcmuaf.edu.vn.bookingtravel.api.model.tourBooked.enums;

import hcmuaf.edu.vn.bookingtravel.api.enums.utils.BaseEnum;
import hcmuaf.edu.vn.bookingtravel.api.model.tour.enums.TimeHowLong;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 09/05/2023, Thứ Ba
 **/
public enum TourBookedStatus {
    READY("Mới đặt"), START("Bắt đầu hành trình"),
    END("Kết thúc hành trình"), CANCELLED("Đã huỷ");
    private final String description;

    private TourBookedStatus(String description) {
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
        for (TourBookedStatus status : values()) {
            listName.append(status.toString()).append(", ");
        }
        return listName.substring(0, listName.length() - 2);
    }

    public static boolean isExist(Object current) {
        return BaseEnum.isExist(values(), current);
    }
}
