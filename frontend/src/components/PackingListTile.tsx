import {PackingList} from "../model/PackingList";
import "./PackingListTile.css"
import {useNavigate} from "react-router-dom";
import React, {MouseEventHandler, useState} from "react";
import {IconContext} from "react-icons";
import DeleteAlert from "./general/DeleteAlert";
import {AiOutlineCloseCircle} from "react-icons/ai";

type packingListTileProps = {
    packingList: PackingList;
    deletePackingList: (id: string) => void
    tileColor: string
}

export default function PackingListTile({packingList, deletePackingList, tileColor}: packingListTileProps) {

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
                    tileColor={tileColor}
                    deletePackingList={deletePackingList}
                    setPopUp={setPopUp}/>
                :
                <div className="packing-list-tile" style={{background: tileColor}} onClick={() =>
                    navigate(`/packinglist/${packingList.id}`)}>
                    <p>{packingList.destination}</p>
                        <button onClick={handleDelete}><AiOutlineCloseCircle /></button>
                </div>}
        </IconContext.Provider>
    )
}