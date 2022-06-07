import {IconContext} from "react-icons";
import {PackingItem} from "../../model/PackingItem";
import "./styling/ItemCard.css";
import React, {MouseEventHandler} from "react";
import {useNavigate} from "react-router-dom";
import {AiOutlineCloseCircle} from "react-icons/ai";
import {StatusData} from "../../data/StatusData";
import {GrRadial} from "react-icons/gr";

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

    const getStatusButton = (actualPackingItem: PackingItem) => {
        const actualStatus = StatusData.find(status => status.value === actualPackingItem.status)
        return actualStatus ?
            actualStatus.icon
            : <GrRadial />
    }

    return (
        <div className="item-card"
             onClick={handleClick}>
            <IconContext.Provider value={{color: '#d7a36f'}}>
                <button>{getStatusButton(packingItem)}</button>
            </IconContext.Provider>
            <p>{packingItem.name}</p>
            <p>{packingItem.category}</p>
            <IconContext.Provider value={{color: '#6a7a7a'}}>
                <button className="delete-button" onClick={confirmDelete}><AiOutlineCloseCircle/></button>
            </IconContext.Provider>
        </div>
    )
}
