import React, {useContext, useEffect, useState} from 'react'
import classNames from 'classnames/bind';

import styles from './BookedHotelDetail.module.scss'
import {Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, Grid} from '@mui/material';
import CusInfoBookedHotel from "~/components/BookHotel/CustomerInfoBookedHotel";
import InfoBookedHotel from "~/components/BookHotel/InfoBookedHotel";
import InfoPayHotel from "~/components/BookHotel/InfoPayHotel";
import {useParams} from "react-router-dom";
import {cancelledHotelBooked, filterHotelBooked, getHotelBookedDetail} from "~/services/workspaces.sevices";
import {UserContext} from "~/config/provider/UserProvider";
import CheckBoxIcon from '@mui/icons-material/CheckBox';

const cx = classNames.bind(styles);

export default function BookedHotelDetail(props) {
    const {id} = useParams();
    const [openConfirmCancel, setOpenConfirmCancel] = React.useState(false);
    const [hotelBookedDetail, setHotelBookedDetail] = useState();
    const {customer} = useContext(UserContext);
    const [success, setSuccess] = useState(false);
    const [cancelled, setCancelled] = useState(false);
    useEffect(() => {
        async function loadData() {
            const allData = await getHotelBookedDetail(id);
            setHotelBookedDetail(allData?.data);
            setCancelled(
                allData?.data?.hotelBooked?.status === 'CANCELLED'
                || allData?.data?.hotelBooked?.status === 'START'
                || allData?.data?.hotelBooked?.status === 'END');
        }

        loadData();

    }, []);
    const handleClickOpen = () => {
        setOpenConfirmCancel(true);
    };

    const handleClose = () => {
        setOpenConfirmCancel(false);
    };

    async function confirmCancelled() {
        const callData = await cancelledHotelBooked(id, customer?.fullName);
        console.log(callData);
        setCancelled(true);
        handleClose();
        setSuccess(true);
        setTimeout(
            () => {
                setSuccess(false);
            }, 3000
        )
    }

    return (
        <div className={cx("bookTour")}>
            <Dialog open={success}>
                <DialogContent>
                    <DialogContentText>
                        <div style={{fontSize: "1.4rem", display: "flex"}}>
                            <CheckBoxIcon style={{color: "#22c55e"}}/>
                            <span style={{paddingTop: "4px"}}>
                                           Bạn đã huỷ đặt phòng thành công
                                      </span>
                        </div>
                    </DialogContentText>
                </DialogContent>
            </Dialog>
            <div className={cx('bookTour-content')}>
                <Grid container spacing={2} sx={{marginBottom: "3rem"}}>

                    <Grid item xs={4}>
                        <InfoBookedHotel hotelId={hotelBookedDetail?.hotel?.hotelid} hotelBookedInfo={hotelBookedDetail?.hotel}/>
                    </Grid>
                    <Grid item xs={8}>
                        <CusInfoBookedHotel hotelBookedInfo={hotelBookedDetail?.hotelBooked}/>
                        <InfoPayHotel hotelBookedInfo={hotelBookedDetail?.hotelBooked}/>
                        <div>
                            {!cancelled && <Button variant="contained" sx={{fontSize: '15px', marginTop: '2rem'}}
                                                   onClick={handleClickOpen}>
                                Hủy đặt phòng
                            </Button>}
                            <Dialog
                                open={openConfirmCancel}
                                onClose={handleClose}
                                aria-labelledby="alert-dialog-title"
                                aria-describedby="alert-dialog-description"
                            >
                                <DialogContent>
                                    <DialogContentText id="alert-dialog-description"
                                                       sx={{fontSize: '17px', color: 'black'}}>
                                        Bạn có chắc muốn hủy đặt phòng
                                    </DialogContentText>
                                </DialogContent>
                                <DialogActions>
                                    <Button sx={{fontSize: '15px'}} onClick={handleClose}>Hủy</Button>
                                    <Button sx={{fontSize: '15px'}} onClick={confirmCancelled} autoFocus>
                                        Đồng ý
                                    </Button>
                                </DialogActions>
                            </Dialog>
                        </div>
                    </Grid>
                </Grid>


            </div>


        </div>
    )
}
