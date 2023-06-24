import React, {useContext, useRef, useState} from 'react';

import classNames from 'classnames/bind';
import styles from './Header.module.scss';
import {styled, useTheme} from '@mui/material/styles';
import TopNavbar from "~/components/Header/TopNavbar";
import {Container, Grid} from "@mui/material";
import {Logo} from "~/components/Icon";
import {useMediaQuery} from "react-responsive";
import {MdMenu, MdOutlineAirplaneTicket} from 'react-icons/md'
import {AiOutlineClose, AiOutlineMenu} from 'react-icons/ai'
import {Link} from "react-router-dom";
import config from "~/config";
import {EnterpriseContext} from "~/config/provider/EnterpriseProvider";
import {makeStyles} from '@material-ui/core/styles';
import Drawer from '@material-ui/core/Drawer';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import IconButton from '@material-ui/core/IconButton';
import {BiPhoneCall} from "react-icons/bi";
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import {Divider, Typography} from "@material-ui/core";
import {ImHome} from "react-icons/im";
import {GiPaperPlane} from "react-icons/gi";
import {FaHotel} from "react-icons/fa";
import logo from '~/assets/logo/Mellow_reverse.png'

const useStyles = makeStyles((theme) => ({
    menuButton: {
        marginRight: theme.spacing(2),
    },
    drawer: {
        width: 240,
        flexShrink: 0,
    },
    drawerPaper: {
        width: 240,
    },
}));

const cx = classNames.bind(styles);

function Header(props) {
    const theme = useTheme();
    const {enterprise} = useContext(EnterpriseContext);
    const minTablet = useMediaQuery({minWidth: 900})
    const isTablet = useMediaQuery({maxWidth: 768})
    const maxWidth600 = useMediaQuery({maxWidth: 600})
    const navRef = useRef();
    const showNavbar = () => {
        navRef.current.classList.toggle(cx('responsive_nav'));
    };
    const classes = useStyles();
    const [isDrawerOpen, setIsDrawerOpen] = useState(false);

    const toggleDrawer = (isOpen) => (event) => {
        if (event && event.type === 'keydown' && (event.key === 'Tab' || event.key === 'Shift')) {
            return;
        }
        setIsDrawerOpen(isOpen);
    };
    // eslint-disable-next-line no-undef
    const DrawerHeader = styled('div')(({theme}) => ({
        display: 'flex',
        alignItems: 'center',
        height: "120px",
        borderBottom: "1px solid #8A9299",
        padding: theme.spacing(0, 1),
        // necessary for content to be below app bar
        ...theme.mixins.toolbar,
        justifyContent: 'flex-end',
    }));
    const drawerMaps = [{
        name: "Trang chủ",
        icon: <ImHome size={20}/>,
        url: config.routes.home
    },
        {
            name: "Du lịch",
            icon: <GiPaperPlane size={20}/>,
            url: `/filter-tour/${'all'}/${'all'}`
        },
        {
            name: "Khách sạn",
            icon: <FaHotel size={20}/>,
            url: config.routes.hotelSearch

        },
        {
            name: "Vé máy bay",
            icon: <MdOutlineAirplaneTicket size={20}/>
        }]

    return (
        <div className={cx('wrapper')}>
            {!maxWidth600 && (<TopNavbar/>)}
            <div className={cx('header')}>
                <Container style={{
                    paddingLeft: 0,
                    paddingRight: 0,
                    height: "5rem",
                    marginLeft: "12rem",
                    marginRight: "12rem"
                }}>
                    <Grid className={cx("wrapper-container")} container={false}>
                        <Grid
                            item
                            md={7}
                            lg={7}
                            container
                            direction="row"
                            justifyContent="flex-start"
                            alignItems="center"
                        >
                            <Grid item>
                                <IconButton
                                    edge="start"
                                    className={classes.menuButton}
                                    color="inherit"
                                    aria-label="menu"
                                    onClick={toggleDrawer(true)}
                                >
                                    <AiOutlineMenu style={{color: "#ffffff"}} size={30}/>
                                </IconButton>
                                <Drawer
                                    className={classes.drawer}
                                    anchor="left"
                                    open={isDrawerOpen}
                                    onClose={toggleDrawer(false)}
                                    classes={{
                                        paper: classes.drawerPaper,
                                    }}
                                >
                                    <DrawerHeader onClick={(event) => setIsDrawerOpen(false)}>
                                        <Logo width={"200px"} height={"30px"} logo={logo}/>
                                        <IconButton>
                                            {theme.direction === 'ltr' ? <ChevronLeftIcon/> : <ChevronRightIcon/>}
                                        </IconButton>
                                    </DrawerHeader>
                                    <Divider/>
                                    <List>
                                        {drawerMaps.map((text, index) => (
                                            <Link style={{color: "#707070"}} to={text.url}>
                                                <ListItem key={text} disablePadding>
                                                    <ListItemButton>
                                                        <ListItemIcon>
                                                            {text.icon}
                                                        </ListItemIcon>
                                                        <ListItemText primary={
                                                            <Typography variant="h5">
                                                                {text.name}
                                                            </Typography>}/>
                                                    </ListItemButton>
                                                </ListItem>
                                            </Link>
                                        ))}
                                    </List>
                                </Drawer>
                            </Grid>
                            {minTablet && (
                                <Grid item md={4} sm={4} xs={4}>
                                    <Link to={config.routes.home}>
                                        <div className={cx('logo')}>
                                            <Logo logo={enterprise.imgLogoUrl} height={"40px"} width={"200px"}
                                                  className={cx('logo-image')}/>
                                        </div>
                                    </Link>
                                </Grid>
                            )}
                            <Grid style={{
                                paddingTop: "0.5rem", flexBasis: "9.5%",
                                maxWidth: "11%"
                            }} item md={1} sm={2} xs={2}>
                                <Link to={`/filter-tour/${'all'}/${'all'}`} className={cx('line-item-container')}>
                                    Du lịch
                                </Link>
                            </Grid>

                            <Grid style={{
                                paddingTop: "0.5rem", flexBasis: "13%",
                                maxWidth: "14%"
                            }} item md={2} sm={2} xs={2}>
                                <Link to={config.routes.hotelSearch} className={cx('line-item-container')}>
                                    Khách sạn
                                </Link>
                            </Grid>

                            <Grid style={{
                                paddingTop: "0.5rem", flexBasis: "14%",
                                maxWidth: "14%"
                            }} item md={2} sm={2} xs={2}>
                                <Link className={cx('line-item-container')}>
                                    Vé máy bay
                                </Link>
                            </Grid>
                            <Grid style={{
                                paddingTop: "0.5rem", flexBasis: "9.5%",
                                maxWidth: "11%"
                            }} item md={2} sm={2} xs={2}>
                                <Link className={cx('line-item-container')}>
                                    Combo
                                </Link>
                            </Grid>
                            <Grid style={{
                                paddingTop: "0.5rem", flexBasis: "9.5%",
                                maxWidth: "11%"
                            }} item md={2} sm={2} xs={2}>
                                <Link className={cx('line-item-container')}>
                                    Định cư
                                </Link>
                            </Grid>
                            {
                                isTablet && (
                                    <Grid item md={2} sm={1} xs={2}>
                                        <div className={cx('menu-mobile')}>
                                            <MdMenu onClick={showNavbar} className={cx('menu-icon', 'nav-btn')}/>
                                        </div>

                                    </Grid>
                                )
                            }
                        </Grid>
                        <Grid
                            item
                            container
                            md={6}
                            lg={6}
                            direction="row"
                            justifyContent={"flex-start"}
                            alignItems="center"
                        >
                            <Grid style={{marginLeft: "31rem"}} item md={4} sm={1} xs={2}>
                                <div className={cx('line-item-container-right')}>
                                    <BiPhoneCall style={{color: "#fbbf24"}} size={25}/>
                                    <div style={{paddingTop: "4px", color: "#fbbf24"}}>
                                        {enterprise.hotLine}
                                    </div>
                                </div>
                            </Grid>


                        </Grid>

                    </Grid>
                    <header>
                        <nav ref={navRef}>
                            <a href="/#">Đăng nhập</a>
                            <a href="/#">Đăng ký</a>
                            <a href="/#">Thông tin cá nhân</a>
                            <button
                                className={cx('nav-btn', 'nav-close-btn')}
                                onClick={showNavbar}>
                                <AiOutlineClose/>
                            </button>
                        </nav>
                    </header>
                </Container>

            </div>


        </div>

    );
}

export default Header;
