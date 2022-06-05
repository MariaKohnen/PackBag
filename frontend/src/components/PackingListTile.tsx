import {PackingList} from "../model/PackingList";
import "./PackingListTile.css"
import {useNavigate} from "react-router-dom";
import {AiOutlineLine} from "react-icons/ai";
import React, {MouseEventHandler, useState} from "react";
import {IconContext} from "react-icons";
import DeleteAlert from "./general/DeleteAlert";

type packingListTileProps = {
    packingList: PackingList;
    deletePackingList: (id: string) => void
}

export default function PackingListTile({packingList, deletePackingList}: packingListTileProps) {

    const navigate = useNavigate()
    const [popUp, setPopUp] = useState<boolean>(false)

    const handleDelete: MouseEventHandler<HTMLButtonElement> = (event) => {
        event.stopPropagation();
        setPopUp(true)
    }

    return (
        <IconContext.Provider value={{color: '#eaeadf'}}>
            {popUp ?
                <DeleteAlert
                    id={packingList.id}
                    deletePackingList={deletePackingList}
                    setPopUp={setPopUp}/>
                :
                <div className="packing-list-tile" onClick={() =>
                    navigate(`/packinglist/${packingList.id}`)}>
                    <p>{packingList.destination}</p>
                        <button onClick={handleDelete}><AiOutlineLine/></button>
                </div>}
        </IconContext.Provider>
    )
}