import {GrCircleAlert, GrRadial, GrStatusGood} from "react-icons/gr";
import React from "react";

export const StatusData =
    [
        {
            id: 1,
            value: "OPEN",
            icon: <GrRadial />
        },
        {
            id: 2,
            value: "TODO",
            icon: <GrCircleAlert />
        },
        {
            id: 3,
            value: "PACKED",
            icon: <GrStatusGood />
        },
    ];
