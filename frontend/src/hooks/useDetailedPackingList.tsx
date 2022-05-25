import {useState} from "react";
import {PackingList} from "../model/PackingList";
import {toast} from "react-toastify";
import {getPackingListById} from "../service/api-service";

export default function useDetailedPackingList() {

    const[detailedPackingList, setDetailedPackingList] = useState<PackingList>()

    const getDetailedPackingListById = (id : string) => {
        getPackingListById(id)
            .then(response => setDetailedPackingList(response))
            .catch((error) => toast.error(error))
    }

    const getUpdatedDetails = (id: string, editedPackingList: Omit<PackingList, "id">) => {
        const updatedList: PackingList = {
            id: id,
            destination : editedPackingList.destination,
            dateOfArrival : editedPackingList.dateOfArrival
        }
        setDetailedPackingList(updatedList)
    }



    return {detailedPackingList, setDetailedPackingList, getDetailedPackingListById, getUpdatedDetails}
}