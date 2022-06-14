import {AiOutlineLine} from "react-icons/ai";
import React from "react";
import EditPackingList from "./EditPackingList";
import {PackingList} from "../../model/PackingList";

type PackingListEditModeProps = {
    id: string
    detailedPackingList: PackingList
    setShowsDetails: (status: boolean) => void
    updateListAndGetNewDetails : (id: string, editedPackingList: Omit<PackingList, "id" | "color">) => void
}

export default function PackingListEditMode({id, detailedPackingList, setShowsDetails, updateListAndGetNewDetails}: PackingListEditModeProps) {
    return (
        <div>
            <div className="list-details-header">
                <h2>{detailedPackingList.destination}</h2>
                <button onClick={() => setShowsDetails(false)}><AiOutlineLine/></button>
            </div>
            <div>
                {detailedPackingList && id && <EditPackingList
                    id={id}
                    detailedPackingList={detailedPackingList}
                    setShowsDetails={setShowsDetails}
                    updateListAndGetNewDetails={updateListAndGetNewDetails}/>}
            </div>
        </div>
    )
}
