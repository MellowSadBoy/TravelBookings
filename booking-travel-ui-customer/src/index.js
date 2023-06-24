import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';
import reportWebVitals from './reportWebVitals';
import GlobalStyles from "./components/GlobalStyles/GlobalStyles";
import UserProvider from "./config/provider/UserProvider";
import EnterpriseProvider from "~/config/provider/EnterpriseProvider";
import {ProSidebarProvider} from "react-pro-sidebar";

import('orange-css');
import('react-responsive')
const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    // <React.StrictMode>
    <GlobalStyles>
        <UserProvider>
            <ProSidebarProvider>
                <EnterpriseProvider>
                    <App/>
                </EnterpriseProvider>
            </ProSidebarProvider>
        </UserProvider>
    </GlobalStyles>
    // </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
