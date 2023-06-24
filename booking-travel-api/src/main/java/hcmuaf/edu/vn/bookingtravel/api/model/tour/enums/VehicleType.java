package hcmuaf.edu.vn.bookingtravel.api.model.tour.enums;

import hcmuaf.edu.vn.bookingtravel.api.enums.utils.BaseEnum;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 13/04/2023, Thứ Năm
 **/
public enum VehicleType {
    CAR_16("Xe 16 chổ",16),
    CAR_30("xe 30 chổ",30),
    CAR_35("Xe 35 chổ",35),
    CAR_40("Xe 40 chổ",40),
    SUBWAY("Tàu lửa",100),
    PLANE("Máy bay",162);
    private final String description;
    private long numberOfSeats;

    private VehicleType(String description, long numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public long getNumberOfSeats() {
        return numberOfSeats;
    }

    @Override
    public String toString() {
        return this.name();
    }

    public static String getListName() {
        StringBuilder listName = new StringBuilder();
        for (VehicleType status : values()) {
            listName.append(status.toString()).append(", ");
        }
        return listName.substring(0, listName.length() - 2);
    }

    public static boolean isExist(Object current) {
        return BaseEnum.isExist(values(), current);
    }
}
