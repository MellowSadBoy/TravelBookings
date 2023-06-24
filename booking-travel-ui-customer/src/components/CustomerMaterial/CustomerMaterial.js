import {createTheme} from "@mui/material";

export const themeCustomer = createTheme({
    status: {
        danger: '#e53e3e',
    },
    breakpoints: {
        values: {
            xs: 0,
            sm: 600,
            md: 900,
            lg: 1200,
            xl: 1536,
        },
    },
    palette: {
        primary: {
            main: '#0064D2',
            darker: '#fff',
        },
        white:{
            main: '#fff',
            darker: ' #0064D2',
        },
        neutral: {
            main: '#64748B',
            contrastText: '#fff',
        },
        danger: {
            main: '#f44336',
        },
        edit:{
            main:'#ffc107'
        }
    },
});
