import {AiOutlinePlus} from "react-icons/ai";
import React from "react";
import PackingItemOverview from "../PackingItemOverview";
import {PackingList} from "../../model/PackingList";
import "./PackingListDetailMode.css";

type PackingListDetailModeProps = {
    detailedPackingList: PackingList
    setShowsDetails: (status: boolean) => void
}

export default function PackingListDetailMode({detailedPackingList, setShowsDetails}: PackingListDetailModeProps) {
    return (
        <div>
            <div className="list-details-header">
                <h2>{detailedPackingList.destination}</h2>
                <button onClick={() => setShowsDetails(true)}><AiOutlinePlus/></button>
            </div>
            <div>
                {detailedPackingList && <PackingItemOverview
                    actualPackingList={detailedPackingList}/>}
            </div>
        </div>
    )
}