package hcmuaf.edu.vn.bookingtravel.api.model.user.enums;


import hcmuaf.edu.vn.bookingtravel.api.enums.utils.BaseEnum;

public enum ServiceStatus {
    IN_PROGRESS_CONNECT("Đang kết nối"), CANCELLED("Đã hủy");
    private final String description;


    private ServiceStatus(String description) {
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
        for (ServiceStatus type : values()) {
            listName.append(type.toString()).append(", ");
        }
        return listName.substring(0, listName.length() - 2);
    }
    public static boolean isExist(Object current) {
        return BaseEnum.isExist(values(), current);
    }

}
