package hcmuaf.edu.vn.bookingtravel.api.model.user.enums;


import hcmuaf.edu.vn.bookingtravel.api.enums.utils.BaseEnum;

public enum RoleType {
    ADMIN("Quản trị viên"),
    EMPLOYEE("Nhân viên"),
    CUSTOMER("Khách hàng");
    private final String description;

    private RoleType(String description) {
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
        for (RoleType type : values()) {
            listName.append(type.toString()).append(", ");
        }
        return listName.substring(0, listName.length() - 2);

    }
    public static boolean isExist(Object current) {
        return BaseEnum.isExist(values(), current);
    }

}
