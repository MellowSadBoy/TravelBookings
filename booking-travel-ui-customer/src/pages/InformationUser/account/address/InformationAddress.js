import React, {useEffect, useState} from 'react';

import classNames from "classnames/bind";

import styles from "./InformationAddress.module.scss";
import {
    Box,
    Button,
    Container,
    createTheme,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle, FormControl, InputLabel, MenuItem, NativeSelect, OutlinedInput, Select, TextField,
    ThemeProvider, useTheme
} from "@mui/material";
import ItemAddress from "~/pages/InformationUser/account/address/itemAddress/ItemAddress";
import {AddOutlined} from "@mui/icons-material";
import Grid from "@mui/material/Grid";
import {
    getDistricts,
    getProvinces, getUser, getUserProfile,
    getWards,
    updateAddressUser,
    updateShopAddress
} from "~/services/workspaces.sevices";


const cx = classNames.bind(styles);
const ITEM_HEIGHT = 48;
const ITEM_PADDING_TOP = 8;
const MenuProps = {
    PaperProps: {
        style: {
            maxHeight: ITEM_HEIGHT * 4.5 + ITEM_PADDING_TOP,
            width: 250,
        },
    },
};

const citys = [
    'Oliver Hansen',
    'Van Henry',
    'April Tucker',
    'Ralph Hubbard',
    'Omar Alexander',
    'Carlos Abbott',
    'Miriam Wagner',
    'Bradley Wilkerson',
    'Virginia Andrews',
    'Kelly Snyder',
];

function getStyles(name, personName, theme) {
    return {
        fontWeight:
            personName.indexOf(name) === -1
                ? theme.typography.fontWeightRegular
                : theme.typography.fontWeightMedium,
    };
}

function InformationAddress(props) {
    const [open, setOpen] = React.useState(false);
    const themes = useTheme();
    const [personName, setPersonName] = React.useState([]);
    const [provinceList, setProvinceList] = useState();
    const [province, setProvince] = useState();
    const [districtList, setDistrictList] = useState();
    const [district, setDistrict] = useState();
    const [wardList, setWardList] = useState();
    const [ward, setWard] = useState();
    const [userAddress, setUserAddress] = useState();
    const id = localStorage.getItem("cs-id");
    const [rsUser, setRsUser] = useState({});

    useEffect(() => {

        async function loadData() {
            await getUser(id).then(async data => {
                setRsUser(data?.data);
                const provinceData = await getProvinces();
                const state = await getDistricts(data?.data?.address?.provinceCode?
                    data?.data?.address?.provinceCode:provinceData?.data[0]?.child_id);
                const stateD = await getWards(data?.data?.address?.districtCode?
                    data?.data?.address?.districtCode:state?.data[0]?.child_id
                );
                setProvinceList(provinceData?.data);
                setDistrictList(state?.data);
                setWardList(stateD?.data);
                setProvince(data?.data?.address?.provinceCode);
                setDistrict(data?.data?.address?.districtCode);
                setWard(data?.data?.address?.wardCode);
                setUserAddress(data?.data?.address?.address1);
                console.log(data?.data);
            })

        }

        loadData();
    }, []);
    const handleProvince = async (event) => {
        setProvince(event.target.value);
        const state = await getDistricts(event.target.value);
        const stateD = await getWards(state?.data[0].child_id);
        setDistrict(state?.data[0].child_id);
        setDistrictList(state?.data);
        setWardList(stateD?.data);

    };
    const handleDistrict = async (event) => {
        const state = await getWards(event.target.value);
        setDistrict(event.target.value);
        setWard(state?.data[0].child_id);
        setWardList(state?.data);
    }
    const handleWard = (event) => {
        setWard(event.target.value);
    }
    const saveAddress = async () => {
        const address = {
            address1: userAddress,
            provinceCode: province,
            districtCode: district,
            wardCode: ward
        }
        const shopSave = await updateAddressUser(rsUser?.id, address);
        setUserAddress(shopSave?.data?.address?.address1);
        setWard(shopSave?.data.address?.wardCode);
        setDistrict(shopSave?.data?.address?.districtCode);
        setProvince(shopSave?.data?.address?.provinceCode);
    }
    const handleChange = (event) => {
        const {
            target: {value},
        } = event;
        setPersonName(
            typeof value === 'string' ? value.split(',') : value,
        );
    };
    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };
    const theme = createTheme({
        palette: {
            primary: {
                // Purple and green play nicely together.
                main: '#ff5588 ',
            },
            secondary: {
                // This is green.A700 as hex.
                main: '#e3f2fd',
            },
        },
    });
    return (
        <div className={cx('wrapper')}>
            <Container>
                <div className={cx('address')}>
                    <div className={cx('myAddress')}>
                        <div>Địa chỉ của tôi</div>

                    </div>

                    <div className={cx('itemAddress')}>
                        <Grid container sx={{marginBottom: '12px'}} spacing={2} direction={"row"}
                              justifyContent={"flex-start"}>
                            <Grid container item xs={12} md={1.5}>
                                <FormControl>
                                    <NativeSelect
                                        labelId="multiple-select-label"
                                        id="multiple-select"
                                        value={province}
                                        onChange={handleProvince}
                                        inputProps={{
                                            name: 'values',
                                            id: 'values',
                                        }}
                                    >{provinceList?.map(({child_id, name}) => (
                                        <option value={child_id}>{name}</option>
                                    ))}

                                    </NativeSelect>
                                </FormControl>
                            </Grid>
                            <Grid item xs={12} md={1.5}>
                                <FormControl>
                                    <NativeSelect
                                        labelId="multiple-select-label"
                                        id="multiple-select"
                                        value={district}
                                        onChange={handleDistrict}
                                        inputProps={{
                                            name: 'values',
                                            id: 'values',
                                        }}
                                    >{districtList?.map(({child_id, name}) => (
                                        <option value={child_id}>{name}</option>
                                    ))}

                                    </NativeSelect>
                                </FormControl>
                            </Grid>
                            <Grid item xs={12} md={1.5}>
                                <FormControl>
                                    <NativeSelect
                                        labelId="multiple-select-label"
                                        id="multiple-select"
                                        value={ward}
                                        onChange={handleWard}
                                        inputProps={{
                                            name: 'values',
                                            id: 'values',
                                        }}
                                    >{wardList?.map(({child_id, name}) => (
                                        <option value={child_id}>{name}</option>
                                    ))}

                                    </NativeSelect>
                                </FormControl>
                            </Grid>
                        </Grid>
                        <Grid container sx={{marginBottom: '12px'}} spacing={2} direction={"row"}
                              justifyContent={"flex-start"}>

                            <Grid container item xs={12} md={4}>
                                <TextField
                                    style={{
                                        width: "100%",
                                        marginLeft: "10%"
                                    }}
                                    id="filled-multiline-static"
                                    label="Địa chỉ chi tiết"
                                    multiline
                                    rows={2}
                                    value={userAddress}
                                    onChange={(event) =>
                                        setUserAddress(event.target.value)}
                                    variant="filled"
                                />
                            </Grid>
                        </Grid>
                        <Grid container sx={{marginBottom: '12px'}} spacing={2} direction={"row"}
                              justifyContent={"flex-start"}>
                            <Grid container item xs={12} md={1.5}>

                            </Grid>
                            <Grid item xs={12} md={6}>
                                <button onClick={saveAddress} style={{
                                    marginTop: "20px",
                                    width: '120px'
                                }}
                                        className='btn-add'>Lưu
                                </button>
                            </Grid>
                        </Grid>


                    </div>
                </div>
            </Container>
        </div>
    );
}

export default InformationAddress;
