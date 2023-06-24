import React from "react";

import { Icon } from "@chakra-ui/react";
import {
  MdBarChart,
  MdPerson,
  MdHome,
  MdHouse,
  MdLock,
  MdOutlineDomain,
  MdStars,
} from "react-icons/md";

// Admin Imports
import MainDashboard from "views/admin/default";
import Profile from "views/admin/profile";
import TourManagement from "views/admin/tourManagement";
import HotelManagement from "views/admin/hotelManagement";
import UserManagement from "views/admin/userManagement";
import TourbookedManagement from "views/admin/tourbookedManagement";
import HotelbookedManagement from "views/admin/hotelbookedManagement";

// Auth Imports
import SignInCentered from "views/auth/signIn";
import SignUpCentered from "views/auth/signup";
import ActiveCentered from "views/auth/active";
import { StarIcon } from "@chakra-ui/icons";

const routes = [
  {
    name: "Quản lý tài khoản",
    layout: "/admin",
    icon: <Icon as={StarIcon} width="20px" height="20px" color="inherit" />,
    path: "/user-management",
    component: UserManagement,
    show: true,
  },
  {
    name: "Quản lý Tour",
    layout: "/admin",
    icon: <Icon as={MdBarChart} width="20px" height="20px" color="inherit" />,
    path: "/tour-management",
    component: TourManagement,
    show: true,
  },
  {
    name: "Quản lý khách sạn",
    layout: "/admin",
    icon: (
      <Icon as={MdOutlineDomain} width="20px" height="20px" color="inherit" />
    ),
    path: "/hotel-management",
    component: HotelManagement,
    show: true,
  },

  {
    name: "Đăng nhập",
    layout: "/auth",
    path: "/sign-in",
    icon: <Icon as={MdLock} width="20px" height="20px" color="inherit" />,
    component: SignInCentered,
    show: false,
  },
  {
    name: "Đăng ký",
    layout: "/auth",
    path: "/sign-up",
    icon: <Icon as={MdLock} width="20px" height="20px" color="inherit" />,
    component: SignUpCentered,
    show: false,
  },
  {
    name: "Kích hoạt tài khoản",
    layout: "/auth",
    path: "/active/:id",
    icon: <Icon as={MdLock} width="20px" height="20px" color="inherit" />,
    component: ActiveCentered,
    show: false,
  },
  {
    name: "Quản lý tour đã đặt",
    layout: "/admin",
    icon: <Icon as={MdStars} width="20px" height="20px" color="inherit" />,
    path: "/tourbooked-management",
    component: TourbookedManagement,
    show: true,
  },
  {
    name: "Quản lý khách sạn đã đặt",
    layout: "/admin",
    icon: <Icon as={MdHouse} width="20px" height="20px" color="inherit" />,
    path: "/hotelbooked-management",
    component: HotelbookedManagement,
    show: true,
  },
  {
    name: "Thông tin tài khoản",
    layout: "/admin",
    path: "/profile",
    icon: <Icon as={MdPerson} width="20px" height="20px" color="inherit" />,
    component: Profile,
    show: true,
  },
];

export default routes;
