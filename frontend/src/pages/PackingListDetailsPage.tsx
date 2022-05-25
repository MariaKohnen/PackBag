import useDetailedPackingList from "../hooks/useDetailedPackingList";
import React, {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import "./PackingListDetailsPage.css";
import PackingItemOverview from "../components/PackingItemOverview";
import {PackingList} from "../model/PackingList";
import {IconContext} from "react-icons";
import {AiOutlineLine, AiOutlinePlus} from "react-icons/ai";
import EditPackingList from "../components/EditPackingList";

type PackingListDetailsPageProps = {
    updatePackingList: (id: string, editedPackingList: Omit<PackingList, "id">) => void
}

export default function PackingListDetailsPage({updatePackingList}: PackingListDetailsPageProps) {

    const {detailedPackingList, getDetailedPackingListById} = useDetailedPackingList()
    const {id} = useParams()
    const [showsDetails, setShowsDetails] = useState<boolean>(false);

    useEffect(() => {
        if (id) {
            getDetailedPackingListById(id)
        }//eslint-disable-next-line
    }, [updatePackingList])

    return (
        <IconContext.Provider value={{color: '#eaeadf'}}>
            <div className="details-page">
                <div className="header-details-page">
                    {detailedPackingList && <p>{detailedPackingList.destination}</p>}
                    {showsDetails ?
                        <div className="edit-list-button">
                            <button onClick={() => setShowsDetails(false)}><AiOutlineLine/></button>
                        </div>
                        :
                        <div className="edit-list-button">
                            <button onClick={() => setShowsDetails(true)}><AiOutlinePlus/></button>
                        </div>
                    }
                </div>
                {showsDetails ?
                    <div>
                        {detailedPackingList && id && <EditPackingList
                            updatePackingList={updatePackingList}
                            detailedPackingList={detailedPackingList}
                            id={id}
                            setShowsDetails={setShowsDetails}/>}
                    </div>
                    :
                    <div className="item-overview">
                        {detailedPackingList && <PackingItemOverview
                            actualPackingList={detailedPackingList}/>}
                    </div>
                }
            </div>
        </IconContext.Provider>
    )
}