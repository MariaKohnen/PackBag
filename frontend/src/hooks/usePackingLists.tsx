import {useEffect, useState} from "react";
import {PackingList} from "../model/PackingList";
import {toast} from "react-toastify";
import {getAllPackingLists} from "../service/api-service";


export default function usePackingLists() {
    const [packingLists, setPackingLists] = useState<PackingList[]>([]);

    useEffect(() => {
        getAllPackingLists()
            .then((lists) => setPackingLists(lists))
            .catch(() => toast.error("Connection failed. Please try again."));
    }, []);

    return {packingLists}
}