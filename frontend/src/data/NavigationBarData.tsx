import React from 'react';
import {AiOutlineHome, AiOutlineLogout} from "react-icons/ai";

export const NavigationBarData = [
    {
        title: "Home",
        path: "/",
        icon: <AiOutlineHome />,
        cName: "nav-text"
    },
    {
        title: "Logout",
        path: "/hello",
        icon: <AiOutlineLogout />,
        cName: "nav-text"
    }
]
