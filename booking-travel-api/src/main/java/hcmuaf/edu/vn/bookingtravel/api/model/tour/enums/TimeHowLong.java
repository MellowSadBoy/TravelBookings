package hcmuaf.edu.vn.bookingtravel.api.model.tour.enums;

import hcmuaf.edu.vn.bookingtravel.api.enums.utils.BaseEnum;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 13/04/2023, Thứ Năm
 **/
public enum TimeHowLong {
    ONE_DAY_ONE_NIGHT("1 ngày 1 đêm",86400L),
    TWO_DAY_ONE_NIGHT("2 ngày 1 đêm",172800L ),
    THREE_DAY_TWO_NIGHT("3 ngày 2 đêm",259200L),
    THREE_DAY_THREE_NIGHT("3 ngày 3 đêm",259200L),
    FOUR_DAY_THREE_NIGHT("4 ngày 3 đêm",345600L),
    FIVE_DAY_FOUR_NIGHT("5 ngày 4 đêm",432000L);
    private final String description;
    private final long seconds;

    private TimeHowLong(String description, long seconds) {
        this.description = description;
        this.seconds = seconds;
    }

    public long getSeconds() {
        return seconds;
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
        for (TimeHowLong status : values()) {
            listName.append(status.toString()).append(", ");
        }
        return listName.substring(0, listName.length() - 2);
    }

    public static boolean isExist(Object current) {
        return BaseEnum.isExist(values(), current);
    }
}
