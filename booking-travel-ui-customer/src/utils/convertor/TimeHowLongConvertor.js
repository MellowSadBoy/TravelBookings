export const TimeHowLongConvertor = {
    convert: function (time) {
        let colorVN = "";
        switch (time) {
            case "ONE_DAY_ONE_NIGHT":
                colorVN = "1 ngày 1 đêm";
                break;
            case "TWO_DAY_ONE_NIGHT":
                colorVN = "2 ngày 1 đêm";
                break;
            case  "THREE_DAY_TWO_NIGHT":
                colorVN = "3 ngày 2 đêm";
                break;
            case  "THREE_DAY_THREE_NIGHT":
                colorVN = "3 ngày 3 đêm";
                break;
            case "FOUR_DAY_THREE_NIGHT":
                colorVN = "4 ngày 3 đêm";
                break;
            case "FIVE_DAY_FOUR_NIGHT":
                colorVN = "5 ngày 4 đêm";
                break;
            default:
                colorVN = "Khác";
                break;
        }
        return colorVN;
    }

}

export default TimeHowLongConvertor;
