import {useContext, useEffect, useState} from "react";
import {PackingList} from "../model/PackingList";
import {toast} from "react-toastify";
import {getAllPackingLists, postPackingList, putPackingList, deletePackingListById, addItemAndUpdateList, deletePackingItemById, updateItemOfList} from "../service/api-service";
import {PackingItem} from "../model/PackingItem";
import {AuthContext} from "../context/AuthProvider";

export default function usePackingLists() {
    const [packingLists, setPackingLists] = useState<PackingList[]>([])
    const {token} = useContext(AuthContext)

    useEffect(() => {
        getAllPackingLists(token)
            .then((lists) => setPackingLists(lists))
            .catch((exception) => token && toast.error(exception + "Connection failed. Please try again."))
    }, [token])

    const addPackingList = (newPackingList: Omit<PackingList, "id" | "dateOfArrival">) => {
        postPackingList(newPackingList, token)
            .then(addedPackingList => setPackingLists([...packingLists, addedPackingList]))
            .catch(exception => toast.error(exception + "Connection failed! Please try again later."))
    }

    const updatePackingList = (id: string, editedPackingList: Omit<PackingList, "id" | "color">) => {
        return putPackingList(id, editedPackingList, token)
            .then(updatedPackingList => {
                setPackingLists(packingLists.map(list => list.id === updatedPackingList.id
                    ? updatedPackingList
                    : list))
                return updatedPackingList
            })
            .catch(() => toast.error("Connection failed! Please retry later"))
    }

    const deletePackingList = (id: string) => {
        return deletePackingListById(id, token)
            .then(() => setPackingLists(packingLists.filter(packingList => packingList.id !== id)))
            .catch(() => toast.error("Error while removing packing list. Please try again"))
    }

    const addNewItem = (id: string, newPackingItem: Omit<PackingItem, "id">) => {
        return addItemAndUpdateList(id, newPackingItem, token)
            .then(packingListWithItem => {
                setPackingLists(packingLists.map(list => list.id === packingListWithItem.id
                    ? packingListWithItem
                    : list))
                return packingListWithItem
            })
            .catch(() => toast.error("Connection failed! Please retry later"))
    }

    const updatePackingItem = (id: string, itemId: string, updatedPackingItem: Omit<PackingItem, "id">) => {
        return updateItemOfList(id, itemId, updatedPackingItem, token)
            .then(packingListWithUpdatedItem => {
                setPackingLists(packingLists.map(list => list.id === packingListWithUpdatedItem.id
                    ? packingListWithUpdatedItem
                    : list))
                return packingListWithUpdatedItem
            })
            .catch(() => toast.error("Connection failed! Please retry later"))
    }

    const deletePackingItem = (id: string, removeItemId: string) => {
        return deletePackingItemById(id, removeItemId, token)
            .then(packingListWithoutItem => {
                setPackingLists(packingLists.map(list => list.id === packingListWithoutItem.id
                    ? packingListWithoutItem
                    : list))
                return packingListWithoutItem
            })
            .catch(() => toast.error("Error while removing item from packing list. Please try again."))
    }

return {packingLists, addPackingList, updatePackingList, deletePackingList, addNewItem, deletePackingItem, updatePackingItem}
}
