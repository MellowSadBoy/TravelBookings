package hcmuaf.edu.vn.bookingtravel.api.model.user.enums;


import hcmuaf.edu.vn.bookingtravel.api.enums.utils.BaseEnum;

public enum GenderType {
    MAN("Nam"), WOMEN("Nữ"), OTHER("Khác");
    private final String description;

    private GenderType(String description) {
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
        for (GenderType type : values()) {
            listName.append(type.toString()).append(", ");
        }
        return listName.toString();
    }
    public static boolean isExist(Object current) {
        return BaseEnum.isExist(values(), current);
    }


}

