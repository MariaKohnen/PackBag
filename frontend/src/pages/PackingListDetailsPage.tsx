import useDetailedPackingList from "../hooks/useDetailedPackingList";
import React, {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import "./PackingListDetailsPage.css";
import {PackingList} from "../model/PackingList";
import {IconContext} from "react-icons";
import PackingListEditMode from "../components/packingListDetails/PackingListEditMode";
import PackingListDetailMode from "../components/packingListDetails/PackingListDetailMode";

type PackingListDetailsPageProps = {
    updatePackingList: (id: string, editedPackingList: Omit<PackingList, "id">) => Promise<PackingList | void>
}

export default function PackingListDetailsPage({updatePackingList}: PackingListDetailsPageProps) {

    const {detailedPackingList, getDetailedPackingListById, setDetailedPackingList} = useDetailedPackingList()
    const {id} = useParams()
    const [showsDetails, setShowsDetails] = useState<boolean>(false);

    const updateListAndGetNewDetails = (idOfEditedList: string, editedPackingList: Omit<PackingList, "id">) => {
        updatePackingList(idOfEditedList, editedPackingList)
            .then((response) => response && setDetailedPackingList(response))
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
                                id={id}
                                detailedPackingList={detailedPackingList}
                                setShowsDetails={setShowsDetails}
                                updateListAndGetNewDetails={updateListAndGetNewDetails}/>
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