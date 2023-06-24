package hcmuaf.edu.vn.bookingtravel.api.model.hotel.enums;

import hcmuaf.edu.vn.bookingtravel.api.enums.utils.BaseEnum;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 13/04/2023, Thứ Năm
 **/
public enum RankHotelType {
    ONE_STAR("1 sao"),
    TWO_STAR("2 sao"),
    THREE_STAR("3 sao"),
    FOUR_STAR("4 sao"),
    FIVE_STAR("5 sao");
    private final String description;

    private RankHotelType(String description) {
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
        for (RankHotelType status : values()) {
            listName.append(status.toString()).append(", ");
        }
        return listName.substring(0, listName.length() - 2);

    }

    public static boolean isExist(Object current) {
        return BaseEnum.isExist(values(), current);
    }
}
