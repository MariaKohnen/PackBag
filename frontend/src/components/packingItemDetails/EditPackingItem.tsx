import {FormEvent, useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import {PackingItem} from "../../model/PackingItem";
import {toast} from "react-toastify";
import "./styling/EditPackingItem.css";
import useDetailedPackingItem from "../../hooks/useDetailedPackingItem";

type EditPackingItemProps = {
    updateItemAndGetUpdatedList: (id: string, itemId: string, updatedPackingItem: Omit<PackingItem, "id">) => void
    id: string
}

export default function EditPackingItem({updateItemAndGetUpdatedList, id}: EditPackingItemProps) {

    const {detailedPackingItem, getDetailedPackingItemById} = useDetailedPackingItem()

    const {itemId} = useParams()
    const navigate = useNavigate()
    const [newName, setNewName] = useState<string>("")
    const [buttonText, setButtonText] = useState<string>("go back")

    const handleClick  = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        if (!newName.trim()) {
            toast.error("The item was not updated, the name was not given!")
            navigate(-1)
            return
        }
        const editedItemDto: Omit<PackingItem, "id"> = {
            name: newName
        }
            itemId && updateItemAndGetUpdatedList(id, itemId, editedItemDto)
            navigate(-1)}

    useEffect(() => {
        if (itemId) {
            getDetailedPackingItemById(id, itemId)
        }//eslint-disable-next-line
    }, [])

    return (
        <form className="edit-item-container" onSubmit={handleClick}>
            <input
                className="item-input-field"
                type={"text"}
                value={newName}
                placeholder={detailedPackingItem && detailedPackingItem.name}
                onChange={event => {setNewName(event.target.value)
                    event.target.value.trim()?
                        setButtonText("confirm")
                        :setButtonText("go back")
                }}
            />
        <button type={"submit"}>{buttonText}</button>
        </form>
    )
}