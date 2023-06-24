import React, {createContext, useEffect, useState} from 'react';

import {getEnterprise} from "~/services/workspaces.sevices";

export const EnterpriseContext = createContext({});

function EnterpriseProvider(props) {
    const [enterprise, setEnterprise] = useState({});
    const [shouldUpdate, setShouldUpdate] = useState(false);

    async function loadE() {
        const data = await getEnterprise();
        setEnterprise(data?.data);

    }

    useEffect(() => {
        loadE()
    }, [shouldUpdate]);


    return (
        <EnterpriseContext.Provider value={{enterprise: enterprise, setShouldUpdate}}>
            {props.children}
        </EnterpriseContext.Provider>
    );
}

export default EnterpriseProvider;