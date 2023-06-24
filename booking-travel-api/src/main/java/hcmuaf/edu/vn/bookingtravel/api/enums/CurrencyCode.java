package hcmuaf.edu.vn.bookingtravel.api.enums;


import hcmuaf.edu.vn.bookingtravel.api.enums.utils.BaseEnum;

public enum CurrencyCode {
    VND("Việt Nam đồng"), USD("Đô la Mỹ"),
    EUR("Đồng Euro"), GBP("Bảng Anh");

    private final String description;

    private CurrencyCode(String description) {
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
        for (CurrencyCode code : values()) {
            listName.append(code.toString()).append(", ");
        }
        return listName.toString();
    }
    public static boolean isExist(Object current) {
        return BaseEnum.isExist(values(), current);
    }

}
