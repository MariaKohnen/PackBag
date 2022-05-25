import {PackingList} from "../../model/PackingList";

type EditDestinationProps = {
    detailedPackingList : PackingList
    newDestination : string
    setNewDestination: (newDestination : string) => void
}

export default function EditDestination({detailedPackingList, newDestination, setNewDestination}: EditDestinationProps) {
    return (
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
    )
}