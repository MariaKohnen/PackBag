import {PackingList} from "../model/PackingList";
import {FormEvent, useState} from "react";
import "./EditPackingList.css";

type EditPackingListProps = {
    detailedPackingList: PackingList
    updatePackingList: (id: string, editedPackingList: Omit<PackingList, "id">) => void
    id: string
    setShowsDetails: (status: boolean) => void
}

export default function EditPackingList({updatePackingList, detailedPackingList, id, setShowsDetails}: EditPackingListProps) {

    const [newDestination, setNewDestination] = useState<string>(detailedPackingList.destination)
    const [newDateOfArrival, setNewDateOfArrival] = useState<string>(detailedPackingList.dateOfArrival)

    const updateActualList = (event : FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        const updatedList: Omit<PackingList, "id"> = {
            destination : newDestination,
            dateOfArrival : newDateOfArrival
        }
        updatePackingList(id, updatedList)
        setShowsDetails(false)
    }

    return (
        <form className="edit-list-container"
              onSubmit={updateActualList}>
            <div className="to-change">
                <p>Where do you want to go?</p>
                <input
                    className="input-field"
                    type={"text"}
                    value={newDestination}
                    placeholder={detailedPackingList.destination}
                    onChange={event => setNewDestination(event.target.value)}
                />
            </div>
            <div className="to-change">
                <p>When are you planning to go?</p>
                <input
                    className="input-field"
                    type={"text"}
                    value={newDateOfArrival}
                    placeholder={"choose a date"}
                    onChange={event => setNewDateOfArrival(event.target.value)}
                />
            </div>
            <button type={"submit"}>save changes</button>
        </form>
)
}