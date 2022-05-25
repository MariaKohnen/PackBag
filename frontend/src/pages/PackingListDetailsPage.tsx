import useDetailedPackingList from "../hooks/useDetailedPackingList";
import React, {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import "./PackingListDetailsPage.css";
import {PackingList} from "../model/PackingList";
import {IconContext} from "react-icons";
import PackingListEditMode from "../components/PackingListEditMode";
import PackingListDetailMode from "../components/PackingListDetailMode";

type PackingListDetailsPageProps = {
    updatePackingList: (id: string, editedPackingList: Omit<PackingList, "id">) => void
}

export default function PackingListDetailsPage({updatePackingList}: PackingListDetailsPageProps) {

    const {detailedPackingList, getDetailedPackingListById, getUpdatedDetails} = useDetailedPackingList()
    const {id} = useParams()
    const [showsDetails, setShowsDetails] = useState<boolean>(false);

    const updateAndGetNewDetails = (idOfUpdatedList: string, editedPackingList: Omit<PackingList, "id">) => {
        updatePackingList(idOfUpdatedList, editedPackingList)
        getUpdatedDetails(idOfUpdatedList, editedPackingList)
    }

    useEffect(() => {
        if (id) {
            getDetailedPackingListById(id)
        }//eslint-disable-next-line
    }, [])



    return (
        <IconContext.Provider value={{color: '#eaeadf'}}>
            <div className="details-page">
                {detailedPackingList && id &&
                    <div className="header-details-page">
                        {showsDetails ?
                            <PackingListEditMode
                                detailedPackingList={detailedPackingList}
                                id={id}
                                setShowsDetails={setShowsDetails}
                                updateAndGetNewDetails={updateAndGetNewDetails}/>
                            :
                            <PackingListDetailMode
                                detailedPackingList={detailedPackingList}
                                setShowsDetails={setShowsDetails}/>
                        }
                    </div>}
            </div>
        </IconContext.Provider>
    )
}