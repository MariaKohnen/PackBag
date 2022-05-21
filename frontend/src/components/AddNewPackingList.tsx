import {FormEvent, useState} from "react";
import { toast } from "react-toastify";
import {PackingList} from "../model/PackingList";
import "./AddNewPackingList.css";

type AddPackingListProp = {
    addPackingList : (newPackingList : Omit<PackingList, "id" | "dateOfArrival">) => void
}

export default function AddNewPackingList({addPackingList} : AddPackingListProp) {
    const [newName, setNewName] = useState('')

    const getOnSubmit = (event : FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        if (!newName) {
            toast.error("Destination ist required.")
            return
        }
        const newPackingList : Omit<PackingList, "id" | "dateOfArrival"> = {
            name : newName
        }
        addPackingList(newPackingList)
        setNewName('')
        }

    return (
        <div>
            <form className="add-packing-list" onSubmit={getOnSubmit}>
                <input className="text-field" type="name" placeholder="where do you want to go next?" value={newName} onChange={event => setNewName(event.target.value)}/>
                <input className="button" type="submit" value={"add item"}/>
            </form>

        </div>
    )
}