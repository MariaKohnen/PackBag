import {PackingList} from "../../model/PackingList";

type EditDestinationProps = {
    detailedPackingList : PackingList
    newDestination : string
    setNewDestination: (newDestination : string) => void
    setButtonText: (buttonText: string) => void
}

export default function EditDestination({detailedPackingList, newDestination, setNewDestination, setButtonText}: EditDestinationProps) {
    return (
        <div className="to-change">
            <p>Where do you want to go?</p>
            <input
                className="input-field"
                type={"text"}
                value={newDestination}
                placeholder={detailedPackingList.destination}
                onChange={event => { setNewDestination(event.target.value)
                    event.target.value.trim()?
                        setButtonText("confirm")
                        :setButtonText("go back")
                }}
            />
        </div>
    )
}