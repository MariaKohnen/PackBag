import {FormEvent, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import {PackingItem} from "../../model/PackingItem";

type EditPackingItemProps = {
    updateItemAndGetUpdatedList: (id: string, itemId: string, updatedPackingItem: Omit<PackingItem, "id">) => void
    id: string
}

export default function EditPackingItem({updateItemAndGetUpdatedList, id}: EditPackingItemProps) {

    const {itemId} = useParams()
    const navigate = useNavigate()
    const [newName, setNewName] = useState<string>("new Name")

    const handleClick  = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        const editedItemDto: Omit<PackingItem, "id"> = {
            name: newName
        }
        itemId && updateItemAndGetUpdatedList(id, itemId, editedItemDto)
        navigate(-1)
    }

    return (
        <form onSubmit={handleClick}>
            <input
                className="input-field"
                type={"text"}
                value={newName}
                placeholder="nothing here"
                onChange={event => setNewName(event.target.value)}
            />
        <button type={"submit"}>save changes</button>
        </form>
    )
}