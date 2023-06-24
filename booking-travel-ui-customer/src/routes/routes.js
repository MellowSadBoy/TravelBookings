import config from "~/config";
import Home from "~/pages/Home";
import RegisterLayout from "~/pages/auth/register/RegisterLayout";
import LoginLayout from "~/pages/auth/Login/LoginLayout";
import VerifyCodeRegister from "~/pages/auth/VertifyCodeRegister/VerifyCodeRegister";
import TourDetail from "~/pages/Tour-detail/TourDetail";
import CustomerProfile from "~/components/CustomerProfile/CustomerProfile";
import BookTourLayout from "~/pages/BookTour/BookTourLayout";
import BookHotelLayout from "~/pages/BookHotel/BookHotelLayout";
import InformationUser from "~/pages/InformationUser";
import InformationAddressManager from "~/pages/InformationUser/account/address";
import PasswordChangeManager from "~/pages/InformationUser/account/PasswordChange";
import FilterTour from "~/pages/filterTour";
import HotelResult from "~/components/HotelResult/HotelResult";
import BookedTourDetail from "~/pages/InformationUser/InfoBookedTourDetail/BookedTourDetail";
import HotelDetail from "~/pages/Hotel-detail/HotelDetail";
import InfoTourBookedManager from "~/pages/InformationUser/info-tour-booked";
import InfoHotelBookedManager from "~/pages/InformationUser/info-hotel-booked";

const publicRoutes = [
    {path: '/', component: Home},
    {path: config.routes.home, component: Home},
    {path: config.routes.filterTour, component: FilterTour},
    {path: config.routes.tourDetail, component: TourDetail},
    {path: config.routes.login, component: LoginLayout, layout: null},
    {path: config.routes.register, component: RegisterLayout, layout: null},
    {path: config.routes.bookTour, component: BookTourLayout, layout: null},
    {path: config.routes.bookHotel, component: BookHotelLayout, layout: null},
    {path: config.routes.verifyCodeRegister, component: VerifyCodeRegister, layout: null},
    {path: config.routes.bookedTourDetail, component: BookedTourDetail},
    {path: config.routes.hotelDetail, component: HotelDetail},
    {path: config.routes.informationUser, component: InformationUser},
    {path: config.routes.informationAddress, component: InformationAddressManager},
    {path: config.routes.infoTourBooked, component: InfoTourBookedManager},
    {path: config.routes.passwordChange, component: PasswordChangeManager},
    {path: config.routes.customerProfile, component: CustomerProfile},
    {path: config.routes.hotelSearch, component: HotelResult},
    {path: config.routes.infoHotelBooked, component: InfoHotelBookedManager},

];
const privateRoutes = [];
export {publicRoutes, privateRoutes};

