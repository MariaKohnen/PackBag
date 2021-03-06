import React, {FormEvent, useState} from "react";
import {toast} from "react-toastify";
import {PackingItem} from "../../model/PackingItem";
import "./styling/AddItemToPackingList.css";
import {AiOutlinePlus} from "react-icons/ai";
import { IconContext } from "react-icons";

type AddItemToPackingListProps = {
    addItemToPackingList : (id: string, newPackingItem: Omit<PackingItem, "id">) => void
    id: string
}

export default function AddItemToPackingList({addItemToPackingList, id}: AddItemToPackingListProps) {

    const [newName, setNewName] = useState("")

    const getOnSubmit = (event : FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        if (!newName.trim()) {
            toast.error("Name of Item is required.")
            setNewName("")
            return
        }
        const newPackingItem : Omit<PackingItem, "id"> = {
            name : newName,
            status : "Open",
            category : "no category"
        }
        addItemToPackingList(id, newPackingItem)
        setNewName("")
    }

    return (
        <div>
            <IconContext.Provider value={{color: '#a87f4f'}}>
            <form className="add-packing-item" onSubmit={getOnSubmit}>
                <button className="button"
                        type="submit"><AiOutlinePlus/></button>
                <input className="text-field-item"
                       type="name"
                       placeholder="add a new item to your list"
                       value={newName}
                       onChange={event => setNewName(event.target.value)}/>
            </form>
            </IconContext.Provider>
        </div>
    )
}
