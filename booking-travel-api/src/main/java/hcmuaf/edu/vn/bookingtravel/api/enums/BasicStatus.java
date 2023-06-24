package hcmuaf.edu.vn.bookingtravel.api.enums;


import hcmuaf.edu.vn.bookingtravel.api.enums.utils.BaseEnum;

public enum BasicStatus {
    success("Thành công"), failure("Thất bại"),
    error("Lỗi"), pending("Treo");
    private final String description;

    private BasicStatus(String description) {
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
        for (BasicStatus status : values()) {
            listName.append(status.toString()).append(", ");
        }
        return listName.substring(0, listName.length() - 2);

    }
    public static boolean isExist(Object current) {
        return BaseEnum.isExist(values(), current);
    }


}
