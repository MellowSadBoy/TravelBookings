import React from 'react';

import LoginRegisterLayout from '~/layout/LoginRegisterLayout';
import Login from '~/pages/auth/Login/Login';

function LoginLayout(props) {
    return (
        <LoginRegisterLayout children={<Login/>}/>
    );
}

export default LoginLayout;