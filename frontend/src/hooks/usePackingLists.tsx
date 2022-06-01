import {useEffect, useState} from "react";
import {PackingList} from "../model/PackingList";
import {toast} from "react-toastify";
import {
    getAllPackingLists,
    postPackingList,
    putPackingList,
    deletePackingListById,
    addItemAndUpdateList, deletePackingItemById
} from "../service/api-service";
import {PackingItem} from "../model/PackingItem";


export default function usePackingLists() {
    const [packingLists, setPackingLists] = useState<PackingList[]>([]);

    useEffect(() => {
        getAllPackingLists()
            .then((lists) => setPackingLists(lists))
            .catch((exception) => toast.error(exception + "Connection failed. Please try again."));
    }, []);

    const addPackingList = (newPackingList: Omit<PackingList, "id" | "dateOfArrival">) => {
        postPackingList(newPackingList)
            .then(addedPackingList => setPackingLists([...packingLists, addedPackingList]))
            .then(() => {
                toast.success("PackingList was added")
            })
            .catch((exception) => toast.error(exception + "Connection failed! Please try again later."))
    }

    const updatePackingList = (id: string, editedPackingList: Omit<PackingList, "id">) => {
        return putPackingList(id, editedPackingList)
            .then(updatedPackingList => {
                setPackingLists(packingLists.map(list => list.id === updatedPackingList.id
                    ? updatedPackingList
                    : list))
                toast.success("Packing list: " + updatedPackingList.name + " was updated")
                return updatedPackingList})
            .catch(() => toast.error("Connection failed! Please retry later"))
    }

    const deletePackingList = (id: string) => {
        return deletePackingListById(id)
            .then(() => setPackingLists(packingLists.filter(packingList => packingList.id !== id)))
            .catch(() => toast.error("Error while removing packing list. Please try again"))
    }

    const addNewItem = (id: string, newPackingItem: Omit<PackingItem, "id">) => {
        return addItemAndUpdateList(id, newPackingItem)
            .then(packingListWithItem => {
                setPackingLists(packingLists.map(list => list.id === packingListWithItem.id
                    ? packingListWithItem
                    : list))
                toast.success("Packing list: " + packingListWithItem.name + " was updated")
                return packingListWithItem})
            .catch(() => toast.error("Connection failed! Please retry later"))
    }

    const deletePackingItem = (id: string, removeItemId: string) => {
        return deletePackingItemById(id, removeItemId)
    }


return {packingLists, addPackingList, updatePackingList, deletePackingList, addNewItem}
}