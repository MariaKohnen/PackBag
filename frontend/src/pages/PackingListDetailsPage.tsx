import useDetailedPackingList from "../hooks/useDetailedPackingList";
import {useEffect} from "react";
import {useParams} from "react-router-dom";
import "./PackingListDetailsPage.css";
import PackingItemOverview from "../components/PackingItemOverview";

export default function PackingListDetailsPage() {

    const {detailedPackingList, getDetailedPackingListById} = useDetailedPackingList()
    const {id} = useParams()

    useEffect(() => {
        if(id) {
            getDetailedPackingListById(id)
        }//eslint-disable-next-line
    },[id])

    return (
        <div className="details-page">
            <div className="header-details-page">
            {detailedPackingList && <p>{detailedPackingList.destination}</p>}
            </div>
            <div>
                {detailedPackingList && <PackingItemOverview
                    actualPackingList={detailedPackingList}/>}
            </div>
        </div>
    )
}