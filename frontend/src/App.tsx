import React from 'react';
import './App.css';
import {HashRouter, Route, Routes} from "react-router-dom";
import PackingListOverviewPage from "./pages/PackingListOverviewPage";

function App() {
    return (
        <HashRouter>
            <div className="App">
                <Routes>
                    <Route path="/"
                           element={<PackingListOverviewPage/>}/>
                </Routes>
            </div>
        </HashRouter>
    )
}

export default App