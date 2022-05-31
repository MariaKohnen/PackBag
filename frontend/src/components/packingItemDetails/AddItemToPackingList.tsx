import {FormEvent, useState} from "react";
import {toast} from "react-toastify";
import {PackingItem} from "../../model/PackingItem";

type AddItemToPackingListProps = {
    addItemToPackingList : (id: string, newPackingItem: Omit<PackingItem, "id">) => void
    id: string
}


export default function AddItemToPackingList({addItemToPackingList, id}: AddItemToPackingListProps) {

    const [newName, setNewName] = useState<string> ("")

    const getOnSubmit = (event : FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        if (!newName) {
            toast.error("Name of Item is required.")
        }
        const newPackingItem : Omit<PackingItem, "id"> = {
            name : newName
        }
        addItemToPackingList(id, newPackingItem)
        setNewName("")
    }

    return (
        <div>
            <form className="add-packing-item" onSubmit={getOnSubmit}>
                <input className="text-field"
                       type="name"
                       placeholder="add a new item to your list?"
                       value={newName}
                       onChange={event => setNewName(event.target.value)}/>
                <input className="button"
                       type="submit"
                       value={"add item"}/>
            </form>
        </div>
    )
}