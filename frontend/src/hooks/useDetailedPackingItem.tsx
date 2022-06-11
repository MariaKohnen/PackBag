import {useContext, useState} from "react";
import {toast} from "react-toastify";
import {PackingItem} from "../model/PackingItem";
import {getPackingItemById} from "../service/api-service";
import {AuthContext} from "../context/AuthProvider";

export default function useDetailedPackingItem() {

    const[detailedPackingItem, setDetailedPackingItem] = useState<PackingItem>()
    const {token} = useContext(AuthContext)

    const getDetailedPackingItemById = (id: string, itemId: string) => {
        getPackingItemById(id, itemId, token)
            .then(response => setDetailedPackingItem(response))
            .catch((error) => toast.error(error))
    }

    return {detailedPackingItem, getDetailedPackingItemById}
}
