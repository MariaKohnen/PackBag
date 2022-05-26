import React from "react";

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
        <div className="delete-alert">
            <button onClick={() => setPopUp(false)}>No</button>
            <button onClick={confirmDelete}>Yes</button>
        </div>
    )
}