import {IconContext} from "react-icons";
import {PackingItem} from "../../model/PackingItem";
import {BsCircle} from "react-icons/bs";
import "./styling/ItemCard.css";
import React, {MouseEventHandler} from "react";
import {useNavigate} from "react-router-dom";
import {AiOutlineCloseCircle} from "react-icons/ai";

type ItemOverviewProps = {
    packingItem: PackingItem
    deleteItem: (id: string, itemId: string) => void
    id: string
}

export function ItemCard({packingItem, deleteItem, id}: ItemOverviewProps) {

    const navigate = useNavigate();

    const confirmDelete: MouseEventHandler<HTMLButtonElement> = (event) => {
        event.stopPropagation();
        deleteItem(id, packingItem.id)
    }

    const handleClick = () => {
        navigate(`/packinglist/${id}/packingitems/${packingItem.id}`)
    }

    return (
        <div className="item-card"
             onClick={handleClick}>
            <IconContext.Provider value={{color: '#d7a36f'}}>
                <button><BsCircle/></button>
            </IconContext.Provider>
            <p>{packingItem.name}</p>
            <IconContext.Provider value={{color: '#6a7a7a'}}>
                <button className="delete-button" onClick={confirmDelete}><AiOutlineCloseCircle /></button>
            </IconContext.Provider>
        </div>
    )
}