import {PackingList} from "../model/PackingList";

type EditPackingListProps = {
    detailedPackingList : PackingList
    updatePackingList : (editedPackingList: Omit<PackingList, "id">) => void
}

export default function EditPackingList({updatePackingList, detailedPackingList} : EditPackingListProps) {
    return (
        <div>
            {detailedPackingList.destination}
        </div>
    )
}