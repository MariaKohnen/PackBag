import {PackingList} from "../model/PackingList";

type packingListTileProps = {
    packingList : PackingList;
}

export default function packingListTile({packingList} : packingListTileProps) {
    return (
        <div>
            <p>{packingList.name}</p>
        </div>
    )
}