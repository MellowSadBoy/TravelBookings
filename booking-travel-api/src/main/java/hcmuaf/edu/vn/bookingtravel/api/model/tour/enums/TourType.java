package hcmuaf.edu.vn.bookingtravel.api.model.tour.enums;

import hcmuaf.edu.vn.bookingtravel.api.enums.utils.BaseEnum;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 13/04/2023, Thứ Năm
 **/
public enum TourType {
    DOMESTIC("Trong nước"),
    ABROAD("Nước ngoài") ;
    private final String description;

    private TourType(String description) {
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
        for (TourType status : values()) {
            listName.append(status.toString()).append(", ");
        }
        return listName.substring(0, listName.length() - 2);
    }
    public static boolean isExist(Object current) {
        return BaseEnum.isExist(values(), current);
    }
}
