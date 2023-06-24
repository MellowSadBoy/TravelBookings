export const TimeHowLongConvertor = {
    convert: function (time) {
        let tourType = "";
        switch (time) {
            case "DOMESTIC":
                tourType = "trong nước";
                break;
            case "ABROAD":
                tourType = "quốc tế";
                break;
        }
        return tourType;
    }

}

export default TimeHowLongConvertor;
