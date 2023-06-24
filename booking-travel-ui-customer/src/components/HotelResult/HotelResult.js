import React, {useState, useEffect} from 'react';
import {
    Button,
    Card,
    CardContent,
    Checkbox,
    FormControlLabel,
    FormGroup,
    Grid,
    Slider,
    Typography
} from '@material-ui/core';
import classNames from 'classnames/bind';
import styles from './HotelResult.module.scss';
import HotelCard from "~/components/HotelResult/HotelCard/HotelCard"
import Filter from "~/components/HotelResult/HotelCard/Filter/Filter";
import StarIcon from "@material-ui/icons/Star";
import {ExpandLess, ExpandMore} from "@material-ui/icons";
import RoomTypeConvertor from "~/utils/convertor/RoomTypeConvertor";
import {filterRoom, getProvinces} from "~/services/workspaces.sevices";
import {Pagination, PaginationItem} from "@mui/lab";
import {ArrowLeft, ArrowRight} from "~/components/Icon";

const cx = classNames.bind(styles);
const MAX_DISPLAYED_ROOM_TYPES = 5; // Số loại phòng tối đa được hiển thị
function HotelResult(props) {
    const [changeCost, setChangeCost] = useState([0, 10000000]);
    const [filteredHotels, setFilteredHotels] = useState([]);
    const [checkedValues, setCheckedValues] = useState([]);
    const [selectedRoomTypes, setSelectedRoomTypes] = useState([]);
    const [showAllRoomTypes, setShowAllRoomTypes] = useState(false);
    const [page, setPage] = useState(1);
    const [rowsPerPage] = useState(4);
    const handleChangePage = (event, newPage) => {
        setPage(newPage);
    };

    const startIndex = (page - 1) * rowsPerPage;
    const endIndex = startIndex + rowsPerPage;
    const currentHotels = filteredHotels.slice(startIndex, endIndex);

    const roomTypes = ['SINGLE_ROOM', 'DOUBLE_ROOM', 'TWIN_ROOM',
        'TRIPLE_ROOM', 'QUAD_ROOM', 'SUITE', 'EXECUTIVE_SUITE',
        'CONNECTING_ROOMS', 'ADJOINING_ROOMS', 'PENTHOUSE'];

    const [allProvinces, setAllProvinces] = useState([]);
    const [selectedProvinces, setSelectedProvinces] = useState([]);
    const [showAllProvinces, setShowAllProvinces] = useState(false);

    const handProvinceChange = async (event) => {
        const {name} = event.target;
        const updatedSelectedProvinces = selectedProvinces.includes(parseInt(name))
            ? selectedProvinces.filter((selectedType) => selectedType !== parseInt(name))
            : [...selectedProvinces, parseInt(name)];
        setSelectedProvinces(updatedSelectedProvinces);
        const body = {
            types: selectedRoomTypes,
            hotelTypes: checkedValues,
            provinceCodes: selectedProvinces
        }
        const rooms = await filterRoom(body);
        setFilteredHotels(rooms?.data?.searchList);

    };
    const displayedProvinces = showAllProvinces
        ? allProvinces
        : allProvinces.slice(0, 10);
    useEffect(() => {
        const fetchData = async () => {
            const provinces = await getProvinces();
            const provinceArray = provinces?.data;
            setAllProvinces(provinceArray);
            const body = {}
            const rooms = await filterRoom(body);
            setFilteredHotels(rooms?.data?.searchList);
        };
        fetchData();
    }, []);
    const displayedRoomTypes = showAllRoomTypes
        ? roomTypes
        : roomTypes.slice(0, MAX_DISPLAYED_ROOM_TYPES);
    const toggleRoomTypeSelection = async (event) => {
        const {name} = event.target;
        const updatedSelectedRoomTypes = selectedRoomTypes.includes(name)
            ? selectedRoomTypes.filter((selectedType) => selectedType !== name)
            : [...selectedRoomTypes, name];
        setSelectedRoomTypes(updatedSelectedRoomTypes);
        const body = {
            types: updatedSelectedRoomTypes,
            hotelTypes: checkedValues,
            provinceCodes: selectedProvinces
        }
        const rooms = await filterRoom(body);
        setFilteredHotels(rooms?.data?.searchList);

    };

    const handleChangeRankType = async (event) => {
        const {name} = event.target;
        const updatedCheckedValues = checkedValues.includes(name)
            ? checkedValues.filter((selectedType) => selectedType !== name)
            : [...checkedValues, name];
        setCheckedValues(updatedCheckedValues);
        const body = {
            types: selectedRoomTypes,
            hotelTypes: updatedCheckedValues,
            provinceCodes:selectedProvinces
        }
        const rooms = await filterRoom(body);
        setFilteredHotels(rooms?.data?.searchList);

    };

    const handleChangeCost = (event, newValue) => {
        setChangeCost(newValue);
    };
    const formatCurrency = (value) => {
        return value.toLocaleString("vi-VN", {
            style: "currency",
            currency: "VND",
        });
    };
    return (
        <div>
            <div className={cx('wrapper')}>
                <div className="profile">
                    <Grid container spacing={4}>
                        <Grid item xs={1} md={1}>
                        </Grid>

                        <Grid item xs={9} md={3} spacing={2}>
                            <Card>
                                <CardContent>
                                    <Typography variant="h6">Lọc Theo Giá tiền (VND)</Typography>
                                    <Slider
                                        value={changeCost}
                                        onChange={handleChangeCost}
                                        valueLabelDisplay="auto"
                                        min={0}
                                        max={20000000}
                                        step={100000}
                                        valueLabelFormat={(value) => formatCurrency(value)} // Định dạng giá trị thành tiền tệ
                                        getAriaValueText={(value) => formatCurrency(value)} // Định dạng giá trị thành tiền tệ
                                    />

                                    <FormGroup>
                                        <Typography variant="h6">Lọc Theo Hạng Sao</Typography>
                                        <FormControlLabel
                                            control={<Checkbox checked={checkedValues.includes("ONE_STAR")}
                                                               onChange={handleChangeRankType} name="ONE_STAR"
                                                               style={{color: "#0064D2"}}/>}
                                            label={<span><StarIcon/> </span>}
                                            style={{color: '#0064D2'}}
                                        />
                                        <FormControlLabel
                                            control={<Checkbox checked={checkedValues.includes("TWO_STAR")}
                                                               onChange={handleChangeRankType} name="TWO_STAR"
                                                               style={{color: "#0064D2"}}/>}
                                            label={<span><StarIcon/><StarIcon/>  </span>}
                                            style={{color: '#0064D2'}}
                                        />
                                        <FormControlLabel
                                            control={<Checkbox checked={checkedValues.includes("THREE_STAR")}
                                                               onChange={handleChangeRankType} name="THREE_STAR"
                                                               style={{color: "#0064D2"}}/>}
                                            label={<span><StarIcon/> <StarIcon/> <StarIcon/> </span>}
                                            style={{color: '#0064D2'}}
                                        />
                                        <FormControlLabel
                                            control={<Checkbox checked={checkedValues.includes("FOUR_STAR")}
                                                               onChange={handleChangeRankType} name="FOUR_STAR"
                                                               style={{color: "#0064D2"}}/>}
                                            label={<span><StarIcon/> <StarIcon/> <StarIcon/> <StarIcon/>  </span>}
                                            style={{color: '#0064D2'}}
                                        />
                                        <FormControlLabel
                                            control={<Checkbox checked={checkedValues.includes("FIVE_STAR")}
                                                               onChange={handleChangeRankType} name="FIVE_STAR"
                                                               style={{color: "#0064D2"}}/>}
                                            label={
                                                <span><StarIcon/> <StarIcon/> <StarIcon/> <StarIcon/><StarIcon/>  </span>}
                                            style={{color: '#0064D2'}}
                                        />
                                    </FormGroup>
                                    <FormGroup>
                                        <Typography variant="h6">Loại Phòng Ở</Typography>
                                        {displayedRoomTypes.map((roomType) => (
                                            <FormControlLabel
                                                key={roomType}
                                                control={
                                                    <Checkbox
                                                        checked={selectedRoomTypes.includes(roomType)}
                                                        onChange={toggleRoomTypeSelection}
                                                        name={roomType}
                                                        style={{color: "#0064D2"}}
                                                    />
                                                }
                                                label={RoomTypeConvertor.convert(roomType)}
                                            />
                                        ))}
                                        <Button
                                            variant="outlined"
                                            color="primary"
                                            onClick={() => setShowAllRoomTypes(!showAllRoomTypes)}
                                            startIcon={showAllRoomTypes ? <ExpandLess/> : <ExpandMore/>}
                                        >
                                            {showAllRoomTypes ? "Hiển thị ít hơn" : "Hiển thị tất cả"}
                                        </Button>
                                    </FormGroup>
                                    <FormGroup>
                                        <Typography variant="h6">Lọc Theo Tỉnh/Thành phố</Typography>
                                        {displayedProvinces.map((province) => (
                                            <FormControlLabel
                                                key={province.id}
                                                control={
                                                    <Checkbox
                                                        checked={selectedProvinces.includes(province.child_id)}
                                                        onChange={handProvinceChange}
                                                        name={province.child_id}
                                                        style={{color: "#0064D2"}}
                                                    />
                                                }
                                                label={province.name}
                                            />
                                        ))}
                                        <Button
                                            variant="outlined"
                                            color="primary"
                                            onClick={() => setShowAllProvinces(!showAllProvinces)}
                                            startIcon={showAllProvinces ? <ExpandLess/> : <ExpandMore/>}
                                        >
                                            {showAllProvinces ? "Hiển thị ít hơn" : "Hiển thị tất cả"}
                                        </Button>
                                    </FormGroup>
                                </CardContent>
                            </Card>
                        </Grid>
                        <Grid item xs={12} md={8}>
                            <Card className={cx('custom-card')}>
                                <div className={cx('title')}>
                                    <div className={cx('filter-wrapper')}>
                                        <Filter className={cx('filter-icon')}/>
                                    </div>
                                </div>
                            </Card>
                            <Card>
                                {currentHotels?.map((room) => (
                                    <HotelCard key={room.id} room={room}/>
                                ))}
                            </Card>
                        </Grid>
                        <Grid item md={12} style={{paddingTop: "40px", display: "flex", justifyContent: "flex-end"}}>
                            <Pagination
                                count={Math.ceil(filteredHotels.length / rowsPerPage)}
                                page={page}
                                onChange={handleChangePage}
                                color="primary"
                                renderItem={(item) => (
                                    <PaginationItem
                                        slots={{
                                            previous: ArrowLeft,
                                            next: ArrowRight,
                                        }}
                                        {...item}
                                    />
                                )}
                            />
                        </Grid>
                    </Grid>
                </div>
            </div>
        </div>
    );
}

export default HotelResult;
