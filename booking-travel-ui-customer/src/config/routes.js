const routes = {
    home: "/home",
    login: "/login",
    register: "/register",
    verifyCodeRegister: "/register-verify-code/:email/id/:id",
    informationUser: "/info-user",
    informationAddress: "/info-address",
    passwordChange: "/password-change",
    tourDetail: "/tour-detail/id/:id",
    bookTour: "/booked-tour/id/:id/children/:children/adult/:adult",
    bookHotel: "/book-hotel/id/:id/adult/:adult/check-in/:checkIn/check-out/:checkOut",
    payment: "/payment",
    customerProfile: "/customer/profile",
    filterTour: "/filter-tour/:id/:name",
    hotelResult:"/hotel/:name/:checkindate/:checkoutdate/:numguest",
    bookedTourDetail: "/booked-tour-detail/:id",
    infoTourBooked: "/info-tour-booked",
    hotelDetail: "/hotel-detail/:id",
    hotelSearch:"/hotel/filter/room",
    infoHotelBooked:"/info-hotel-booked",

};
export default routes;
