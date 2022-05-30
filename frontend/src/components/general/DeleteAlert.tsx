import React from "react";
import "./styling/DeleteAlert.css";

type DeleteAlertProps = {
    id: string
    deletePackingList: (id: string) => void
    setPopUp: (status: boolean) => void
}

export default function DeleteAlert({id, deletePackingList, setPopUp}: DeleteAlertProps) {

    const confirmDelete = () => {
        deletePackingList(id)
        setPopUp(false)
    }

    return (
        <div className="packing-list-tile">
            <div className="delete-alert">
                <h3>Do you really want to delete this list?</h3>
                <div className="alert-button">
                    <button onClick={() => setPopUp(false)}>No</button>
                    <button onClick={confirmDelete}>Yes</button>
                </div>
            </div>
        </div>
    )
}