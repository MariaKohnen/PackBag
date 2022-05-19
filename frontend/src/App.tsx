import React from 'react';
import './App.css';
import {HashRouter, Route, Routes} from "react-router-dom";
import PackingListOverviewPage from "./pages/PackingListOverviewPage";

function App() {
    return (
        <HashRouter>
            <div className="App">
                <h1>Hello World!</h1>
                <Routes>
                    <Route path="/"
                           element={<PackingListOverviewPage/>}/>
                </Routes>
            </div>
        </HashRouter>
    )
}

export default App