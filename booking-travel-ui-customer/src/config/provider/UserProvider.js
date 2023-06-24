import React, {createContext, useEffect, useState} from 'react';

import {getUser} from "~/services/workspaces.sevices";

export const UserContext = createContext({});

function UserProvider(props) {
    const [customer, setCustomer] = useState({});
    const [shouldUpdate, setShouldUpdate] = useState(false);
    const id = localStorage.getItem("cs-id");
    async function loadData() {
        if (id !== null) {
            const data = await getUser(id);
            setCustomer(data?.data);
        }
    }
    useEffect(() => {
        loadData()
    }, [shouldUpdate]);
    return (
        <UserContext.Provider value={{ customer, setShouldUpdate}}>
            {props.children}
        </UserContext.Provider>
    );
}

export default UserProvider;