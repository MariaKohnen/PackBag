import {PackingList} from "../../model/PackingList";
import {FormEvent, useState} from "react";
import "./styling/EditPackingList.css";
import EditDestination from "./EditDestination";
import EditDateOfArrival from "./EditDateOfArrival";
import {toast} from "react-toastify";

type EditPackingListProps = {
    id: string
    detailedPackingList: PackingList
    setShowsDetails: (status: boolean) => void
    updateListAndGetNewDetails: (id: string, editedPackingList: Omit<PackingList, "id">) => void
}

export default function EditPackingList({id, detailedPackingList, setShowsDetails, updateListAndGetNewDetails}: EditPackingListProps) {

    const [newDestination, setNewDestination] = useState<string>(detailedPackingList.destination)
    const [newDateOfArrival, setNewDateOfArrival] = useState<Date>(detailedPackingList.dateOfArrival)
    const [buttonText, setButtonText] = useState<string>("go back")

    const updateActualList = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        if (!newDestination.trim()) {
            toast.error("Destination is required.")
            setNewDestination(detailedPackingList.destination)
            setButtonText("go back")
            return
        }
        const editedPackingList: Omit<PackingList, "id"> = {
            destination: newDestination,
            dateOfArrival: newDateOfArrival,
            packingItemList: detailedPackingList.packingItemList
        }
        updateListAndGetNewDetails(id, editedPackingList)
        setShowsDetails(false)
    }

    return (
        <form className="edit-list-container"
              onSubmit={updateActualList}>
            <EditDestination
                detailedPackingList={detailedPackingList}
                newDestination={newDestination}
                setNewDestination={setNewDestination}
                setButtonText={setButtonText}/>
            <EditDateOfArrival
                newDateOfArrival={newDateOfArrival}
                setNewDateOfArrival={setNewDateOfArrival}
                setButtonText={setButtonText}/>
            <button type={"submit"}>{buttonText}</button>
        </form>
    )
}