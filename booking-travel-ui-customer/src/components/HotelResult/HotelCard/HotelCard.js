import React, {useEffect, useState} from "react";
import {
    Card,
    CardMedia,
    CardContent,
    Typography,
    Box,
    makeStyles,
} from "@material-ui/core";
import getStarIcon from "~/components/HotelResult/HotelCard/getStarIcon/getStarIcon"
import MoneyUtils from "~/utils/MoneyUtils";
import {getHotelDetail} from "~/services/workspaces.sevices";
import RoomTypeConvertor from "~/utils/convertor/RoomTypeConvertor";
import {Link} from "react-router-dom";

const useStyles = makeStyles((theme) => ({
    card: {
        display: "flex",
        margin: theme.spacing(1),
        padding: theme.spacing(1),
    },
    cardContent: {
        flexGrow: 1,
        paddingLeft: theme.spacing(2),
        paddingRight: theme.spacing(2),
    },
    cardMedia: {
        width: "25%",
    },
    hotelName: {
        fontWeight: "bold",
        marginBottom: theme.spacing(1),
    },
    hotelAddress: {
        color: theme.palette.text.secondary,
        marginBottom: theme.spacing(1),
    },
    hotelPrice: {
        fontWeight: "bold",
        color: theme.palette.primary.main,
        float: "right",
    },
    hotelRank: {
        display: "flex",
        marginBottom: theme.spacing(1),
    },
    hotelRankStar: {
        color: theme.palette.secondary.main,
        marginRight: theme.spacing(0.5),
    },
}));

function HotelCard({room}) {
    const classes = useStyles();
    const [hotel, setHotel] = useState(null);
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        async function updateData() {
            const data = await getHotelDetail(room?.hotelId);
            setHotel(data?.data);
            setIsLoading(false);
        }
        updateData();
    }, [room?.hotelId]);



    return (
        <div>
            <Link to={`/hotel-detail/${room?.id}`}>
            <Card className={classes.card} >

                <CardMedia component="img" height="200" image={room.imgUrls[0]} className={classes.cardMedia}/>
                <CardContent className={classes.cardContent}>
                    <Typography variant="h6" className={classes.hotelName}>
                        KHÁCH SẠN {getStarIcon(room.hotelType)}
                    </Typography>
                    <Typography variant="h6" className={classes.hotelName}>
                        {room.hotelName}
                    </Typography>
                    <Box className={classes.hotelRank}>
                        {Array.from({length: room.hotelType}, (_, i) => (
                            <Typography
                                key={i}
                                variant="body1"
                                className={classes.hotelRankStar}
                            >
                                &#9733;
                            </Typography>
                        ))}
                        <Typography variant="body1">{RoomTypeConvertor.convert(room.type)}</Typography>
                    </Box>
                    <Typography variant="body1" className={classes.hotelAddress}>
                        {hotel?.address?.address}
                    </Typography>
                    <Typography variant="h6" className={classes.hotelPrice}>
                        Giá từ {MoneyUtils.getMoney(room.price.amount)} đ
                    </Typography>
                </CardContent>

            </Card>
            </Link>
        </div>
    );
}

export default HotelCard;
