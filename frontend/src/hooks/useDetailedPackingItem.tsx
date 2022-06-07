import {useState} from "react";
import {toast} from "react-toastify";
import {PackingItem} from "../model/PackingItem";
import {getPackingItemById} from "../service/api-service";

export default function useDetailedPackingItem() {

    const[detailedPackingItem, setDetailedPackingItem] = useState<PackingItem>()

    const getDetailedPackingItemById = (id: string, itemId: string) => {
        getPackingItemById(id, itemId)
            .then(response => setDetailedPackingItem(response))
            .catch((error) => toast.error(error))
    }

    return {detailedPackingItem, getDetailedPackingItemById}
}
