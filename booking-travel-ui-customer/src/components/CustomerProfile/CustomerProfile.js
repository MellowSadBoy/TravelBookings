import React, { useState, useEffect }  from 'react';
import { makeStyles } from '@material-ui/core/styles';
import { Avatar, Card, CardContent, Grid, Typography, Icon,FormControl,InputLabel,Select,MenuItem } from '@material-ui/core';
import { Redeem, Favorite, LocalOffer, Assignment, AccountCircle, ExitToApp } from '@material-ui/icons';
import classNames from 'classnames/bind';
import { MuiPickersUtilsProvider, DatePicker } from '@material-ui/pickers';
import DateFnsUtils from '@date-io/date-fns';
import styles from './CustomerProfile.module.scss';
import {getUserProfile} from "~/services/workspaces.sevices";
// import updateButton from "~/components/CustomerProfile/UpdateButton/UpdateButton";
const cx = classNames.bind(styles);
const useStyles = makeStyles((theme) => ({
    avatar: {
        width: theme.spacing(10),
        height: theme.spacing(10),
    },
}));

function CustomerProfile() {
    const classes = useStyles();
    const [user, setUser] = useState(null);
    // const [loading, setLoading] = useState(true);
    const id  = localStorage.getItem("cs-id");
    // // const id ='1682148763060142';
    // const [selectedDate, setSelectedDate] = React.useState(user.birthday || new Date());
    //
    // const handleDateChange = (date) => {
    //     setSelectedDate(date);
    // };
    useEffect(() => {
        const fetchUser = async () => {
            try {
                const response = await getUserProfile(id);
                setUser(response.data)

            } catch (error) {
                console.log(error);
            }
        };
        fetchUser();

    }, [id]);

    return (
        <div>
            {user ? (
                <div className={cx('wrapper')}>
                    <div className="profile" >
                        <Grid container spacing={4}>
                            <Grid item xs={1} md={1}>
                            </Grid>

                            <Grid item xs={5} md={2} spacing={2} >
                                <Card>
                                    <CardContent style={{color: '#0064D2'}}>
                                        <Typography variant="h6" component="h2">
                                            <Icon><Redeem /></Icon> Điểm Đổi Quà
                                        </Typography >
                                        <Typography variant="h6" component="h2">
                                            <Icon><Favorite /></Icon> Tour Yêu Thích
                                        </Typography>
                                        <Typography variant="h6" component="h2">
                                            <Icon><LocalOffer /></Icon> Khuyến Mãi
                                        </Typography >
                                        <Typography variant="h6" component="h2">
                                            <Icon><Assignment /></Icon> Tour đã đặt
                                        </Typography>
                                        <Typography variant="h6" component="h2">
                                            <Icon><AccountCircle /></Icon> Tài Khoản
                                        </Typography>
                                        <Typography variant="h6" component="h2">
                                            <Icon><ExitToApp /></Icon> Đăng Xuất
                                        </Typography>
                                    </CardContent>
                                </Card>
                            </Grid>
                            <Grid item xs={12} md={8} >
                                <Card>
                                    <CardContent>
                                        <Avatar alt="Avatar" src={user.imageUrl} className={classes.avatar} />
                                        <Typography variant="h5" component="h2" gutterBottom>
                                            {}
                                        </Typography>
                                        <Typography variant="subtitle1" gutterBottom>
                                            {user.username}
                                        </Typography>
                                        {/*<Typography variant="body1" gutterBottom>*/}
                                        {/*    {user.description}*/}
                                        {/*</Typography>*/}
                                    </CardContent>
                                </Card>
                                <Card>
                                    <CardContent>
                                        <Typography variant="h5" component="h2" gutterBottom>
                                            Thông tin cá nhân
                                        </Typography>
                                        <Typography variant="body1" gutterBottom>
                                            <strong>Họ Tên:</strong> {user.fullName}
                                        </Typography>
                                        <Typography variant="body1" gutterBottom>
                                            <strong>Email:</strong> {user.email}
                                        </Typography>
                                        <Typography variant="body1" gutterBottom>
                                            <strong>Số điện thoại:</strong> {user.telephone}
                                        </Typography>
                                        <Typography variant="body1" gutterBottom>
                                            <strong>Địa Chỉ:</strong> {user.address}
                                        </Typography>
                                        <Typography variant="body1" gutterBottom>
                                            <strong>Giới Tính:</strong>
                                            <FormControl>
                                                <Select>
                                                    {/*    labelId="gender-label"*/}
                                                    {/*    id="gender"*/}
                                                    {/*    value={gender}*/}
                                                    {/*    onChange={(event) => setGender(event.target.value)}*/}
                                                    {/*>*/}
                                                    <MenuItem value="male">Nam</MenuItem>
                                                    <MenuItem value="female">Nữ</MenuItem>
                                                </Select>
                                            </FormControl>
                                        </Typography>
                                        <Typography variant="body1" gutterBottom>
                                            <strong>Ngày Sinh:</strong>
                                            <FormControl variant="outlined">
                                                <MuiPickersUtilsProvider utils={DateFnsUtils}>
                                                    <DatePicker
                                                        id="birthday"
                                                        // value={selectedDate}
                                                        // onChange={handleDateChange}
                                                        format="dd/MM/yyyy"
                                                        inputVariant="outlined"
                                                    />
                                                </MuiPickersUtilsProvider>
                                            </FormControl>
                                        </Typography>
                                    </CardContent>
                                </Card>
                            </Grid>

                        </Grid>
                    </div>
                </div>
            ): (
                <div className={cx('wrapper')}>
                    <p>Loading...</p>
                </div>
            )}
        </div>
    );
}
export default CustomerProfile;
