import {FormEvent, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import {PackingItem} from "../../model/PackingItem";
import {toast} from "react-toastify";
import "./styling/EditPackingItem.css";

type EditPackingItemProps = {
    updateItemAndGetUpdatedList: (id: string, itemId: string, updatedPackingItem: Omit<PackingItem, "id">) => void
    id: string
}

export default function EditPackingItem({updateItemAndGetUpdatedList, id}: EditPackingItemProps) {

    const {itemId} = useParams()
    const navigate = useNavigate()
    const [newName, setNewName] = useState<string>("")
    const [buttonText, setButtonText] = useState<string>("go back")

    const handleClick  = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        if (!newName.trim()) {
            toast.error("Please enter a name or return!")
            navigate(-1)
            return
        }
        const editedItemDto: Omit<PackingItem, "id"> = {
            name: newName
        }
            itemId && updateItemAndGetUpdatedList(id, itemId, editedItemDto)
            navigate(-1)}

    return (
        <form className="edit-item-container" onSubmit={handleClick}>
            <input
                className="item-input-field"
                type={"text"}
                value={newName}
                placeholder="change name of item"
                onInput={() => setButtonText("confirm")}
                onChange={event => setNewName(event.target.value)}
            />
        <button type={"submit"}>{buttonText}</button>
        </form>
    )
}