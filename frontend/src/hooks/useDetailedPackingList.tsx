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

    const getUpdatedDetails = (idOfUpdatedList: string, editedPackingList: Omit<PackingList, "id">) => {
        const updatedPackingList: PackingList = {
            id: idOfUpdatedList,
            destination : editedPackingList.destination,
            dateOfArrival : editedPackingList.dateOfArrival
        }
        setDetailedPackingList(updatedPackingList)
    }

    return {detailedPackingList, getDetailedPackingListById, getUpdatedDetails}
}