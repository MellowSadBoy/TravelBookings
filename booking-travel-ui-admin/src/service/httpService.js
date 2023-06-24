import AxiosServ from "./axiosService";

class HttpRequestService {
  constructor() {}

  // User

  signIn(data) {
    AxiosServ.addOption({
      params: data,
    });
    return AxiosServ.postMethod("/booking-travel/user/1.0.0/admin/login");
  }

  signUp(data) {
    AxiosServ.addOption({
      params: "",
    });
    return AxiosServ.postMethod("/booking-travel/user/1.0.0/register", data);
  }

  getUserInfoByToken(token, serviceType = "NORMALLY") {
    AxiosServ.addOption({
      params: {
        "code-token": token,
        "service-type": serviceType,
      },
    });
    return AxiosServ.getMethod("/booking-travel/user/1.0.0/login/info-token");
  }

  activeAccount(userId, code) {
    AxiosServ.addOption({
      params: "",
    });
    return AxiosServ.putMethod(
      `/booking-travel/user/1.0.0/user/${userId}/active/${code}`
    );
  }

  updateAvatar(userId, newAvatar) {
    AxiosServ.addOption({
      params: {
        newAvatar,
      },
    });
    return AxiosServ.putMethod(
      `/booking-travel/user/1.0.0/user/${userId}/avatar/update`
    );
  }

  updateInfo(userId, data) {
    AxiosServ.addOption({
      params: {},
    });
    return AxiosServ.putMethod(
      `/booking-travel/user/1.0.0/user/${userId}/info-basic`,
      data
    );
  }

  updateAddress(userId, data) {
    AxiosServ.addOption({
      params: {},
    });
    return AxiosServ.putMethod(
      `/booking-travel/user/1.0.0/user/${userId}/info-address`,
      data
    );
  }

  // End User

  // Tour

  createTour(data) {
    AxiosServ.addOption({
      params: {},
    });
    return AxiosServ.postMethod("/booking-travel/tour/1.0.0/tour", data);
  }

  getTours() {
    AxiosServ.addOption({
      params: {},
    });
    return AxiosServ.getMethod("/booking-travel/tour/1.0.0/tour/all");
  }

  getTour(id) {
    AxiosServ.addOption({
      params: {},
    });
    return AxiosServ.getMethod(`/booking-travel/tour/1.0.0/tour/${id}/detail`);
  }

  updateTour(id, data) {
    AxiosServ.addOption({
      params: {},
    });
    return AxiosServ.putMethod(
      `/booking-travel/tour/1.0.0/tour/id/${id}/update`,
      data
    );
  }

  deleteTour(id, userId, note) {
    AxiosServ.addOption({
      params: {},
    });
    return AxiosServ.deleteMethod(
      `/booking-travel/tour/1.0.0/tour/id/${id}/by-user/${userId}/note/${note}`
    );
  }

  // End tour

  // Geo

  getProvinces() {
    AxiosServ.addOption({
      params: {},
    });
    return AxiosServ.getMethod("/booking-travel/geo/1.0.0/province-list");
  }

  getDistrictsByProvinceId(provinceId) {
    AxiosServ.addOption({
      params: {
        "province-id": provinceId,
      },
    });
    return AxiosServ.getMethod("/booking-travel/geo/1.0.0/district-list");
  }

  getWardsByDistrictId(districtId) {
    AxiosServ.addOption({
      params: {
        "district-id": districtId,
      },
    });
    return AxiosServ.getMethod("/booking-travel/geo/1.0.0/ward-list");
  }

  // End Geo

  // User manager

  filterUsers(options) {
    AxiosServ.addOption({
      params: {},
    });
    return AxiosServ.postMethod(
      "/booking-travel/user/1.0.0/user/filter",
      options
    );
  }

  deleteUser(userId, byUser) {
    AxiosServ.addOption({
      params: {},
    });
    return AxiosServ.deleteMethod(
      `/booking-travel/user/1.0.0/user/${userId}/deleted/${byUser}`
    );
  }

  getUserInfo(userId) {
    AxiosServ.addOption({
      params: {},
    });
    return AxiosServ.getMethod(`/booking-travel/user/1.0.0/user/${userId}`);
  }

  activeUser(userId, activeBy) {
    AxiosServ.addOption({
      params: {},
    });
    return AxiosServ.putMethod(
      `/booking-travel/user/1.0.0/user/${userId}/active/${activeBy}`
    );
  }

  inActiveUser(userId, activeBy) {
    AxiosServ.addOption({
      params: {},
    });
    return AxiosServ.putMethod(
      `/booking-travel/user/1.0.0/user/${userId}/inactive/${activeBy}`
    );
  }

  // End user manager

  //Hotel

  getHotels() {
    AxiosServ.addOption({
      params: {},
    });
    return AxiosServ.getMethod("/booking-travel/hotel/1.0.0/hotel/all");
  }

  // InfoCompany

  getInfoCompany() {
    AxiosServ.addOption({
      params: {},
    });
    return AxiosServ.getMethod(
      "/booking-travel/enterprise/1.0.0/enterprise/info"
    );
  }

  // End InfoCompany

  deleteHotel(id, userId) {
    console.log("id hotel: ", id);
    console.log("id user: ", userId);

    AxiosServ.addOption({
      params: {},
    });
    return AxiosServ.deleteMethod(
      `/booking-travel/hotel/1.0.0/hotel/${id}/deleted/${userId}`
    );
  }

  getHotel(id) {
    AxiosServ.addOption({
      params: {},
    });
    return AxiosServ.getMethod(`/booking-travel/hotel/1.0.0/hotel/${id}`);
  }

  createHotel(data) {
    AxiosServ.addOption({
      params: {},
    });
    return AxiosServ.postMethod("/booking-travel/hotel/1.0.0/hotel", data);
  }

  updateHotel(id, data) {
    AxiosServ.addOption({
      params: {},
    });
    return AxiosServ.putMethod(
      `/booking-travel/hotel/1.0.0/hotel/${id}/info-basic`,
      data
    );
  }

  filterTourbooked(options) {
    console.log("search tourbooked ", options);

    AxiosServ.addOption({
      params: {},
    });
    return AxiosServ.postMethod(
      "/booking-travel/tour-booked/1.0.0/tour-booked/filter",
      options
    );
  }

  filterHotelbooked(options) {
    console.log("search hotelbooked ", options);

    AxiosServ.addOption({
      params: {},
    });
    return AxiosServ.postMethod(
      "/booking-travel/hotel-booked/1.0.0/hotel-booked/filter",
      options
    );
  }

  getTourbookedById(id) {
    AxiosServ.addOption({
      params: {},
    });
    return AxiosServ.getMethod(
      `/booking-travel/tour-booked/1.0.0/tour-booked/${id}/detail`
    );
  }

  getHotelbookedById(id) {
    AxiosServ.addOption({
      params: {},
    });
    return AxiosServ.getMethod(
      `/booking-travel/hotel-booked/1.0.0/hotel-booked/${id}/detail`
    );
  }

  filterHotel(options) {
    AxiosServ.addOption({
      params: {},
    });
    return AxiosServ.postMethod(
      "/booking-travel/hotel/1.0.0/hotel/filter",
      options
    );
  }

  //

  updateTourbooked(id, data) {
    console.log("handle api update tourbooked ", data);
    AxiosServ.addOption({
      params: {},
    });
    return AxiosServ.putMethod(
      `booking-travel/tour-booked/1.0.0/tour-booked/info/${id}`,
      data
    );
  }

  updateHotelbooked(id, data) {
    console.log("handle api update hotelbooked ", data);
    AxiosServ.addOption({
      params: {},
    });
    return AxiosServ.putMethod(
      `booking-travel/hotel-booked/1.0.0/hotel-booked/info/${id}`,
      data
    );
  }

  cancelTour(id, userId) {
    console.log("id tourbooked: ", id);
    console.log("id user: ", userId);

    AxiosServ.addOption({
      params: {},
    });
    return AxiosServ.putMethod(
      `booking-travel/tour-booked/1.0.0/tour-booked/cancel/${id}/${userId}`
    );
  }

  cancelHotelbooked(id, userId) {
    console.log("id hotelbooked: ", id);
    console.log("id user: ", userId);

    AxiosServ.addOption({
      params: {},
    });
    return AxiosServ.putMethod(
      `booking-travel/hotel-booked/1.0.0/hotel-booked/cancel/${id}/${userId}`
    );
  }
}

const httpServ = new HttpRequestService();

export default httpServ;
