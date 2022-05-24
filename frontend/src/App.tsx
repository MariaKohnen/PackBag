import React from 'react';
import './App.css';
import {HashRouter, Route, Routes} from "react-router-dom";
import PackingListOverviewPage from "./pages/PackingListOverviewPage";
import PackingListDetailsPage from "./pages/PackingListDetailsPage";
import NavigationBar from "./components/NavigationBar";
import usePackingLists from "./hooks/usePackingLists";

export default function App() {

    const {packingLists, addPackingList, updatePackingList} = usePackingLists();

    return (
        <HashRouter>
            <div className="App">
                <Routes>
                    <Route path="/"
                           element={<PackingListOverviewPage
                           packingLists={packingLists}
                           addPackingList={addPackingList}/>}/>
                    <Route path={`/packinglist/:id`}
                           element={<PackingListDetailsPage
                           updatePackingList={updatePackingList}/>}/>
                </Routes>
                <NavigationBar />
            </div>
        </HashRouter>
    )
}