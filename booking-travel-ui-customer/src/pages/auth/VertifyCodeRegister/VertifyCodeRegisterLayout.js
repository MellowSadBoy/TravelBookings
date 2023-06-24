import React from 'react';

import LoginRegisterLayout from '~/layout/LoginRegisterLayout';
import VerifyCodeRegister from './VerifyCodeRegister';

function VertifyCodeRegisterLayout(props) {
    return (
        <LoginRegisterLayout children={<VerifyCodeRegister/>}/>
    );
}

export default VertifyCodeRegisterLayout;