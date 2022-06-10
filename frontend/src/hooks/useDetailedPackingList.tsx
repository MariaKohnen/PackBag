import {useContext, useState} from "react";
import {PackingList} from "../model/PackingList";
import {toast} from "react-toastify";
import {getPackingListById} from "../service/api-service";
import {AuthContext} from "../context/AuthProvider";

export default function useDetailedPackingList() {

    const[detailedPackingList, setDetailedPackingList] = useState<PackingList>()
    const {token} = useContext(AuthContext)

    const getDetailedPackingListById = (id : string) => {
        getPackingListById(id, token)
            .then(response => setDetailedPackingList(response))
            .catch((error) => toast.error(error))
    }

    return {detailedPackingList, getDetailedPackingListById, setDetailedPackingList}
}