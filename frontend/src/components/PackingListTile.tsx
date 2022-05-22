import {PackingList} from "../model/PackingList";
import "./PackingListTile.css"
import {useNavigate} from "react-router-dom";

type packingListTileProps = {
    packingList: PackingList;
}

export default function PackingListTile({packingList}: packingListTileProps) {

    const navigate = useNavigate()

    return (
            <div className="packing-list-tile" onClick={() => navigate(`/packinglist/${packingList.id}`)}>
                <p>{packingList.destination}</p>
            </div>
    )
}