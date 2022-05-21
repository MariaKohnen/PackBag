import {useEffect, useState} from "react";
import {PackingList} from "../model/PackingList";
import {toast} from "react-toastify";
import {getAllPackingLists, postPackingList} from "../service/api-service";


export default function usePackingLists() {
    const [packingLists, setPackingLists] = useState<PackingList[]>([]);

    useEffect(() => {
        getAllPackingLists()
            .then((lists) => setPackingLists(lists))
            .catch(() => toast.error("Connection failed. Please try again."));
    }, []);

    const addPackingList = (newPackingList : Omit<PackingList, "id"> ) => {
        postPackingList(newPackingList)
            .then(addedPackingList => setPackingLists([...packingLists, addedPackingList]))
            .then(() => {toast.success("PackingList was added")})
            .catch(() => toast.error("Connection failed! Please try again later."))
    }

    return {packingLists, addPackingList}
}