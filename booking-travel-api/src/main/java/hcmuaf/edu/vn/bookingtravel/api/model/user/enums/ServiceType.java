package hcmuaf.edu.vn.bookingtravel.api.model.user.enums;

import hcmuaf.edu.vn.bookingtravel.api.enums.utils.BaseEnum;

public enum ServiceType {
    GOOGLE("Tài khoản GOOGLE"),
    FACEBOOK("Tài khoản FACEBOOK"),
    NORMALLY("Tài khoản Thường");

    private final String description;


    private ServiceType(String description) {
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
        for (ServiceType type : values()) {
            listName.append(type.toString()).append(", ");
        }
        return listName.substring(0, listName.length() - 2);
    }
    public static boolean isExist(Object current) {
        return BaseEnum.isExist(values(), current);
    }


}
