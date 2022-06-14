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
    updateItemAndGetUpdatedList: (id: string, itemId: string, updatedPackingItem: Omit<PackingItem, "id">) => void
    id: string
}

export function ItemCard({packingItem, deleteItem, updateItemAndGetUpdatedList, id}: ItemOverviewProps) {

    const navigate = useNavigate()

    const confirmDelete: MouseEventHandler<HTMLButtonElement> = (event) => {
        event.stopPropagation()
        deleteItem(id, packingItem.id)
    }

    const handleClick = () => {
        navigate(`/packinglist/${id}/packingitems/${packingItem.id}`)
    }

    const getStatusButton = (actualPackingItem: PackingItem) => {
        const actualStatus = StatusData.find(status => status.value === actualPackingItem.status)
        return actualStatus ?
            actualStatus.icon
            : <GrRadial/>
    }

    const handleOnClick = () => {
        const nextStatus = getNextStatus(packingItem.status)
        if (packingItem) {
            const editItemDto: Omit<PackingItem, "id"> = {
                name: packingItem.name,
                status: nextStatus,
                category: packingItem.category
            }
            updateItemAndGetUpdatedList(id, packingItem.id, editItemDto)
        }
    }

    const getNextStatus = (status: string) => {
        const actualIndex = StatusData.findIndex(obj => obj.value === status)
        const nextIndex = (actualIndex + 1) % StatusData.length
        return StatusData[nextIndex].value
    }

    return (
        <div className="item-card"
             onClick={handleClick}>
            <button title={packingItem.status} type={'submit'}
                    onClick={(event) => {
                        event.stopPropagation()
                        handleOnClick()
                    }}>
                {getStatusButton(packingItem)}
            </button>
            <p>{packingItem.name}</p>
            <IconContext.Provider value={{color: '#6a7a7a'}}>
                <button className="delete-button" onClick={confirmDelete}><AiOutlineCloseCircle/></button>
            </IconContext.Provider>
        </div>
    )
}
