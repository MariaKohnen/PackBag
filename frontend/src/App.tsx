import React from 'react';
import './App.css';
import {HashRouter, Route, Routes} from "react-router-dom";
import PackingListOverviewPage from "./pages/PackingListOverviewPage";
import PackingListDetailsPage from "./pages/PackingListDetailsPage";

export default function App() {
    return (
        <HashRouter>
            <div className="App">
                <Routes>
                    <Route path="/"
                           element={<PackingListOverviewPage/>}/>
                    <Route path={`/packinglist/:id`}
                           element={<PackingListDetailsPage />}/>
                </Routes>
            </div>
        </HashRouter>
    )
}