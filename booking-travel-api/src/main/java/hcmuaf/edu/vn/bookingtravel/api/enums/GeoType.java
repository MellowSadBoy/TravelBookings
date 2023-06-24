package hcmuaf.edu.vn.bookingtravel.api.enums;


import hcmuaf.edu.vn.bookingtravel.api.enums.utils.BaseEnum;

public enum GeoType {
    WARD("Phường, Xã"), DISTRICT("Quận, Huyện"),
    PROVINCE("Thành phố"),COUNTRY("Quốc gia");
    private final String description;

    private GeoType(String description) {
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
        for (GeoType type : values()) {
            listName.append(type.toString()).append(", ");
        }
        return listName.toString();
    }
    public static boolean isExist(Object current) {
        return BaseEnum.isExist(values(), current);
    }

}
