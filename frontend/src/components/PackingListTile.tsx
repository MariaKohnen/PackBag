import {PackingList} from "../model/PackingList";
import "./PackingListTile.css"

type packingListTileProps = {
    packingList : PackingList;
}

export default function packingListTile({packingList} : packingListTileProps) {
    return (
        <div className="packing-list-tile">
            <p>{packingList.destination}</p>
        </div>
    )
}