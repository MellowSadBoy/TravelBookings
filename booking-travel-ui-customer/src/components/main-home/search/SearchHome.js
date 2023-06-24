import React, {useEffect, useState} from 'react';
import classNames from "classnames/bind";
import {useMediaQuery} from "react-responsive";
import Tippy from "@tippyjs/react/headless";
import {Link, useNavigate} from "react-router-dom";
import styles from './SearchHome.module.scss';
import {Button, Avatar} from "@mui/material";
import {GrMapLocation} from "react-icons/gr";
import {
    FormControl,
    Grid,
    InputAdornment, InputLabel, MenuItem, OutlinedInput,
    Select,
    TextField
} from "@material-ui/core";
import {filterTour, getProvinces} from "~/services/workspaces.sevices";
import {GiPaperPlane} from "react-icons/gi";
import useDebounce from "~/components/Hooks/useDebounce";

const cx = classNames.bind(styles);

function SearchHome(props) {
    const tablet = useMediaQuery({maxWidth: 768});
    const [showResult, setShowResult] = useState(false);
    const [searchValue, setSearchValue] = useState("");
    const [tours, setTours] = useState([]);
    const navigate = useNavigate();
    const [selectedDate, setSelectedDate] = useState(new Date());
    const [provinceList, setProvinceList] = useState([]);
    const [province, setProvince] = useState();


    const handleProvince = async (event) => {
        setProvince(event.target.value);
    };
    const formatDate = (date) => {
        return date.toLocaleDateString("vi-VN", {
            weekday: "long"
        });
    };

    const ITEM_HEIGHT = 48;
    const ITEM_PADDING_TOP = 8;
    const MenuProps = {
        PaperProps: {
            style: {
                maxHeight: ITEM_HEIGHT * 4.5 + ITEM_PADDING_TOP,
                width: 250,
                borderRadius: 0
            },
        },
    };


    const handleKeyPress = (event) => {
        if (event.key === "Enter") {
            setShowResult(false);
            navigate(`/filter-tour/${"search"}/${searchValue}`);
            setSearchValue("");
        }
    };
    const handleChange = (event) => {
        setSearchValue(event.target.value);
    };
    const handleHideResult = () => {
        setShowResult(false);
    };
    const debouncedValue = useDebounce(searchValue, 700);

    // eslint-disable-next-line react-hooks/exhaustive-deps
    const body = {
        search: debouncedValue,
        maxResult: 5,
    };
    useEffect(() => {
        async function fetchData() {
            await getProvinces().then(provinceData => setProvinceList(provinceData?.data));
            await filterTour(body).then(result => setTours(result?.data?.searchList));
        }

        fetchData();
    }, [debouncedValue]);
    const handleClick = () => {
        if (searchValue.length > 0) {
            navigate(`filter-tour/${"search"}/${searchValue}`);
            setSearchValue("");
        }
    };

    return (
        <div className={cx("container")}>
            <div className={cx("main-form")}>
                <Tippy
                    interactive
                    popperOptions={{
                        strategy: "fixed",
                    }}
                    placement="bottom-start"
                    visible={showResult && searchValue.length > 0}
                    offset={[-12, 10]}
                    onClickOutside={handleHideResult}
                    render={(attrs) => (
                        <div
                            style={{width: tablet ? "100%" : "480px",}}
                            className={cx("search-result")}
                            tabIndex="-1"
                            {...attrs}
                        >
                            {tours.length > 0 ? (
                                tours.map((item, index) => {
                                    return (
                                        <Link
                                            key={item.id}
                                            to={`/tour-detail/id/${item.id}`}
                                            className={cx("result-item")}
                                            onClick={() => setShowResult(false)}
                                        >
                                            <Avatar
                                                variant="square"
                                                sx={{width: 40, height: 40}}
                                                alt="Avatar"
                                                src={item.imgUrls[0]}
                                            />
                                            {item.name}
                                        </Link>
                                    );
                                })
                            ) : (
                                <p className={cx("empty")}>Không tìm thấy Tour</p>
                            )}
                        </div>
                    )}
                >


                    <TextField
                        className={cx("location-input")}
                        fullWidth
                        spellCheck={false}
                        value={searchValue}
                        onChange={handleChange}
                        onFocus={() => setShowResult(true)}
                        onKeyPress={handleKeyPress}
                        placeholder={
                            "Bạn muốn đi đâu?"
                        }

                        variant={"outlined"}
                        InputProps={{
                            startAdornment: (
                                <InputAdornment style={{color: "#ffffff", display: "flex",}} position="start">
                                    <GrMapLocation style={{marginBottom: "5px"}} color={"#ffffff"} size={15}/>
                                </InputAdornment>
                            ), inputProps: {
                                style: {
                                    fontSize: "1.4rem",
                                    display: "flex",
                                }
                            }
                        }}

                    />


                </Tippy>
                <Button
                    className={cx("btn-search")}
                    onClick={handleClick}
                    size={"medium"}
                    variant="contained"
                    style={{
                        marginLeft: "1rem",
                        width: "10rem",
                        height: "5rem",
                        backgroundColor: "#0064D2",
                        color: "#ffffff"
                    }}
                >
                    Tìm kiếm
                </Button>
                {/*<Grid style={{paddingTop: "15px"}} container>*/}
                {/*    <Grid item container md={5}>*/}
                {/*        <div className={cx("input-container")}>*/}
                {/*            <input*/}
                {/*                style={{*/}
                {/*                    border: "1px solid gray",*/}
                {/*                    color: "#666666",*/}
                {/*                    width: "19rem",*/}
                {/*                    paddingLeft: "71px",*/}
                {/*                    height: "5rem"*/}
                {/*                }}*/}
                {/*                type="date"*/}
                {/*                value={selectedDate.toISOString().substr(0, 10)}*/}
                {/*                onChange={(e) => setSelectedDate(new Date(e.target.value))}*/}
                {/*            />*/}
                {/*            <p className={cx("search-icon")} style={{*/}
                {/*                backgroundColor: "#ffffff",*/}
                {/*            }}*/}
                {/*            >{formatDate(selectedDate)},</p>*/}
                {/*        </div>*/}


                {/*    </Grid>*/}
                {/*    <Grid item>*/}
                {/*        <FormControl style={{*/}
                {/*            minWidth: "18rem",*/}
                {/*            backgroundColor: "#fff",*/}
                {/*            border: "1px solid gray"*/}
                {/*        }} sx={{m: 1, backgroundColor: "#fff"}}>*/}
                {/*            <InputLabel style={{*/}
                {/*                transform: "translate(0, 15.2px) scale(0.75)",*/}
                {/*                fontSize: "1.5rem", marginLeft: "1.1rem",*/}
                {/*                display: "flex"*/}
                {/*            }} id="demo-multiple-chip-label">*/}
                {/*                <GiPaperPlane size={20}/><span style={{marginTop: "4px", fontSize: "1.6rem"}}>Khởi hành từ</span>*/}
                {/*            </InputLabel>*/}
                {/*            <Select*/}
                {/*                labelId="demo-multiple-chip-label"*/}
                {/*                id="demo-multiple-chip"*/}
                {/*                value={province}*/}
                {/*                onChange={handleProvince}*/}
                {/*                input={<OutlinedInput id="select-multiple-chip"/>}*/}
                {/*                MenuProps={MenuProps}*/}
                {/*                style={{borderRadius: 0,}}*/}
                {/*            >*/}
                {/*                {provinceList.map(({child_id, name}) => (*/}
                {/*                    <MenuItem*/}
                {/*                        value={child_id}*/}
                {/*                    >*/}
                {/*                        <span style={{marginLeft: "8rem"}}>{name}</span>*/}
                {/*                    </MenuItem>*/}
                {/*                ))}*/}
                {/*            </Select>*/}
                {/*        </FormControl>*/}
                {/*    </Grid>*/}
                {/*    <Grid item>*/}
                {/*        <Button*/}
                {/*            // className={cx("btn-search")}*/}
                {/*            onClick={handleClick}*/}
                {/*            size={"medium"}*/}
                {/*            variant="contained"*/}
                {/*            style={{*/}
                {/*                marginLeft: "1rem",*/}
                {/*                width: "8rem",*/}
                {/*                height: "5rem",*/}
                {/*                backgroundColor: "#0064D2",*/}
                {/*                color: "#ffffff"*/}
                {/*            }}*/}
                {/*        >*/}
                {/*            Tìm kiếm*/}
                {/*        </Button>*/}
                {/*    </Grid>*/}
                {/*</Grid>*/}

            </div>
        </div>

    );
}

export default SearchHome;
