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
                {detailedPackingList && id &&
                    <div className="header-details-page">
                        {showsDetails ?
                            <PackingListEditMode
                                updatePackingList={updatePackingList}
                                detailedPackingList={detailedPackingList}
                                id={id}
                                setShowsDetails={setShowsDetails}/>
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