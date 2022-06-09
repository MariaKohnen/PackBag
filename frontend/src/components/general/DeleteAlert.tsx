import React from "react";
import "./styling/DeleteAlert.css";

type DeleteAlertProps = {
    id: string
    deletePackingList: (id: string) => void
    setPopUp: (status: boolean) => void
    tileColor: string
}

export default function DeleteAlert({id, deletePackingList, setPopUp, tileColor}: DeleteAlertProps) {

    const confirmDelete = () => {
        deletePackingList(id)
        setPopUp(false)
    }

    return (
        <div className="packing-list-tile" style={{background: tileColor}}>
            <div className="delete-alert">
                <h3>Do you really want to delete this list?</h3>
                <div className="alert-buttons">
                    <button onClick={() => setPopUp(false)}>No</button>
                    <button onClick={confirmDelete}>Yes</button>
                </div>
            </div>
        </div>
    )
}
