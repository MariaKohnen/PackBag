import {FormEvent, useState} from "react";
import { toast } from "react-toastify";
import {PackingList} from "../model/PackingList";

type AddPackingListProp = {
    addPackingList : (newPackingList : Omit<PackingList, "id">) => void
}

export default function AddNewPackingList({addPackingList} : AddPackingListProp) {
    const [newName, setNewName] = useState('')

    const getOnSubmit = (event : FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        if (!newName) {
            toast.error("Destination ist required.")
            return
        }
        const newPackingList : Omit<PackingList, "id"> = {
            name : newName,
            dateOfArrival : "2022-05-21"
        }
        addPackingList(newPackingList)
        setNewName('')
        }

    return (
        <div>
            <form onSubmit={getOnSubmit}>
                <input type="name" min="2" value={newName} onChange={event => setNewName(event.target.value)}/>
                <input type="submit" value={"add item"}/>
            </form>

        </div>
    )
}