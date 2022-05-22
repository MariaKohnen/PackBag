import useDetailedPackingList from "../hooks/useDetailedPackingList";
import {useEffect} from "react";
import {useParams} from "react-router-dom";

export default function PackingListDetailsPage() {

    const {detailedPackingList, getDetailedPackingListById} = useDetailedPackingList()
    const {id} = useParams()

    useEffect(() => {
        if(id) {
            getDetailedPackingListById(id)
        }
    },[id])

    return (
        <div>
            <h2>Details</h2>
            {detailedPackingList && <p>{detailedPackingList.destination}</p>}
        </div>
    )
}