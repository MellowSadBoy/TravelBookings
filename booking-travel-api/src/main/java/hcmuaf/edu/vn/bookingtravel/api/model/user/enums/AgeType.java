package hcmuaf.edu.vn.bookingtravel.api.model.user.enums;

import hcmuaf.edu.vn.bookingtravel.api.enums.utils.BaseEnum;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 13/04/2023, Thứ Năm
 **/
public enum AgeType {
    CHILDREN("Dưới 10 tuổi"),
    ADULTS("Từ 10 tuổi trở lên") ;
    private final String description;

    private AgeType(String description) {
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
        for (AgeType status : values()) {
            listName.append(status.toString()).append(", ");
        }
        return listName.substring(0, listName.length() - 2);
    }
    public static boolean isExist(Object current) {
        return BaseEnum.isExist(values(), current);
    }
}
