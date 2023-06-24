package hcmuaf.edu.vn.bookingtravel.api.model.user.enums;


import hcmuaf.edu.vn.bookingtravel.api.enums.utils.BaseEnum;

public enum ScoreType {
    RANK_BRONZE("Hạng đồng", 1500),
    RANK_SILVER("Hạng bạc", 3000),
    RANK_GOLD("Hạng vàng", 9000),
    RANK_VIP("Hạng vip", 1L);
    private final String description;
    private final long limit;

    private ScoreType(String description, long limit) {
        this.description = description;
        this.limit = limit;
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
        for (ScoreType type : values()) {
            listName.append(type.toString()).append(", ");
        }
        return listName.substring(0, listName.length() - 2);

    }

    public static boolean isExist(Object current) {
        return BaseEnum.isExist(values(), current);
    }
}
