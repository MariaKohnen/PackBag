import {FormEvent, useState} from "react";
import {toast} from "react-toastify";
import {PackingList} from "../model/PackingList";
import "./AddNewPackingList.css";
import {ColorData} from "../data/ColorData";

type AddPackingListProp = {
    addPackingList: (newPackingList: Omit<PackingList, "id" | "dateOfArrival">) => void
    lengthOfList: number
}

export default function AddNewPackingList({addPackingList, lengthOfList}: AddPackingListProp) {
    const [newDestination, setNewDestination] = useState("")

    const getOnSubmit = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        if (!newDestination.trim()) {
            toast.error("Destination is required.")
            setNewDestination("")
            return
        }
        const newPackingList: Omit<PackingList, "id" | "dateOfArrival"> = {
            destination: newDestination,
            color: getColorForList()
        }
        addPackingList(newPackingList)
        setNewDestination("")
    }

    const getColorForList = () : string => {
        return ColorData[lengthOfList % ColorData.length]
    }

    return (
        <div>
            <form className="add-packing-list" onSubmit={getOnSubmit}>
                <input className="text-field"
                       type="destination"
                       placeholder="where do you want to go next?"
                       value={newDestination}
                       onChange={event => setNewDestination(event.target.value)}/>
                <input className="form-button"
                       type="submit"
                       value={"add destination"}/>
            </form>
        </div>
    )
}
