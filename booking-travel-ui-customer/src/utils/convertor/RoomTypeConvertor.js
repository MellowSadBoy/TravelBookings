export const RoomTypeConvertor = {
    convert: function (type) {
        let roomType = "";
        switch (type) {
            case "SINGLE_ROOM":
                roomType = "Phòng đơn";
                break;
            case "DOUBLE_ROOM":
                roomType = "Phòng đôi";
                break;
            case "TWIN_ROOM":
                roomType = "Phòng đôi có hai giường đơn";
                break;
            case "TRIPLE_ROOM":
                roomType = "Phòng ba giường";
                break;
            case "QUAD_ROOM":
                roomType = "Phòng bốn giường";
                break;
            case "SUITE":
                roomType = "Phòng sang trọng";
                break;
            case "EXECUTIVE_SUITE":
                roomType = "Phòng sang trọng cao cấp";
                break;
            case "CONNECTING_ROOMS":
                roomType = "Hai phòng kết nối với nhau";
                break;
            case "ADJOINING_ROOMS":
                roomType = "Hai phòng sát cạnh nhau";
                break;
            case "PENTHOUSE":
                roomType = "Phòng trên cùng của khách sạn";
                break;
            default:
                roomType = "";
                break;
        }
        return roomType;
    }
};

export default RoomTypeConvertor;