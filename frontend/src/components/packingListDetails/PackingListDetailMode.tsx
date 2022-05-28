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

    const formattedDate = new Date(detailedPackingList.dateOfArrival).toLocaleDateString();

    return (
        <div>
            <div className="list-details-header">
                <h2>{detailedPackingList.destination}</h2>
                <button onClick={() => setShowsDetails(true)}><AiOutlinePlus/></button>
                <p>{detailedPackingList.dateOfArrival && formattedDate}</p>
            </div>
            {detailedPackingList && detailedPackingList.packingItemList ?
                <div>
                    <PackingItemOverview
                        actualItemList={detailedPackingList.packingItemList}/>
                </div>
                : <div>No Items</div>
            }
        </div>
    )
}