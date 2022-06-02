import React from 'react';
import './App.css';
import {HashRouter, Route, Routes} from "react-router-dom";
import PackingListOverviewPage from "./pages/PackingListOverviewPage";
import PackingListDetailsPage from "./pages/PackingListDetailsPage";
import NavigationBar from "./components/general/NavigationBar";
import usePackingLists from "./hooks/usePackingLists";
import {updateItemOfList} from "./service/api-service";

export default function App() {

    const {packingLists, addPackingList, updatePackingList, deletePackingList, addNewItem, deletePackingItem, updateItemOfList} = usePackingLists();

    return (
        <HashRouter>
            <div className="App">
                <Routes>
                    <Route path="/"
                           element={<PackingListOverviewPage
                           packingLists={packingLists}
                           addPackingList={addPackingList}
                           deletePackingList={deletePackingList} />}/>
                    <Route path={`/packinglist/:id/packingitems`}
                           element={<PackingListDetailsPage
                           updatePackingList={updatePackingList}
                           addNewItem={addNewItem}
                           deletePackingItem={deletePackingItem}
                           updatePackingItem={updateItemOfList}/>}/>
                </Routes>
                <NavigationBar />
            </div>
        </HashRouter>
    )
}