import {useEffect, useState} from "react";
import {PackingList} from "../model/PackingList";
import {toast} from "react-toastify";
import {getAllPackingLists, postPackingList} from "../service/api-service";


export default function usePackingLists() {
    const [packingLists, setPackingLists] = useState<PackingList[]>([]);

    useEffect(() => {
        getAllPackingLists()
            .then((lists) => setPackingLists(lists))
            .catch((exception) => toast.error(exception + "Connection failed. Please try again."));
    }, []);

    const addPackingList = (newPackingList : Omit<PackingList, "id" | "dateOfArrival">) => {
        postPackingList(newPackingList)
            .then(addedPackingList => setPackingLists([...packingLists, addedPackingList]))
            .then(() => {toast.success("PackingList was added")})
            .catch((exception) => toast.error(exception + "Connection failed! Please try again later."))
    }

    return {packingLists, addPackingList}
}