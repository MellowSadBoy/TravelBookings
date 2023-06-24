import React, {useContext, useEffect, useState} from 'react'
import classNames from 'classnames/bind';

import styles from './BookedTourDetail.module.scss'
import {Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, Grid} from '@mui/material';
import CusInfoBookedTour from '~/components/BookTour/CustomerInfoBookedTour/CusInfoBookedTour';
import InfoBookedTour from '~/components/BookTour/InfoBookedTour/InfoBookedTour';
import InfoPayTour from '~/components/BookTour/InfoPayTour/InfoPayTour';
import {useParams} from "react-router-dom";
import {cancelledTourBooked, filterTourBooked, getTourBookedDetail} from "~/services/workspaces.sevices";
import {UserContext} from "~/config/provider/UserProvider";
import CheckBoxIcon from '@mui/icons-material/CheckBox';

const cx = classNames.bind(styles);

export default function BookedTourDetail(props) {
    const {id} = useParams();
    const [openConfirmCancel, setOpenConfirmCancel] = React.useState(false);
    const [tourBookedDetail, setTourBookedDetail] = useState();
    const {customer} = useContext(UserContext);
    const [success, setSuccess] = useState(false);
    const [cancelled, setCancelled] = useState(false);
    useEffect(() => {
        async function loadData() {
            const allData = await getTourBookedDetail(id);
            setTourBookedDetail(allData?.data);
            setCancelled(
                allData?.data?.tourBooked?.status === 'CANCELLED'
                || allData?.data?.tourBooked?.status === 'START'
                || allData?.data?.tourBooked?.status === 'END');
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
        const callData = await cancelledTourBooked(id, customer?.fullName);
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
                                           Bạn đã huỷ tour thành công
                                      </span>
                        </div>
                    </DialogContentText>
                </DialogContent>
            </Dialog>
            <div className={cx('bookTour-content')}>
                <Grid container spacing={2} sx={{marginBottom: "3rem"}}>

                    <Grid item xs={4}>
                        <InfoBookedTour tourId={tourBookedDetail?.tour?.id} tourBookedInfo={tourBookedDetail?.tour}/>
                    </Grid>
                    <Grid item xs={8}>
                        <CusInfoBookedTour tourBookedInfo={tourBookedDetail?.tourBooked}/>
                        <InfoPayTour tourBookedInfo={tourBookedDetail?.tourBooked}/>
                        <div>
                            {!cancelled && <Button variant="contained" sx={{fontSize: '15px', marginTop: '2rem'}}
                                                   onClick={handleClickOpen}>
                                Hủy tour
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
                                        Bạn có chắc muốn hủy tour
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
