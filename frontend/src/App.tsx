import React from 'react';
import './App.css';
import {HashRouter} from "react-router-dom";
import PackingListOverviewPage from "./pages/PackingListOverviewPage";

function App() {
    return (
        <div className="App">
            <h1>Hello World!</h1>
            <HashRouter>
                <PackingListOverviewPage/>
            </HashRouter>
        </div>
    );
}

export default App;
