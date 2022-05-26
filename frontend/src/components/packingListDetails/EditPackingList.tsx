import {PackingList} from "../../model/PackingList";
import {FormEvent, useState} from "react";
import "./EditPackingList.css";
import EditDestination from "./EditDestination";
import EditDateOfArrival from "./EditDateOfArrival";

type EditPackingListProps = {
    id: string
    detailedPackingList: PackingList
    setShowsDetails: (status: boolean) => void
    updateListAndGetNewDetails: (id: string, editedPackingList: Omit<PackingList, "id">) => void
}

export default function EditPackingList({id, detailedPackingList, setShowsDetails, updateListAndGetNewDetails}: EditPackingListProps) {

    const [newDestination, setNewDestination] = useState<string>(detailedPackingList.destination)
    const [newDateOfArrival, setNewDateOfArrival] = useState<Date>(detailedPackingList.dateOfArrival)

    const updateActualList = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        const dateAsString = new Date(newDateOfArrival).toISOString().split('T')[0]
        const date = new Date(dateAsString)
        console.log("DateAsString: " + dateAsString)
        console.log("Date: " + date)
        const editedPackingList: Omit<PackingList, "id"> = {
            destination: newDestination,
            dateOfArrival: date
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
                setNewDestination={setNewDestination}/>
            <EditDateOfArrival
                newDateOfArrival={newDateOfArrival}
                setNewDateOfArrival={setNewDateOfArrival}/>
            <button type={"submit"}>save changes</button>
        </form>
    )
}