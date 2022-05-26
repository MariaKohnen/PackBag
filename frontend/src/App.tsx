import React from 'react';
import './App.css';
import {HashRouter, Route, Routes} from "react-router-dom";
import PackingListOverviewPage from "./pages/PackingListOverviewPage";
import PackingListDetailsPage from "./pages/PackingListDetailsPage";
import NavigationBar from "./components/general/NavigationBar";
import usePackingLists from "./hooks/usePackingLists";

export default function App() {

    const {packingLists, addPackingList, updatePackingList, deletePackingList} = usePackingLists();

    return (
        <HashRouter>
            <div className="App">
                <Routes>
                    <Route path="/"
                           element={<PackingListOverviewPage
                           packingLists={packingLists}
                           addPackingList={addPackingList}
                           deletePackingList={deletePackingList} />}/>
                    <Route path={`/packinglist/:id`}
                           element={<PackingListDetailsPage
                           updatePackingList={updatePackingList} />}/>
                </Routes>
                <NavigationBar />
            </div>
        </HashRouter>
    )
}