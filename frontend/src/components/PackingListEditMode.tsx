import {AiOutlineLine} from "react-icons/ai";
import React from "react";
import EditPackingList from "./EditPackingList";
import {PackingList} from "../model/PackingList";
import "./PackingListEditMode.css";

type PackingListEditModeProps = {
    detailedPackingList: PackingList
    id: string
    setShowsDetails: (status: boolean) => void
    updateAndGetNewDetails : (id: string, editedPackingList: Omit<PackingList, "id">) => void
}

export default function PackingListEditMode({detailedPackingList, id, setShowsDetails, updateAndGetNewDetails}: PackingListEditModeProps) {
    return (
        <div>
            <div className="list-details-header">
                <h2>{detailedPackingList.destination}</h2>
                <button onClick={() => setShowsDetails(false)}><AiOutlineLine/></button>
            </div>
            <div>
                {detailedPackingList && id && <EditPackingList
                    updateAndGetNewDetails={updateAndGetNewDetails}
                    detailedPackingList={detailedPackingList}
                    id={id}
                    setShowsDetails={setShowsDetails}/>}
            </div>
        </div>
    )
}