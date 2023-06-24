import React, {useEffect, useState} from 'react';
import {useNavigate} from "react-router-dom";
import classNames from "classnames/bind";
import Tippy from "@tippyjs/react/headless";
import styles from './SearchHotelBar.module.scss';
import {Button, Avatar} from "@mui/material";
import BusinessIcon from '@mui/icons-material/Business';
import {
    FormControl,
    Grid,
    InputAdornment, InputLabel, MenuItem, OutlinedInput,
    Select,
    TextField
} from "@material-ui/core";



const cx = classNames.bind(styles);


const SearchHotelBar = ({ onSearch }) => {
    const [searchValue, setSearchValue] = useState('');
    const [checkinDate, setCheckinDate] = useState('');
    const [checkoutDate, setCheckoutDate] = useState('');
    const [numGuests, setNumGuests] = useState('');
    const navigate = useNavigate();
    const [date, setDate] = useState(new Date().toISOString().substr(0, 10));
    const tomorrow = new Date();
    tomorrow.setDate(tomorrow.getDate() + 1);
    const tomorrowISOString = tomorrow.toISOString().substr(0, 10);

    const [tomorrowDate, setTomorrowDate] = useState(tomorrowISOString);
    const handleChange = (event) => {
        setSearchValue(event.target.value);
    };

    const handleCheckinDateChange = (event) => {
        setCheckinDate(event.target.value);
    };

    const handleCheckoutDateChange = (event) => {
        setCheckoutDate(event.target.value);
    };

    const handleNumGuestsChange = (event) => {
        setNumGuests(event.target.value);
    };

    const handleSearch = () => {
        if (searchValue.length > 0) {
            navigate(`/hotel/${searchValue}/${checkinDate}/${checkoutDate}/${numGuests}`);
            setSearchValue("");
            setCheckinDate();
            setCheckoutDate();
            setNumGuests();
        }
    };

    return (
        <div className={cx('container')}>
            <div className={cx('main-form')}>
                <Tippy
                    interactive
                    popperOptions={{
                        strategy: 'fixed',
                    }}
                    placement="bottom-start"
                    visible={false} // Không hiển thị kết quả tìm kiếm ngay khi nhập
                    offset={[-12, 10]}
                    render={(attrs) => (
                        <div
                            style={{ width: '480px' }}
                            className={cx('search-result')}
                            tabIndex="-1"
                            {...attrs}
                        >
                            {/* Hiển thị kết quả tìm kiếm khách sạn */}
                        </div>
                    )}
                >
                    <TextField
                        className={cx('location-input')}
                        fullWidth
                        spellCheck={false}
                        value={searchValue}
                        onChange={handleChange}
                        placeholder="Tên khách sạn, khu vực"
                        variant="outlined"
                        style={{
                            margin: '5px',
                            width: '1600px',
                        }}
                        InputProps={{
                            startAdornment: (
                                <InputAdornment position="start">
                                    <BusinessIcon style={{ marginBottom: '3px' }} color="#ffffff" size={15} />
                                </InputAdornment>
                            ),
                            inputProps: {
                                style: {
                                    display: 'flex',
                                },
                            },
                        }}
                    />
                </Tippy>
                <TextField
                    className={cx('location-input')}
                    label="Ngày check-in"
                    name="checkinDate"
                    type="date"
                    value={checkinDate}
                    onChange={handleCheckinDateChange}
                    placeholder="mm/dd/yy"
                    variant="outlined"
                    fullWidth
                    style={{
                        margin: '5px',
                        width: '1000px',
                    }}
                    InputLabelProps={{
                        style: {
                            transform: 'translate(14px, 3px) scale(1)',
                        },
                    }}
                />
                <TextField
                    className={cx('location-input')}
                    label="Ngày check-out"
                    name="checkoutDate"
                    type="date"
                    onChange={handleCheckoutDateChange}
                    placeholder="mm/dd/yy"
                    variant="outlined"
                    fullWidth
                    style={{
                        margin: '5px',
                        width: '1000px',
                    }}
                    InputLabelProps={{
                        style: {
                            transform: 'translate(14px, 3px) scale(1)',
                        },
                    }}
                />
                <TextField
                    className={cx('location-input')}
                    name="numGuests"
                    type="number"
                    value={numGuests}
                    onChange={handleNumGuestsChange}
                    placeholder="Số hành khách"
                    variant="outlined"
                    fullWidth
                    style={{
                        margin: '5px',
                        width: '1000px',
                    }}
                    InputLabelProps={{
                        style: {
                            transform: 'translate(14px, 3px) scale(1)',

                        },
                    }}
                />
                <Button
                    className={cx('btn-search')}
                    onClick={handleSearch}
                    size="medium"
                    variant="contained"
                    style={{
                        marginLeft: '1rem',
                        width: '10rem',
                        height: '5rem',
                        backgroundColor: '#0064D2',
                        color: '#ffffff',
                    }}
                >
                    Tìm kiếm
                </Button>
            </div>
        </div>
    );
};
export default SearchHotelBar;

