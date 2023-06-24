package hcmuaf.edu.vn.bookingtravel.api.model.hotelBooked.enums;

import hcmuaf.edu.vn.bookingtravel.api.enums.utils.BaseEnum;

public enum HotelBookedStatus {
    READY("Mới đặt"), START("Bắt đầu thuê"),
    END("Kết thúc thuê"), CANCELLED("Huỷ");
    private final String description;

    private HotelBookedStatus(String description) {
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
        for (HotelBookedStatus status : values()) {
            listName.append(status.toString()).append(", ");
        }
        return listName.substring(0, listName.length() - 2);
    }

    public static boolean isExist(Object current) {
        return BaseEnum.isExist(values(), current);
    }
}
