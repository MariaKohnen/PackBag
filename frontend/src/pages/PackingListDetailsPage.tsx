import useDetailedPackingList from "../hooks/useDetailedPackingList";
import React, {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import "./PackingListDetailsPage.css";
import {PackingList} from "../model/PackingList";
import {IconContext} from "react-icons";
import PackingListEditMode from "../components/packingListDetails/PackingListEditMode";
import PackingListDetailMode from "../components/packingListDetails/PackingListDetailMode";
import {PackingItem} from "../model/PackingItem";

type PackingListDetailsPageProps = {
    updatePackingList: (id: string, editedPackingList: Omit<PackingList, "id" | "color">) => Promise<PackingList | void>
    addNewItem: (id: string, newPackingItem: Omit<PackingItem, "id">) => Promise<PackingList | void>
    deletePackingItem: (id: string, itemId: string) => Promise<PackingList>
    updatePackingItem: (id: string, itemId: string, updatedPackingItem: Omit<PackingItem, "id">) => Promise<PackingList>
}

export default function PackingListDetailsPage({updatePackingList, addNewItem, deletePackingItem, updatePackingItem}: PackingListDetailsPageProps) {

    const {detailedPackingList, getDetailedPackingListById, setDetailedPackingList} = useDetailedPackingList()
    const {id} = useParams()
    const [showsDetails, setShowsDetails] = useState<boolean>(false);

    const updateListAndGetNewDetails = (idOfEditedList: string, editedPackingList: Omit<PackingList, "id" | "color">) => {
        updatePackingList(idOfEditedList, editedPackingList)
            .then((response) => response && setDetailedPackingList(response))
    }

    const addItemToPackingList = (idOfList: string, newPackingItem : Omit<PackingItem, "id">) => {
        addNewItem(idOfList, newPackingItem)
            .then(response => response && setDetailedPackingList(response))
    }

    const updateItemAndGetUpdatedList = (listId: string, itemId: string, updatedPackingItem: Omit<PackingItem, "id">) => {
        updatePackingItem(listId, itemId, updatedPackingItem)
            .then(response => response && setDetailedPackingList(response))
    }

    const deleteItemGetUpdatedList = (listId: string, itemId: string) => {
        deletePackingItem(listId, itemId)
            .then(response => response && setDetailedPackingList(response))
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
                                setShowsDetails={setShowsDetails}
                                addItemToPackingList={addItemToPackingList}
                                deleteItem={deleteItemGetUpdatedList}
                                updateItemAndGetUpdatedList={updateItemAndGetUpdatedList}/>
                        }
                    </div>}
            </div>
        </IconContext.Provider>
    )
}
