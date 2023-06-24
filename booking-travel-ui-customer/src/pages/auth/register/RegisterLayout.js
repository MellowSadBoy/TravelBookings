import React from 'react';

import LoginRegisterLayout from '~/layout/LoginRegisterLayout';
import Register from '~/pages/auth/register/Register';

function RegisterLayout(props) {
    return (
        <LoginRegisterLayout children={<Register/>}/>
    );
}

export default RegisterLayout;