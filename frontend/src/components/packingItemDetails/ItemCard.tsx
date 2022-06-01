import { IconContext } from "react-icons";
import {PackingItem} from "../../model/PackingItem";
import {BsCircle} from "react-icons/bs";
import "./styling/ItemCard.css";
import React from "react";

type ItemOverviewProps = {
    packingItem: PackingItem
    deleteItem: (id: string, itemId: string) => void
    id: string
}

export function ItemCard({packingItem, deleteItem, id}: ItemOverviewProps) {

    const confirmDelete = () => {
        deleteItem(id, packingItem.id)
    }

    return(
        <IconContext.Provider value={{color: '#d7a36f'}}>
        <div className="item-card">
            <BsCircle />
            <p>{packingItem.name}</p>
            <button onClick={confirmDelete}>Yes</button>
        </div>
        </IconContext.Provider>
    )
}