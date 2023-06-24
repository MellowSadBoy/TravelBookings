import {format} from "date-fns";

export const DateUtils = {
    convert: function (date) {
        const newDate = new Date(date);
        return format(newDate, 'HH:mm:ss dd-MM-yyyy');
    }
    ,
    formatDayWeek: function (date) {

        const daysOfWeek = ['Chủ nhật', 'Thứ hai', 'Thứ ba', 'Thứ tư', 'Thứ năm', 'Thứ sáu', 'Thứ bảy'];
        const dayOfWeek = new Date(date).getDay();
        return daysOfWeek[dayOfWeek];
    }
    , formatDate: function (date) {
        const dayOfWeek = new Date(date);
        const day = dayOfWeek.getDate().toString().padStart(2, '0');
        const month = (dayOfWeek.getMonth() + 1).toString().padStart(2, '0');
        const year = dayOfWeek.getFullYear().toString();

        return `${day}-${month}-${year}`;
    },
    formatDateRes: function (date) {
        const dayOfWeek = date ? new Date(date) : new Date();
        const day = dayOfWeek.getDate().toString().padStart(2, '0');
        const month = (dayOfWeek.getMonth() + 1).toString().padStart(2, '0');
        const year = dayOfWeek.getFullYear().toString();

        return `${year}-${month}-${day}`;
    },
    convertDate: function (date) {
        const dateParts = date.split("-");
        const year = parseInt(dateParts[0], 10);
        const month = parseInt(dateParts[1], 10) - 1;
        const day = parseInt(dateParts[2], 10);

        return new Date(year, month, day);
    },
    convertTime: function (date) {
        if (date) {
            const newDate = new Date(date);
            return format(newDate, 'HH:mm:ss dd-MM-yyyy');
        }
    }
    , calculateDay: function (dateFrom, dateTo) {
        const startDate = new Date(dateFrom);
        const endDate = new Date(dateTo);
        const timeDiff = Math.abs(endDate.getTime() - startDate.getTime());
        return Math.ceil(timeDiff / (1000 * 3600 * 24));
    }
    , reverseDate: function (date) {
        const [year, month, day] = date.split('-');
        return `${day}-${month}-${year}`;

    }
}

export default DateUtils;