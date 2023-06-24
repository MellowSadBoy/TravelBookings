import React, {useContext, useEffect, useState} from 'react';
import classNames from "classnames/bind";
import {Link} from "react-router-dom";
import {Sidebar, Menu, MenuItem, SubMenu} from 'react-pro-sidebar';

import styles from "./InformationUserMain.module.scss";
import {AssignmentOutlined, CreateOutlined, PersonOutline, ApartmentOutlined} from "@mui/icons-material";
import {Avatar} from "@mui/material";
import config from "~/config";
import {getUser, getUserProfile} from "~/services/workspaces.sevices";
import {UserContext} from "~/config/provider/UserProvider";


const cx = classNames.bind(styles);

function InformationUserMain(props) {
    const {child} = props;
    const {customer} = useContext(UserContext);
    const handleRenderIcon = ({open}) => {

    }

    const categories = [
        {
            id: 2,
            label: 'Tài khoản của tôi',
            icon: <PersonOutline/>,
            to: "",

            items: [
                {
                    name: 'Hồ sơ',
                    to: "/info-user"
                }, {
                    name: 'Địa chỉ',
                    to: "/info-address"
                }
            ]
        },
        {
            id: 3,
            label: 'Tour đã đặt',
            icon: <AssignmentOutlined/>,
            to: "/info-tour-booked",

            items: [],

        },
        {
            id: 4,
            label: 'Khách sạn đã đặt',
            icon: <ApartmentOutlined/>,
            to: "/info-hotel-booked",

            items: [],

        }
    ]

    return (
        <div className={cx('wrapper')}>
            <Sidebar>
                <div className={cx('avatar')}>
                    <Avatar sizes={'large'}
                            alt={customer?.fullName}
                            src={customer?.imageUrl}
                    />
                    <div className={cx('name')}>
                        <h4>{customer?.username}</h4>
                        <Link to={config.routes.informationUser}>
                            <span><CreateOutlined sx={{fontSize: 20,}}/> Sửa hồ sơ</span>
                        </Link>
                    </div>

                </div>
                <Menu
                    renderExpandIcon={handleRenderIcon}
                    // menuItemStyles={{
                    //     button: ({level, active, disabled}) => {
                    //         if (level === 0)
                    //             return {
                    //                 color: disabled ? '#f5d9ff' : '#ff5588',
                    //                 backgroundColor: active ? '#eecef9' : undefined,
                    //             };
                    //     },
                    // }}
                >
                    {
                        categories.map((category, index) => {
                            return (
                                <SubMenu key={category.id}
                                         label={category.label}
                                         component={<Link to={category.to}/>}
                                         active={window.location.pathname === category.to}

                                         icon={category.icon}>
                                    {category.items.map((item, index) => {
                                        return (
                                            <MenuItem key={index}
                                                      component={<Link to={item.to}/>}
                                                      active={window.location.pathname === item.to}
                                            >
                                                {item.name}
                                            </MenuItem>
                                        )
                                    })
                                    }
                                </SubMenu>
                            )
                        })
                    }

                </Menu>
            </Sidebar>
            <main style={{width: '100%'}}>
                {child}
            </main>

        </div>
    );
}

export default InformationUserMain;
