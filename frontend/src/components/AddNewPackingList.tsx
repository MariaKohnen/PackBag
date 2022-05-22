import {FormEvent, useState} from "react";
import { toast } from "react-toastify";
import {PackingList} from "../model/PackingList";
import "./AddNewPackingList.css";

type AddPackingListProp = {
    addPackingList : (newPackingList : Omit<PackingList, "id" | "dateOfArrival">) => void
}

export default function AddNewPackingList({addPackingList} : AddPackingListProp) {
    const [newDestination, setNewDestination] = useState('')

    const getOnSubmit = (event : FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        if (!newDestination) {
            toast.error("Destination ist required.")
            return
        }
        const newPackingList : Omit<PackingList, "id" | "dateOfArrival"> = {
            destination : newDestination
        }
        addPackingList(newPackingList)
        setNewDestination('')
        }

    return (
        <div>
            <form className="add-packing-list" onSubmit={getOnSubmit}>
                <input className="text-field" type="destination" placeholder="where do you want to go next?" value={newDestination} onChange={event => setNewDestination(event.target.value)}/>
                <input className="button" type="submit" value={"add item"}/>
            </form>

        </div>
    )
}