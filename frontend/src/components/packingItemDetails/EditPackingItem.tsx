import {FormEvent, useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import {PackingItem} from "../../model/PackingItem";
import {toast} from "react-toastify";
import "./styling/EditPackingItem.css";
import useDetailedPackingItem from "../../hooks/useDetailedPackingItem";
import Dropdown from "./Dropdown";
import {StatusData} from "../../data/StatusData";

type EditPackingItemProps = {
    updateItemAndGetUpdatedList: (id: string, itemId: string, updatedPackingItem: Omit<PackingItem, "id">) => void
    id: string
}

export default function EditPackingItem({updateItemAndGetUpdatedList, id}: EditPackingItemProps) {

    const {detailedPackingItem, getDetailedPackingItemById} = useDetailedPackingItem()

    const {itemId} = useParams()
    const navigate = useNavigate()
    const [newName, setNewName] = useState<string>('')
    const [newStatus, setNewStatus] = useState<{ id: number, value: string }>();
    const [buttonText, setButtonText] = useState<string>("go back")

    const handleClick  = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        console.log(newName)
        if (!newName.trim()) {
            toast.error("The item was not updated, the name was not given!")
            return
        }
        if (detailedPackingItem) {
            const editedItemDto: Omit<PackingItem, "id"> = {
            name: newName,
            status : getStatusOfItem(detailedPackingItem)
        }
            itemId && updateItemAndGetUpdatedList(id, itemId, editedItemDto)
            navigate(-1)}}

    const getStatusOfItem = (packingList: PackingItem): string => {
         return newStatus ?
            newStatus.value :
            packingList.status
    }

    useEffect( () => {
        detailedPackingItem?
            setNewName(detailedPackingItem.name)
            :setNewName('')
    }, [detailedPackingItem])

    useEffect(() => {
        if (itemId) {
            getDetailedPackingItemById(id, itemId)
        }//eslint-disable-next-line
    }, [])

    return (
        <form className="edit-item-container" onSubmit={handleClick}>
            <p>Change name of the given item:</p>
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
            <Dropdown status={StatusData} newStatus={newStatus} setNewStatus={setNewStatus}/>
        <button type={"submit"}>{buttonText}</button>
        </form>
    )
}