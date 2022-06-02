import {IconContext} from "react-icons";
import {PackingItem} from "../../model/PackingItem";
import {BsCircle, BsDashCircle} from "react-icons/bs";
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

    return (
        <div className="item-card">
            <IconContext.Provider value={{color: '#d7a36f'}}>
               <button><BsCircle/></button>
            </IconContext.Provider>
            <p>{packingItem.name}</p>
            <IconContext.Provider value={{color: '#6a7a7a'}}>
                <button onClick={confirmDelete}><BsDashCircle/></button>
            </IconContext.Provider>
        </div>

    )
}