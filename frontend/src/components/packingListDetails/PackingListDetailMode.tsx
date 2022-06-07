import {AiOutlinePlus} from "react-icons/ai";
import React from "react";
import PackingItemOverview from "../packingItemDetails/PackingItemOverview";
import {PackingList} from "../../model/PackingList";
import "./styling/PackingListDetailMode.css";
import {PackingItem} from "../../model/PackingItem";
import ReturnButton from "../general/ReturnButton";

type PackingListDetailModeProps = {
    detailedPackingList: PackingList
    setShowsDetails: (status: boolean) => void
    addItemToPackingList: (id: string, newPackingItem: Omit<PackingItem, "id">) => void
    deleteItem: (id: string, itemId: string) => void
    updateItemAndGetUpdatedList: (id: string, itemId: string, updatedPackingItem: Omit<PackingItem, "id">) => void
}

export default function PackingListDetailMode({detailedPackingList, setShowsDetails, addItemToPackingList, deleteItem, updateItemAndGetUpdatedList}: PackingListDetailModeProps) {

    const formattedDate = new Date(detailedPackingList.dateOfArrival).toLocaleDateString();

    return (
        <div>
            <ReturnButton />
            <div className="list-details-header">
                <h2>{detailedPackingList.destination}</h2>
                <button onClick={() => setShowsDetails(true)}><AiOutlinePlus/></button>
                <p>{detailedPackingList.dateOfArrival && formattedDate}</p>
            </div>
            {detailedPackingList &&
                    <PackingItemOverview
                        actualItemList={detailedPackingList.packingItemList}
                        addItemToPackingList={addItemToPackingList}
                        id={detailedPackingList.id}
                        deleteItem={deleteItem}
                        updateItemAndGetUpdatedList={updateItemAndGetUpdatedList}
                    />
            }
        </div>
    )
}