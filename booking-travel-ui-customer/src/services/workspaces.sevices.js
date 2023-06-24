import instance from "~/config/interceptors/axios";

export const getEnterprise = async () => {
    return await instance.get("/enterprise/1.0.0/enterprise/info");
}
export const registerUser = async (body) => {
    return await instance.post("/user/1.0.0/register", body);
};
export const sendEmail = async (email) => {
    return await instance.post("/mail/1.0.0/send?email=" + email);
}
export const filterUser = async (body) => {
    return await instance.post("/user/1.0.0/user/filter", body);
}
export const loginCustomer = async (email, password, serviceType, fullName, imgUrl) => {
    return await instance.post("/user/1.0.0/customer/login?email=" + email +
        "&password=" + password + "&service-type=" + serviceType + "&full-name=" + fullName +
        "&image=" + imgUrl);
};
export const getUserToken = async (token, type) => {
    return await instance.get("/user/1.0.0/login/info-token?code-token=" +
        token + "&service-type=" + type);
};
export const getUser = async (id) => {
    return await instance.get(`/user/1.0.0/user/${id}`);
};
export const getProvinces = async () => {
    return await instance.get('/geo/1.0.0/province-list');
}
export const getDistricts = async (provinceId) => {
    return await instance.get('/geo/1.0.0/district-list?province-id=' + provinceId);
}
export const getWards = async (districtId) => {
    return await instance.get('/geo/1.0.0/ward-list?district-id=' + districtId);
}
export const getQRImage = async (amount) => {
    return await instance.post("/bank/1.0.0/qr-code-info?addInfo=" + null + "&amount=" + amount);
}
export const checkedPayment = async (amount) => {
    return await instance.post("/bank/1.0.0/check-payment?amount=" + amount);
}
export const updateAddressUser = async (userId, address) => {
    return await instance.put("/user/1.0.0/user/" + userId + "/info-address", address);
}
export const updateInfoUser = async (userId, info) => {
    return await instance.put("/user/1.0.0/user/" + userId + "/info-basic", info);
}
export const activeUser = async (userId, info) => {
    return await instance.put("/user/1.0.0/user/" + userId + "/active/" + info, info);
}

export const updatePasswordUser = async (userId, pw) => {
    return await instance.put("/user/1.0.0/user/" + userId + "/info-password?pwd=" + pw);
}

export const getUserProfile = async (id) => {
    return await instance.get(`/user/1.0.0/user/${id}`);
}
export const getTourProfile = async (id) => {
    return await instance.get(`tour/1.0.0/tour/${id}/detail`);
}
export const filterTour = async (body) => {
    return await instance.post("tour/1.0.0/tour/filter", body);
}
export const createTourBooked = async (body) => {
    return await instance.post("/tour-booked/1.0.0/tour-booked", body);
}

export const getHotelDetail = async (hotelId) => {
    return await instance.get(`/hotel/1.0.0/hotel/${hotelId}/detail`);
}
export const getRoomDetail = async (roomId) => {
    return await instance.get(`/hotel/1.0.0/room/${roomId}/detail`);
}
export const filterRoom = async (body) => {
    return await instance.post("hotel/1.0.0/room/filter", body);
}
export const filterTourBooked = async (body) => {
    return await instance.post("/tour-booked/1.0.0/tour-booked/filter", body);
}
export const getTourBookedDetail = async (id) => {
    return await instance.get(`/tour-booked/1.0.0/tour-booked/${id}/detail`);
}
export const cancelledTourBooked = async (id,byUser) => {
    return await instance.put(`/tour-booked/1.0.0/tour-booked/cancel/${id}/${byUser}`);
}
export const createHotelBooked = async (body) => {
    return await instance.post("/hotel-booked/1.0.0/hotel-booked", body);
}
export const filterHotelBooked = async (body) => {
    return await instance.post("/hotel-booked/1.0.0/hotel-booked/filter", body);
}
export const cancelledHotelBooked = async (id,byUser) => {
    return await instance.put(`/hotel-booked/1.0.0/hotel-booked/cancel/${id}/${byUser}`);
}
export const getHotelBookedDetail = async (id) => {
    return await instance.get(`/hotel-booked/1.0.0/hotel-booked/${id}/detail`);
}
