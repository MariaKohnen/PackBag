import {AiOutlineLine} from "react-icons/ai";
import React from "react";
import EditPackingList from "./EditPackingList";
import {PackingList} from "../model/PackingList";
import "./PackingListEditMode.css";

type PackingListEditModeProps = {
    detailedPackingList: PackingList
    updatePackingList: (id: string, editedPackingList: Omit<PackingList, "id">) => void
    id: string
    setShowsDetails: (status: boolean) => void
}

export default function PackingListEditMode({updatePackingList, detailedPackingList, id, setShowsDetails}: PackingListEditModeProps) {
    return (
        <div>
            <div className="list-details-header">
                <h2>{detailedPackingList.destination}</h2>
                <button onClick={() => setShowsDetails(false)}><AiOutlineLine/></button>
            </div>
            <div>
                {detailedPackingList && id && <EditPackingList
                    updatePackingList={updatePackingList}
                    detailedPackingList={detailedPackingList}
                    id={id}
                    setShowsDetails={setShowsDetails}/>}
            </div>
        </div>
    )
}