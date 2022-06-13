import {GrCircleAlert, GrRadial, GrStatusGood} from "react-icons/gr";
import React from "react";

export const StatusData = [
        {
            id: 1,
            value: "Open",
            icon: <GrRadial />
        },
        {
            id: 2,
            value: "To be done",
            icon: <GrCircleAlert />
        },
        {
            id: 3,
            value: "Packed",
            icon: <GrStatusGood />
        },
    ]
