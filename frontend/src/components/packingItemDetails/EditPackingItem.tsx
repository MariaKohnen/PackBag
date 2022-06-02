import {FormEvent, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import {PackingItem} from "../../model/PackingItem";
import {toast} from "react-toastify";

type EditPackingItemProps = {
    updateItemAndGetUpdatedList: (id: string, itemId: string, updatedPackingItem: Omit<PackingItem, "id">) => void
    id: string
}

export default function EditPackingItem({updateItemAndGetUpdatedList, id}: EditPackingItemProps) {

    const {itemId} = useParams()
    const navigate = useNavigate()
    const [newName, setNewName] = useState<string>("")

    const handleClick  = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        if (!newName) {
            toast.error("Please enter a name or return!")
            return
        }
        const editedItemDto: Omit<PackingItem, "id"> = {
            name: newName
        }
            itemId && updateItemAndGetUpdatedList(id, itemId, editedItemDto)
            navigate(-1)}

    return (
        <form onSubmit={handleClick}>
            <input
                className="input-field"
                type={"text"}
                value={newName}
                placeholder="change name of item"
                onChange={event => setNewName(event.target.value)}
            />
        <button type={"submit"}>save changes</button>
        </form>
    )
}